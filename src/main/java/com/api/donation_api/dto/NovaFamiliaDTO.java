package com.api.donation_api.dto;

import com.api.donation_api.model.Endereco;
import com.api.donation_api.model.Pessoa;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class NovaFamiliaDTO {
    private String nome;
    private Endereco endereco;
    private Pessoa lider;
    private Set<Pessoa> pessoas;
}
