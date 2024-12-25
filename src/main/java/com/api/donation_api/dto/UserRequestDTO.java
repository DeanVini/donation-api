package com.api.donation_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String cpf;
}