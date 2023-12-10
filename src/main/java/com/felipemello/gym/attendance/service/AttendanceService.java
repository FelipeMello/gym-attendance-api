package com.felipemello.gym.attendance.service;

import java.time.LocalDate;

public interface AttendanceService {

  void addAttendance(Long userId, LocalDate date);

  int calculateAttendanceStreak(Long userId);

}
