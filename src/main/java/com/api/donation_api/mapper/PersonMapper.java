package com.api.donation_api.mapper;

import com.api.donation_api.dto.PersonRequestDTO;
import com.api.donation_api.model.Person;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface PersonMapper {
    PersonRequestDTO toPersonDTO(Person person);

}
