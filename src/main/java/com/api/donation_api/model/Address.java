package com.api.donation_api.model;

import com.fasterxml.jackson.annotation.*;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private Set<Family> families = new HashSet<>();
}
