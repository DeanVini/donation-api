package com.api.donation_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PessoaRequestDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    private String nome;

    private String cpf;

    private String telefone;

    private LocalDate dataNascimento;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private addressRequestDTO address;
}