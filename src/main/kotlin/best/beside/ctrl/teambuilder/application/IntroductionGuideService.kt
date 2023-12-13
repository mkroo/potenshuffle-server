package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.domain.dto.ChatbotMessage

interface IntroductionGuideService {
    fun answer(previousMessage: List<ChatbotMessage>, userMessage: ChatbotMessage.User): ChatbotMessage.Bot
}