package com.api.donation_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoRequestDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    private String logradouro;

    private String cep;

    private String bairro;

    private String municipio;

    private String estado;

    private String Complemento;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer numero;
}
