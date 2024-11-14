package com.api.donation_api.model;

import com.api.donation_api.model.Pessoa;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "enderecos")
@Table(name = "enderecos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private String logradouro;

    @NotNull
    private String cep;

    @NotNull
    private String bairro;

    @NotNull
    private String municipio;

    @NotNull
    private String estado;

    private  String complemento = "";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer numero;

    @JsonBackReference("endereco-pessoas")
    @OneToMany(mappedBy = "endereco")
    private Set<Pessoa> pessoas = new HashSet<>();
}
