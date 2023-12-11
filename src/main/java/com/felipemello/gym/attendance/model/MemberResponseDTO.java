package com.felipemello.gym.attendance.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO {

  private Long id;
  private String name;
  private String email;
  private List<AttendanceDTO> attendances;
}
