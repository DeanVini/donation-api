package com.api.donation_api.repository;

import com.api.donation_api.interfaces.JpaSpecificationRepository;
import com.api.donation_api.model.Family;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface FamilyRepository extends JpaSpecificationRepository<Family, Long> {
    @Override
    @NotNull
    Optional<Family> findById(@NotNull Long id);

}
