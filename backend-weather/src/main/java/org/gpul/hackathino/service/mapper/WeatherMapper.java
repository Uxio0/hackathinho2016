package org.gpul.hackathino.service.mapper;

import org.gpul.hackathino.domain.*;
import org.gpul.hackathino.service.dto.WeatherDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Weather and its DTO WeatherDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WeatherMapper {

    WeatherDTO weatherToWeatherDTO(Weather weather);

    List<WeatherDTO> weathersToWeatherDTOs(List<Weather> weathers);

    Weather weatherDTOToWeather(WeatherDTO weatherDTO);

    List<Weather> weatherDTOsToWeathers(List<WeatherDTO> weatherDTOs);
}
