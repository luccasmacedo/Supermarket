package com.example.Supermarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Payment not found")
public class PaymentNotFoundException extends Exception {

  /**
   * Instantiates a new PaymentNotFoundException exception.
   *
   * @param message the message
   */
  public PaymentNotFoundException(String message) {
    super(message);
  }
}
