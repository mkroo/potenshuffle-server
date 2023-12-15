package best.beside.ctrl.teambuilder.infrastructure.ncp.response

data class ClovaResponse<Result>(val status: Status, val result: Result) {
    data class Status(val code: String, val message: String)
}
