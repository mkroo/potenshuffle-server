package best.beside.ctrl.teambuilder.infrastructure.ncp.request

data class SummarizationRequest(
    val texts: List<String>,
    val autoSentenceSplitter: Boolean = true,
    val segCount: Int = -1,
    val segMaxSize: Int = 1000,
    val segMinSize: Int = 300,
    val maxTokens: Int = 256,
    val includeAiFilters: Boolean = true,
)