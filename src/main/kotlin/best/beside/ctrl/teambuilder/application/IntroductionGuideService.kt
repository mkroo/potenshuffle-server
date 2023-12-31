package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.domain.dto.ChatbotMessage

interface IntroductionGuideService {
    fun answer(previousMessages: List<ChatbotMessage>, userMessage: ChatbotMessage.User): ChatbotMessage
    fun complete(messages: List<ChatbotMessage>): String
}