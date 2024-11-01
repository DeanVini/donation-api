package com.api.donation_api.model;

import com.api.donation_api.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "pessoas")
@Table(name = "pessoas")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonView(Views.PessoaResumo.class)
    private Long id;

    @Column(nullable = false)
    @JsonView(Views.PessoaResumo.class)
    private String nome;

    @Column(unique = true, nullable = false)
    @JsonView(Views.PessoaResumo.class)
    private String cpf;

    private String telefone;

    @Column(nullable = false)
    @JsonView(Views.PessoaResumo.class)
    private LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    @JsonView(Views.PessoaCompleta.class)
    private Endereco endereco;
}
