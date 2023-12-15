package best.beside.ctrl.teambuilder.infrastructure.ncp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class HttpClientConfig(
    val properties: NaverCloudPlatformProperties
) {
    @Bean
    fun clovaStudioClient(builder: RestClient.Builder): ClovaStudioClient {
        val client = builder
            .baseUrl("https://clovastudio.stream.ntruss.com")
            .defaultHeader("X-NCP-APIGW-API-KEY", properties.apiGatewayKey)
            .defaultHeader(HttpHeaders.ACCEPT, "application/json")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .build()

        val adapter = RestClientAdapter.create(client)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(ClovaStudioClient::class.java)
    }
}
