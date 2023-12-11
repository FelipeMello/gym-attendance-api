package com.felipemello.gym.attendance.service;

import com.felipemello.gym.attendance.entity.Attendance;
import com.felipemello.gym.attendance.entity.Member;
import com.felipemello.gym.attendance.exceptions.UserAlreadyExistsException;
import com.felipemello.gym.attendance.exceptions.UserNotFoundException;
import com.felipemello.gym.attendance.model.AttendanceDTO;
import com.felipemello.gym.attendance.model.MemberDTO;
import com.felipemello.gym.attendance.model.MemberResponseDTO;
import com.felipemello.gym.attendance.repository.MemberRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@AllArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;
  private final AttendanceService attendanceService;

  @Override
  public MemberResponseDTO getMemberById(Long userId) {
    log.info("Service. Getting user by id: {}", userId);
    Optional<Member> user = memberRepository.findById(userId);
    if (user.isEmpty()) {
      throw new UserNotFoundException(userId);
    }
    return MemberResponseDTO.builder()
        .id(user.get().getId())
        .name(user.get().getName())
        .email(user.get().getEmail())
        .attendances(CollectionUtils.isEmpty(user.get().getAttendances()) ? Collections.emptyList()
            : buildAttendanceDto(user.get().getAttendances())
        ).build();
  }

  private List<AttendanceDTO> buildAttendanceDto(List<Attendance> attendances) {
    return attendances.stream()
        .map(attendance -> AttendanceDTO.builder()
            .id(attendance.getId())
            .date(attendance.getDate())
            .build()).toList();
  }

  @Override
  public Member createMember(MemberDTO memberDTO) {
    log.info("Service. Creating member: {}", memberDTO);
    validateUserDTO(memberDTO);
    Member member = Member.builder()
        .name(memberDTO.getName())
        .email(memberDTO.getEmail())
        //password should be hashed
        .password(memberDTO.getPassword())
        .build();
    return memberRepository.save(member);
  }

  private void validateUserDTO(MemberDTO memberDTO) {
    memberRepository.findByEmail(memberDTO.getEmail()).ifPresent(user -> {
      log.error("Member already exists: {}", user);
      throw new UserAlreadyExistsException("Member already exists");
    });
  }

  @Override
  public Member updateMember(MemberDTO user) {
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
  public int getMemberStreak(Long userId) {
    validateUserId(userId);
    return attendanceService.calculateAttendanceStreak(userId);
  }

  private void validateUserId(Long userId) {
    Optional<Member> user = memberRepository.getMemberById(userId);
    // Check if the user exists
    if (user.isEmpty()) {
      throw new UserNotFoundException(userId);
    }
  }
}
