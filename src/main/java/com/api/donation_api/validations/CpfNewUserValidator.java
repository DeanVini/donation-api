package com.api.donation_api.validations;

import com.api.donation_api.dto.UsuarioRequestDTO;
import com.api.donation_api.exception.InvalidCpfException;
import com.api.donation_api.repository.UsuarioRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CpfNewUserValidator implements NewUserValidator {
    private final UsuarioRepository usuarioRepository;

    public CpfNewUserValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validate(@NotNull UsuarioRequestDTO usuarioRequestDTO){
        String cpf = usuarioRequestDTO.getCpf();

        if (!CpfValidator.isCpfValido(cpf)) {
            throw new InvalidCpfException("O CPF informado não é válido!");
        }

        if (usuarioRepository.existsByCpf(cpf)) {
            throw new InvalidCpfException("O CPF informado já foi cadastrado!");
        }
    }
}
