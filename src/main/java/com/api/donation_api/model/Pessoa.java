package com.api.donation_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity(name = "pessoas")
@Table(name = "pessoas")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    private String telefone;

    @Column(nullable = false)
    private Date dataNascimento;
}
