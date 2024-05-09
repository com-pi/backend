package com.example.myplant.common;

import jakarta.validation.*;

import java.util.Set;

public class SelfValidating<T> {

    private final Validator validator;

    protected SelfValidating() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @SuppressWarnings("unchecked")
    protected void validateSelf() {
        Set<ConstraintViolation<T>> result = validator.validate((T) this);
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
    }

}
