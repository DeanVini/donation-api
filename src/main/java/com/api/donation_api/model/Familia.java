package com.api.donation_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "familia")
@Table(name = "familia")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Familia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @OneToOne
    private Endereco endereco;

    @OneToOne
    private Pessoa lider;

    @OneToMany(mappedBy = "familia")
    @Column(nullable = false)
    private Set<Pessoa> pessoas = new HashSet<>();
}
