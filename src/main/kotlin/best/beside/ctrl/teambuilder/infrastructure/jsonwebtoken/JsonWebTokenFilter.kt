package best.beside.ctrl.teambuilder.infrastructure.jsonwebtoken

import best.beside.ctrl.teambuilder.application.TokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JsonWebTokenFilter(
    private val tokenProvider: TokenProvider,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val jsonWebToken = resolveToken(httpServletRequest)
        if (jsonWebToken != null) {
            SecurityContextHolder.getContext().authentication = getAuthentication(jsonWebToken)
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }

    private

    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION)
        logger.debug("token: $bearerToken")

        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            logger.debug("토큰이 없습니다.")
            return null
        }

        return bearerToken.substring(7)
    }

    fun getAuthentication(jsonWebToken: String): Authentication {
        return tokenProvider.getAuthentication(jsonWebToken).also {
            logger.debug("authentication: $it")
        }
    }
}