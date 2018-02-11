package com.jkmiec.selectronic.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFound extends Exception {
    public CategoryNotFound(String message) {
        super(message);
    }
}
