package ru.musify.userservice.valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.musify.userservice.dto.SignUpRequest;
import ru.musify.userservice.model.User;
import ru.musify.userservice.repository.UserRepository;

import java.util.Optional;

/**
 * This class provides validation for user-related operations.
 */
@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    /**
     * The repository for accessing user data.
     */
    private final UserRepository userRepository;

    /**
     * Checks if the validator supports the given class.
     *
     * @param clazz The class to be checked.
     * @return      True if the class is supported, false otherwise.
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    /**
     * Validates the target object and adds errors to the provided Errors object if necessary.
     *
     * @param target The object to be validated.
     * @param errors The object to store validation errors.
     */
    @Override
    public void validate(Object target, Errors errors) {
        SignUpRequest request = (SignUpRequest) target;
        userRepository.findUserByUsername(request.username()).ifPresent(user -> {
            errors.rejectValue("username", "", "This username is already taken");
        });
    }
}