# MCode

面向商业使用的 Java Agent CLI 产品，对标 Claude Code。当前主路径是 **ReAct**、**Plan-and-Execute**、**Multi-Agent** 三套执行模式，共享同一套 `ToolRegistry` / `MemoryManager` / `SnapshotService`。

开发协作规则与改动联动清单以根目录 `CLAUDE.md` 为准，模块行为细节见 `docs/agents-reference.md`；本文件面向运行与功能总览。

---

## 测试与运行

### 编译运行

```bash
# 编译（默认跳过测试）
mvn clean package

# 运行（需要本地 Ollama 已启动且拉取了 nomic-embed-text；grep_code 会优先使用本机 ripgrep，未安装时自动回退）
java -jar target/mcode-1.0-SNAPSHOT.jar
```

或者直接运行：

```bash
mvn clean compile exec:java -Dexec.mainClass="com.mcode.cli.Main"
```

### 测试策略

日常开发不需要每次都跑全量测试。`mvn clean package` 默认跳过测试，优先产出可手工验收的 jar；需要回归时按改动范围选择：

```bash
# 第 16 期终端 / TUI / inline renderer 冒烟
mvn test -Pphase16-smoke

# 常规快速回归，跳过外部进程 / 网络超时 / 命令超时类慢测试
mvn test -Pquick

# 代码搜索 deterministic golden set
mvn test -Dtest=CodeSearchGoldenSetTest -DskipTests=false

# 针对性测试
mvn test -Dtest=XxxTest -DskipTests=false

# 发版或大范围重构前再跑全量
mvn test -DskipTests=false
```

### 配置 API Key

复制 `.env.example` 为 `.env`，并填入你的 GLM、DeepSeek、StepFun、Kimi、FreeLLMAPI 或 Agnes API Key：

```bash
cp .env.example .env
# 编辑 .env 文件，填入你的 API Key
```

或者在环境变量中设置：

```bash
export GLM_API_KEY=your_api_key_here
# 或
export STEP_API_KEY=your_step_api_key_here
export STEP_MODEL=step-3.5-flash
# 或
export KIMI_API_KEY=your_kimi_api_key_here
export KIMI_MODEL=kimi-k2.6
# 或
export FREELLMAPI_API_KEY=your_freellmapi_unified_key_here
export FREELLMAPI_BASE_URL=http://localhost:5173/v1
export FREELLMAPI_MODEL=auto
# 或
export AGNES_API_KEY=your_agnes_api_key_here
export AGNES_MODEL=agnes-2.0-flash
export AGNES_BASE_URL=https://apihub.agnes-ai.com/v1
```

也可以在 MCode 内用命令写入 `~/.mcode/config.json`，不会覆盖 Kimi 配置：

```text
/config provider freellmapi --base-url http://localhost:5173/v1 --api-key <key> --model auto
/model freellmapi
/config provider agnes --api-key <key> --model agnes-2.0-flash --default
/model agnes
```

### 可选：配置 MCP server

MCP 子系统默认开启。`~/.mcode/mcp.json` 不存在时，MCode 会自动创建默认 chrome-devtools 配置：

```json
{
  "mcpServers": {
    "chrome-devtools": {
      "command": "npx",
      "args": ["-y", "chrome-devtools-mcp@latest", "--isolated=true"]
    }
  }
}
```

需要继续接入其他 server 时，可编辑 `~/.mcode/mcp.json` 或项目内 `.mcode/mcp.json`：

```json
{
  "mcpServers": {
    "fetch": {
      "command": "uvx",
      "args": ["mcp-server-fetch"]
    },
    "git": {
      "command": "uvx",
      "args": ["mcp-server-git", "--repository", "${PROJECT_DIR}"]
    },
    "remote-demo": {
      "url": "https://mcp.example.com/v1",
      "headers": {"Authorization": "Bearer ${REMOTE_TOKEN}"}
    },
    "step_search": {
      "url": "https://api.stepfun.com/step_plan/v1/mcp/web_search/mcp",
      "headers": {"Authorization": "Bearer ${STEP_API_KEY}"}
    }
  }
}
```

`command` 表示 stdio server，`url` 表示 Streamable HTTP server。`${PROJECT_DIR}` / `${HOME}` 是内置变量，其他 `${VAR}` 从环境变量读取；缺失会在启动时直接提示。

`step_search` 是约定名称：如果项目 `.env`、用户 `~/.env` 或系统环境变量里存在 `STEP_API_KEY`，MCode 会自动内置这个远程 MCP；上面的手写配置只用于覆盖默认地址或自定义鉴权。当前模型为 `step-3.7-flash*` 时，内置 `web_search` / `web_fetch` 会优先代理到该 MCP server。

需要复用当前登录态时，Chrome 144+ 推荐打开 `chrome://inspect/#remote-debugging` 并勾选 `Allow remote debugging for this browser instance`。旧版本或需要显式 CDP 端口时，可以启动带远程调试端口和独立 user-data-dir 的 Chrome，并在这个调试 Chrome 中完成登录：

```bash
# macOS
open -na "Google Chrome" --args --remote-debugging-port=9222 --user-data-dir=/tmp/mcode-chrome-profile

# Windows
start chrome.exe --remote-debugging-port=9222 --user-data-dir=%TEMP%\mcode-chrome-profile

# Linux
google-chrome --remote-debugging-port=9222 --user-data-dir=/tmp/mcode-chrome-profile
```

通常不需要用户预先切换；Agent 如果遇到登录页会自己调用 `browser_connect`。手工调试时也可以在 MCode 内执行：

```text
/browser status
/browser connect
/browser tabs
/browser disconnect
```

`/browser connect` 只在当前进程内把 `chrome-devtools` 切到 shared 模式，不会改写 `~/.mcode/mcp.json`。如果希望启动后默认 shared，可手动把 args 改为：

```json
["-y", "chrome-devtools-mcp@latest", "--autoConnect"]
```

旧式 CDP HTTP JSON 端口也可使用：

```json
["-y", "chrome-devtools-mcp@latest", "--browser-url=http://127.0.0.1:9222"]
```

浏览器测试可直接让 Agent 读取动态页面，例如：

```text
帮我看下 https://mp.weixin.qq.com/s/RB7kF_BbsJZ5_Hmu9PxWdg 这篇文章讲了什么
```

