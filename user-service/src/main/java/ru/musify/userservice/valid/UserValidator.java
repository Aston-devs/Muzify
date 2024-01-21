package ru.musify.userservice.valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.musify.userservice.dto.SignUpRequest;
import ru.musify.userservice.model.User;
import ru.musify.userservice.repository.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpRequest request = (SignUpRequest) target;
        userRepository.findUserByUsername(request.username()).ifPresent(user -> {
            errors.rejectValue("username", "", "This username is already taken");
        });
    }
}
