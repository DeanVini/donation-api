package com.api.donation_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity(name = "servicos")
@Table(name = "servicos")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private String tipo;

    private String descricao;

    private Boolean disponivel= true;
}
