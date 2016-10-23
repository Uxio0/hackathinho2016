package org.gpul.hackathino.service;

import org.gpul.hackathino.service.dto.WeatherDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Weather.
 */
public interface WeatherService {

    /**
     * Download data
     */
    public void download();

    /**
     * Save a weather.
     *
     * @param weatherDTO the entity to save
     * @return the persisted entity
     */
    WeatherDTO save(WeatherDTO weatherDTO);

    /**
     *  Get all the weathers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WeatherDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" weather.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WeatherDTO findOne(Long id);

    /**
     *  Delete the "id" weather.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
