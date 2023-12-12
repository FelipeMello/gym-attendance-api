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
    List<Attendance> userAttendances = attendanceRepository.findByMemberIdOrderByDateDesc(userId);

    if (userAttendances.isEmpty()) {
      return 0;
    }

    int streak = 0;
    LocalDate startDate = userAttendances.getFirst().getDate();

    for (Attendance attendance : userAttendances) {
      LocalDate currentDate = attendance.getDate();

      // Check if the attendance is within the same week as the previous one
      if (currentDate.minusWeeks(1).isBefore(startDate)) {
        streak++;
      } else {
        streak = 1; // Start a new streak if attendance is in a new week
        startDate = currentDate;
      }
    }
    return streak;
  }

}
