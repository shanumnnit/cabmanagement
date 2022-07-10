package com.example.cabmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "City not onboarded yet")
public class CityNotFoundException extends Exception {
}
