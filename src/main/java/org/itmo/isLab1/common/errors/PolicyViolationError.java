package org.itmo.isLab1.common.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PolicyViolationError extends RuntimeException {
    public PolicyViolationError() {
        super();
    }

    public PolicyViolationError(String message) {
        super(message);
    }
}