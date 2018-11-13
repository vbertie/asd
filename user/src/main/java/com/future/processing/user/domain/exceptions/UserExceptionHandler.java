package com.future.processing.user.domain.exceptions;

import lombok.NoArgsConstructor;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class UserExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private List<UserExceptionDto> processUserValidation(MethodArgumentNotValidException ex) {

        BindingResult bindings = ex.getBindingResult();

        return Collections.unmodifiableList(bindings.getFieldErrors())
                .stream()
                .map(error -> new UserExceptionDto(error.getField(), error.getCode(), error.getRejectedValue()))
                .collect(Collectors.toList());
    }
}
