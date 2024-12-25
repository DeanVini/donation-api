package com.api.donation_api.validations;

import com.api.donation_api.dto.UserRequestDTO;
import com.api.donation_api.exception.InvalidCpfException;
import com.api.donation_api.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CpfNewUserValidator implements NewUserValidator {
    private final UserRepository userRepository;

    public CpfNewUserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validate(@NotNull UserRequestDTO userRequestDTO){
        String cpf = userRequestDTO.getCpf();

        if (CpfValidator.isValidCpf(cpf)) {
            throw new InvalidCpfException("O CPF informado não é válido!");
        }

        if (userRepository.existsByCpf(cpf)) {
            throw new InvalidCpfException("O CPF informado já foi cadastrado!");
        }
    }
}
