package com.api.donation_api.validations;

import com.api.donation_api.dto.PersonRequestDTO;

public interface NewPersonValidator {
    void validate(PersonRequestDTO personRequestDTO);
}
