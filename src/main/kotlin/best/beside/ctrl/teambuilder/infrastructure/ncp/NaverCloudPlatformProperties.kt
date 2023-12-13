package best.beside.ctrl.teambuilder.infrastructure.ncp

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "naver-cloud-platform")
data class NaverCloudPlatformProperties @ConstructorBinding constructor(
    val clovaStudioApiKey: String,
    val apiGatewayApiKey: String,
)
