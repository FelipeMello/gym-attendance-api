package com.felipemello.gym.attendance.controller;

import com.felipemello.gym.attendance.entity.User;
import com.felipemello.gym.attendance.model.UserDTO;
import com.felipemello.gym.attendance.service.UserService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

  private final UserService userService;


  @GetMapping("/{userId}")
  public ResponseEntity<User> getUserById(@PathVariable Long userId) {
    log.info("Controller. Getting user by id: {}", userId);
    User user = userService.getUserById(userId);
    return ResponseEntity.of(Optional.ofNullable(user));
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
    log.info("Controller. Creating user: {}", userDTO);
    User user = userService.createUser(userDTO);
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @GetMapping("/{userId}/discount-eligibility")
  public ResponseEntity<Boolean> checkDiscountEligibility(@PathVariable Long userId) {
    // Implement logic to check if the user is eligible for a discount
    boolean isEligible = userService.checkDiscountEligibility(userId);
    return ResponseEntity.ok(isEligible);
  }

  @GetMapping("/{userId}/attendance-streak")
  public ResponseEntity<Integer> getUserStreak(@PathVariable Long userId) {
    return ResponseEntity.ok(userService.getUserStreak(userId));
  }
}