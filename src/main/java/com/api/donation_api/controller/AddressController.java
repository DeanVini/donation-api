package com.api.donation_api.controller;

import com.api.donation_api.dto.addressRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Address;
import com.api.donation_api.service.PostalCodeService;
import com.api.donation_api.service.AddressService;
import com.api.donation_api.utils.ResponseConstructorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class AddressController {
    private final AddressService addressService;
    private final PostalCodeService postalCodeService;

    @Autowired
    public AddressController(AddressService addressService, PostalCodeService postalCodeService) {
        this.addressService = addressService;
        this.postalCodeService = postalCodeService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllAddresses(){
        List<Address> addresses = addressService.getAllAddresses();
        return ResponseConstructorUtils.okResponse(addresses);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createAddress(@RequestBody addressRequestDTO newAddressRequest) throws ResourceNotFoundException {
        Address addressCriado = addressService.createAddress(newAddressRequest);
        return ResponseConstructorUtils.createdResponse(addressCriado);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<Object> updateAddress(@PathVariable Long addressId, @RequestBody addressRequestDTO addressDTO) throws ResourceNotFoundException {
        Address updatedAddress = addressService.updateAddress(addressId, addressDTO);
        return ResponseConstructorUtils.okResponse(updatedAddress);
    }

    @GetMapping("/viaCep/{postalCode}")
    public ResponseEntity<Object> getAddressViaCepApi(@PathVariable String postalCode) {
        Address address = postalCodeService.findAddressByPostalCode(postalCode);
        return ResponseConstructorUtils.okResponse(address);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<Object> getAddressById(@PathVariable Long addressId) throws ResourceNotFoundException {
        Address address = addressService.getAddressById(addressId);
        return ResponseConstructorUtils.okResponse(address);
    }
}
