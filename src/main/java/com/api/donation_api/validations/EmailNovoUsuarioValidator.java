package com.api.donation_api.validations;

import com.api.donation_api.dto.UsuarioRequestDTO;
import com.api.donation_api.exception.LoginInvalidoException;
import com.api.donation_api.repository.UsuarioRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class EmailNovoUsuarioValidator implements NovoUsuarioValidator{
    private final UsuarioRepository usuarioRepository;

    public EmailNovoUsuarioValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validar(@NotNull UsuarioRequestDTO usuarioRequestDTO){
        if(usuarioRepository.existsByEmail(usuarioRequestDTO.getEmail())){
            throw new LoginInvalidoException("O E-mail informado já foi cadastrado!");
        }
    }
}
