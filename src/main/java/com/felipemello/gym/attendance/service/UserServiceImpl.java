package com.felipemello.gym.attendance.service;

import com.felipemello.gym.attendance.entity.User;
import com.felipemello.gym.attendance.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public User getUserById(Long userId) {
    return userRepository.findById(userId).orElse(null);
  }

  // Implement other user-related methods
}
