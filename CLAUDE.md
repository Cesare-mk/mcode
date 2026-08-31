# CLAUDE.md

本仓库的 AI 协作记忆与改动守则，写给在本仓库上写代码的 agent。

三层文档分工，不要把内容放错层：

- `CLAUDE.md`（本文件）：协作规则——构建/验证命令、架构地图、行为约束、改动联动清单
- `README.md`：项目介绍——产品定位、功能、安装运行、配置、命令，面向使用者
- `docs/agents-reference.md`：行为细节——配置读取顺序、各模块详细行为、文件说明，深入某模块前查阅

事实优先级：代码实际行为 > 本文件 > `README.md` > `docs/agents-reference.md`。

另：`MCODE.md` 是 MCode 这个产品自身的项目记忆约定（运行时注入 system prompt），与本文件职责不同，不要混淆。

## 项目快照

- MCode：Java 17 + Maven 的 Agent CLI，对标 Claude Code；已交付 23 期演进，细节见 `README.md` 与 `docs/phase-*.md`
- 未交付（不要当成已有，也不要暗示已支持）：容器/VM 沙箱、MCP OAuth/sampling、MCP server 自动重启
- Banner 版本 `v16.1.0` 与 Maven 产物 `mcode-1.0-SNAPSHOT.jar` 版本号不一致是正常状态，不要"修复"

## 构建与验证

```bash
mvn clean package                          # 默认跳过测试，产出可手工验收 jar
mvn test -Pquick                           # 日常回归（跳过外部进程/网络/命令超时类慢测试）
mvn test -Pphase16-smoke                   # 终端 / TUI / inline renderer 冒烟
mvn test -Dtest=XxxTest -DskipTests=false  # 针对性；-DskipTests=false 全量
```

- 运行需要 `.env`（从 `.env.example` 复制）里至少一个 provider API Key；`grep_code` 优先本机 ripgrep，缺失自动回退
- 按改动范围选回归面：

| 场景 | 命令 |
|------|------|
| 代码搜索工具 | `mvn test -Dtest=ToolRegistryTest,CodeSearchGoldenSetTest,ApprovalPolicyTest` |
| 命令解析 | `mvn test -Dtest=CliCommandParserTest,PlanReviewInputParserTest,MainInputNormalizationTest` |
| DAG / Plan | `mvn test -Dtest=ExecutionPlanTest` |
| Multi-Agent | `mvn test -Dtest=AgentRoleTest,AgentMessageTest,AgentOrchestratorTest` |
| RAG | `mvn test -Dtest=CodeChunkerTest,CodeAnalyzerTest,VectorStoreTest,CodeIndexTest` |
| TUI / 终端 | `mvn test -Pphase16-smoke` |

## 架构地图

三条主执行路径，共享 `ToolRegistry` / `MemoryManager` / `SnapshotService`，不为单一模式造孤立能力：

| 路径 | 入口 | 触发 |
|------|------|------|
| ReAct（默认） | `agent/Agent.java` | 普通输入 |
| Plan-and-Execute | `agent/PlanExecuteAgent.java` | `/plan` |
| Multi-Agent | `agent/AgentOrchestrator.java` + `agent/SubAgent.java` | `/team` |

包结构（`src/main/java/com/mcode/`）：

```
agent/      三条执行路径 + SubAgent 编排
cli/        Main 入口、CliCommandParser、补全/高亮/历史
llm/        LlmClient 接口 + 6 个 provider 客户端 + LlmClientFactory
memory/     MemoryManager 门面 + 短期/长期记忆 + 压缩
context/    上下文模式（short/balanced/long）与预算
plan/       Planner、ExecutionPlan（DAG）、Task
tool/       ToolRegistry：内置 11 工具 + MCP 动态工具 mcp__{server}__{tool}
mcp/        McpServerManager + transport/ + resources/ + mention/
hitl/       HitlToolRegistry 审批拦截层
policy/     PathGuard / CommandGuard / AuditLog
rag/        CodeIndex、CodeRetriever、VectorStore、CodeChunker
prompt/     PromptAssembler 分层 system prompt 组装
skill/      SkillRegistry + SkillContextBuffer
render/     Renderer 接口：InlineRenderer（默认）/ lanterna / plain
snapshot/   SideGitManager（JGit side-history 快照与回滚）
runtime/    api/（RuntimeApiServer）+ task/（DurableTaskManager）
browser/    BrowserSession、BrowserGuard、SensitivePagePolicy
web/        SearchProvider、WebFetcher、NetworkPolicy
wechat/     iLink client 与非交互策略
lsp/ image/ LSP 诊断注入、图片引用解析
```

按任务进模块：

| 任务 | 先看 |
|------|------|
| CLI 命令 | `cli/Main.java` + `cli/CliCommandParser.java` |
| 执行循环 / 工具调用 | `agent/Agent.java` + `tool/ToolRegistry.java` |
| 规划 / DAG | `agent/PlanExecuteAgent.java` + `plan/Planner.java` |
| 模型 / API | `llm/*Client.java` + `llm/LlmClientFactory.java` |
| MCP | `mcp/McpServerManager.java` |
| TUI / 渲染 | `render/Renderer.java` + `render/RendererFactory.java` |
| 记忆 / 上下文 | `memory/MemoryManager.java` |
| 安全（HITL / 策略） | `hitl/HitlToolRegistry.java` + `policy/` |
| RAG | `rag/CodeRetriever.java` + `rag/CodeIndex.java` |

