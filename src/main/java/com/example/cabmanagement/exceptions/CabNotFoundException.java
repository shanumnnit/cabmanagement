package com.example.cabmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such cab with this id is registered")
public class CabNotFoundException extends Exception {
}