期望路径是 `web_fetch` 尝试失败后，fallback 到 `mcp__chrome-devtools__navigate_page` 与 `take_snapshot`。

如果 server 支持 resources，可以直接查看或引用：

```text
/mcp resources filesystem
/mcp prompts filesystem
帮我看下 @filesystem:file://README.md 这份文档
```

OAuth 和 `sampling/createMessage` 当前未实现；远程 server 需要鉴权时仍使用 `headers` + 环境变量注入 Bearer token。

### 指定运行目录

长期记忆默认保存在 `~/.mcode/memory/long_term_memory.json`；代码索引默认保存在 `~/.mcode/rag/codebase.db`；调试日志默认滚动写入 `~/.mcode/logs/mcode.log`，旧日志按保留天数和总容量自动清理。ReAct / Plan task / SubAgent / Planner 的模型 `reasoning_content` 会以 `LLM reasoning [...]` 形式写入该日志。

如果你想为某次运行指定单独目录，可以额外传入：

```bash
# 指定记忆目录
java -Dmcode.memory.dir=/tmp/mcode-memory -jar target/mcode-1.0-SNAPSHOT.jar

# 指定 RAG 索引目录
java -Dmcode.rag.dir=/tmp/mcode-rag -jar target/mcode-1.0-SNAPSHOT.jar

# 指定日志目录与保留策略
java -Dmcode.log.dir=/tmp/mcode-logs \
     -Dmcode.log.level=DEBUG \
     -Dmcode.log.maxHistory=3 \
     -Dmcode.log.maxFileSize=5MB \
     -Dmcode.log.totalSizeCap=20MB \
     -jar target/mcode-1.0-SNAPSHOT.jar
```

也可以放到 `.env` 或环境变量中：

```bash
MCODE_LOG_LEVEL=DEBUG
MCODE_LOG_DIR=/Users/yourname/.mcode/logs
MCODE_LOG_MAX_HISTORY=7
MCODE_LOG_MAX_FILE_SIZE=10MB
MCODE_LOG_TOTAL_SIZE_CAP=100MB
```

---

## 功能与 Agent 能力

### 执行模式

三条主执行路径，共享同一套 `ToolRegistry` / `MemoryManager` / `SnapshotService`，不为某个模式创建孤立能力：

- **ReAct（默认）**：思考-行动-观察循环，单轮对话驱动，适合简单任务或单步操作。流式输出模型 reasoning 与正文，inline 模式用固定高度 live thinking 区动态预览。
- **Plan-and-Execute**：`/plan` 先把复杂任务拆解成 DAG，再按依赖顺序执行；计划生成后先与用户确认再执行，支持补充要求后重新规划，计划失败时可重规划。简单任务会自动生成最小计划，不为凑步数扩展无关步骤。
- **Multi-Agent**：`/team` 进入多 Agent 协作，三个角色分工——规划者（Planner）拆解任务、执行者（Worker）执行、检查者（Reviewer）审查质量；主从架构由编排器（Orchestrator）协调子代理（SubAgent），审查未通过时带反馈重试（最多 2 次），冲突自动解决。

运行中输入 `/cancel` 并回车可请求取消当前 Agent run；ReAct、Plan、Team、工具批次与 `execute_command` 都在边界处协同检查取消信号。

### 工具系统

内置 11 个工具，外加 MCP 动态工具：

- `read_file` / `write_file` / `list_dir` - 文件读写与目录列举
- `glob_files` - 按文件名 glob 实时查找项目内文件（只读，自动跳过常见构建/依赖目录）
- `grep_code` - 按关键字或正则实时搜索项目内代码，优先使用 ripgrep，返回文件、行号、可选上下文、partial 状态与 suggested_reads
- `execute_command` - 在当前项目目录执行短时 Shell 命令（默认 60 秒超时，黑名单拦截破坏性命令）
- `create_project` - 创建项目结构（java/python/node）
- `search_code` - 语义检索代码库（自然语言查询，作为模糊语义或常规搜索无果时的辅助）
- `web_search` / `web_fetch` - 联网搜索与 URL 正文抓取
- `revert_turn` - 恢复到最近第 N 个 pre-turn 快照（走 HITL 与审计）
- `mcp__{server}__{tool}` - MCP server 动态提供的外部工具
- `mcp__{server}__list_resources` / `mcp__{server}__read_resource` - 支持 resources 的 MCP server 自动注册的虚拟工具

代码库理解默认走 Claude Code 式实时探索：精确代码定位优先 `glob_files` 找候选文件、`grep_code` 精确定位符号或字符串、`read_file` 按需读取具体行段；`search_code` 是 RAG 语义辅助，适合模糊自然语言、关键词不明确、常规搜索无果、巨型/跨知识检索场景，不作为精确代码定位的首选。

**并行执行**：同一轮 LLM 返回多个 `tool_calls` 时，工具层会并行执行（默认最多 4 个并发），ReAct、Plan-and-Execute、Multi-Agent Worker 复用同一套批量工具执行入口。工具结果仍按原始 `tool_call` 顺序回灌，保证消息历史协议稳定。批量工具调用有统一超时与取消兜底，超时工具会被取消并把超时结果回灌给模型；单个 `execute_command` 仍保留 60 秒命令级超时。Plan-and-Execute 与 Multi-Agent 支持按 DAG 依赖批次并行推进独立任务。

文件类与代码检索工具路径强制限定在项目根之内，越界请求会被策略层拒绝；`execute_command` 通过命令黑名单拦截破坏性命令；`revert_turn` 会批量回写工作区，默认触发 HITL 和审计。所有 `mcp__` 前缀工具默认触发 HITL 和审计。详见 `/policy`。

### 记忆与上下文工程

