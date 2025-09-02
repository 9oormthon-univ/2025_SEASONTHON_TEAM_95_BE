package org.seasonthon.fakecheck.global.exception;

import org.seasonthon.fakecheck.global.exception.dto.ErrorStatus;

public class UnAuthenticationException extends RootException {

    public UnAuthenticationException(ErrorStatus status) {
        super(status);
    }

    public UnAuthenticationException(ErrorStatus status, Throwable cause) {
        super(status, cause);
    }

}