## 行为约束（改动前必读）

### 共享与复用

- 三条路径共用同一套批量工具执行入口 `executeTools()`，不手写 for-loop；默认 4 并发，结果按原始 `tool_call` 顺序回灌
- 三种 renderer 共享同一套 Agent / ToolRegistry / MemoryManager / MCP / Skill / HITL，不创建孤立能力
- 代码理解默认实时探索（`glob_files` / `grep_code` / `read_file`），`search_code` 只做 RAG 语义辅助，不是精确代码定位的首选

### 记忆

- 长期记忆只在 `/save` 或用户明确要求时写入，不自动提取事实；只存跨会话稳定事实
- `MCODE.md` 管团队共享项目规则，长期记忆管个人/项目作用域事实，职责不混
- 两道压缩不要混淆：shortTermMemory 压缩 vs conversationHistory 压缩（后者是防 window 超限的关键）

### HITL + 策略层

- 拦截顺序：`HitlToolRegistry` -> `ToolRegistry` -> `PathGuard` / `CommandGuard`；用户无法批准策略层拒绝的请求
- `CommandGuard` 是辅助黑名单不是主防线；`PathGuard` 强制路径限定在项目根内
- 微信通道没有人工审批面板，必须走非交互默认拒绝策略（只读允许、`execute_command` / `mcp__*` 白名单、`revert_turn` 默认拒绝）

### 模型调用

- provider 特有约束（DeepSeek SSE 强制 HTTP/1.1、thinking 模式 reasoning_content 历史回带、不支持图片的 provider 降级为文本、xfyun 的 modelId/lora_id）集中在 `docs/agents-reference.md` 的 LLM Clients 一节，改 provider 前先读

### Web + 浏览器

- 已知 URL 先 `web_fetch`，SPA / 防爬墙 fallback 到 Chrome DevTools MCP；浏览器读取优先 `take_snapshot`；公开页面不提前切 shared 模式
- 「当前项目 / 当前文件」类表达走本地工具，不走联网

### TUI / 渲染

- 交互主路径不新增裸 `System.out.println`：输出走 `Renderer.stream()`；`LineReader.isReading()` 时完整行优先走 `printAbove`
- 启动先 `Renderer.start()` 建立 Terminal -> LineReader -> Renderer；Banner 通过 `InlineRenderer.installStartupScreen` 挂 `LineReader.CALLBACK_INIT`，不在 `readLine` 前裸写 stdout
- `BottomStatusBar` 由 JLine `Status` 托管，不手写 `\n` / `moveUp` / `CLEAR_TO_EOS`；live thinking 区只能清理自己刚打印的行，不能向上覆盖 transcript
- 启动期 MCP 不阻塞首屏：默认最多等 8 秒（`MCODE_MCP_STARTUP_WAIT_SECONDS` 可调），未完成的 server 保持后台启动
- Markdown 表格按当前终端列宽分配列宽，单元格内部换行，不依赖终端自动折行

### Plan 审阅交互

- `Enter` 执行 / `Ctrl+O` 展开 / `ESC` 取消 / `I` 补充重规划；方向键不能误判为 ESC
- 涉及改动要连 raw mode 和回退路径一起看

## 改动联动（硬规则）

改任何一处，同步检查右列；未识别的 `/xxx` 命令在 CLI 层直接报"未知命令"，不回退给 Agent：

| 改动 | 必须同步 |
|------|----------|
| 行为 / 功能 | `README.md`；影响协作规则时同步本文件 |
| CLI 命令入口 | `cli/Main.java` + `cli/CliCommandParser.java` + 补全器 + 测试 + `README.md` |
| Plan 审阅交互 | `cli/Main.java` + `cli/PlanReviewInputParser.java` + 测试 + 手工验证 |
| 工具集 | `tool/ToolRegistry.java` + 三条路径提示词 + 文档 |
| 模型 / 接口 | 对应 `llm/*Client.java` + `llm/LlmClientFactory.java` + `.env.example` + 文档 |
| Embedding | `rag/EmbeddingClient.java` + `rag/VectorStore.java` + `.env.example` |
| Web / 搜索 | `web/` + ToolRegistry + `.env.example` + 测试 |
| Memory | `memory/MemoryManager.java` + `LongTermMemory` + `TokenBudget` + 测试 |
| HITL / 策略 | `policy/` + `hitl/` + ToolRegistry + 提示词 + 测试 |
| MCP | `mcp/` + ToolRegistry + HITL + AuditLog + 提示词 + 测试 |
| system prompt 模板 | `src/main/resources/prompts/` + `prompt/PromptAssembler.java`；最终 prompt 必须保留 `## Language` section |

其他：

- 不提交 `.env` / 真实 API Key / `target/` 产物
- 保持代码可读性，不过度抽象

## 维护约定

- 形成稳定协作规则时直接补进本文件，不要只留在聊天记录里；细节补 `docs/agents-reference.md`，产品行为变更同步 `README.md`
- prompt 模板改动按 `docs/prompt-analysis-template.md` 的模板做审计
