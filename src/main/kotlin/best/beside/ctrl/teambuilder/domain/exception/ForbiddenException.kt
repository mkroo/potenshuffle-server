package best.beside.ctrl.teambuilder.domain.exception

import org.springframework.http.HttpStatus

class ForbiddenException(message: String = "허용되지 않은 동작입니다") : BusinessException(HttpStatus.FORBIDDEN, message)