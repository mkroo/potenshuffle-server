package best.beside.ctrl.teambuilder.domain.exception

import org.springframework.http.HttpStatus

abstract class BusinessException(val httpStatus: HttpStatus, override val message: String) : Exception(message)