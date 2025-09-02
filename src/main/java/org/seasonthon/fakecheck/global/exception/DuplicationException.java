package org.seasonthon.fakecheck.global.exception;

import org.seasonthon.fakecheck.global.exception.dto.ErrorStatus;

public class DuplicationException extends RootException {

    public DuplicationException(ErrorStatus status) {
        super(status);
    }

    public DuplicationException(ErrorStatus status, Throwable cause) {
        super(status, cause);
    }

}