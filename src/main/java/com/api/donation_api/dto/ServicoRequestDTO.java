package com.api.donation_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicoRequestDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    private String tipo;

    private String descricao;

    private Boolean disponivel;

    public String getDescricao(){
        return descricao != null ? descricao : "";
    }
}
