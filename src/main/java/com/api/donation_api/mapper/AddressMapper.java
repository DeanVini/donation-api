package com.api.donation_api.mapper;

import com.api.donation_api.dto.AddressRequestDTO;
import com.api.donation_api.dto.PersonRequestDTO;
import com.api.donation_api.model.Address;
import com.api.donation_api.model.Person;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = PersonMapper.class)
public interface AddressMapper {

    AddressRequestDTO toAddressDTO(Address address, @Context boolean includePeople);

    default List<PersonRequestDTO> mapPeopleIfNeeded(List<Person> people, @Context boolean includePeople) {
        if (!includePeople) {
            return Collections.emptyList();
        }
        return people.stream()
                .map(this::toPersonDTO)
                .collect(Collectors.toList());
    }

    PersonRequestDTO toPersonDTO(Person person);
}
