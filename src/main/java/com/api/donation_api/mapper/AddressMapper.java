package com.api.donation_api.mapper;

import com.api.donation_api.dto.AddressRequestDTO;
import com.api.donation_api.dto.PersonRequestDTO;
import com.api.donation_api.model.Address;
import com.api.donation_api.model.Person;
import org.mapstruct.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = PersonMapper.class)
public abstract class AddressMapper {
    @BeforeMapping
    protected AddressRequestDTO addressDTOWithPeople(Address address, Boolean withPeople, @MappingTarget AddressRequestDTO addressRequestDTO) {
        Set<Person> people = address.getPeople();
        if(people != null){
            return addressRequestDTO;
        }
        return addressRequestDTO;
    }

    public abstract AddressRequestDTO toAddressDTO(Address address, Boolean withPeople);
    public abstract AddressRequestDTO toAddressDTO(Address address);
}
