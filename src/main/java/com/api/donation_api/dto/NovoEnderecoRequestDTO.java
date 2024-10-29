package com.api.donation_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NovoEnderecoRequestDTO {
    private String logradouro;

    private String cep;

    private String bairro;

    private String municipio;

    private String estado;

    private String Complemento;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Number numero;
}
