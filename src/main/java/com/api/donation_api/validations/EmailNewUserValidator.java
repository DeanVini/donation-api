package com.api.donation_api.validations;

import com.api.donation_api.dto.UsuarioRequestDTO;
import com.api.donation_api.exception.InvalidLoginException;
import com.api.donation_api.repository.UsuarioRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class EmailNewUserValidator implements NewUserValidator {
    private final UsuarioRepository usuarioRepository;

    public EmailNewUserValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validate(@NotNull UsuarioRequestDTO usuarioRequestDTO){
        if(usuarioRepository.existsByEmail(usuarioRequestDTO.getEmail())){
            throw new InvalidLoginException("O E-mail informado já foi cadastrado!");
        }
    }
}
