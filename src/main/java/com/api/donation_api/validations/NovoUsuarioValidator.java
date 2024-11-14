package com.api.donation_api.validations;

import com.api.donation_api.dto.UsuarioRequestDTO;

public interface NovoUsuarioValidator {
    void validar(UsuarioRequestDTO usuarioRequestDTO);
}
