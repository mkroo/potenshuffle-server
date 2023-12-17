package best.beside.ctrl.teambuilder.infrastructure.security

import best.beside.ctrl.teambuilder.infrastructure.jsonwebtoken.JsonWebTokenAuthenticationEntryPoint
import best.beside.ctrl.teambuilder.infrastructure.jsonwebtoken.JsonWebTokenDeniedHandler
import best.beside.ctrl.teambuilder.infrastructure.jsonwebtoken.JsonWebTokenFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
class SecurityConfig(
    private val jsonWebTokenAuthenticationEntryPoint: JsonWebTokenAuthenticationEntryPoint,
    private val jsonWebTokenDeniedHandler: JsonWebTokenDeniedHandler,
) {
    @Bean
    fun filterChain(http: HttpSecurity, jsonWebTokenFilter: JsonWebTokenFilter): SecurityFilterChain {
        http.csrf { it.disable() }
            .formLogin {
                it.disable()
            }
            .httpBasic { it.disable() }
            .headers {
                it.frameOptions { frameOptions -> frameOptions.disable() }
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it.requestMatchers("/favicon.ico").permitAll()
                it.requestMatchers("/api-docs/**").permitAll()
                it.requestMatchers("/swagger-ui/**").permitAll()
                it.requestMatchers("/sign-in", "/sign-up", "/user-cards", "/teams").permitAll()
                it.requestMatchers("/users/*/information").permitAll()
                it.anyRequest().authenticated()
            }
            .addFilterBefore(jsonWebTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint(jsonWebTokenAuthenticationEntryPoint)
                it.accessDeniedHandler(jsonWebTokenDeniedHandler)
            }

        return http.build()
    }
}