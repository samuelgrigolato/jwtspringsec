package com.quasarconsultoria.jwtspringsec.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class CredenciaisInvalidasException extends RuntimeException {
}
