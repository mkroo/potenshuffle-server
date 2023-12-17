package best.beside.ctrl.teambuilder.infrastructure.ncp.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "naver-cloud-platform")
data class NaverCloudPlatformProperties @ConstructorBinding constructor(
    val apiGatewayKey: String,
    val chatCompletion: ChatCompletion,
    val summarization: Summarization,
    val intentRecognize: ChatCompletion,
) {
    data class ChatCompletion(val modelName: String, val apiKey: String)
    data class Summarization(val appId: String, val apiKey: String)
}
