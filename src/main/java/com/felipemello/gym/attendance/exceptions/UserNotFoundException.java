package com.felipemello.gym.attendance.exceptions;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(Long userId) {
    super("User with id " + userId + " not found");
  }
}
