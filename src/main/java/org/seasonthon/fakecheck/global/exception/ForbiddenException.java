package org.seasonthon.fakecheck.global.exception;

import org.seasonthon.fakecheck.global.exception.dto.ErrorStatus;

public class ForbiddenException extends RootException {

    public ForbiddenException(ErrorStatus status) {
        super(status);
    }

    public ForbiddenException(ErrorStatus status, Throwable cause) {
        super(status, cause);
    }

}