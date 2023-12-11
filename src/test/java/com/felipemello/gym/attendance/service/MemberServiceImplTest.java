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

import com.felipemello.gym.attendance.entity.Member;
import com.felipemello.gym.attendance.exceptions.UserAlreadyExistsException;
import com.felipemello.gym.attendance.exceptions.UserNotFoundException;
import com.felipemello.gym.attendance.model.MemberDTO;
import com.felipemello.gym.attendance.model.MemberResponseDTO;
import com.felipemello.gym.attendance.repository.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private AttendanceService attendanceService;

  @InjectMocks
  private MemberServiceImpl userService;

  @Test
  void testGetUserById() {
    Long userId = 1L;
    Member mockMember = new Member();
    mockMember.setId(userId);

    when(memberRepository.findById(userId)).thenReturn(Optional.of(mockMember));

    MemberResponseDTO result = userService.getMemberById(userId);

    assertEquals(userId, result.getId());
  }

  @Test
  void testGetUserById_UserNotFound() {
    Long userId = 1L;
    when(memberRepository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.getMemberById(userId));

    verify(memberRepository, times(1)).findById(userId);
  }

  @Test
  void testCreateUser() {
    MemberDTO memberDTO = MemberDTO.builder()
        .email("john@example.com")
        .name("John Doe")
        .password("password123")
        .build();

    when(memberRepository.findByEmail(memberDTO.getEmail())).thenReturn(Optional.empty());
    when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> {
      Member member = invocation.getArgument(0);
      member.setId(1L);
      member.setName(memberDTO.getName());
      member.setEmail(memberDTO.getEmail());
      member.setPassword(memberDTO.getPassword());
      return member;
    });

    Member result = userService.createMember(memberDTO);

    assertEquals(memberDTO.getName(), result.getName());
    assertEquals(memberDTO.getEmail(), result.getEmail());
    assertEquals(memberDTO.getPassword(), result.getPassword());
    verify(memberRepository, times(1)).save(any(Member.class));
  }

  @Test
  void testCreateUser_UserAlreadyExists() {
    MemberDTO memberDTO = MemberDTO.builder()
        .email("john@example.com")
        .name("John Doe")
        .password("password123")
        .build();

    when(memberRepository.findByEmail(memberDTO.getEmail())).thenReturn(Optional.of(new Member()));

    assertThrows(UserAlreadyExistsException.class, () -> userService.createMember(memberDTO));

    verify(memberRepository, times(1)).findByEmail(memberDTO.getEmail());
  }

  @Test
  void testCheckDiscountEligibility() {
    Long userId = 1L;

    when(memberRepository.getMemberById(userId)).thenReturn(Optional.of(new Member()));
    when(attendanceService.calculateAttendanceStreak(userId)).thenReturn(3);

    assertTrue(userService.checkDiscountEligibility(userId));

    verify(memberRepository, times(1)).getMemberById(userId);
    verify(attendanceService, times(1)).calculateAttendanceStreak(userId);
  }

  @Test
  void testCheckDiscountEligibility_NotElegible() {
    Long userId = 1L;

    when(memberRepository.getMemberById(userId)).thenReturn(Optional.of(new Member()));
    when(attendanceService.calculateAttendanceStreak(userId)).thenReturn(2);

    assertFalse(userService.checkDiscountEligibility(userId));

    verify(memberRepository, times(1)).getMemberById(userId);
    verify(attendanceService, times(1)).calculateAttendanceStreak(userId);
  }

  @Test
  void testCheckDiscountEligibility_UserNotFound() {
    Long userId = 1L;
    when(memberRepository.getMemberById(userId)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.checkDiscountEligibility(userId));

    verify(memberRepository, times(1)).getMemberById(userId);
    verifyNoInteractions(attendanceService); // Ensure that attendanceService is not called
  }


  @Test
  void testGetUserStreak() {
    Long userId = 1L;

    when(memberRepository.getMemberById(userId)).thenReturn(Optional.of(new Member()));
    when(attendanceService.calculateAttendanceStreak(userId)).thenReturn(3);

    assertEquals(3, userService.getMemberStreak(userId));

    verify(memberRepository, times(1)).getMemberById(userId);
    verify(attendanceService, times(1)).calculateAttendanceStreak(userId);
  }

  @Test
  void testGetUserStreak_UserNotFound() {
    Long userId = 1L;
    when(memberRepository.getMemberById(userId)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.getMemberStreak(userId));

    verify(memberRepository, times(1)).getMemberById(userId);
    verifyNoInteractions(attendanceService); // Ensure that attendanceService is not called
  }
}