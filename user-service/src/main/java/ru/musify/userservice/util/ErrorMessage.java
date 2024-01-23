package ru.musify.userservice.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Provides methods for generating error messages from BindingResult.
 */
public class ErrorMessage {

    /**
     * Generates an error message from the BindingResult.
     *
     * @param bindingResult The BindingResult containing the errors.
     * @return The error message generated from the BindingResult.
     */
    public static String bindingResultHasErrors(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMessage
                    .append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append(";");
        }
        return errorMessage.toString();
    }
}