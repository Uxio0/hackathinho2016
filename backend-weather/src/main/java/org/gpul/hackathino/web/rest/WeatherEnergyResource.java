package org.gpul.hackathino.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.gpul.hackathino.service.WeatherEnergyService;
import org.gpul.hackathino.service.dto.WeatherEnergyDTO;
import org.gpul.hackathino.web.rest.util.HeaderUtil;
import org.gpul.hackathino.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WeatherEnergy.
 */
@RestController
@RequestMapping("/api")
public class WeatherEnergyResource {

    private final Logger log = LoggerFactory.getLogger(WeatherEnergyResource.class);
        
    @Inject
    private WeatherEnergyService weatherEnergyService;

    /**
     * POST  /weather-energies : Create a new weatherEnergy.
     *
     * @param weatherEnergyDTO the weatherEnergyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new weatherEnergyDTO, or with status 400 (Bad Request) if the weatherEnergy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/weather-energies",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WeatherEnergyDTO> createWeatherEnergy(@Valid @RequestBody WeatherEnergyDTO weatherEnergyDTO) throws URISyntaxException {
        log.debug("REST request to save WeatherEnergy : {}", weatherEnergyDTO);
        if (weatherEnergyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("weatherEnergy", "idexists", "A new weatherEnergy cannot already have an ID")).body(null);
        }
        WeatherEnergyDTO result = weatherEnergyService.save(weatherEnergyDTO);
        return ResponseEntity.created(new URI("/api/weather-energies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("weatherEnergy", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /weather-energies : Updates an existing weatherEnergy.
     *
     * @param weatherEnergyDTO the weatherEnergyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated weatherEnergyDTO,
     * or with status 400 (Bad Request) if the weatherEnergyDTO is not valid,
     * or with status 500 (Internal Server Error) if the weatherEnergyDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/weather-energies",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WeatherEnergyDTO> updateWeatherEnergy(@Valid @RequestBody WeatherEnergyDTO weatherEnergyDTO) throws URISyntaxException {
        log.debug("REST request to update WeatherEnergy : {}", weatherEnergyDTO);
        if (weatherEnergyDTO.getId() == null) {
            return createWeatherEnergy(weatherEnergyDTO);
        }
        WeatherEnergyDTO result = weatherEnergyService.save(weatherEnergyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("weatherEnergy", weatherEnergyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /weather-energies : get all the weatherEnergies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of weatherEnergies in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/weather-energies",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<WeatherEnergyDTO>> getAllWeatherEnergies(@PageableDefault(sort = { "createdDateTime" }, value = 50) Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of WeatherEnergies");
        Page<WeatherEnergyDTO> page = weatherEnergyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/weather-energies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /weather-energies/:id : get the "id" weatherEnergy.
     *
     * @param id the id of the weatherEnergyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the weatherEnergyDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/weather-energies/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WeatherEnergyDTO> getWeatherEnergy(@PathVariable Long id) {
        log.debug("REST request to get WeatherEnergy : {}", id);
        WeatherEnergyDTO weatherEnergyDTO = weatherEnergyService.findOne(id);
        return Optional.ofNullable(weatherEnergyDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /weather-energies/:id : delete the "id" weatherEnergy.
     *
     * @param id the id of the weatherEnergyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/weather-energies/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteWeatherEnergy(@PathVariable Long id) {
        log.debug("REST request to delete WeatherEnergy : {}", id);
        weatherEnergyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("weatherEnergy", id.toString())).build();
    }

}
