package com.devlife.job_management.domain.exception;

public class UserAlreadyExistsException extends RuntimeException{

  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
