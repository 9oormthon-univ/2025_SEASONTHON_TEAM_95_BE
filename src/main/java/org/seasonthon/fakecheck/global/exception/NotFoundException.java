package org.seasonthon.fakecheck.global.exception;

import org.seasonthon.fakecheck.global.exception.dto.ErrorStatus;

public class NotFoundException extends RootException {

    public NotFoundException(ErrorStatus status) {
        super(status);
    }

    public NotFoundException(ErrorStatus status, Throwable cause) {
        super(status, cause);
    }

}