package com.api.donation_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NovoUsuarioRequestDTO {
    private String login;
    private String senha;
    private String email;
    private String nome;
    private String cpf;
}