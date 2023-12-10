package com.felipemello.gym.attendance.controller;

import com.felipemello.gym.attendance.service.AttendanceService;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/attendances")
public class AttendanceController {

  private final AttendanceService attendanceService;

  @PostMapping()
  public ResponseEntity<String> addAttendance(@RequestParam Long userId,
      @RequestParam String date) {
    log.info("Controller. Adding attendance for user id: {} and date: {}", userId, date);
    // Convert date string to LocalDate (implement the conversion logic in a utility class)
    // The format is yyyy-MM-dd.
    LocalDate attendanceDate = LocalDate.parse(date);
    attendanceService.addAttendance(userId, attendanceDate);
    return ResponseEntity.status(HttpStatus.CREATED).body("Attendance added successfully");
  }
}
