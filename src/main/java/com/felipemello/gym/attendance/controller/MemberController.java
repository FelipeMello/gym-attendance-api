package com.felipemello.gym.attendance.controller;

import com.felipemello.gym.attendance.entity.Member;
import com.felipemello.gym.attendance.model.MemberDTO;
import com.felipemello.gym.attendance.model.MemberResponseDTO;
import com.felipemello.gym.attendance.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Member")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {

  private final MemberService memberService;


  @GetMapping("/{userId}")
  public ResponseEntity<MemberResponseDTO> getUserById(@PathVariable Long userId) {
    log.info("Controller. Getting member by id: {}", userId);
    MemberResponseDTO member = memberService.getMemberById(userId);
    return ResponseEntity.of(Optional.ofNullable(member));
  }

  @PostMapping
  public ResponseEntity<Member> createUser(@RequestBody MemberDTO memberDTO) {
    log.info("Controller. Creating member: {}", memberDTO);
    Member member = memberService.createMember(memberDTO);
    return new ResponseEntity<>(member, HttpStatus.CREATED);
  }

  @GetMapping("/{userId}/discount-eligibility")
  public ResponseEntity<Boolean> checkDiscountEligibility(@PathVariable Long userId) {
    // Implement logic to check if the user is eligible for a discount
    boolean isEligible = memberService.checkDiscountEligibility(userId);
    return ResponseEntity.ok(isEligible);
  }

  @GetMapping("/{userId}/attendance-streak")
  public ResponseEntity<Integer> getUserStreak(@PathVariable Long userId) {
    return ResponseEntity.ok(memberService.getMemberStreak(userId));
  }
}