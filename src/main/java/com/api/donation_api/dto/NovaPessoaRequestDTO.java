package com.api.donation_api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class NovaPessoaRequestDTO {
    private String nome;
    private String cpf;
    private String telefone;
    private LocalDate dataNascimento;
}