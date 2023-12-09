package com.felipemello.gym.attendance.controller;

import com.felipemello.gym.attendance.entity.User;
import com.felipemello.gym.attendance.service.AttendanceService;
import com.felipemello.gym.attendance.service.UserService;
import java.time.LocalDate;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class GymController {

  private final UserService userService;
  private final AttendanceService attendanceService;

  @GetMapping("/users/{userId}")
  public ResponseEntity<User> getUserById(@PathVariable Long userId) {
    log.info("Controller. Getting user by id: {}", userId);
    User user = userService.getUserById(userId);
    return ResponseEntity.of(Optional.ofNullable(user));
  }

  @PostMapping("/attendances")
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
