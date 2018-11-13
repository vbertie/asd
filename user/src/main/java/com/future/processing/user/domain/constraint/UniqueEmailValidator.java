package com.future.processing.user.domain.constraint;

import com.future.processing.user.domain.UserFacade;

import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserFacade userFacade;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && !userFacade.isEmailInUse(value);
    }
}
