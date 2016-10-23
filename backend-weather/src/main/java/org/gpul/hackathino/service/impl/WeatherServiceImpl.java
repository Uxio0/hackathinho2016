package org.gpul.hackathino.service.impl;

import okhttp3.OkHttpClient;
import org.gpul.hackathino.domain.Weather;
import org.gpul.hackathino.repository.WeatherRepository;
import org.gpul.hackathino.service.WeatherService;
import org.gpul.hackathino.service.dto.WeatherDTO;
import org.gpul.hackathino.service.mapper.WeatherMapper;
import org.gpul.hackathino.service.util.OpenWeatherClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service Implementation for managing Weather.
 */
@Service
@Transactional
public class WeatherServiceImpl implements WeatherService{

    private final Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);
    
    @Inject
    private WeatherRepository weatherRepository;

    @Inject
    private WeatherMapper weatherMapper;

    private OpenWeatherClient openWeatherClient;

    @PostConstruct
    private void init(){
        openWeatherClient = new OpenWeatherClient(new OkHttpClient());

    }

    /**
     * Save a weather.
     *
     * @return the persisted entity
     */
    //@Scheduled(cron = "0 30 12 * * ?")
    //@Scheduled(fixedDelay=86400000)
    public void download() {

        List<Weather> list = new ArrayList<>();

        String[] provinces = new String[]{"A Coruña", "Vitoria", "Albacete", "Alicante", "Almeria",
                "Asturias", "Avila", "Badajoz",
                "Palma de Mallorca", "Barcelona", "Bilbao", "Burgos", "Caceres", "Cadiz",
                "Cantabria", "Castellon", "Ciudad Real", "Cordoba", "Cuenca", "San Sebastian",
                "Girona", "Granada", "Guadalajara", "Huelva", "Huesca", "Jaen", "La Rioja",
                "Las Palmas", "Leon", "Lleida", "Lugo", "Madrid", "Malaga",
                "Murcia", "Navarra", "Ourense", "Palencia",
                "Pontevedra", "Salamanca", "Santa Cruz de Tenerife", "Segovia", "Sevilla",
                "Soria", "Tarragona", "Teruel", "Toledo", "Valencia", "Valladolid", "Zamora", "Zaragoza"};
        Arrays.asList(provinces).forEach(
                province -> {
                    try {
                        list.add(openWeatherClient.getWeatherByProvince(province));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );

        weatherRepository.save(list);
    }

    /**
     * Save a weather.
     *
     * @param weatherDTO the entity to save
     * @return the persisted entity
     */
    public WeatherDTO save(WeatherDTO weatherDTO) {
        log.debug("Request to save Weather : {}", weatherDTO);
        Weather weather = weatherMapper.weatherDTOToWeather(weatherDTO);
        weather = weatherRepository.save(weather);
        WeatherDTO result = weatherMapper.weatherToWeatherDTO(weather);

        return result;
    }

    /**
     *  Get all the weathers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<WeatherDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Weathers");
        Page<Weather> result = weatherRepository.findAll(pageable);
        return result.map(weather -> weatherMapper.weatherToWeatherDTO(weather));
    }

    /**
     *  Get one weather by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public WeatherDTO findOne(Long id) {
        log.debug("Request to get Weather : {}", id);
        Weather weather = weatherRepository.findOne(id);
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);
        return weatherDTO;
    }

    /**
     *  Delete the  weather by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Weather : {}", id);
        weatherRepository.delete(id);
    }
}
