package com.felipemello.gym.attendance.service;

import com.felipemello.gym.attendance.entity.User;
import com.felipemello.gym.attendance.exceptions.UserAlreadyExistsException;
import com.felipemello.gym.attendance.exceptions.UserNotFoundException;
import com.felipemello.gym.attendance.model.UserDTO;
import com.felipemello.gym.attendance.repository.UserRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final AttendanceService attendanceService;

  @Override
  public User getUserById(Long userId) {
    log.info("Service. Getting user by id: {}", userId);
    Optional<User> user = userRepository.findById(userId);
    if (user.isEmpty()) {
      throw new UserNotFoundException(userId);
    }
    return user.get();
  }

  @Override
  public User createUser(UserDTO userDTO) {
    log.info("Service. Creating user: {}", userDTO);
    validateUserDTO(userDTO);
    User user = User.builder()
        .name(userDTO.getName())
        .email(userDTO.getEmail())
        //password should be hashed
        .password(userDTO.getPassword())
        .build();
    return userRepository.save(user);
  }

  private void validateUserDTO(UserDTO userDTO) {
    userRepository.findByEmail(userDTO.getEmail()).ifPresent(user -> {
      log.error("User already exists: {}", user);
      throw new UserAlreadyExistsException("User already exists");
    });
  }

  /**
   * TODO Implement this method
   * @param user UserDTO
   * @return User
   */
  @Override
  public User updateUser(UserDTO user) {
    return null;
  }

  @Override
  public boolean checkDiscountEligibility(Long userId) {
    validateUserId(userId);
    // Check if the user has a streak of at least 3 weeks
    int streakWeeks = attendanceService.calculateAttendanceStreak(userId);
    return streakWeeks >= 3;
  }

  @Override
  public int getUserStreak(Long userId) {
    validateUserId(userId);
    return attendanceService.calculateAttendanceStreak(userId);
  }

  private void validateUserId(Long userId) {
    Optional<User> user = userRepository.getUserById(userId);
    // Check if the user exists
    if (user.isEmpty()) {
      throw new UserNotFoundException(userId);
    }
  }
}
