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

    @Mapping(target = "people", qualifiedByName = "mapWithoutAddress")
    public abstract AddressRequestDTO toAddressDTO(Address address);

    @Mapping(target = "people", ignore = true)
    public abstract AddressRequestDTO toAddressDTOWithoutPeople(Address address);
}