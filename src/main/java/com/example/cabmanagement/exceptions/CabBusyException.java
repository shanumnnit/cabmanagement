package com.example.cabmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Cab is busy on a trip, try again in sometime")
public class CabBusyException extends Exception {
}
