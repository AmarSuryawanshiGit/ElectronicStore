package com.lcwd.electronic.store.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageNameValidator implements ConstraintValidator<ImageNameValidate,String> {

    private Logger logger= LoggerFactory.getLogger(ImageNameValidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        logger.info("Message From Valid = {} ",value);

        if (value.isBlank()){
            return false;
        }else {
            return true;
        }
    }
}