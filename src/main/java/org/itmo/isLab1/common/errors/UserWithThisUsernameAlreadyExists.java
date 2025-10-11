package org.itmo.isLab1.common.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserWithThisUsernameAlreadyExists extends RuntimeException {
  public UserWithThisUsernameAlreadyExists() {
    super();
  }

  public UserWithThisUsernameAlreadyExists(String message) {
    super(message);
  }
}
