package com.api.donation_api.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import com.api.donation_api.view.Views;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

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
    @JsonView(Views.AddressListView.class)
    private Long id;

    @NotNull
    @JsonView(Views.AddressListView.class)
    private String street;

    @NotNull
    @JsonView(Views.AddressListView.class)
    private String postalCode;

    @NotNull
    @JsonView(Views.AddressListView.class)
    private String neighborhood;

    @NotNull
    @JsonView(Views.AddressListView.class)
    private String city;

    @NotNull
    @JsonView(Views.AddressListView.class)
    private String state;

    @JsonView(Views.AddressListView.class)
    private String additionalInfo = "";

    @JsonView(Views.AddressListView.class)
    private double latitude;

    @JsonView(Views.AddressListView.class)
    private double longitude;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView(Views.AddressListView.class)
    private Integer number;

    @Transient
    @JsonView(Views.AddressListView.class)
    private Long peopleCount;

    @JsonBackReference("address-people")
    @OneToMany(mappedBy = "address")
    private Set<Person> people = new HashSet<>();

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    @JsonView(Views.AddressDetailView.class)
    private Set<Family> families = new HashSet<>();

    @PostLoad
    public void computePeopleCount() {
        if (families == null || families.isEmpty()) {
            peopleCount = 0L;
            return;
        }

        AtomicLong peopleAtomicCount = new AtomicLong();

        families.forEach(family -> {
            if (family.getMembers() != null) {
                peopleAtomicCount.addAndGet(family.getMembers().size());
            }
        });

        peopleCount = peopleAtomicCount.get();
    }
}
