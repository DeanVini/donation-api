package com.api.donation_api.repository;

import com.api.donation_api.interfaces.JpaSpecificationRepository;
import com.api.donation_api.model.Address;

import java.util.List;

public interface AddressRepository extends JpaSpecificationRepository<Address, Long> {
    public List<Address> findAllByPostalCode(String postalCode);

    public Boolean existsByPostalCode(String cep);
}
