package com.felipemello.gym.attendance.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor(force = true)
@Builder
public class ErrorResponse {

  private final int status;
  private final String error;
  private final String message;
}
