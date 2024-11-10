package com.api.donation_api.validations;

import com.api.donation_api.dto.PessoaRequestDTO;

public interface NovaPessoaValidator {
    void validar(PessoaRequestDTO pessoaRequestDTO);
}
