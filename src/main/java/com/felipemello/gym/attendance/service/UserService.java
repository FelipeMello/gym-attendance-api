package com.felipemello.gym.attendance.service;

import com.felipemello.gym.attendance.entity.User;
import com.felipemello.gym.attendance.model.UserDTO;

public interface UserService {

  User getUserById(Long userId);

  User createUser(UserDTO user);

  User updateUser(UserDTO user);

  boolean checkDiscountEligibility(Long userId);

  int getUserStreak(Long userId);
}
