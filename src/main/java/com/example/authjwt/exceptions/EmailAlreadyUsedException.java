package com.example.authjwt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EmailAlreadyUsedException extends RuntimeException {

  public EmailAlreadyUsedException(String arg0) {
    super(arg0);
  }

}
