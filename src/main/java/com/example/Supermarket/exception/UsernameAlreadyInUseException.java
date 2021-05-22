package com.example.Supermarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Username in Use")
public class UsernameAlreadyInUseException extends Exception {

  /**
   * Instantiates a new UsernameAlreadyInUse exception.
   *
   * @param message the message
   */
  public UsernameAlreadyInUseException(String message) {
    super(message);
  }
}
