package best.beside.ctrl.teambuilder.infrastructure.jsonwebtoken

import best.beside.ctrl.teambuilder.application.TokenProvider
import best.beside.ctrl.teambuilder.domain.valueobject.Account
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.LocalDateTime

@Component
class JsonWebTokenProvider : TokenProvider {
    private val secret = "Z2ZmbmprZnNkbmdkYnNodWFiaHFrd2puZWplYmh3anFiZWhqYmRkCg=="
    private val tokenValidityInSeconds = 3600L
    private val key = Keys.hmacShaKeyFor(secret.toByteArray())

    companion object {
        private val logger = LoggerFactory.getLogger(JsonWebTokenProvider::class.java)
    }

    override fun createToken(authentication: Authentication): String {
        val now = LocalDateTime.now()
        val validity = now.plusSeconds(tokenValidityInSeconds)

        return Jwts.builder()
            .setSubject(authentication.name)
            .signWith(key, SignatureAlgorithm.HS256)
            .setExpiration(Timestamp.valueOf(validity))
            .compact()
    }

    override fun getAuthentication(token: String): Authentication {
        val claims = Jwts.parserBuilder()
            .setSigningKey(secret.toByteArray())
            .build()
            .parseClaimsJws(token)
            .body

        val principal = Account(claims.subject.toLong(), "")

        return UsernamePasswordAuthenticationToken(principal, token, setOf())
    }

    override fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
            return true
        } catch (e: SecurityException) {
            logger.info("잘못된 JWT 서명입니다.")
        } catch (e: MalformedJwtException) {
            logger.info("잘못된 JWT 서명입니다.")
        } catch (e: ExpiredJwtException) {
            logger.info("만료된 JWT 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
            logger.info("지원되지 않는 JWT 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            logger.info("JWT 토큰이 잘못되었습니다.")
        }
        return false
    }
}