package com.api.donation_api.mapper;

import com.api.donation_api.dto.PersonRequestDTO;
import com.api.donation_api.model.Person;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    @Named("mapWithoutAddress")
    @Mapping(target = "address", ignore = true)
    PersonRequestDTO toPersonDTO(Person person);
}
