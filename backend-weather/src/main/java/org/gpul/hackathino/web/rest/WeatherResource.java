package org.gpul.hackathino.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.gpul.hackathino.service.WeatherService;
import org.gpul.hackathino.web.rest.util.HeaderUtil;
import org.gpul.hackathino.web.rest.util.PaginationUtil;
import org.gpul.hackathino.service.dto.WeatherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Weather.
 */
@RestController
@RequestMapping("/api")
public class WeatherResource {

    private final Logger log = LoggerFactory.getLogger(WeatherResource.class);
        
    @Inject
    private WeatherService weatherService;

    /**
     * POST  /weathers : Create a new weather.
     *
     * @param weatherDTO the weatherDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new weatherDTO, or with status 400 (Bad Request) if the weather has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/weathers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WeatherDTO> createWeather(@Valid @RequestBody WeatherDTO weatherDTO) throws URISyntaxException {
        log.debug("REST request to save Weather : {}", weatherDTO);
        if (weatherDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("weather", "idexists", "A new weather cannot already have an ID")).body(null);
        }
        WeatherDTO result = weatherService.save(weatherDTO);
        return ResponseEntity.created(new URI("/api/weathers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("weather", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /weathers : Updates an existing weather.
     *
     * @param weatherDTO the weatherDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated weatherDTO,
     * or with status 400 (Bad Request) if the weatherDTO is not valid,
     * or with status 500 (Internal Server Error) if the weatherDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/weathers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WeatherDTO> updateWeather(@Valid @RequestBody WeatherDTO weatherDTO) throws URISyntaxException {
        log.debug("REST request to update Weather : {}", weatherDTO);
        if (weatherDTO.getId() == null) {
            return createWeather(weatherDTO);
        }
        WeatherDTO result = weatherService.save(weatherDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("weather", weatherDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /weathers : get all the weathers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of weathers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/weathers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<WeatherDTO>> getAllWeathers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Weathers");
        Page<WeatherDTO> page = weatherService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/weathers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /weathers/:id : get the "id" weather.
     *
     * @param id the id of the weatherDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the weatherDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/weathers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WeatherDTO> getWeather(@PathVariable Long id) {
        log.debug("REST request to get Weather : {}", id);
        WeatherDTO weatherDTO = weatherService.findOne(id);
        return Optional.ofNullable(weatherDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /weathers/:id : delete the "id" weather.
     *
     * @param id the id of the weatherDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/weathers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteWeather(@PathVariable Long id) {
        log.debug("REST request to delete Weather : {}", id);
        weatherService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("weather", id.toString())).build();
    }

}
