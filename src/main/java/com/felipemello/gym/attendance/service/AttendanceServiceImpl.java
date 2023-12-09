package com.felipemello.gym.attendance.service;

import com.felipemello.gym.attendance.entity.Attendance;
import com.felipemello.gym.attendance.entity.User;
import com.felipemello.gym.attendance.model.UserNotFoundException;
import com.felipemello.gym.attendance.repository.AttendanceRepository;
import com.felipemello.gym.attendance.repository.UserRepository;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

  private final UserRepository userRepository;
  private final AttendanceRepository attendanceRepository;

  @Override
  public void addAttendance(Long userId, LocalDate date) {
    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    Attendance attendance = new Attendance();
    attendance.setUser(user);
    attendance.setDate(date);
    attendanceRepository.save(attendance);
  }
}
