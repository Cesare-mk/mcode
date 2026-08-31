# MCode 品牌迁移改动定位

本记录由迁移脚本按本次实际文本替换生成。行号均指迁移完成后的文件位置；同一行内的多处替换只记录一次。

## 文本改动

| 文件 | 改动后行号 |
|---|---|
| `.env` | 90, 91, 98, 99, 100, 101, 103, 104, 109, 110, 114, 115, 116, 117, 118, 121, 122, 124, 128, 132, 133, 134, 137, 142, 147, 153, 157, 158, 160, 163, 166, 169, 172, 176, 177, 178, 182, 183, 184, 185, 186 |
| `.env.example` | 1, 96, 97, 104, 105, 106, 107, 109, 110, 115, 116, 120, 121, 122, 123, 124, 127, 128, 130, 134, 138, 139, 140, 143, 148, 153, 159, 163, 164, 166, 169, 172, 175, 178, 182, 183, 184, 188, 189, 190, 191, 192 |
| `.gitignore` | 3, 4, 5, 6 |
| `.mcode/logs/mcode.log` | 1, 2, 3, 5, 6, 7, 8, 9, 14, 15, 16, 19, 24 |
| `面试准备-新版简历源码讲解.md` | 1, 7, 19, 47, 77, 78, 79, 80, 81, 82, 132, 197, 198, 199, 200, 201, 202, 203, 204, 205, 316, 322, 342, 343, 344, 345, 346, 347, 348, 349, 350, 358, 445, 455, 501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 663, 664, 665, 666, 667, 668, 669, 670, 671, 693, 738, 744, 750, 796, 812, 826, 827, 828, 829, 830, 831, 832, 833, 834, 836, 855, 881, 925, 926, 927, 928, 939, 941, 946, 976, 993, 1017, 1019, 1024, 1056, 1069, 1071, 1076, 1087, 1151, 1153, 1158, 1213, 1215, 1220, 1252, 1254, 1259, 1275, 1277, 1278, 1282, 1296, 1299, 1324, 1326, 1331, 1347, 1353, 1395, 1397, 1402, 1486, 1488, 1493, 1542, 1544, 1549, 1584, 1608, 1610, 1615, 1670, 1672, 1677, 1701, 1703, 1708, 1724, 1743, 1755, 1777, 1779, 1784, 1830, 1832, 1837, 1846, 1876, 1878, 1883, 1919, 1947, 1949, 1954, 1986, 2003, 2005, 2013, 2015, 2020, 2068, 2070, 2075, 2133, 2150, 2152, 2157, 2190, 2192, 2197, 2228, 2230, 2235, 2259, 2291, 2293, 2298, 2301, 2314, 2349, 2358, 2360, 2365 |
| `面试准备-源码讲解.md` | 1, 4, 16, 17, 20, 21, 24, 34, 64, 65, 66, 116, 117, 118, 119, 120, 121, 122, 129, 130, 163, 165, 166, 167, 168, 169, 170, 192, 195, 211, 212, 213, 214, 216, 217, 225, 232 |
| `AGENTS.md` | 13, 16, 18, 31, 32, 33, 39, 59, 65, 71, 97, 108, 109, 110, 112, 113, 114, 115, 123 |
| `docs/agents-reference.md` | 13, 22, 23, 24, 25, 26, 27, 31, 39, 43, 49, 69, 70, 108, 109, 110, 111, 166, 170, 179, 180, 181, 188, 198, 202, 210, 211, 269, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312 |
| `docs/code-search-golden-set.md` | 3 |
| `docs/inline-tui-manual-tests.md` | 12, 16, 26, 30, 219, 232, 235, 239, 247, 267, 280, 283, 290, 309, 342, 350, 351, 414, 437, 446, 449, 464, 518 |
| `docs/inline-tui-pivot.md` | 76, 79, 111, 129, 135, 137, 153, 156, 166, 172, 173, 188, 190, 202, 203, 204, 206, 212, 213, 214, 215, 216, 217, 218, 219, 220, 379, 388, 390, 416, 426, 465, 471, 473, 480, 482, 485, 495, 496, 507, 515, 516, 517, 518, 519 |
| `docs/phase-10-mcp-core.md` | 8, 20, 24, 36, 62, 146, 161, 171, 210, 211, 214, 215, 216, 217, 218, 219, 220, 221, 222, 243, 263, 265, 443 |
| `docs/phase-11-mcp-advanced.md` | 38, 67, 115 |
| `docs/phase-12-long-context.md` | 3, 20, 46, 51 |
| `docs/phase-13-chrome-devtools-mcp.md` | 9, 10, 12, 18, 22, 62, 161, 178, 201, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 230, 326, 388, 390, 424, 433, 492 |
| `docs/phase-14-cdp-session-reuse.md` | 10, 11, 12, 14, 27, 28, 37, 38, 93, 96, 98, 141, 191, 195, 203, 302, 314, 320, 342, 343, 344, 345, 346, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 384, 491, 495, 496, 539, 569, 607, 608, 612, 627, 751, 752, 812, 816 |
| `docs/phase-15-skill-system.md` | 8, 9, 10, 21, 30, 58, 59, 61, 72, 114, 126, 127, 152, 203, 236, 303, 315, 326, 346, 347, 348, 369, 372, 385, 386, 387, 388, 389, 390, 391, 392, 398, 399, 400, 401, 402, 403, 404, 424, 440, 469, 480, 491, 500, 519, 542, 545, 548, 590, 592, 627, 629, 703, 796, 802, 835 |
| `docs/phase-16-tui-productization.md` | 6, 16, 17, 18, 26, 30, 32, 88, 100, 159, 167, 236, 237, 246, 277, 300, 326, 327, 328, 331, 332, 339, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 361, 362, 363, 364, 365, 366, 367, 368, 493, 495, 531, 564, 632, 650, 656, 662, 680, 686, 789, 809, 813, 821, 828, 829, 843, 850, 851, 916, 998, 999, 1005, 1036, 1041, 1046, 1066, 1067, 1075, 1119, 1134 |
| `docs/phase-17-lsp-diagnostics.md` | 16, 35, 38, 44, 45, 52 |
| `docs/phase-18-side-history-snapshot.md` | 19, 47, 120, 132, 133, 134, 140, 141, 142 |
| `docs/phase-19-prompt-layering.md` | 20, 85, 86, 91, 92, 93, 112, 113 |
| `docs/phase-20-runtime-api.md` | 10, 17, 27, 32, 33, 39, 42, 77 |
| `docs/phase-21-image-input-manual-test.md` | 7, 173, 179, 195 |
| `docs/phase-21-image-input.md` | 7, 52 |
| `docs/phase-22-jline-interaction-upgrade.md` | 3, 47, 77, 88, 104, 105, 106, 159, 171, 185 |
| `docs/phase-23-wechat-channel.md` | 7, 9, 72, 75, 76, 77, 78, 80, 83, 93, 114, 124 |
| `docs/prompt-analysis-template.md` | 3 |
| `docs/website/index.html` | 6, 22, 31, 52, 61, 91, 94, 111, 116, 447, 448, 452, 461, 463, 481, 620, 627, 628, 629, 647 |
| `docs/website/script.js` | 2 |
| `docs/website/styles.css` | 2 |
| `landing/index.html` | 6, 668, 690, 699, 700, 717, 754, 870, 939, 953, 956, 957, 971, 987, 1011 |
| `pom.xml` | 8, 9, 13, 137, 175 |
| `ppt/index.html` | 6, 436, 501, 505, 539, 543, 544, 546, 583, 656, 684, 962, 987, 991 |
| `README.md` | 1, 18, 24, 77, 88, 101, 128, 134, 137, 140, 143, 152, 184, 190, 193, 196, 197, 198, 199, 200, 201, 207, 208, 209, 210, 211, 253, 281, 285, 294, 323, 327, 337, 343, 344, 358, 360, 363, 365, 374, 375, 376, 380, 381, 382, 394, 400, 404, 410, 411, 419, 422, 427, 430, 435, 436, 441, 463, 471, 472, 473, 474, 475, 481, 482, 516, 517, 557, 646 |
| `resume-ref.txt` | 1, 52, 108, 113, 179, 183, 187, 194, 195, 224, 232, 262, 298, 304, 353 |
| `src/main/java/com/mcode/agent/Agent.java` | 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 401, 432, 440, 488, 496, 514, 530 |
| `src/main/java/com/mcode/agent/AgentBudget.java` | 1, 3, 4, 24, 25, 30, 78, 80, 81, 82 |
| `src/main/java/com/mcode/agent/AgentMessage.java` | 1 |
| `src/main/java/com/mcode/agent/AgentOrchestrator.java` | 1, 5, 6, 7, 8, 9, 123, 124, 414 |
| `src/main/java/com/mcode/agent/AgentRole.java` | 1 |
| `src/main/java/com/mcode/agent/PlanExecuteAgent.java` | 1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 384, 577 |
| `src/main/java/com/mcode/agent/SubAgent.java` | 1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 157, 440 |
| `src/main/java/com/mcode/browser/BrowserAuditMetadata.java` | 1 |
| `src/main/java/com/mcode/browser/BrowserCheckResult.java` | 1 |
| `src/main/java/com/mcode/browser/BrowserConnectivityCheck.java` | 1 |
| `src/main/java/com/mcode/browser/BrowserConnector.java` | 1 |
| `src/main/java/com/mcode/browser/BrowserGuard.java` | 1, 50 |
| `src/main/java/com/mcode/browser/BrowserMode.java` | 1 |
| `src/main/java/com/mcode/browser/BrowserSession.java` | 1, 8 |
| `src/main/java/com/mcode/browser/SensitivePagePolicy.java` | 1, 32 |
| `src/main/java/com/mcode/cli/CliCommandParser.java` | 1 |
| `src/main/java/com/mcode/cli/LocalPathMentionExpander.java` | 1, 93, 118 |
| `src/main/java/com/mcode/cli/Main.java` | 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 100, 106, 108, 113, 114, 115, 116, 117, 118, 119, 120, 219, 238, 260, 261, 263, 292, 301, 302, 303, 305, 309, 310, 314, 324, 326, 338, 339, 341, 474, 480, 746, 865, 882, 884, 885, 990, 1050, 1111, 1116, 1285, 1306, 1583, 1584, 1600, 1601, 1621, 1625, 1694, 1697, 1704, 1715, 1722, 1728, 1916, 1921, 1931, 1942, 1954, 1968, 1973, 1997, 2035, 2209, 2241, 2293, 2294, 2295, 2487, 2489, 2592, 2597, 2621, 2625, 2667, 2668, 2669, 2670, 2671, 2672, 2790, 2794, 2834, 2852, 2863 |
| `src/main/java/com/mcode/cli/MCodeCompleter.java` | 1, 3, 4, 5, 15, 19, 23, 74 |
| `src/main/java/com/mcode/cli/MCodeHighlighter.java` | 1, 18 |
| `src/main/java/com/mcode/cli/MCodeHistory.java` | 1, 9 |
| `src/main/java/com/mcode/cli/PlanReviewInputParser.java` | 1 |
| `src/main/java/com/mcode/cli/ProjectMemoryInitializer.java` | 1, 18, 20, 25, 33, 88, 89, 113 |
| `src/main/java/com/mcode/cli/SkillCommandHandler.java` | 1, 3, 4, 5, 99 |
| `src/main/java/com/mcode/config/MCodeConfig.java` | 1, 17, 19, 94, 97, 102 |
| `src/main/java/com/mcode/context/ContextProfile.java` | 1, 3 |
| `src/main/java/com/mcode/context/TokenUsageFormatter.java` | 1, 3, 4 |
| `src/main/java/com/mcode/hitl/ApprovalPolicy.java` | 1 |
| `src/main/java/com/mcode/hitl/ApprovalRequest.java` | 1 |
| `src/main/java/com/mcode/hitl/ApprovalResult.java` | 1 |
| `src/main/java/com/mcode/hitl/HitlHandler.java` | 1 |
| `src/main/java/com/mcode/hitl/HitlToolRegistry.java` | 1, 3, 4, 5, 6, 19 |
| `src/main/java/com/mcode/hitl/RendererHitlHandler.java` | 1, 3 |
| `src/main/java/com/mcode/hitl/SwitchableHitlHandler.java` | 1 |
| `src/main/java/com/mcode/hitl/TerminalHitlHandler.java` | 1 |
| `src/main/java/com/mcode/image/ClipboardImage.java` | 1, 22 |
| `src/main/java/com/mcode/image/ImageProcessor.java` | 1, 3 |
| `src/main/java/com/mcode/image/ImageReferenceParser.java` | 1, 3 |
| `src/main/java/com/mcode/llm/AbstractOpenAiCompatibleClient.java` | 1, 24, 25, 26, 27 |
| `src/main/java/com/mcode/llm/AgnesClient.java` | 1 |
| `src/main/java/com/mcode/llm/DeepSeekClient.java` | 1 |
| `src/main/java/com/mcode/llm/FreeLlmApiClient.java` | 1 |
| `src/main/java/com/mcode/llm/GLMClient.java` | 1 |
| `src/main/java/com/mcode/llm/KimiClient.java` | 1 |
| `src/main/java/com/mcode/llm/LlmClient.java` | 1 |
| `src/main/java/com/mcode/llm/LlmClientFactory.java` | 1, 3, 9, 41 |
| `src/main/java/com/mcode/llm/LlmTraceLogger.java` | 1 |
| `src/main/java/com/mcode/llm/StepClient.java` | 1, 66 |
| `src/main/java/com/mcode/llm/XfyunMaaSClient.java` | 1 |
| `src/main/java/com/mcode/lsp/LspDiagnostic.java` | 1 |
| `src/main/java/com/mcode/lsp/LspDiagnosticFormatter.java` | 1, 3, 45, 47 |
| `src/main/java/com/mcode/lsp/LspDiagnosticReport.java` | 1 |
| `src/main/java/com/mcode/lsp/LspManager.java` | 1, 142, 144 |
| `src/main/java/com/mcode/lsp/LspSeverity.java` | 1 |
| `src/main/java/com/mcode/mcp/config/McpConfigFile.java` | 1 |
| `src/main/java/com/mcode/mcp/config/McpConfigLoader.java` | 1, 29, 30, 42 |
| `src/main/java/com/mcode/mcp/config/McpServerConfig.java` | 1 |
| `src/main/java/com/mcode/mcp/jsonrpc/JsonRpcClient.java` | 1, 6, 27 |
| `src/main/java/com/mcode/mcp/jsonrpc/JsonRpcException.java` | 1 |
| `src/main/java/com/mcode/mcp/jsonrpc/JsonRpcMessage.java` | 1 |
| `src/main/java/com/mcode/mcp/McpClient.java` | 1, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 27, 28 |
| `src/main/java/com/mcode/mcp/McpServer.java` | 1, 3, 4 |
| `src/main/java/com/mcode/mcp/McpServerManager.java` | 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 91, 172, 251, 277 |
| `src/main/java/com/mcode/mcp/McpServerStatus.java` | 1 |
| `src/main/java/com/mcode/mcp/mention/AtMentionCompleter.java` | 1, 3 |
| `src/main/java/com/mcode/mcp/mention/AtMentionExpander.java` | 1, 3, 45 |
| `src/main/java/com/mcode/mcp/mention/AtMentionParser.java` | 1 |
| `src/main/java/com/mcode/mcp/notifications/NotificationRouter.java` | 1, 27, 61 |
| `src/main/java/com/mcode/mcp/protocol/McpCallToolRequest.java` | 1 |
| `src/main/java/com/mcode/mcp/protocol/McpCallToolResult.java` | 1, 4, 5, 6, 70 |
| `src/main/java/com/mcode/mcp/protocol/McpCapabilities.java` | 1 |
| `src/main/java/com/mcode/mcp/protocol/McpContent.java` | 1 |
| `src/main/java/com/mcode/mcp/protocol/McpInitializeRequest.java` | 1, 18 |
| `src/main/java/com/mcode/mcp/protocol/McpInitializeResult.java` | 1 |
| `src/main/java/com/mcode/mcp/protocol/McpSchemaSanitizer.java` | 1 |
| `src/main/java/com/mcode/mcp/protocol/McpToolDescriptor.java` | 1 |
| `src/main/java/com/mcode/mcp/resources/McpResourceCache.java` | 1 |
| `src/main/java/com/mcode/mcp/resources/McpResourceContent.java` | 1 |
| `src/main/java/com/mcode/mcp/resources/McpResourceDescriptor.java` | 1 |
| `src/main/java/com/mcode/mcp/resources/McpResourceTool.java` | 1, 7, 8 |
| `src/main/java/com/mcode/mcp/transport/McpTransport.java` | 1 |
| `src/main/java/com/mcode/mcp/transport/StdioTransport.java` | 1, 130, 132, 146, 148 |
| `src/main/java/com/mcode/mcp/transport/StreamableHttpTransport.java` | 1, 5, 108 |
| `src/main/java/com/mcode/memory/ContextCompressor.java` | 1, 3 |
| `src/main/java/com/mcode/memory/ConversationHistoryCompactor.java` | 1, 3, 16 |
| `src/main/java/com/mcode/memory/ConversationMemory.java` | 1 |
| `src/main/java/com/mcode/memory/ExplicitMemoryHints.java` | 1 |
| `src/main/java/com/mcode/memory/LongTermMemory.java` | 1, 27, 28, 183 |
| `src/main/java/com/mcode/memory/Memory.java` | 1 |
| `src/main/java/com/mcode/memory/MemoryEntry.java` | 1 |
| `src/main/java/com/mcode/memory/MemoryManager.java` | 1, 3, 4 |
| `src/main/java/com/mcode/memory/MemoryQueryTokenizer.java` | 1, 4 |
| `src/main/java/com/mcode/memory/MemoryRetriever.java` | 1 |
| `src/main/java/com/mcode/memory/TokenBudget.java` | 1, 3 |
| `src/main/java/com/mcode/plan/ExecutionPlan.java` | 1 |
| `src/main/java/com/mcode/plan/Planner.java` | 1, 5, 6, 7, 8, 9, 10, 11, 12, 82 |
| `src/main/java/com/mcode/plan/Task.java` | 1 |
| `src/main/java/com/mcode/policy/AuditLog.java` | 1, 6, 24, 114, 118, 122 |
| `src/main/java/com/mcode/policy/CommandGuard.java` | 1 |
| `src/main/java/com/mcode/policy/PathGuard.java` | 1 |
| `src/main/java/com/mcode/policy/PolicyException.java` | 1 |
| `src/main/java/com/mcode/prompt/ProjectMemoryLoader.java` | 1, 16, 35, 63, 69, 71, 72, 73, 74, 81, 85, 89, 109, 131 |
| `src/main/java/com/mcode/prompt/PromptAssembler.java` | 1, 104 |
| `src/main/java/com/mcode/prompt/PromptContext.java` | 1 |
| `src/main/java/com/mcode/prompt/PromptMode.java` | 1 |
| `src/main/java/com/mcode/prompt/PromptRepository.java` | 1, 28, 29 |
| `src/main/java/com/mcode/rag/CodeAnalyzer.java` | 1 |
| `src/main/java/com/mcode/rag/CodeChunk.java` | 1 |
| `src/main/java/com/mcode/rag/CodeChunker.java` | 1 |
| `src/main/java/com/mcode/rag/CodeIndex.java` | 1 |
| `src/main/java/com/mcode/rag/CodeRelation.java` | 1 |
| `src/main/java/com/mcode/rag/CodeRetriever.java` | 1 |
| `src/main/java/com/mcode/rag/EmbeddingClient.java` | 1 |
| `src/main/java/com/mcode/rag/RagQueryTokenizer.java` | 1, 4 |
| `src/main/java/com/mcode/rag/SearchResultFormatter.java` | 1 |
| `src/main/java/com/mcode/rag/VectorStore.java` | 1, 24, 25 |
| `src/main/java/com/mcode/render/inline/AnsiSeq.java` | 1, 6 |
| `src/main/java/com/mcode/render/inline/BlockRegistry.java` | 1 |
| `src/main/java/com/mcode/render/inline/BottomStatusBar.java` | 1, 3, 4 |
| `src/main/java/com/mcode/render/inline/FoldableBlock.java` | 1 |
| `src/main/java/com/mcode/render/inline/InlineActivityDisplay.java` | 1, 54 |
| `src/main/java/com/mcode/render/inline/InlineApprovalPrompter.java` | 1, 4, 5, 6, 7, 23 |
| `src/main/java/com/mcode/render/inline/InlineDiffRenderer.java` | 1, 3 |
| `src/main/java/com/mcode/render/inline/InlineRenderer.java` | 1, 3, 4, 5, 6, 7, 8, 9 |
| `src/main/java/com/mcode/render/inline/SlashPalette.java` | 1, 3 |
| `src/main/java/com/mcode/render/inline/TerminalCapabilities.java` | 1, 41, 44 |
| `src/main/java/com/mcode/render/inline/ToolCallRenderer.java` | 1, 5, 6 |
| `src/main/java/com/mcode/render/PlainRenderer.java` | 1, 5, 6, 7, 8, 9 |
| `src/main/java/com/mcode/render/Renderer.java` | 1, 3, 4, 5 |
| `src/main/java/com/mcode/render/RendererFactory.java` | 1, 3, 4, 12, 16, 33, 37, 41, 42, 44, 56 |
| `src/main/java/com/mcode/render/StatusInfo.java` | 1 |
| `src/main/java/com/mcode/runtime/api/RuntimeApiServer.java` | 1, 5, 24, 31, 42, 44, 132 |
| `src/main/java/com/mcode/runtime/api/RuntimeEvent.java` | 1 |
| `src/main/java/com/mcode/runtime/api/RuntimeThreadStore.java` | 1, 25, 27, 30 |
| `src/main/java/com/mcode/runtime/CancellationContext.java` | 1 |
| `src/main/java/com/mcode/runtime/CancellationToken.java` | 1 |
| `src/main/java/com/mcode/runtime/task/DurableTask.java` | 1 |
| `src/main/java/com/mcode/runtime/task/DurableTaskManager.java` | 1, 46, 48, 51, 57, 59, 77 |
| `src/main/java/com/mcode/runtime/task/TaskCommandFormatter.java` | 1 |
| `src/main/java/com/mcode/runtime/task/TaskRunner.java` | 1 |
| `src/main/java/com/mcode/runtime/task/TaskStatus.java` | 1 |
| `src/main/java/com/mcode/skill/Skill.java` | 1, 7 |
| `src/main/java/com/mcode/skill/SkillBuiltinExtractor.java` | 1, 11 |
| `src/main/java/com/mcode/skill/SkillContextBuffer.java` | 1 |
| `src/main/java/com/mcode/skill/SkillFrontmatterParser.java` | 1 |
| `src/main/java/com/mcode/skill/SkillIndexFormatter.java` | 1 |
| `src/main/java/com/mcode/skill/SkillRegistry.java` | 1, 19, 20 |
| `src/main/java/com/mcode/skill/SkillStateStore.java` | 1 |
| `src/main/java/com/mcode/snapshot/RestoreResult.java` | 1 |
| `src/main/java/com/mcode/snapshot/SideGitManager.java` | 1, 31, 197 |
| `src/main/java/com/mcode/snapshot/SnapshotConfig.java` | 1, 18, 28, 29, 30, 31, 32 |
| `src/main/java/com/mcode/snapshot/SnapshotPhase.java` | 1 |
| `src/main/java/com/mcode/snapshot/SnapshotService.java` | 1, 19 |
| `src/main/java/com/mcode/snapshot/TurnSnapshot.java` | 1 |
| `src/main/java/com/mcode/tool/CodeSearchEngine.java` | 1 |
| `src/main/java/com/mcode/tool/JavaCodeSearchEngine.java` | 1 |
| `src/main/java/com/mcode/tool/RipgrepCodeSearchEngine.java` | 1, 35, 48 |
| `src/main/java/com/mcode/tool/ToolOutput.java` | 1, 3 |
| `src/main/java/com/mcode/tool/ToolRegistry.java` | 1, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 75, 1027, 1029, 1214, 1299, 1383 |
| `src/main/java/com/mcode/tui/config/TuiConfigPanel.java` | 1, 6, 20, 23 |
| `src/main/java/com/mcode/tui/highlight/CodeHighlighter.java` | 1, 3 |
| `src/main/java/com/mcode/tui/history/ConversationSnapshot.java` | 1, 19, 27 |
| `src/main/java/com/mcode/tui/hitl/TuiHitlHandler.java` | 1, 6, 7, 8, 58 |
| `src/main/java/com/mcode/tui/LanternaRenderer.java` | 1, 7, 8, 9, 10, 11, 12, 13, 27 |
| `src/main/java/com/mcode/tui/LanternaWindow.java` | 1, 8, 9, 28, 46, 60, 141 |
| `src/main/java/com/mcode/tui/pane/CenterPane.java` | 1, 6, 7, 8, 37, 73 |
| `src/main/java/com/mcode/tui/pane/FileTreePane.java` | 1, 6, 36, 72, 76 |
| `src/main/java/com/mcode/tui/pane/InputBar.java` | 1, 9, 39 |
| `src/main/java/com/mcode/tui/pane/StatusPane.java` | 1, 4, 35 |
| `src/main/java/com/mcode/tui/RootPane.java` | 1, 9, 10, 11, 12, 13, 14, 40, 56, 188 |
| `src/main/java/com/mcode/tui/TuiBootstrap.java` | 1, 3, 4, 5, 6, 7, 8, 9, 26, 27, 35, 36, 37, 38, 73, 129 |
| `src/main/java/com/mcode/tui/TuiSessionController.java` | 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 37, 52, 72 |
| `src/main/java/com/mcode/util/AnsiStyle.java` | 1, 115 |
| `src/main/java/com/mcode/util/JiebaSegmenterFactory.java` | 1 |
| `src/main/java/com/mcode/util/TerminalMarkdownRenderer.java` | 1, 548 |
| `src/main/java/com/mcode/web/FetchResult.java` | 1 |
| `src/main/java/com/mcode/web/HtmlExtractor.java` | 1 |
| `src/main/java/com/mcode/web/NetworkPolicy.java` | 1 |
| `src/main/java/com/mcode/web/SearchProvider.java` | 1 |
| `src/main/java/com/mcode/web/SearchProviderFactory.java` | 1, 62 |
| `src/main/java/com/mcode/web/SearchResult.java` | 1 |
| `src/main/java/com/mcode/web/SearxngSearchProvider.java` | 1, 88 |
| `src/main/java/com/mcode/web/SerpApiSearchProvider.java` | 1 |
| `src/main/java/com/mcode/web/WebFetcher.java` | 1, 62 |
| `src/main/java/com/mcode/web/ZhipuSearchProvider.java` | 1, 25 |
| `src/main/java/com/mcode/wechat/IlinkClient.java` | 1, 96 |
| `src/main/java/com/mcode/wechat/TerminalQrRenderer.java` | 1, 115 |
| `src/main/java/com/mcode/wechat/WechatAccount.java` | 1 |
| `src/main/java/com/mcode/wechat/WechatAccountStore.java` | 1 |
| `src/main/java/com/mcode/wechat/WechatAgentSession.java` | 1, 3, 4, 5, 6, 7, 8, 9, 22, 37 |
| `src/main/java/com/mcode/wechat/WechatCommandMain.java` | 1, 82, 83, 92, 135, 216, 217, 218, 219, 220, 225, 226 |
| `src/main/java/com/mcode/wechat/WechatCommandParser.java` | 1 |
| `src/main/java/com/mcode/wechat/WechatLoginResult.java` | 1 |
| `src/main/java/com/mcode/wechat/WechatMediaItem.java` | 1 |
| `src/main/java/com/mcode/wechat/WechatMessage.java` | 1 |
| `src/main/java/com/mcode/wechat/WechatMessageLoop.java` | 1, 3, 4, 5, 46, 190, 289 |
| `src/main/java/com/mcode/wechat/WechatMessageSender.java` | 1 |
| `src/main/java/com/mcode/wechat/WechatPaths.java` | 1, 10, 12, 15, 37 |
| `src/main/java/com/mcode/wechat/WechatPolicyConfig.java` | 1 |
| `src/main/java/com/mcode/wechat/WechatPolicyDecider.java` | 1 |
| `src/main/java/com/mcode/wechat/WechatPolicyDecision.java` | 1 |
| `src/main/java/com/mcode/wechat/WechatQrLogin.java` | 1 |
| `src/main/java/com/mcode/wechat/WechatRenderer.java` | 1, 3, 4, 5, 6, 7, 51 |
| `src/main/java/com/mcode/wechat/WechatTerminalRenderer.java` | 1, 3, 4, 5, 6, 7, 8 |
| `src/main/java/com/mcode/wechat/WechatTextFormatter.java` | 1 |
| `src/main/java/com/mcode/wechat/WechatToolRegistry.java` | 1, 3, 4, 5 |
| `src/main/java/com/mcode/wechat/WechatUpdate.java` | 1 |
| `src/main/resources/logback.xml` | 2, 3, 4, 5, 6, 9, 14 |
| `src/main/resources/prompts/base.md` | 3 |
| `src/main/resources/skills/web-access/references/cdp-cheatsheet.md` | 3, 38, 40 |
| `src/main/resources/skills/web-access/references/site-patterns/github.com.md` | 63 |
| `src/main/resources/skills/web-access/references/site-patterns/x.com.md` | 39 |
| `src/main/resources/skills/web-access/SKILL.md` | 7, 48, 77, 89, 109, 110 |
| `src/test/java/com/mcode/agent/AgentBudgetTest.java` | 1, 3, 4, 103, 105, 111, 113 |
| `src/test/java/com/mcode/agent/AgentClearHistoryTest.java` | 1, 3, 4, 26, 27, 56, 58 |
| `src/test/java/com/mcode/agent/AgentLspDiagnosticsTest.java` | 1, 3, 4 |
| `src/test/java/com/mcode/agent/AgentMemoryHintTest.java` | 1, 3, 4, 5, 25, 26, 46, 48 |
| `src/test/java/com/mcode/agent/AgentMessageTest.java` | 1 |
| `src/test/java/com/mcode/agent/AgentOrchestratorTest.java` | 1, 6, 7, 8, 9, 10 |
| `src/test/java/com/mcode/agent/AgentRoleTest.java` | 1 |
| `src/test/java/com/mcode/agent/AgentStreamRendererTest.java` | 1, 3, 4, 5, 6, 7, 103, 108, 115 |
| `src/test/java/com/mcode/agent/AgentWebSearchDecisionTest.java` | 1, 3, 4 |
| `src/test/java/com/mcode/agent/PlanExecuteAgentTest.java` | 1, 3, 4, 5, 6, 7, 8, 9, 10 |
| `src/test/java/com/mcode/agent/SubAgentTest.java` | 1, 3, 4, 5 |
| `src/test/java/com/mcode/browser/BrowserConnectivityCheckTest.java` | 1 |
| `src/test/java/com/mcode/browser/BrowserGuardTest.java` | 1 |
| `src/test/java/com/mcode/browser/BrowserSessionTest.java` | 1 |
| `src/test/java/com/mcode/browser/SensitivePagePolicyTest.java` | 1 |
| `src/test/java/com/mcode/cli/CliCommandParserTest.java` | 1 |
| `src/test/java/com/mcode/cli/LocalPathMentionExpanderTest.java` | 1 |
| `src/test/java/com/mcode/cli/MainBrowserCommandTest.java` | 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 |
| `src/test/java/com/mcode/cli/MainConfigBootstrapTest.java` | 1, 17, 28 |
| `src/test/java/com/mcode/cli/MainInputNormalizationTest.java` | 1, 3, 61, 62, 92, 94, 98 |
| `src/test/java/com/mcode/cli/MainPlanAgentFactoryTest.java` | 1, 3, 4, 5, 6, 7, 8 |
| `src/test/java/com/mcode/cli/MCodeCompleterTest.java` | 1, 6, 7, 15, 19, 31, 45, 55, 65, 75, 85, 95, 112, 129, 139, 149, 159 |
| `src/test/java/com/mcode/cli/MCodeHighlighterTest.java` | 1, 10, 11 |
| `src/test/java/com/mcode/cli/MCodeHistoryTest.java` | 1, 20, 26, 44, 67, 88 |
| `src/test/java/com/mcode/cli/PlanReviewInputParserTest.java` | 1 |
| `src/test/java/com/mcode/cli/ProjectMemoryInitializerTest.java` | 1, 18, 19, 20, 25, 27, 28, 36, 41, 46, 47, 52 |
| `src/test/java/com/mcode/cli/SkillCommandHandlerTest.java` | 1, 3, 4 |
| `src/test/java/com/mcode/context/ContextProfileTest.java` | 1, 3, 4 |
| `src/test/java/com/mcode/hitl/ApprovalPolicyTest.java` | 1 |
| `src/test/java/com/mcode/hitl/ApprovalResultTest.java` | 1 |
| `src/test/java/com/mcode/hitl/HitlToolRegistryTest.java` | 1, 7, 8, 9, 10 |
| `src/test/java/com/mcode/hitl/RendererHitlHandlerTest.java` | 1, 3, 4, 5 |
| `src/test/java/com/mcode/hitl/SwitchableHitlHandlerTest.java` | 1 |
| `src/test/java/com/mcode/hitl/TerminalHitlHandlerTest.java` | 1 |
| `src/test/java/com/mcode/image/ClipboardImageTest.java` | 1 |
| `src/test/java/com/mcode/image/ImageProcessorTest.java` | 1 |
| `src/test/java/com/mcode/image/ImageReferenceParserTest.java` | 1, 3 |
| `src/test/java/com/mcode/llm/AbstractOpenAiCompatibleClientImageInputTest.java` | 1, 19, 400, 401, 404 |
| `src/test/java/com/mcode/llm/LlmClientFactoryTest.java` | 1, 3, 16, 18, 30, 32, 45, 48, 62, 65, 80, 83, 99, 102, 120, 123, 139, 140 |
| `src/test/java/com/mcode/lsp/LspManagerTest.java` | 1 |
| `src/test/java/com/mcode/mcp/config/McpConfigLoaderTest.java` | 1, 102, 111 |
| `src/test/java/com/mcode/mcp/InMemoryTransport.java` | 1, 6 |
| `src/test/java/com/mcode/mcp/jsonrpc/JsonRpcClientTest.java` | 1, 5 |
| `src/test/java/com/mcode/mcp/McpClientTest.java` | 1, 5, 6, 7, 179, 181, 185, 187, 194 |
| `src/test/java/com/mcode/mcp/McpServerManagerTest.java` | 1, 3, 4, 5, 175 |
| `src/test/java/com/mcode/mcp/McpToolRegistrationTest.java` | 1, 4, 5, 6, 7, 125, 126, 131, 133 |
| `src/test/java/com/mcode/mcp/mention/AtMentionCompleterTest.java` | 1, 3 |
| `src/test/java/com/mcode/mcp/mention/AtMentionExpanderTest.java` | 1, 3, 4 |
| `src/test/java/com/mcode/mcp/mention/AtMentionParserTest.java` | 1 |
| `src/test/java/com/mcode/mcp/notifications/NotificationRouterTest.java` | 1 |
| `src/test/java/com/mcode/mcp/protocol/McpCallToolResultTest.java` | 1, 3, 4 |
| `src/test/java/com/mcode/mcp/protocol/McpSchemaSanitizerTest.java` | 1 |
| `src/test/java/com/mcode/mcp/resources/McpResourceCacheTest.java` | 1 |
| `src/test/java/com/mcode/mcp/transport/StdioTransportTest.java` | 1 |
| `src/test/java/com/mcode/mcp/transport/StreamableHttpTransportTest.java` | 1 |
| `src/test/java/com/mcode/memory/ConversationHistoryCompactorTest.java` | 1, 3 |
| `src/test/java/com/mcode/memory/ConversationMemoryTest.java` | 1 |
| `src/test/java/com/mcode/memory/ExplicitMemoryHintsTest.java` | 1 |
| `src/test/java/com/mcode/memory/LongTermMemoryTest.java` | 1 |
| `src/test/java/com/mcode/memory/MemoryEntryTest.java` | 1 |
| `src/test/java/com/mcode/memory/MemoryManagerTest.java` | 1, 3, 4 |
| `src/test/java/com/mcode/memory/MemoryRetrieverTest.java` | 1 |
| `src/test/java/com/mcode/memory/TokenBudgetTest.java` | 1, 3 |
| `src/test/java/com/mcode/plan/ExecutionPlanTest.java` | 1 |
| `src/test/java/com/mcode/plan/PlannerTest.java` | 1, 3, 4, 50 |
| `src/test/java/com/mcode/policy/AuditLogTest.java` | 1, 5 |
| `src/test/java/com/mcode/policy/CommandGuardTest.java` | 1 |
| `src/test/java/com/mcode/policy/PathGuardTest.java` | 1 |
| `src/test/java/com/mcode/prompt/ProjectMemoryLoaderTest.java` | 1, 22, 23, 24, 25, 26, 30, 43 |
| `src/test/java/com/mcode/prompt/PromptAssemblerTest.java` | 1, 23, 69 |
| `src/test/java/com/mcode/rag/CodeAnalyzerTest.java` | 1 |
| `src/test/java/com/mcode/rag/CodeChunkerTest.java` | 1 |
| `src/test/java/com/mcode/rag/CodeIndexTest.java` | 1, 22 |
| `src/test/java/com/mcode/rag/CodeRetrieverTest.java` | 1, 14, 19 |
| `src/test/java/com/mcode/rag/EmbeddingClientTest.java` | 1 |
| `src/test/java/com/mcode/rag/SearchResultFormatterTest.java` | 1, 15 |
| `src/test/java/com/mcode/rag/VectorStoreTest.java` | 1, 19 |
| `src/test/java/com/mcode/render/inline/AnsiSeqTest.java` | 1 |
| `src/test/java/com/mcode/render/inline/BlockRegistryTest.java` | 1 |
| `src/test/java/com/mcode/render/inline/BottomStatusBarTest.java` | 1, 3, 4 |
| `src/test/java/com/mcode/render/inline/FoldableBlockTest.java` | 1 |
| `src/test/java/com/mcode/render/inline/InlineActivityDisplayTest.java` | 1, 3, 39, 61, 83 |
| `src/test/java/com/mcode/render/inline/InlineApprovalPrompterTest.java` | 1, 3, 4 |
| `src/test/java/com/mcode/render/inline/InlineDiffRendererTest.java` | 1 |
| `src/test/java/com/mcode/render/inline/InlineRendererTest.java` | 1, 3, 4, 5, 6 |
| `src/test/java/com/mcode/render/inline/SlashPaletteTest.java` | 1 |
| `src/test/java/com/mcode/render/inline/TerminalCapabilitiesTest.java` | 1, 20, 26, 28, 69 |
| `src/test/java/com/mcode/render/inline/ToolCallRendererTest.java` | 1, 3 |
| `src/test/java/com/mcode/render/PlainRendererTest.java` | 1, 3, 4, 5 |
| `src/test/java/com/mcode/render/RendererFactoryTest.java` | 1, 16, 22, 24, 30, 37, 43, 49, 55, 61 |
| `src/test/java/com/mcode/runtime/api/RuntimeApiServerTest.java` | 1 |
| `src/test/java/com/mcode/runtime/CancellationContextTest.java` | 1 |
| `src/test/java/com/mcode/runtime/task/DurableTaskManagerTest.java` | 1 |
| `src/test/java/com/mcode/skill/LoadSkillToolTest.java` | 1, 3 |
| `src/test/java/com/mcode/skill/SkillBuiltinExtractorTest.java` | 1 |
| `src/test/java/com/mcode/skill/SkillContextBufferTest.java` | 1 |
| `src/test/java/com/mcode/skill/SkillFrontmatterParserTest.java` | 1 |
| `src/test/java/com/mcode/skill/SkillIndexFormatterTest.java` | 1 |
| `src/test/java/com/mcode/skill/SkillRegistryTest.java` | 1 |
| `src/test/java/com/mcode/skill/SkillStateStoreTest.java` | 1 |
| `src/test/java/com/mcode/snapshot/SideGitManagerTest.java` | 1 |
| `src/test/java/com/mcode/tool/CodeSearchGoldenSetTest.java` | 1, 27, 28, 55 |
| `src/test/java/com/mcode/tool/ToolRegistryTest.java` | 1, 5, 6, 125, 126, 146, 152, 153, 171 |
| `src/test/java/com/mcode/tui/highlight/CodeHighlighterTest.java` | 1 |
| `src/test/java/com/mcode/tui/history/ConversationSnapshotTest.java` | 1 |
| `src/test/java/com/mcode/tui/LanternaRendererTest.java` | 1, 3 |
| `src/test/java/com/mcode/tui/TuiBootstrapTest.java` | 1, 20, 21, 26, 33, 46, 55, 56 |
| `src/test/java/com/mcode/util/AnsiStyleTest.java` | 1 |
| `src/test/java/com/mcode/util/TerminalMarkdownRendererTest.java` | 1, 10, 80, 86, 87 |
| `src/test/java/com/mcode/web/HtmlExtractorTest.java` | 1, 21 |
| `src/test/java/com/mcode/web/NetworkPolicyTest.java` | 1 |
| `src/test/java/com/mcode/web/SearchProviderFactoryTest.java` | 1, 18 |
| `src/test/java/com/mcode/web/SearchResultTest.java` | 1 |
| `src/test/java/com/mcode/web/WebFetcherTest.java` | 1 |
| `src/test/java/com/mcode/web/ZhipuSearchProviderTest.java` | 1, 74, 75, 90 |
| `src/test/java/com/mcode/wechat/TerminalQrRendererTest.java` | 1 |
| `src/test/java/com/mcode/wechat/WechatCommandParserTest.java` | 1 |
| `src/test/java/com/mcode/wechat/WechatPolicyDeciderTest.java` | 1 |
| `src/test/java/com/mcode/wechat/WechatRendererTest.java` | 1, 106, 108 |
| `src/test/java/com/mcode/wechat/WechatTerminalRendererTest.java` | 1, 3 |
| `src/test/resources/code-search/golden-set.json` | 7, 15, 23, 31, 39 |
| `tools/redfox-dashboard/generate_ai_dashboard.py` | 242 |

