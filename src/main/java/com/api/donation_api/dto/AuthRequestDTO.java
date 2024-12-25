package com.api.donation_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequestDTO {
    private String username;
    private String password;
}