package com.cache.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class EmployeeNotSavedException extends RuntimeException {

    public EmployeeNotSavedException() {
        super("Employee did not save");
    }

}
