package com.api.donation_api.model;

import com.api.donation_api.model.Pessoa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity(name = "enderecos")
@Table(name = "enderecos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private String logradouro;

    private String cep;

    private String bairro;

    private String municipio;

    private String estado;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "endereco_pessoas", joinColumns = @JoinColumn(name = "endereco_id"), inverseJoinColumns = @JoinColumn(name = "pessoa_id"))
    private Set<Pessoa> pessoas;


}
