package com.felipemello.gym.attendance.service;

import com.felipemello.gym.attendance.entity.Attendance;
import com.felipemello.gym.attendance.entity.Member;
import com.felipemello.gym.attendance.exceptions.UserNotFoundException;
import com.felipemello.gym.attendance.repository.AttendanceRepository;
import com.felipemello.gym.attendance.repository.MemberRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

  private final MemberRepository memberRepository;
  private final AttendanceRepository attendanceRepository;

  @Override
  public void addAttendance(Long userId, LocalDate date) {
    Member member = memberRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
    Attendance attendance = new Attendance();
    attendance.setMember(member);
    attendance.setDate(date);
    attendanceRepository.save(attendance);
  }

  @Override
  public int calculateAttendanceStreak(Long userId) {
    List<Attendance> userAttendances = attendanceRepository.findByMemberIdOrderByDateAsc(userId);

    if (userAttendances.isEmpty()) {
      return 0;
    }

    int maxStreak = 0;
    int streak = 0;

    LocalDate previousDate = userAttendances.getFirst().getDate();

    for (Attendance attendance : userAttendances) {
      LocalDate currentDate = attendance.getDate();

      if (previousDate.plusDays(7).isBefore(currentDate)) {
        maxStreak = Math.max(maxStreak, streak);
        streak = 0;
      }

      streak++;
      previousDate = currentDate;
    }

    return Math.max(maxStreak, streak);
  }


}
