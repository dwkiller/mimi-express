package com.mimi.core.common.exception;

import lombok.Getter;

/**
 * 业务异常
 *
 */
@Getter
public class MimiException extends RuntimeException {
    private static final long serialVersionUID = 6610083281801529147L;

    private Throwable throwable;

    public MimiException(String message) {
        super(message);
    }

    public MimiException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }
}
