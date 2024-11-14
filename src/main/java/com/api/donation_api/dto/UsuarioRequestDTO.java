package com.api.donation_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioRequestDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String login;
    private String senha;
    private String email;
    private String nome;
    private String cpf;
}