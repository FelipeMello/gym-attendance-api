package com.felipemello.gym.attendance.service;

import com.felipemello.gym.attendance.entity.Member;
import com.felipemello.gym.attendance.model.MemberDTO;
import com.felipemello.gym.attendance.model.MemberResponseDTO;

public interface MemberService {

  MemberResponseDTO getMemberById(Long userId);

  Member createMember(MemberDTO user);

  Member updateMember(MemberDTO user);

  boolean checkDiscountEligibility(Long userId);

  int getMemberStreak(Long userId);
}
