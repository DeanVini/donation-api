package com.api.donation_api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FamiliaRequestDTO {
    private String nome;
    private EnderecoRequestDTO endereco;
    private PessoaRequestDTO lider;
    private List<PessoaRequestDTO> pessoas;
}