## 路径改动

| 改动类型 | 新路径或删除目标 |
|---|---|
| 源码文件路径 | `.mcode` |
| 源码文件路径 | `.mcode/logs/mcode.log` |
| 删除广告资源 | `landing/*-poster-1200x1600.png` |
| 删除广告资源 | `landing/*-poster-600x800.png` |
| 删除广告资源 | `poster/assets/*-qr.png` |
| 源码文件路径 | `src/main/java/com/mcode` |
| 源码文件路径 | `src/main/java/com/mcode/cli/MCodeCompleter.java` |
| 源码文件路径 | `src/main/java/com/mcode/cli/MCodeHighlighter.java` |
| 源码文件路径 | `src/main/java/com/mcode/cli/MCodeHistory.java` |
| 源码文件路径 | `src/main/java/com/mcode/config/MCodeConfig.java` |
| 源码文件路径 | `src/test/java/com/mcode` |
| 源码文件路径 | `src/test/java/com/mcode/cli/MCodeCompleterTest.java` |
| 源码文件路径 | `src/test/java/com/mcode/cli/MCodeHighlighterTest.java` |
| 源码文件路径 | `src/test/java/com/mcode/cli/MCodeHistoryTest.java` |

## 审计范围

- 包含源码、测试、文档、配置、网页、脚本及当前工作区中的 UTF-8 文本文件。
- 构建产物、IDE 元数据、版本控制元数据和代码索引属于派生或外部状态，不直接改写。