- **短期记忆**管理当前对话与工具结果；对话接近预算时自动做摘要压缩。两道压缩不要混淆：shortTermMemory 压缩 vs conversationHistory 压缩（后者是防 window 超限的关键）。自动压缩阈值按 Claude Code 风格预留摘要输出和安全缓冲：大窗口使用 `window - 20k - 13k`，小窗口按比例缩小。
- **长期记忆**只保存显式保存意图下的稳定事实：`/save <事实>`，或用户在自然语言里明确说"记一下 / 记住 / 以后记得"时由 Agent 调用 `save_memory`。默认项目级作用域，跨会话复用；跨项目通用偏好用 `/save --global <事实>` 或 `save_memory(scope=global)`。它不应包含一次性任务请求或临时文件名/目录名。可用 `/memory list` / `/memory search <关键词>` / `/memory delete <id>` / `/memory clear` 管理与审计。
- **项目级记忆**使用 Markdown 文件维护，和 `/save` 的长期记忆分工不同：`~/.mcode/MCODE.md`（用户级稳定偏好，所有项目可见）、`MCODE.md` / `.mcode/MCODE.md`（项目级团队规则，建议提交 git）、`MCODE.local.md` / `.mcode/MCODE.local.md`（本地覆盖，建议加入 `.gitignore`）；`@relative/path.md` 可在 `MCODE.md` 中导入项目根内文件，越靠后的文件优先级越高。`/init` 可为当前项目生成短 `MCODE.md`（默认不覆盖，`/init --force` 可重写）。启动期注入 Project Context 有字符预算，避免项目记忆变成 token 噪音。
- **长上下文工程**：`LlmClient` 声明模型能力 `maxContextWindow()` / `supportsPromptCaching()` / `promptCacheMode()`；GLM-5.1 默认 200k、DeepSeek V4 默认 1M、Agnes 2.0 Flash 默认 1M、StepFun 默认 256k、Kimi K2.6 默认 256k、FreeLLMAPI 默认按 128k 保守预算。`AgentBudget` 按当前模型动态计算预算（默认 `80% × maxContextWindow`，仍可用系统属性覆盖）。short / balanced / long 三种上下文模式：长上下文模式跳过摘要压缩，语义检索 topK 可提升到 20；`search_code` 未显式传 `top_k` 时按上下文模式自适应。长模式下自动把 MCP resources 的 URI / 描述索引注入 system prompt（不自动注入正文）。inline 模式下 Token / cached input tokens / 估算成本 / 耗时进入底部状态栏，避免占用正文输出区。`/context` 显示当前上下文模式、prompt cache 模式、RAG topK、resources 自动索引状态。

`ctx` 表示当前仍会带入下一轮请求的上下文估算；`in/out/cache` 表示最近任务的 LLM 调用统计，二者不要混用。

### RAG 代码库理解

- 代码向量化（Embedding），支持本地 Ollama 和远程 API
- SQLite 持久化 + 余弦相似度语义检索
- 代码分块（文件/类/方法粒度）与 AST 解析
- 代码关系图谱（extends / implements / imports / calls / contains）
- CLI 命令：`/index [路径]` 索引代码库、`/search <查询>` 语义检索、`/graph <类名>` 查看关系图谱

精确代码定位默认走实时 `glob_files` / `grep_code` / `read_file` 现用现查，`search_code` 只做 RAG 语义辅助。

### 安全模型（HITL + 策略层）

安全模型是 **HITL + 路径校验 + 命令快速拒绝 + 审计**，不是隔离。本地 Agent CLI（参考 Claude Code / Cursor / Aider）默认都不做容器/VM 沙箱——沙箱削弱 Agent 能力、给虚假安全感、体验更差。生产级 Agent 沙箱实际是 microVM-level（Devin / Modal / Anthropic Computer Use 用 Firecracker / gVisor）；想做容器隔离请自行实现 `SandboxDriver` 接口。

**HITL（人机协同审批）**：

- 危险操作静态规则识别：`write_file`、`execute_command`、`create_project`、`revert_turn`
- 三级危险等级：高危（`execute_command`）、中危（`write_file` / `create_project`）
- 审批决策：批准 / 全部放行 / 拒绝 / 跳过 / 修改参数后执行
- HITL 默认关闭，`/hitl on` 启用、`/hitl off` 关闭、`/hitl` 查看状态
- `HitlToolRegistry` 透明拦截层，HITL 关闭时与普通 `ToolRegistry` 行为完全相同
- 拦截顺序：HitlToolRegistry -> ToolRegistry -> PathGuard / CommandGuard；用户无法批准策略拒绝的请求

**策略层**（`com.mcode.policy` 包，HITL 之外的辅助层，不是沙箱、不提供进程隔离）：

- `PathGuard` 路径围栏：文件类工具（`read_file` / `write_file` / `list_dir` / `create_project`）强制限定在项目根之内，拦截绝对路径越界、`..` 穿越、符号链接逃逸（`Files.toRealPath` 防逃逸）
- `CommandGuard` 命令快速拒绝：HITL 之前的 fast-fail 黑名单（`sudo` / `rm -rf 全盘` / `mkfs` / `dd of=/dev` / fork bomb / `curl|sh` / `find /` / `chmod 777 /` / `shutdown`），减少 HITL 弹窗骚扰；是辅助黑名单，不是主防线
- `AuditLog` 结构化审计：危险工具调用按天写 JSONL 到 `~/.mcode/audit/`，含 `outcome (allow|deny|error)` 与 `approver (hitl|policy|none)`；审计参数会脱敏 token / key / password / Authorization / Bearer 凭证；`revert_turn` 也纳入危险工具链
- 资源上限：`write_file` 单文件 5MB；`execute_command` 60 秒超时 + 8KB 输出截断
- CLI 命令：`/policy` 查看安全策略状态、`/audit [N]` 查看今日最近 N 条审计记录（默认 10）

### 多模型适配与图片输入

- `LlmClient` 接口抽象 + `AbstractOpenAiCompatibleClient` 模板方法基类，共享 SSE 流式解析、请求构建、工具调用增量合并逻辑；新增 provider 只需 ~20 行瘦子类
- 内置 `GLMClient`、`DeepSeekClient`、`StepClient`、`KimiClient`、`FreeLlmApiClient`、`AgnesClient` 六个实现
- 运行时模型切换：`/model glm-5.1` / `/model glm-5v-turbo` 明确切 GLM 模型；`/model deepseek` / `/model step` / `/model kimi` / `/model freellmapi` / `/model agnes` 切 provider 并读取配置里的具体模型
- 配置持久化到 `~/.mcode/config.json`，API Key 可从配置、环境变量或 `.env` 读取
- `LlmClientFactory` 工厂根据 provider 名称和配置创建对应客户端

**图片输入**：

