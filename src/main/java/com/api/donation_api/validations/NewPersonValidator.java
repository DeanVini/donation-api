package com.api.donation_api.validations;

import com.api.donation_api.dto.PessoaRequestDTO;

public interface NewPersonValidator {
    void validate(PessoaRequestDTO pessoaRequestDTO);
}
