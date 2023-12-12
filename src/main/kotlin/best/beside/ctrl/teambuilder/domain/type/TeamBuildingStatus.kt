package best.beside.ctrl.teambuilder.domain.type

enum class TeamBuildingStatus(val value: String) {
    WAITING("제안 대기 중"),
    IN_PROGRESS("팀 구성 중"),
    COMPLETED("완료"),
}