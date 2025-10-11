package org.itmo.isLab1.common.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AdminRequestAlreadyProcessed extends RuntimeException {
    public AdminRequestAlreadyProcessed() {
        super();
    }

    public AdminRequestAlreadyProcessed(String message) {
        super(message);
    }
}