- `LlmClient.Message` 支持 `ContentPart`，包括 `text`、`image_base64`、`image_url`；请求体在含图片且 provider 支持图片输入时输出带图片块的 content array，纯文本仍保持 string content
- 公共接口用 `supportsImageInput()` 声明图片能力；DeepSeek 等文本 provider 会把图片块替换成文本提示，避免 `image_url` 进入不支持多模态的 API 请求体
- GLM 套餐用户可通过 `/model glm-5v-turbo` 切换到 GLM-5V-Turbo 多模态模型，再用 Ctrl+V 或 `@image:` 输入图片；本地 base64 图片按智谱格式写入 `image_url.url`
- MCP `image` content 保留 base64 与 `mimeType`，在 ReAct / Plan / SubAgent 工具结果后作为图片 user message 回灌；当前 provider 不支持图片输入时，请求序列化层自动省略图片 payload 并保留文本提示
- 用户可通过 `@image:file:///abs/path.png`、`@image:/abs/path.png` 或 `@image:relative/path.png` 引用本地图片
- 本地图片和 MCP 图片按 Claude Code 同类策略预处理：不是 OCR 成文本，而是压缩 / 缩放后作为图片块发送；带 alpha 的 PNG 会铺白底重编码；额外注入来源、尺寸和坐标映射元信息
- 新一轮 ReAct / SubAgent 任务开始前会省略历史 image payload，仅保留文本元信息，避免旧截图反复进入上下文
- DeepSeek 流式调用默认使用 HTTP/1.1，规避部分 HTTP/2 网关长 SSE 响应被重置导致的 `stream was reset: INTERNAL_ERROR`
- 当前边界：不做视频 / 音频、图像生成、TUI sixel 图片预览

### 联网与浏览器

**联网工具**：

- `web_search` 抽象成 `SearchProvider` 接口，四条路：Step 3.7 Flash + StepSearch MCP 优先、智谱 Web Search（与 GLM 共用 Key，默认推荐，0.01–0.05 元/次）、SerpAPI（国际通用付费）、SearXNG（开源自托管免费）
- `web_fetch`：URL -> OkHttp 抓取 -> Jsoup 解析 -> 简易 readability -> Markdown 正文
- 当当前模型是 `step-3.7-flash*` 且自动/显式 `step_search` 远程 server 已就绪时，内置 `web_search` / `web_fetch` 会优先走 StepSearch MCP；未就绪或调用失败时自动回退到原 provider
- ReAct 对"最新/当前/今天/今年/2026/趋势/新闻/版本"等时效性问题会先做一次 `web_search` 预检并注入本轮上下文，避免模型在工具可用时误说无法实时搜索；用户明确不要联网时跳过
- 默认安全策略：屏蔽 `file://` / 内网 / loopback；30 秒超时；5MB 响应上限；每分钟 30 次限流
- 边界明确：SPA / 防爬墙站点会返回空正文 + 已知边界提示，不重试，Agent 会 fallback 到浏览器 MCP 路线

**Chrome DevTools MCP**：

- 默认接入 Google 官方 `chrome-devtools-mcp@latest`，注册为 `mcp__chrome-devtools__navigate_page`、`take_snapshot`、`click`、`fill_form` 等浏览器工具
- `~/.mcode/mcp.json` 不存在时启动自动创建模板，默认使用 `--isolated=true` 临时浏览器 profile
- 用于处理 SPA / JS 渲染 / 防爬墙 / 表单交互页面；微信公众号文章、知乎专栏、推特、小红书等 `web_fetch` 失败站点会引导走浏览器 MCP
- HITL 的"全部放行"支持 MCP server 维度，连续浏览器操作可对 `chrome-devtools` 一次确认
- `image` 类型结果会作为图片输入附加到下一轮；文本 fallback 仍保留，用于日志、人类可读摘要，以及 DeepSeek 等不接受图片块的 provider 自动降级上下文
- MCP initialize 默认超时 60 秒；CLI 首屏默认最多等待 8 秒（`MCODE_MCP_STARTUP_WAIT_SECONDS` 可调），超时后先进入交互，未完成的 server 保持 `starting` 并在后台继续启动，可用 `/mcp` 和 `/mcp logs <name>` 追踪
- 浏览器读取优先 `take_snapshot`（DOM 文本快照），不默认 `take_screenshot`；公开页面不要提前切 shared 模式

**CDP 会话复用 + 登录态访问**：

- `/browser status` / `/browser connect [port]` / `/browser disconnect` / `/browser tabs` 命令组，并给 Agent 暴露内部 `browser_connect` / `browser_disconnect` / `browser_status` 工具
- 默认仍使用 `--isolated=true` 临时 profile；执行 `/browser connect` 后运行时把 `chrome-devtools` 切到 `--autoConnect`，复用已在 `chrome://inspect/#remote-debugging` 允许远程调试的登录态 Chrome
- Agent 遇到登录页、权限不足或明确需要登录态页面时，会先调用 `browser_connect` 自动切到 shared；公开页面如微信公众号文章不提前切换
- `/browser connect <port>` 保留旧式 CDP 端口兼容路径：先探活 `127.0.0.1:<port>/json/version`，成功后切到 `--browser-url=http://127.0.0.1:<port>`；失败时输出各平台 Chrome 启动命令
- 切换 shared / isolated 模式都会清空 `chrome-devtools` 的 server 维度全部放行，避免旧信任跨模式延续
- shared 模式下 `close_page` 只能关闭 MCode 自己创建的 tab；无法证明是 MCode 创建的 tab 会被策略层拒绝
- 敏感页面命中规则后，`click` / `fill_form` / `evaluate_script` 等改写型浏览器工具必须单步 HITL 审批，不复用全部放行；读型工具如 `take_snapshot` 仍可继续使用
- 审计日志为 chrome-devtools 工具追加可选浏览器 metadata：`browser_mode`、`sensitive`、`target_url`，旧格式 JSONL 仍可读取

### MCP 生态

