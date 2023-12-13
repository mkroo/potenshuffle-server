package best.beside.ctrl.teambuilder.presentation

import best.beside.ctrl.teambuilder.application.IntroductionGuideConversationService
import best.beside.ctrl.teambuilder.domain.dto.ConversationResponse
import best.beside.ctrl.teambuilder.domain.valueobject.Account
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class IntroductionGuideConversationController(
    private val introductionGuideConversationService: IntroductionGuideConversationService
) {
    data class SendMessageParams(
        val message: String,
    )

    @GetMapping("/introduction-guide-conversations/{conversationId}")
    @SecurityRequirement(name = "JsonWebToken")
    fun getIntroductionGuideConversation(
        @AuthenticationPrincipal account: Account,
        @PathVariable conversationId: Long,
    ): ConversationResponse {
        return introductionGuideConversationService.listMessages(account.userId, conversationId)
    }

    @PostMapping("/introduction-guide-conversations")
    @SecurityRequirement(name = "JsonWebToken")
    fun startConversation(
        @AuthenticationPrincipal account: Account
    ): ConversationResponse {
        return introductionGuideConversationService.startConversation(account.userId)
    }

    @PostMapping("/introduction-guide-conversations/{conversationId}/messages")
    @SecurityRequirement(name = "JsonWebToken")
    fun respondConversation(
        @AuthenticationPrincipal account: Account,
        @PathVariable conversationId: Long,
        @RequestBody params: SendMessageParams,
    ): ConversationResponse {
        return introductionGuideConversationService.respondConversation(account.userId, conversationId, params.message)
    }
}