package com.example.Supermarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Product Not Found")
public class ProductNotFoundException extends Exception {

  /**
   * Instantiates a new ProductNotFoundException exception.
   *
   * @param message the message
   */
  public ProductNotFoundException(String message) {
    super(message);
  }
}