- `com.mcode.mcp` 模块，支持 stdio 子进程 server 与 Streamable HTTP 远程 server；手写 `JsonRpcClient`（JSON-RPC 2.0：请求-响应配对、通知、错误码、超时）
- 启动时读取 `~/.mcode/mcp.json` 与 `.mcode/mcp.json`，项目级配置按 server 名覆盖用户级配置；格式与 Claude Code `claude_desktop_config.json` 兼容
- MCP `${VAR}` 支持系统环境变量、系统属性、项目 `.env`、用户 `~/.env`；检测到 `STEP_API_KEY` 时自动内置 `step_search` 远程 MCP，显式同名配置优先
- `initialize` 握手 + capabilities 协商 + protocol version negotiation；`tools/list` + `tools/call`，工具按 `mcp__{server}__{tool}` 前缀注册到 `ToolRegistry`；参数 schema 会清洗 `$ref` / `anyOf` / 超长 description，降低模型调用失败率
- 所有 MCP 工具默认走 HITL 审批和审计，审计参数会脱敏凭证
- **resources 双轨**：工具层每个支持 resources 的 server 注册 `mcp__{server}__list_resources` / `mcp__{server}__read_resource` 虚拟工具让 LLM 自决；用户层 `@server:protocol://path` 语法 + jline 自动补全，提交给 Agent 前展开为 `<resource>` 内联块
- **prompts 查看**：`/mcp prompts <server>` 展示 server 暴露的 prompt 模板（只查看，不注入对话流）
- **被动通知**：处理 `notifications/tools/list_changed`（重拉工具列表全量替换）、`notifications/resources/list_changed` / `notifications/resources/updated`（缓存失效，下次重拉）；不做 health ping，不主动探活，避免对按量或按月计费 server 造成额外负担
- 启动时 eager 并行启动所有 server（复用并行调度）；未配置 `mcp.json` 时不启动外部 server，避免首次运行被 `npx` / `uvx` 冷启动阻塞
- CLI 命令：`/mcp`、`/mcp restart <name>`、`/mcp logs <name>`（最近 200 行 stderr）、`/mcp disable <name>`、`/mcp enable <name>`、`/mcp resources <name>`、`/mcp prompts <name>`

### Skill 系统

把"Agent 该怎么思考"从硬编码 system prompt 抽出，沉淀成可复用单元。每个 Skill 是一个目录：`SKILL.md`（决策手册）+ `references/`（按需读取）+ 可选 `scripts/`（可执行依赖）。设计意图：从「写工具」演进到「打包专家手册」——当工具堆成山（内置 11 个 + MCP 60+ 工具），用 Skill 给 LLM 一份按场景展开的"专家手册"，比往 system prompt 里塞更多规则更可扩展。

- 三层加载位置（按优先级，后者整体覆盖同名 skill）：jar 内置 < 用户级 `~/.mcode/skills/<name>/` < 项目级 `<project>/.mcode/skills/<name>/`
- 启动期把启用 skill 的 `name` + `description` 注入三处 Agent 系统提示词索引段（启用上限 20 个，单 description ≤ 500 codepoint，索引段 ≤ 4KB）
- 内置工具 `load_skill(name)`：LLM 在 system prompt 看到匹配 description 时主动调用，MCode 把 SKILL.md 正文（5KB 截断）写入 `SkillContextBuffer`，下一轮 user message 自动前置注入（lazy 展开，节省 token）；一次性消费、最多保留 3 个 skill body、`/clear` 可 reset
- 内置 web-access skill：决策手册（浏览哲学四步法 + 工具选择表 + 浏览器优先级 + Jina 兜底说明）+ 6 个站点经验文件（mp.weixin / zhuanlan.zhihu / x.com / xiaohongshu / github / juejin）+ cdp-cheatsheet
- frontmatter 走手写 YAML 子集解析，不引 SnakeYAML；解析失败 stderr 警告但不阻塞启动
- 启动期 `SkillBuiltinExtractor` 把 jar 内置 skill 解压到 `~/.mcode/skills-cache/`，按 `.version` 文件控制重建
- CLI 命令：`/skill` / `/skill list` / `/skill show <name>` / `/skill on <name>` / `/skill off <name>` / `/skill reload`
- 启用状态持久化：`~/.mcode/skills.json` 的 `disabled` 列表，默认全启用
- 与 HITL 协同：Skill 内调用 `execute_command` 等危险工具仍走既有 HITL 审批，沿用 `execute_command` 工具维度全放行；不给 Skill 单独审批维度

### TUI 产品化

抽出 `Renderer` 接口 + 三个实现，三种形态共享同一套 `Agent` / `ToolRegistry` / `MemoryManager` / MCP server / SkillRegistry / HITL handler，不创建孤立空会话：

| 形态 | 启用方式 | 视觉风格 |
|---|---|---|
| **inline 流式 TUI**（默认） | 直接运行 / `MCODE_RENDERER=inline` | Claude Code / Qoder 风格：MCode 主题彩色开屏、主屏直出、transcript 当前位置的 `* ` 输入提示、JLine `Status` 托管的底部 dock（YOLO/HITL、MCP、Skill、model、ctx、token、cwd 等关键字段带克制彩色高亮）、右侧输入提示、行内可折叠工具块（`Read 3 files (ctrl+o to expand)`）、行内 git diff、HITL 单字符 `[y/n/a/s/m]` 提示 |
| **lanterna 全屏 TUI** | `MCODE_RENDERER=lanterna`（或兼容旧 `MCODE_TUI=true`） | v16 三栏全屏：文件树 + 对话流 + 状态栏 + 底部输入栏，HITL 模态弹窗 |
| **plain 兜底** | `MCODE_RENDERER=plain` | 纯 println，无折叠 / 状态栏 |

- 普通输入走 ReAct；`/plan <任务>` 走 Plan-and-Execute；`/team <任务>` 走 Multi-Agent；`/cancel` 可取消运行中任务
- 通用命令：`/clear`、`/context`、`/memory`、`/memory clear`、`/save <事实>`、`/export`、`/hitl`、`/hitl on`、`/hitl off`、`/config`、`/exit`
- 对话历史保存到 `~/.mcode/history/session_*.jsonl`
- 兼容旧设置：`MCODE_TUI=true` 自动映射为 `MCODE_RENDERER=lanterna`（已 deprecated）
- `MCODE_NO_STATUSBAR=true` 在 inline 模式下禁用 JLine 底部 dock（不适合 ANSI 光标控制的终端）
- `NO_COLOR=1` 禁用所有 ANSI 颜色，保留布局
- 终端会对常见 Markdown（标题、列表、表格、代码块）做渲染后再显示；表格按当前窗口宽度分配列宽，单元格内部换行，避免长 URL / 中文内容把列打散

