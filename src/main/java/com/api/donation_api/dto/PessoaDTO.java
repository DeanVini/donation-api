package com.api.donation_api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PessoaDTO {
    private String nome;
    private String cpf;
    private String telefone;
    private Date dataNascimento;
}