package com.api.donation_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity(name = "familia")
@Table(name = "familia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Familia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    private Endereco endereco;

    @OneToOne
    private Pessoa lider;

    @ManyToMany
    @Column(nullable = false)
    @JsonIgnore
    private Set<Pessoa> dependentes;


}