### 开发体验安全网

**LSP 诊断注入**：

- `write_file` 成功后触发 post-edit 诊断，诊断结果不会阻塞工具主流程
- 当前 MVP 对 Java 文件使用 JavaParser 做轻量语法诊断，不依赖本机安装 JDT LS
- ReAct、Plan-and-Execute、Multi-Agent 三条路径都会在下一轮 LLM 请求前把 pending 诊断作为合成 user message 注入——模型在下一轮推理前就能看到编译错误
- 诊断按 error / warning / info、文件、行列号、message 格式化，默认最多注入 20 条；inline 模式下以红色/黄色 ANSI 渲染
- 配置：`MCODE_LSP_ENABLED=false` 可关闭，`MCODE_LSP_MAX_DIAGNOSTICS=20` 可调整注入上限
- 优雅降级：LSP server 启动失败或超时时只打 trace 日志，不阻塞 Agent 主流程

**Git Side-History 快照与回滚**：

- 每个 ReAct / Plan / Team turn 开始前创建 `pre-turn` 快照，结束后异步创建 `post-turn` 快照
- 快照仓库使用 JGit 纯 Java 实现，默认位于 `~/.mcode/snapshots/<project_hash>/<worktree_hash>/.git`，与用户工作区 `.git` 完全隔离，不写用户项目 `.git`
- `/snapshot` 查看最近快照、`/snapshot status` 查看配置与 side-git 目录、`/snapshot clean` 清理当前项目快照目录
- `/restore <N>` 恢复到最近第 N 个 `pre-turn` 快照；恢复前会先创建 `pre-restore` 快照，不改变用户 `.git` 和对话历史
- Agent 内置 `revert_turn` 工具，纳入 HITL 与 AuditLog 危险工具链，让 Agent 自己能判断"改坏了需要撤销"
- 配置：`MCODE_SNAPSHOT_ENABLED=false` 可关闭，`MCODE_SNAPSHOT_MAX=50`、`MCODE_SNAPSHOT_EXCLUDES=...`、`MCODE_SNAPSHOT_DIR=...` 可调整策略

**Prompt 分层架构**：

- ReAct、Plan task executor、Multi-Agent 三角色、Planner 的 system prompt 已从 Java 硬编码抽离到 `src/main/resources/prompts/`（`base.md` / `modes/*.md` / `approvals/*.md` / `personalities/*.md`）
- `PromptAssembler` 按 `base -> personality -> mode -> approval -> runtime_context -> project_context -> skills -> context_mgmt -> handoff` 组装，遵循"volatile content last"原则以最大化 KV prefix cache 命中率；`runtime_context` 注入当前日期/时区，动态项目上下文靠后注入
- `project_context` 会先注入 `MCODE.md` 项目记忆，再注入 `/save` 检索到的相关长期记忆和 MCP resource 索引
- 支持用户级覆盖 `~/.mcode/prompts/...`，支持项目级覆盖 `.mcode/prompts/...`，项目级优先级最高；覆盖是整文件替换
- 启动时校验：`base.md` 和最终 prompt 必须包含 `## Language` section（保证 reasoning_content 语言跟随）
- Prompt 改动审计模板见 `docs/prompt-analysis-template.md`

### 异步后台任务与 Runtime API

**后台任务（Task Manager）**：

- `DurableTaskManager` 使用 SQLite 持久化后台任务队列，默认位置 `~/.mcode/tasks/tasks.db`
- 任务生命周期：`enqueued -> running -> completed / failed / canceled`
- `/task`、`/task add <任务内容>`、`/task cancel <task_id>`、`/task log <task_id>` 提供 CLI 闭环
- Worker Pool 默认 2 个后台 worker，可通过 `MCODE_TASK_WORKERS` / `-Dmcode.task.workers` 调整
- 持久化恢复：进程重启后未完成的任务自动重入队

**Runtime API**：

- `RuntimeApiServer` 基于 JDK `HttpServer`，仅监听 `127.0.0.1`，启动方式 `java -jar target/mcode-1.0-SNAPSHOT.jar serve --http --port 8080`
- 兼容 OpenAI Assistants API 的端点：`POST /v1/threads`、`POST /v1/threads/glm_5.2_ark_toC/turns`、`GET /v1/threads/glm_5.2_ark_toC/events`（SSE 流式事件）
- `RuntimeThreadStore`：SQLite 保存 thread 与 event 时间线，可重放
- Runtime API 强制要求 `MCODE_RUNTIME_API_KEY` 或 `-Dmcode.runtime.api.key`
- 详细文档见 `docs/phase-20-runtime-api.md`

### 微信 iLink 通道（文本 MVP）

- 进程级入口：`mcode wechat setup`、`mcode wechat start`、`mcode wechat status`、`mcode wechat daemon start|stop|restart|status|logs`
- 交互式入口：在 MCode 主界面输入 `/wechat` 可扫码绑定并在当前进程后台启动微信通道；`/wechat setup` 重新扫码绑定，`/wechat status` 查看状态，`/wechat stop` 停止通道
- 默认不开启微信通道；用户必须主动执行 `setup` 并扫码确认完成绑定
- 支持在 Warp / iTerm2 / WezTerm 等兼容终端内直接显示 260px PNG 二维码；不支持终端图片协议时回退为字符二维码和链接
- 微信侧使用 iLink `getupdates` 长轮询收消息、`sendmessage` 分片回消息，不依赖 SSE；这是独立通道，不是 Skill，也不是 Runtime API
- 运行时只接受绑定用户私聊；普通消息单并发排队，`/help`、`/status`、`/pause`、`/resume`、`/stop` 走队列外控制路径
- 微信侧用户消息会回显到 MCode 终端 transcript；MCode 终端继续显示 thinking / 工具调用过程，微信侧只接收 assistant 正文。MCode 会保留 ClawBot 稳定支持的 Markdown 子集（列表、引用、粗体、行内代码、真实代码块），把标题转成粗体标题、把表格转成移动端更稳的键值/列表，过滤图片 Markdown / H5-H6 / 中文斜体等兼容性差的标记
- 微信通道使用非交互式默认拒绝策略：只读工具默认允许，`write_file` / `create_project` 继续受 workspace PathGuard 限制，`execute_command` 必须精确命中命令白名单，`mcp__*` 必须命中 MCP 白名单，`revert_turn` 和浏览器会话切换默认拒绝
- 当前文本 MVP 会保留图片 / 文件消息的媒体元数据提示，但 CDN 下载解密、图片块输入和 `/send` 文件推送仍待后续媒体链路补齐

