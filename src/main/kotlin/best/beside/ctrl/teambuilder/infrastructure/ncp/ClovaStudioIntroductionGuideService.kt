package best.beside.ctrl.teambuilder.infrastructure.ncp

import best.beside.ctrl.teambuilder.application.IntroductionGuideService
import best.beside.ctrl.teambuilder.domain.dto.ChatbotMessage
import org.springframework.stereotype.Service

@Service
class ClovaStudioIntroductionGuideService(
    private val clovaStudioClient: ClovaStudioClient
): IntroductionGuideService {
    override fun answer(previousMessages: List<ChatbotMessage>, userMessage: ChatbotMessage.User): ChatbotMessage.Bot {
        previousMessages.plus(userMessage).map(::convert)

        val request = ChatCompletionRequest(
            messages = listOf(systemMessage) + previousMessages.plus(userMessage).map(::convert)
        )

        val response = clovaStudioClient.chatCompletion(request)
        val responseMessage = response.result.message.content

        return ChatbotMessage.Bot(responseMessage)
    }

    private

    val systemMessage = ChatCompletionRequest.Message(
        role = "system",
        content = """
            사용자에게 아래 항목들을 하나씩 물어봐야합니다.
            - 같은 팀으로 함께하고 싶은 사람들의 성향
            - 참여 가능한 시간
            - 직군
            - 강점
            사용자가 입력한 내용 중, 위 항목이 포함되어 있지 않다면 해당 항목을 다시 입력받을 수 있도록 질문합니다.
            위 항목들을 모두 입력받았다면 사용자에게 자기소개를 출력합니다.
        """.trimIndent()
    )

    fun convert(message: ChatbotMessage): ChatCompletionRequest.Message {
        return when (message) {
            is ChatbotMessage.Bot -> ChatCompletionRequest.Message(role = "assistant", content = message.content)
            is ChatbotMessage.User -> ChatCompletionRequest.Message(role = "user", content = message.content)
        }
    }
}