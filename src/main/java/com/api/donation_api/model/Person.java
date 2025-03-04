package com.api.donation_api.model;

import com.api.donation_api.view.Views;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "person")
@Table(name = "people")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonView(Views.PersonSummary.class)
    private Long id;

    @Column(nullable = false)
    @JsonView(Views.PersonSummary.class)
    private String name;

    @Column(unique = true, nullable = false)
    @JsonView(Views.PersonSummary.class)
    private String cpf;

    private String telephone;

    @Column(nullable = false)
    @JsonView(Views.PersonSummary.class)
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @JsonView(Views.PersonComplete.class)
    @JsonManagedReference("endereco-pessoas")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "family_id")
    @JsonView(Views.PersonComplete.class)
    @JsonBackReference
    private Family family;
}
