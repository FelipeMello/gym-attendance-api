package com.felipemello.gym.attendance.model;

import jakarta.validation.constraints.NotBlank;
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
public class UserDTO {

  @NotBlank(message = "Name is mandatory")
  private String name;

  @NotBlank(message = "Email is mandatory")
  private String email;

  @NotBlank(message = "Password is mandatory")
  private String password;
}