---

## 可用工具

- `read_file` - 读取文件内容
- `write_file` - 写入文件内容
- `list_dir` - 列出目录内容
- `glob_files` - 按文件名 glob 实时查找项目内文件（只读，自动跳过常见构建/依赖目录）
- `grep_code` - 按关键字或正则实时搜索项目内代码，优先使用 ripgrep，返回文件、行号、可选上下文、partial 状态与 suggested_reads
- `execute_command` - 在当前项目目录执行短时 Shell 命令（默认 60 秒超时，黑名单拦截破坏性命令）
- `create_project` - 创建项目结构（java/python/node）
- `search_code` - 语义检索代码库（自然语言查询，适合作为模糊语义或常规搜索无果时的辅助）
- `web_search` - 搜索互联网获取实时信息
- `web_fetch` - 抓取已知 URL 并提取正文 Markdown
- `revert_turn` - 恢复到最近第 N 个 pre-turn 快照（走 HITL 与审计）
- `mcp__{server}__{tool}` - MCP server 动态提供的外部工具
- `mcp__{server}__list_resources` / `mcp__{server}__read_resource` - 支持 resources 的 MCP server 自动注册的虚拟工具

同一轮模型返回多个工具调用时，MCode 会并行执行这些工具；如果工具之间有依赖关系，模型应分多轮调用。

---

## 命令

进程级入口：

- `mcode wechat setup` - 绑定微信 iLink 通道，选择 workspace 并完成扫码确认
- `mcode wechat start` - 前台启动微信通道
- `mcode wechat status` - 查看绑定状态和 daemon pid
- `mcode wechat daemon start|stop|restart|status|logs` - 管理本机微信通道后台进程
- `mcode serve --http --port 8080` - 启动 localhost Runtime API

交互式斜杠命令：

- `/wechat` - 扫码绑定并启动微信 iLink 通道；已绑定时直接启动
- `/wechat setup` - 重新扫码绑定并启动微信通道
- `/wechat status` - 查看当前 MCode 进程内微信通道状态
- `/wechat stop` - 停止当前 MCode 进程内微信通道
- `/plan` - 下一条任务使用 Plan-and-Execute 模式
- `/plan <任务>` - 直接用 Plan-and-Execute 模式执行这条任务
- `/team` - 下一条任务使用 Multi-Agent 协作模式
- `/team <任务>` - 直接用 Multi-Agent 协作模式执行这条任务
- `/cancel` - 运行中请求取消当前任务；空闲时会提示当前没有正在运行的任务
- `/hitl on` - 启用危险操作人工审批（HITL）
- `/hitl off` - 关闭 HITL 审批
- `/hitl` - 查看 HITL 当前状态
- `/mcp` - 查看所有 MCP server 状态
- `/mcp restart <name>` - 重启单个 MCP server
- `/mcp logs <name>` - 查看 MCP server 最近 200 行 stderr 日志
- `/mcp disable <name>` - 运行时禁用 MCP server 并移除其工具
- `/mcp enable <name>` - 运行时启用 MCP server
- `/mcp resources <name>` - 查看 MCP server 暴露的 resources
- `/mcp prompts <name>` - 查看 MCP server 暴露的 prompts（只查看，不注入对话）
- `/browser status` / `/browser connect [port]` / `/browser disconnect` / `/browser tabs` - 浏览器会话管理
- `/policy` - 查看安全策略状态（路径围栏 / 命令黑名单 / 资源上限 / 审计目录）
- `/audit [N]` - 查看今日最近 N 条危险工具审计记录（默认 10）
- `/snapshot` - 查看最近 Side-Git 快照
- `/snapshot status` - 查看 Side-Git 快照状态
- `/snapshot clean` - 清理当前项目 Side-Git 快照目录
- `/restore <N>` - 恢复到最近第 N 个 pre-turn 快照
- `/skill` / `/skill list` / `/skill show <name>` / `/skill on <name>` / `/skill off <name>` / `/skill reload` - Skill 管理
- `/task` / `/task add <任务内容>` / `/task cancel <task_id>` / `/task log <task_id>` - 后台任务管理
- `/model <provider|model>` - 运行时切换模型
- `/context` - 查看当前上下文模式、prompt cache、RAG topK、resources 索引状态
- `/memory` / `/mem` - 查看记忆系统状态
- `/memory list` - 查看长期记忆列表
- `/memory search <关键词>` - 搜索当前项目可见长期记忆
- `/memory delete <id>` - 删除单条长期记忆
- `/memory clear` - 清空长期记忆
- `/save <事实>` - 手动保存项目级关键事实到长期记忆；`/save --global <事实>` 保存跨项目通用偏好
- `save_memory` - Agent 内置工具，仅在用户明确要求保存长期偏好或稳定事实时调用；默认 `scope=project`，跨项目通用偏好才用 `scope=global`
- `/init` - 生成精简项目级记忆 `MCODE.md`；已存在时不覆盖，`/init --force` 可重写
- `/export` - 导出当前 ReAct 会话对话记录为 Markdown（包含完整 system prompt），写入 `~/.mcode/exports/session-*.md`
- `/index [路径]` - 索引代码库（默认当前目录）
- `/search <查询>` - 语义检索代码（RAG 辅助路径）
- `/graph <类名>` - 查看代码关系图谱
- `/clear` - 清空当前对话历史、短期记忆、待注入 Skill 上下文和上一轮检索记忆注入；长期记忆保留
- `/exit` / `/quit` - 退出程序

---

## Plan 模式

当前默认模式是 `ReAct`。进入 `Plan-and-Execute` 的方式只有 `/plan`：

1. 输入 `/plan`
2. 下一条任务会用计划模式执行
3. 执行完成后自动回到默认 `ReAct`

如果想一条命令切模式并执行任务，可以直接输入：

```text
/plan 创建一个 demo 项目，然后读取 pom.xml，最后验证项目结构
```

这条命令执行完成后，会自动回到默认的 `ReAct` 模式。

计划生成后，CLI 会先停下来等待确认：

