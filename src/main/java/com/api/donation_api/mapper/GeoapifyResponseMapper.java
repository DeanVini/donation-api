package com.api.donation_api.mapper;

import com.api.donation_api.dto.GeolocationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface GeoapifyResponseMapper {

    @Mapping(source = "lon", target = "longitude")
    GeolocationResponseDTO toGeolocationResponse(Map<String, String> apiResponse);
}
