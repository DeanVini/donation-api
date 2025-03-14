package com.api.donation_api.repository;

import com.api.donation_api.interfaces.JpaSpecificationRepository;
import com.api.donation_api.model.Family;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FamilyRepository extends JpaSpecificationRepository<Family, Long> {
    @Override
    @NotNull
    @Query(value = "select f from family f")
    List<Family> findAll();

    @Override
    @NotNull
    Optional<Family> findById(@NotNull Long id);

}
