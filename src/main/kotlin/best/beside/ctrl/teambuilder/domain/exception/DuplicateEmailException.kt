package best.beside.ctrl.teambuilder.domain.exception

import org.springframework.http.HttpStatus

class DuplicateEmailException : BusinessException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다")
