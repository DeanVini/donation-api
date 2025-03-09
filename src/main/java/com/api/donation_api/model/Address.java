package com.api.donation_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "address")
@Table(name = "addresses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private String street;

    @NotNull
    private String postalCode;

    @NotNull
    private String neighborhood;

    @NotNull
    private String city;

    @NotNull
    private String state;

    private String additionalInfo = "";

    private double latitude;

    private double longitude;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer number;

    @JsonBackReference("address-people")
    @OneToMany(mappedBy = "address")
    private Set<Person> people = new HashSet<>();
}
