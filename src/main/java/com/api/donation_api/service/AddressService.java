package com.api.donation_api.service;

import com.api.donation_api.dto.AddressRequestDTO;
import com.api.donation_api.dto.GeolocationResponseDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Address;
import com.api.donation_api.model.Family;
import com.api.donation_api.repository.AddressRepository;
import com.api.donation_api.utils.CustomBeanUtils;
import com.api.donation_api.validations.NewAddressValidator;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final NewAddressValidator newAddressValidator;
    private final GeolocationService geolocationService;



    public AddressService(AddressRepository addressRepository, NewAddressValidator newAddressValidator, GeolocationService geolocationService) {
        this.addressRepository = addressRepository;
        this.newAddressValidator = newAddressValidator;
        this.geolocationService = geolocationService;
    }

    public List<Address> getAllAddresses(){
        return addressRepository.findAll();
    }

    public Address createAddress(@NotNull AddressRequestDTO newAddressRequest) {
        newAddressValidator.validate(newAddressRequest);

        Address address = Address
                .builder()
                .postalCode(newAddressRequest.getPostalCode())
                .street(newAddressRequest.getStreet())
                .neighborhood(newAddressRequest.getNeighborhood())
                .city(newAddressRequest.getCity())
                .state(newAddressRequest.getState())
                .additionalInfo(newAddressRequest.getAdditionalInfo())
                .number(newAddressRequest.getNumber())
                .build();

        GeolocationResponseDTO geoResponse = geolocationService.fetchGeolocation(address)
                .blockOptional()
                .orElse(null);

        if (geoResponse != null) {
            address.setLatitude(geoResponse.getLatitude());
            address.setLongitude(geoResponse.getLongitude());
        }

        return addressRepository.save(address);
    }

    public Address updateAddress(Long id, AddressRequestDTO addressDTO) throws ResourceNotFoundException {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com o ID: " + id));
        CustomBeanUtils.copyNonNullProperties(addressDTO, address);
        return addressRepository.save(address);
    }

    public List<Address> getAddressesByPostalCode(String postalCode){
        return addressRepository.findAllByPostalCode(postalCode);
    }

    public Address getAddressById(@NotNull Long addressId) throws ResourceNotFoundException {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
    }

    public Address getAddressByIdWithFamilies(Long id) throws ResourceNotFoundException {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
        Hibernate.initialize(address.getFamilies());
        for (Family family : address.getFamilies()) {
            Hibernate.initialize(family);
        }
        return address;
    }

    public Address getOrCreateAddress(AddressRequestDTO addressRequestDTO) throws ResourceNotFoundException {
        if (addressRequestDTO.getId() != null){
            return getAddressById(addressRequestDTO.getId());
        }
        return createAddress(addressRequestDTO);
    }
}
