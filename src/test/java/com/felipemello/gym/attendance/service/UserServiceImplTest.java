package com.felipemello.gym.attendance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.felipemello.gym.attendance.entity.User;
import com.felipemello.gym.attendance.exceptions.UserAlreadyExistsException;
import com.felipemello.gym.attendance.exceptions.UserNotFoundException;
import com.felipemello.gym.attendance.model.UserDTO;
import com.felipemello.gym.attendance.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private AttendanceService attendanceService;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  void testGetUserById() {
    Long userId = 1L;
    User mockUser = new User();
    mockUser.setId(userId);

    when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

    User result = userService.getUserById(userId);

    assertEquals(userId, result.getId());
  }

  @Test
  void testGetUserById_UserNotFound() {
    Long userId = 1L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));

    verify(userRepository, times(1)).findById(userId);
  }

  @Test
  void testCreateUser() {
    UserDTO userDTO = UserDTO.builder()
        .email("john@example.com")
        .name("John Doe")
        .password("password123")
        .build();

    when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
      User user = invocation.getArgument(0);
      user.setId(1L);
      user.setName(userDTO.getName());
      user.setEmail(userDTO.getEmail());
      user.setPassword(userDTO.getPassword());
      return user;
    });

    User result = userService.createUser(userDTO);

    assertEquals(userDTO.getName(), result.getName());
    assertEquals(userDTO.getEmail(), result.getEmail());
    assertEquals(userDTO.getPassword(), result.getPassword());
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void testCreateUser_UserAlreadyExists() {
    UserDTO userDTO = UserDTO.builder()
        .email("john@example.com")
        .name("John Doe")
        .password("password123")
        .build();

    when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(new User()));

    assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userDTO));

    verify(userRepository, times(1)).findByEmail(userDTO.getEmail());
  }

  @Test
  void testCheckDiscountEligibility() {
    Long userId = 1L;

    when(userRepository.getUserById(userId)).thenReturn(Optional.of(new User()));
    when(attendanceService.calculateAttendanceStreak(userId)).thenReturn(3);

    assertTrue(userService.checkDiscountEligibility(userId));

    verify(userRepository, times(1)).getUserById(userId);
    verify(attendanceService, times(1)).calculateAttendanceStreak(userId);
  }

  @Test
  void testCheckDiscountEligibility_NotElegible() {
    Long userId = 1L;

    when(userRepository.getUserById(userId)).thenReturn(Optional.of(new User()));
    when(attendanceService.calculateAttendanceStreak(userId)).thenReturn(2);

    assertFalse(userService.checkDiscountEligibility(userId));

    verify(userRepository, times(1)).getUserById(userId);
    verify(attendanceService, times(1)).calculateAttendanceStreak(userId);
  }

  @Test
  void testCheckDiscountEligibility_UserNotFound() {
    Long userId = 1L;
    when(userRepository.getUserById(userId)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.checkDiscountEligibility(userId));

    verify(userRepository, times(1)).getUserById(userId);
    verifyNoInteractions(attendanceService); // Ensure that attendanceService is not called
  }


  @Test
  void testGetUserStreak() {
    Long userId = 1L;

    when(userRepository.getUserById(userId)).thenReturn(Optional.of(new User()));
    when(attendanceService.calculateAttendanceStreak(userId)).thenReturn(3);

    assertEquals(3, userService.getUserStreak(userId));

    verify(userRepository, times(1)).getUserById(userId);
    verify(attendanceService, times(1)).calculateAttendanceStreak(userId);
  }

  @Test
  void testGetUserStreak_UserNotFound() {
    Long userId = 1L;
    when(userRepository.getUserById(userId)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.getUserStreak(userId));

    verify(userRepository, times(1)).getUserById(userId);
    verifyNoInteractions(attendanceService); // Ensure that attendanceService is not called
  }
}