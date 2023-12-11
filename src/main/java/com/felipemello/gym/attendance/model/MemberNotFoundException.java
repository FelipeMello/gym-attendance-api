package com.felipemello.gym.attendance.model;

public class MemberNotFoundException extends RuntimeException {

  public MemberNotFoundException(Long userId) {
    super("Member with id " + userId + " not found");
  }
}
