package com.book.userservice.exception;

public class UnauthorizedAccessException extends RuntimeException {
  public UnauthorizedAccessException(String message) {
    super(message);
  }
}
