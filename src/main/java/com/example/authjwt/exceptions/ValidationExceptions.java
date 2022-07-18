package com.example.authjwt.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ValidationExceptions {

  private final Map<String, String> erros;
  private final long statusCode;
  private final String statusName;
  private final LocalDateTime timestamp;
}
