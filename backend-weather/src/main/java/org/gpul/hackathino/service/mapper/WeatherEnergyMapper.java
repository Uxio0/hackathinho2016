package org.gpul.hackathino.service.mapper;

import org.gpul.hackathino.domain.*;
import org.gpul.hackathino.service.dto.WeatherEnergyDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity WeatherEnergy and its DTO WeatherEnergyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WeatherEnergyMapper {

    WeatherEnergyDTO weatherEnergyToWeatherEnergyDTO(WeatherEnergy weatherEnergy);

    List<WeatherEnergyDTO> weatherEnergiesToWeatherEnergyDTOs(List<WeatherEnergy> weatherEnergies);

    WeatherEnergy weatherEnergyDTOToWeatherEnergy(WeatherEnergyDTO weatherEnergyDTO);

    List<WeatherEnergy> weatherEnergyDTOsToWeatherEnergies(List<WeatherEnergyDTO> weatherEnergyDTOs);
}
