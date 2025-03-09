package com.api.donation_api.service;

import com.api.donation_api.dto.AddressRequestDTO;
import com.api.donation_api.dto.GeolocationResponseDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.mapper.AddressMapper;
import com.api.donation_api.mapper.GeoapifyResponseMapper;
import com.api.donation_api.model.Address;
import com.api.donation_api.repository.AddressRepository;
import com.api.donation_api.utils.CustomBeanUtils;
import com.api.donation_api.validations.NewAddressValidator;
import com.google.common.util.concurrent.RateLimiter;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AddressService {
    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);
    private final GeoapifyResponseMapper geoapifyResponseMapper;
    private final OkHttpClient httpClient = new OkHttpClient();
    private final AddressRepository addressRepository;
    private final NewAddressValidator newAddressValidator;
    private final AddressMapper addressMapper;
    private final GeolocationService geolocationService;
    private final RateLimiter rateLimiter = RateLimiter.create(1.0);



    public AddressService(AddressRepository addressRepository, NewAddressValidator newAddressValidator, AddressMapper addressMapper, RestTemplate restTemplate, GeoapifyResponseMapper geoapifyResponseMapper, GeolocationService geolocationService) {
        this.addressRepository = addressRepository;
        this.newAddressValidator = newAddressValidator;
        this.addressMapper = addressMapper;
        this.geolocationService = geolocationService;
        this.geoapifyResponseMapper = geoapifyResponseMapper;
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
