package com.api.donation_api.dto;

import com.api.donation_api.model.Endereco;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class NovaPessoaRequestDTO {
    private String nome;

    private String cpf;

    private String telefone;

    private LocalDate dataNascimento;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Endereco endereco;
}