- 按 `Enter`：按当前计划执行
- 按 `Ctrl+O`：展开完整计划
- 按 `ESC`：折叠完整计划或取消本次计划
- 按 `I`：输入补充要求并重新规划
- 按方向键不会触发取消；只有单独按下 `ESC` 才会取消待执行 plan

---

## 启动界面

当前启动输出以命令行实际产物为准：

```text
    ▐▛███▜▌    MCode  v16.1.0
   ▝▜█████▛▘    Model step-3.5-flash-2603 (step)
     ▘▘ ▝▝      MCP 4/4 · 61 tools · 2/2 skills · ReAct
                ReAct · Plan · MCP · Browser · Image · Tools · Memory · RAG

Tips for getting started:
1. Type / for commands and Tab completion
2. Ask coding questions, edit code or run commands
3. Attach context with @path or @image:
```

普通任务和斜杠命令提交后会先把本轮原始输入以 `>` 暗色整行块写回 transcript；输入态仍显示 `* `，单行提交只占一行，不额外追加空白行。普通任务随后再进入 Thinking / 工具调用，避免 dock 刷新或 activity 重绘后用户输入从可见历史里消失。默认通过流式接口获取模型输出；inline ReAct 用固定高度 live thinking 区动态预览 reasoning，content / tool call 开始前清掉 live 区并把完整 reasoning 引用块落到 transcript。`web_search` / `web_fetch` 会在折叠头展示 query / URL，并在执行后输出一行结果摘要。

---

## 使用示例

### ReAct 示例

```text
* 创建一个Java项目叫myapp

🧠 思考过程:
用户要创建一个 Java 项目。我先调用 create_project 工具生成基础结构，再根据工具返回结果确认是否创建成功。

🤖 最终结果:
已成功创建 Java 项目 "myapp"，包含基本的 Maven 结构。
```

### Plan-and-Execute 示例

```text
💡 提示:
   - 输入你的问题或任务
   - 输入 '/' 后按 Tab 补全命令
   - 输入 '@server:protocol://path' 可显式引用 MCP resource
   - 任务运行中按 ESC 取消当前任务
   - 默认模式是 ReAct
   - 未识别的 `/xxx` 命令会直接提示"未知命令"，不会再交给 Agent 当普通对话处理

* /plan 创建一个名为 demoapp 的 java 项目，然后读取 pom.xml，最后验证项目结构

📋 使用 Plan-and-Execute 模式

📋 正在规划任务: 创建一个名为 demoapp 的 java 项目，然后读取 pom.xml，最后验证项目结构

╔══════════════════════════════════════════════════════════╗
║  执行计划: 创建一个名为 demoapp 的 java 项目，然后读取... ║
╠══════════════════════════════════════════════════════════╣
║  1. ⏳ task_1               [COMMAND   ] 依赖: 无        ║
║     创建 demoapp 项目结构                              ║
║  2. ⏳ task_2               [FILE_READ ] 依赖: task_1    ║
║     读取 demoapp/pom.xml 内容                          ║
║  3. ⏳ task_3               [VERIFICATION] 依赖: task_2  ║
║     验证项目结构与 Maven 配置                          ║
╚══════════════════════════════════════════════════════════╝

📝 计划已生成。
   - 回车：按当前计划执行
   - ESC：取消本次计划
   - I：输入补充要求后重新规划

I
补充> 请在执行前先检查 README

📝 已收到补充要求，正在重新规划...

🚀 开始执行计划...
```

---

## 技术栈

- Java 17
- Maven
- GLM-5.1 API
- OkHttp
- Jackson
- JLine 4（终端交互、Status、输入 widgets）
- SQLite（向量与图谱持久化）
- JavaParser（AST 分析）
- Ollama（本地 Embedding）

---

## 项目结构

```
src/main/java/com/mcode
├── agent/
│   ├── Agent.java              # ReAct Agent
│   ├── PlanExecuteAgent.java   # Plan-and-Execute Agent
│   ├── AgentRole.java          # Agent 角色枚举
│   ├── AgentMessage.java       # Agent 间通信消息
│   ├── SubAgent.java           # 可配置子代理
│   └── AgentOrchestrator.java  # Multi-Agent 编排器
├── cli/
│   ├── Main.java               # CLI 入口
│   ├── CliCommandParser.java   # 命令解析
│   └── PlanReviewInputParser.java  # 计划审核输入
├── llm/
│   ├── GLMClient.java          # GLM API 客户端；glm-5.1 走 Coding endpoint，glm-5v-turbo 走多模态 endpoint
│   ├── DeepSeekClient.java     # DeepSeek API 客户端
│   ├── StepClient.java         # 阶跃星辰 StepFun API 客户端
│   ├── KimiClient.java         # Kimi / Moonshot API 客户端
│   ├── FreeLlmApiClient.java   # 本地 FreeLLMAPI OpenAI-compatible 网关客户端
│   └── AgnesClient.java        # Agnes AI OpenAI-compatible 客户端
├── context/
│   ├── ContextMode.java        # short / balanced / long 模式
│   ├── ContextProfile.java     # 模型窗口与上下文策略
│   └── TokenUsageFormatter.java # Token / cache / 成本展示
├── memory/
│   ├── MemoryEntry.java        # 记忆条目
│   ├── ConversationMemory.java # 短期记忆
│   ├── LongTermMemory.java     # 长期记忆
│   ├── ContextCompressor.java  # 上下文压缩
│   ├── TokenBudget.java        # Token 预算管理
│   ├── MemoryRetriever.java    # 记忆检索
│   └── MemoryManager.java      # 记忆门面类
├── plan/
│   ├── Task.java               # 任务定义
│   ├── ExecutionPlan.java      # 执行计划
│   └── Planner.java            # 规划器
├── rag/
│   ├── EmbeddingClient.java    # Embedding API 客户端
│   ├── VectorStore.java        # SQLite 向量存储
│   ├── CodeChunk.java          # 代码块模型
│   ├── CodeChunker.java        # 代码分块器
│   ├── CodeAnalyzer.java       # AST 关系分析
│   ├── CodeRelation.java       # 代码关系模型
│   ├── CodeIndex.java          # 索引管理器
│   └── CodeRetriever.java      # 检索入口
└── tool/
    └── ToolRegistry.java       # 工具注册表
```
