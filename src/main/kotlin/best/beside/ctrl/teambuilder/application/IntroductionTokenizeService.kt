package best.beside.ctrl.teambuilder.application

interface IntroductionTokenizeService {
    fun tokenize(introduction: String): List<String>
}