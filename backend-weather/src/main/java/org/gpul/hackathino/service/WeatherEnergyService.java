package org.gpul.hackathino.service;

import org.gpul.hackathino.service.dto.WeatherEnergyDTO;
import org.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 * Service Interface for managing WeatherEnergy.
 */
public interface WeatherEnergyService {

    void download() throws JSONException, IOException;

    /**
     * Save a weatherEnergy.
     *
     * @param weatherEnergyDTO the entity to save
     * @return the persisted entity
     */
    WeatherEnergyDTO save(WeatherEnergyDTO weatherEnergyDTO);

    /**
     *  Get all the weatherEnergies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WeatherEnergyDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" weatherEnergy.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WeatherEnergyDTO findOne(Long id);

    /**
     *  Delete the "id" weatherEnergy.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
