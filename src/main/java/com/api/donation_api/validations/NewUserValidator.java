package com.api.donation_api.validations;

import com.api.donation_api.dto.UserRequestDTO;

public interface NewUserValidator {
    void validate(UserRequestDTO userRequestDTO);
}
