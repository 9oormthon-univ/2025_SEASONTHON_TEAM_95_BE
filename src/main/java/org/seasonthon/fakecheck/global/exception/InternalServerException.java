package org.seasonthon.fakecheck.global.exception;

import org.seasonthon.fakecheck.global.exception.dto.ErrorStatus;

public class InternalServerException extends RootException {

    public InternalServerException(ErrorStatus status) {
        super(status);
    }

    public InternalServerException(ErrorStatus status, Throwable cause) {
        super(status, cause);
    }

}