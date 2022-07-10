package com.example.cabmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT, reason = "All cabs are busy, try again in sometime")
public class AllCabsBusyException extends Exception {
}
