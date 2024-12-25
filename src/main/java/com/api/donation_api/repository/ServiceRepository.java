package com.api.donation_api.repository;

import com.api.donation_api.interfaces.JpaSpecificationRepository;
import com.api.donation_api.model.Service;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaSpecificationRepository<Service, Long> {
    @Override
    @NotNull
    Optional<Service> findById(@NotNull Long id);

    List<Service> findAllByAvailableTrue();
}
