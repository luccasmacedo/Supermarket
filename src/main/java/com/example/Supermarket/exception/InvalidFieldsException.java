package com.example.Supermarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Fields provided")
public class InvalidFieldsException extends Exception {

  /**
   * Instantiates a new InvalidFieldsException exception.
   *
   * @param message the message
   */
  public InvalidFieldsException(String message) {
    super(message);
  }
}
