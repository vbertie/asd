package com.future.processing.user.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
class UserExceptionDto implements Serializable {

    private String field;
    private String code;
    private Object rejectedValue;
}
