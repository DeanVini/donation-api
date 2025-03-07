package com.api.donation_api.service;

import com.api.donation_api.dto.AddressRequestDTO;
import com.api.donation_api.dto.GeolocationResponseDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.mapper.AddressMapper;
import com.api.donation_api.model.Address;
import com.api.donation_api.repository.AddressRepository;
import com.api.donation_api.utils.CustomBeanUtils;
import com.api.donation_api.validations.NewAddressValidator;
import com.google.common.util.concurrent.RateLimiter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AddressService {
    @Value("${geolocation.api.url}")
    private String geolocationApiUrl;

    @Value("${geolocation.api.token}")
    private String geolocationApiKey;

    private final AddressRepository addressRepository;
    private final NewAddressValidator newAddressValidator;
    private final AddressMapper addressMapper;
    private final RestTemplate restTemplate;
    private final RateLimiter rateLimiter = RateLimiter.create(1.0);



    public AddressService(AddressRepository addressRepository, NewAddressValidator newAddressValidator, AddressMapper addressMapper, RestTemplate restTemplate) {
        this.addressRepository = addressRepository;
        this.newAddressValidator = newAddressValidator;
        this.addressMapper = addressMapper;
        this.restTemplate = restTemplate;
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

        fetchAndSetGeolocation(address);

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

    public void fetchAndSetGeolocation(Address address){
        rateLimiter.acquire();

        String searchQuery = String.format("%s+%d+%s+%s+%s",
                address.getStreet(),
                address.getNumber() != null ? address.getNumber() : 0,
                address.getNeighborhood(),
                address.getCity(),
                address.getState()
        );

        String url = UriComponentsBuilder.fromHttpUrl(geolocationApiUrl + "/search")
                .queryParam("q", searchQuery)
                .queryParam("api_key", geolocationApiKey)
                .queryParam("limit", "1")
                .toUriString();

        System.out.println("URL gerada: " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<GeolocationResponseDTO[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, GeolocationResponseDTO[].class);
        System.out.println("Status da resposta: " + response.getStatusCode());
        System.out.println("GET response: " + response.getBody().length);

        if (response.hasBody() && response.getBody().length > 0) {
            double lat = response.getBody()[0].getLatitude();
            double lon = response.getBody()[0].getLongitude();
            address.setLatitude(lat);
            address.setLongitude(lon);
        }
    }

}
