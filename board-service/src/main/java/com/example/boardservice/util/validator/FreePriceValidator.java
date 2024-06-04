package com.example.boardservice.util.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FreePriceValidator implements ConstraintValidator<FreePriceValid, Object> {

    private ValidationStrategy strategy;

    @Override
    public void initialize(FreePriceValid constraintAnnotation) {
        this.strategy = new FreePriceValidationStrategy();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        return strategy.validate(obj);
    }
}