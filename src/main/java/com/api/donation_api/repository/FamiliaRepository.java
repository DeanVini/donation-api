package com.api.donation_api.repository;

import com.api.donation_api.interfaces.JpaSpecificationRepository;
import com.api.donation_api.model.Familia;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface FamiliaRepository extends JpaSpecificationRepository<Familia, Long> {
    @Override
    @NotNull
    Optional<Familia> findById(@NotNull Long id);

}
