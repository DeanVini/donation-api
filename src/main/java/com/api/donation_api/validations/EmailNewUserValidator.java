package com.api.donation_api.validations;

import com.api.donation_api.dto.UserRequestDTO;
import com.api.donation_api.exception.InvalidLoginException;
import com.api.donation_api.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class EmailNewUserValidator implements NewUserValidator {
    private final UserRepository userRepository;

    public EmailNewUserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validate(@NotNull UserRequestDTO userRequestDTO){
        if(userRepository.existsByEmail(userRequestDTO.getEmail())){
            throw new InvalidLoginException("O E-mail informado já foi cadastrado!");
        }
    }
}
