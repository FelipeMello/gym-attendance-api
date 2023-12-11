package com.felipemello.gym.attendance.exceptions;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(Long userId) {
    super("Member with id " + userId + " not found");
  }
}
