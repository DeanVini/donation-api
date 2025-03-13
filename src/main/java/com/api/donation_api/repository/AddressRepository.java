package com.api.donation_api.repository;

import com.api.donation_api.interfaces.JpaSpecificationRepository;
import com.api.donation_api.model.Address;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaSpecificationRepository<Address, Long> {
    public List<Address> findAllByPostalCode(String postalCode);

    public Boolean existsByPostalCode(String cep);

    @Override
    @EntityGraph(attributePaths = {"people"})
    @NotNull
    Optional<Address> findById(@NotNull Long id);

    @Query("SELECT a FROM address a LEFT JOIN FETCH a.families WHERE a.id = :id")
    Optional<Address> findByIdWithFamilies(@NotNull Long id);
}
