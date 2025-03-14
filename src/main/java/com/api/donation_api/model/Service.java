package com.api.donation_api.model;

import com.api.donation_api.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity(name = "service")
@Table(name = "services")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @JsonView(Views.AddressDetailView.class)
    private String type;

    @JsonView(Views.AddressDetailView.class)
    private String description;

    @JsonView(Views.AddressDetailView.class)
    private Boolean available= true;
}
