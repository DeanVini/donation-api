package com.api.donation_api.validations;

import com.api.donation_api.dto.NovoUsuarioRequestDTO;

public interface NovoUsuarioValidator {
    void validar(NovoUsuarioRequestDTO novoUsuarioRequestDTO);
}
