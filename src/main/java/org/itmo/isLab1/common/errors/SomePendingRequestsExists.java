package org.itmo.isLab1.common.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SomePendingRequestsExists extends RuntimeException {
    public SomePendingRequestsExists() {
        super();
    }

    public SomePendingRequestsExists(String message) {
        super(message);
    }
}
