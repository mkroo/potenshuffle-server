package best.beside.ctrl.teambuilder.infrastructure.springDocs

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@Configuration
class SpringDocsConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val contact = Contact()
            .name("mkroo")
            .email("ansrl0107@gmail.com")

        val info = Info()
            .title("Team Builder API")
            .description("Team Builder API 명세서입니다.")
            .version("1.0.0")
            .contact(contact)

        val bearerAuth = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER)
            .name(HttpHeaders.AUTHORIZATION)

        return OpenAPI()
            .components(Components().addSecuritySchemes("JsonWebToken", bearerAuth))
            .info(info)
    }
}