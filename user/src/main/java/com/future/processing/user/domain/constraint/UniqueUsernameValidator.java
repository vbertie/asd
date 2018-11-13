package com.future.processing.user.domain.constraint;

import com.future.processing.user.domain.UserFacade;

import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserFacade userFacade;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !userFacade.isUsernameInUse(value);
    }
}
