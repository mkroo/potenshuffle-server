package best.beside.ctrl.teambuilder.domain.exception

import org.springframework.http.HttpStatus

class UserNotFoundException : BusinessException(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다")