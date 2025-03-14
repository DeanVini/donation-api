package com.api.donation_api.model;

import com.api.donation_api.view.Views;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "family")
@Table(name = "families")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonView(Views.AddressDetailView.class)
    private Long id;

    @Column(nullable = false)
    @JsonView(Views.AddressDetailView.class)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    @JsonBackReference
    @JsonIgnore
    private Address address;

    @OneToOne
    @JsonView(Views.AddressDetailView.class)
    private Person leader;

    @OneToMany(mappedBy = "family")
    @Column(nullable = false)
    @JsonView(Views.AddressDetailView.class)
    private Set<Person> members = new HashSet<>();

    @ManyToMany
    @JsonView(Views.AddressDetailView.class)
    private Set<Service> services = new HashSet<>();
}

