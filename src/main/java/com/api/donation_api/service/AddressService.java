package com.api.donation_api.service;

import com.api.donation_api.dto.AddressRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.mapper.AddressMapper;
import com.api.donation_api.model.Address;
import com.api.donation_api.repository.AddressRepository;
import com.api.donation_api.utils.CustomBeanUtils;
import com.api.donation_api.validations.NewAddressValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final NewAddressValidator newAddressValidator;
    private final AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, NewAddressValidator newAddressValidator, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.newAddressValidator = newAddressValidator;
        this.addressMapper = addressMapper;
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
        return addressRepository.save(address);
    }

    public Address updateAddress(Long id, AddressRequestDTO addressDTO) throws ResourceNotFoundException {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com o ID: " + id));
        CustomBeanUtils.copyNonNullProperties(addressDTO, address);
        return addressRepository.save(address);
    }

    public List<Address> getAddressesByPostalCode(String PostalCode){
        return addressRepository.findAllByPostalCode(PostalCode);
    }

    public Address getAddressById(@NotNull Long addressId) throws ResourceNotFoundException {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
    }

    public AddressRequestDTO getAddressByIdWithPeople(@NotNull Long addressId) throws ResourceNotFoundException {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));

        return addressMapper.toAddressDTO(address);
    }

    public Address getOrCreateAddress(AddressRequestDTO addressRequestDTO) throws ResourceNotFoundException {
        if (addressRequestDTO.getId() != null){
            return getAddressById(addressRequestDTO.getId());
        }
        return createAddress(addressRequestDTO);
    }
}
