package org.seasonthon.fakecheck.global.exception;

import org.seasonthon.fakecheck.global.exception.dto.ErrorStatus;

public class BadRequestException extends RootException {

    public BadRequestException(ErrorStatus status) {
        super(status);
    }

    public BadRequestException(ErrorStatus status, Throwable cause) {
        super(status, cause);
    }

}