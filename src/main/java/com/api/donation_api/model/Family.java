package com.api.donation_api.model;

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
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne
    private Address address;

    @OneToOne
    private Person leader;

    @OneToMany(mappedBy = "family")
    @Column(nullable = false)
    private Set<Person> members = new HashSet<>();

    @ManyToMany
    private Set<Service> services = new HashSet<>();
}
