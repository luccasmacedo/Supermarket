package com.example.Supermarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not enough products on stock")
public class NotEnoughItemsOnStockException extends Exception {

  /**
   * Instantiates a new NotEnoughItemsOnStockException exception.
   *
   * @param message the message
   */
  public NotEnoughItemsOnStockException(String message) {
    super(message);
  }
}
