package org.gpul.hackathino.web.rest;

import org.gpul.hackathino.WeatheropendataApp;

import org.gpul.hackathino.domain.WeatherEnergy;
import org.gpul.hackathino.repository.WeatherEnergyRepository;
import org.gpul.hackathino.service.WeatherEnergyService;
import org.gpul.hackathino.service.dto.WeatherEnergyDTO;
import org.gpul.hackathino.service.mapper.WeatherEnergyMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WeatherEnergyResource REST controller.
 *
 * @see WeatherEnergyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeatheropendataApp.class)
public class WeatherEnergyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_ISO_CODE = "AAAAA";
    private static final String UPDATED_ISO_CODE = "BBBBB";

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;

    private static final Double DEFAULT_LON = 1D;
    private static final Double UPDATED_LON = 2D;

    private static final Double DEFAULT_SOLAR = 1D;
    private static final Double UPDATED_SOLAR = 2D;

    private static final Double DEFAULT_MIN_TEMP = 1D;
    private static final Double UPDATED_MIN_TEMP = 2D;

    private static final Double DEFAULT_MAX_TEMP = 1D;
    private static final Double UPDATED_MAX_TEMP = 2D;

    private static final Double DEFAULT_TEMP = 1D;
    private static final Double UPDATED_TEMP = 2D;

    private static final Integer DEFAULT_SUNSET = 1;
    private static final Integer UPDATED_SUNSET = 2;

    private static final Integer DEFAULT_SUNRISE = 1;
    private static final Integer UPDATED_SUNRISE = 2;

    private static final Double DEFAULT_HIDRAULIC = 1D;
    private static final Double UPDATED_HIDRAULIC = 2D;

    private static final Double DEFAULT_RAIN = 1D;
    private static final Double UPDATED_RAIN = 2D;

    private static final Double DEFAULT_EOLIC = 1D;
    private static final Double UPDATED_EOLIC = 2D;

    private static final Double DEFAULT_WIND_SPEED = 1D;
    private static final Double UPDATED_WIND_SPEED = 2D;

    private static final Double DEFAULT_CLOUDS = 1D;
    private static final Double UPDATED_CLOUDS = 2D;

    private static final LocalDate DEFAULT_CREATED_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private WeatherEnergyRepository weatherEnergyRepository;

    @Inject
    private WeatherEnergyMapper weatherEnergyMapper;

    @Inject
    private WeatherEnergyService weatherEnergyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWeatherEnergyMockMvc;

    private WeatherEnergy weatherEnergy;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WeatherEnergyResource weatherEnergyResource = new WeatherEnergyResource();
        ReflectionTestUtils.setField(weatherEnergyResource, "weatherEnergyService", weatherEnergyService);
        this.restWeatherEnergyMockMvc = MockMvcBuilders.standaloneSetup(weatherEnergyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WeatherEnergy createEntity(EntityManager em) {
        WeatherEnergy weatherEnergy = new WeatherEnergy()
                .name(DEFAULT_NAME)
                .isoCode(DEFAULT_ISO_CODE)
                .lat(DEFAULT_LAT)
                .lon(DEFAULT_LON)
                .solar(DEFAULT_SOLAR)
                .minTemp(DEFAULT_MIN_TEMP)
                .maxTemp(DEFAULT_MAX_TEMP)
                .temp(DEFAULT_TEMP)
                .sunset(DEFAULT_SUNSET)
                .sunrise(DEFAULT_SUNRISE)
                .hidraulic(DEFAULT_HIDRAULIC)
                .rain(DEFAULT_RAIN)
                .eolic(DEFAULT_EOLIC)
                .windSpeed(DEFAULT_WIND_SPEED)
                .clouds(DEFAULT_CLOUDS)
                .createdDateTime(DEFAULT_CREATED_DATE_TIME);
        return weatherEnergy;
    }

    @Before
    public void initTest() {
        weatherEnergy = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeatherEnergy() throws Exception {
        int databaseSizeBeforeCreate = weatherEnergyRepository.findAll().size();

        // Create the WeatherEnergy
        WeatherEnergyDTO weatherEnergyDTO = weatherEnergyMapper.weatherEnergyToWeatherEnergyDTO(weatherEnergy);

        restWeatherEnergyMockMvc.perform(post("/api/weather-energies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherEnergyDTO)))
                .andExpect(status().isCreated());

        // Validate the WeatherEnergy in the database
        List<WeatherEnergy> weatherEnergies = weatherEnergyRepository.findAll();
        assertThat(weatherEnergies).hasSize(databaseSizeBeforeCreate + 1);
        WeatherEnergy testWeatherEnergy = weatherEnergies.get(weatherEnergies.size() - 1);
        assertThat(testWeatherEnergy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWeatherEnergy.getIsoCode()).isEqualTo(DEFAULT_ISO_CODE);
        assertThat(testWeatherEnergy.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testWeatherEnergy.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testWeatherEnergy.getSolar()).isEqualTo(DEFAULT_SOLAR);
        assertThat(testWeatherEnergy.getMinTemp()).isEqualTo(DEFAULT_MIN_TEMP);
        assertThat(testWeatherEnergy.getMaxTemp()).isEqualTo(DEFAULT_MAX_TEMP);
        assertThat(testWeatherEnergy.getTemp()).isEqualTo(DEFAULT_TEMP);
        assertThat(testWeatherEnergy.getSunset()).isEqualTo(DEFAULT_SUNSET);
        assertThat(testWeatherEnergy.getSunrise()).isEqualTo(DEFAULT_SUNRISE);
        assertThat(testWeatherEnergy.getHidraulic()).isEqualTo(DEFAULT_HIDRAULIC);
        assertThat(testWeatherEnergy.getRain()).isEqualTo(DEFAULT_RAIN);
        assertThat(testWeatherEnergy.getEolic()).isEqualTo(DEFAULT_EOLIC);
        assertThat(testWeatherEnergy.getWindSpeed()).isEqualTo(DEFAULT_WIND_SPEED);
        assertThat(testWeatherEnergy.getClouds()).isEqualTo(DEFAULT_CLOUDS);
        assertThat(testWeatherEnergy.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void checkSolarIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherEnergyRepository.findAll().size();
        // set the field null
        weatherEnergy.setSolar(null);

        // Create the WeatherEnergy, which fails.
        WeatherEnergyDTO weatherEnergyDTO = weatherEnergyMapper.weatherEnergyToWeatherEnergyDTO(weatherEnergy);

        restWeatherEnergyMockMvc.perform(post("/api/weather-energies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherEnergyDTO)))
                .andExpect(status().isBadRequest());

        List<WeatherEnergy> weatherEnergies = weatherEnergyRepository.findAll();
        assertThat(weatherEnergies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHidraulicIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherEnergyRepository.findAll().size();
        // set the field null
        weatherEnergy.setHidraulic(null);

        // Create the WeatherEnergy, which fails.
        WeatherEnergyDTO weatherEnergyDTO = weatherEnergyMapper.weatherEnergyToWeatherEnergyDTO(weatherEnergy);

        restWeatherEnergyMockMvc.perform(post("/api/weather-energies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherEnergyDTO)))
                .andExpect(status().isBadRequest());

        List<WeatherEnergy> weatherEnergies = weatherEnergyRepository.findAll();
        assertThat(weatherEnergies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEolicIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherEnergyRepository.findAll().size();
        // set the field null
        weatherEnergy.setEolic(null);

        // Create the WeatherEnergy, which fails.
        WeatherEnergyDTO weatherEnergyDTO = weatherEnergyMapper.weatherEnergyToWeatherEnergyDTO(weatherEnergy);

        restWeatherEnergyMockMvc.perform(post("/api/weather-energies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherEnergyDTO)))
                .andExpect(status().isBadRequest());

        List<WeatherEnergy> weatherEnergies = weatherEnergyRepository.findAll();
        assertThat(weatherEnergies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherEnergyRepository.findAll().size();
        // set the field null
        weatherEnergy.setCreatedDateTime(null);

        // Create the WeatherEnergy, which fails.
        WeatherEnergyDTO weatherEnergyDTO = weatherEnergyMapper.weatherEnergyToWeatherEnergyDTO(weatherEnergy);

        restWeatherEnergyMockMvc.perform(post("/api/weather-energies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherEnergyDTO)))
                .andExpect(status().isBadRequest());

        List<WeatherEnergy> weatherEnergies = weatherEnergyRepository.findAll();
        assertThat(weatherEnergies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWeatherEnergies() throws Exception {
        // Initialize the database
        weatherEnergyRepository.saveAndFlush(weatherEnergy);

        // Get all the weatherEnergies
        restWeatherEnergyMockMvc.perform(get("/api/weather-energies?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(weatherEnergy.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].isoCode").value(hasItem(DEFAULT_ISO_CODE.toString())))
                .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
                .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
                .andExpect(jsonPath("$.[*].solar").value(hasItem(DEFAULT_SOLAR.doubleValue())))
                .andExpect(jsonPath("$.[*].minTemp").value(hasItem(DEFAULT_MIN_TEMP.doubleValue())))
                .andExpect(jsonPath("$.[*].maxTemp").value(hasItem(DEFAULT_MAX_TEMP.doubleValue())))
                .andExpect(jsonPath("$.[*].temp").value(hasItem(DEFAULT_TEMP.doubleValue())))
                .andExpect(jsonPath("$.[*].sunset").value(hasItem(DEFAULT_SUNSET)))
                .andExpect(jsonPath("$.[*].sunrise").value(hasItem(DEFAULT_SUNRISE)))
                .andExpect(jsonPath("$.[*].hidraulic").value(hasItem(DEFAULT_HIDRAULIC.doubleValue())))
                .andExpect(jsonPath("$.[*].rain").value(hasItem(DEFAULT_RAIN.doubleValue())))
                .andExpect(jsonPath("$.[*].eolic").value(hasItem(DEFAULT_EOLIC.doubleValue())))
                .andExpect(jsonPath("$.[*].windSpeed").value(hasItem(DEFAULT_WIND_SPEED.doubleValue())))
                .andExpect(jsonPath("$.[*].clouds").value(hasItem(DEFAULT_CLOUDS.doubleValue())))
                .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())));
    }

    @Test
    @Transactional
    public void getWeatherEnergy() throws Exception {
        // Initialize the database
        weatherEnergyRepository.saveAndFlush(weatherEnergy);

        // Get the weatherEnergy
        restWeatherEnergyMockMvc.perform(get("/api/weather-energies/{id}", weatherEnergy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(weatherEnergy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isoCode").value(DEFAULT_ISO_CODE.toString()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.doubleValue()))
            .andExpect(jsonPath("$.solar").value(DEFAULT_SOLAR.doubleValue()))
            .andExpect(jsonPath("$.minTemp").value(DEFAULT_MIN_TEMP.doubleValue()))
            .andExpect(jsonPath("$.maxTemp").value(DEFAULT_MAX_TEMP.doubleValue()))
            .andExpect(jsonPath("$.temp").value(DEFAULT_TEMP.doubleValue()))
            .andExpect(jsonPath("$.sunset").value(DEFAULT_SUNSET))
            .andExpect(jsonPath("$.sunrise").value(DEFAULT_SUNRISE))
            .andExpect(jsonPath("$.hidraulic").value(DEFAULT_HIDRAULIC.doubleValue()))
            .andExpect(jsonPath("$.rain").value(DEFAULT_RAIN.doubleValue()))
            .andExpect(jsonPath("$.eolic").value(DEFAULT_EOLIC.doubleValue()))
            .andExpect(jsonPath("$.windSpeed").value(DEFAULT_WIND_SPEED.doubleValue()))
            .andExpect(jsonPath("$.clouds").value(DEFAULT_CLOUDS.doubleValue()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWeatherEnergy() throws Exception {
        // Get the weatherEnergy
        restWeatherEnergyMockMvc.perform(get("/api/weather-energies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeatherEnergy() throws Exception {
        // Initialize the database
        weatherEnergyRepository.saveAndFlush(weatherEnergy);
        int databaseSizeBeforeUpdate = weatherEnergyRepository.findAll().size();

        // Update the weatherEnergy
        WeatherEnergy updatedWeatherEnergy = weatherEnergyRepository.findOne(weatherEnergy.getId());
        updatedWeatherEnergy
                .name(UPDATED_NAME)
                .isoCode(UPDATED_ISO_CODE)
                .lat(UPDATED_LAT)
                .lon(UPDATED_LON)
                .solar(UPDATED_SOLAR)
                .minTemp(UPDATED_MIN_TEMP)
                .maxTemp(UPDATED_MAX_TEMP)
                .temp(UPDATED_TEMP)
                .sunset(UPDATED_SUNSET)
                .sunrise(UPDATED_SUNRISE)
                .hidraulic(UPDATED_HIDRAULIC)
                .rain(UPDATED_RAIN)
                .eolic(UPDATED_EOLIC)
                .windSpeed(UPDATED_WIND_SPEED)
                .clouds(UPDATED_CLOUDS)
                .createdDateTime(UPDATED_CREATED_DATE_TIME);
        WeatherEnergyDTO weatherEnergyDTO = weatherEnergyMapper.weatherEnergyToWeatherEnergyDTO(updatedWeatherEnergy);

        restWeatherEnergyMockMvc.perform(put("/api/weather-energies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherEnergyDTO)))
                .andExpect(status().isOk());

        // Validate the WeatherEnergy in the database
        List<WeatherEnergy> weatherEnergies = weatherEnergyRepository.findAll();
        assertThat(weatherEnergies).hasSize(databaseSizeBeforeUpdate);
        WeatherEnergy testWeatherEnergy = weatherEnergies.get(weatherEnergies.size() - 1);
        assertThat(testWeatherEnergy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWeatherEnergy.getIsoCode()).isEqualTo(UPDATED_ISO_CODE);
        assertThat(testWeatherEnergy.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testWeatherEnergy.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testWeatherEnergy.getSolar()).isEqualTo(UPDATED_SOLAR);
        assertThat(testWeatherEnergy.getMinTemp()).isEqualTo(UPDATED_MIN_TEMP);
        assertThat(testWeatherEnergy.getMaxTemp()).isEqualTo(UPDATED_MAX_TEMP);
        assertThat(testWeatherEnergy.getTemp()).isEqualTo(UPDATED_TEMP);
        assertThat(testWeatherEnergy.getSunset()).isEqualTo(UPDATED_SUNSET);
        assertThat(testWeatherEnergy.getSunrise()).isEqualTo(UPDATED_SUNRISE);
        assertThat(testWeatherEnergy.getHidraulic()).isEqualTo(UPDATED_HIDRAULIC);
        assertThat(testWeatherEnergy.getRain()).isEqualTo(UPDATED_RAIN);
        assertThat(testWeatherEnergy.getEolic()).isEqualTo(UPDATED_EOLIC);
        assertThat(testWeatherEnergy.getWindSpeed()).isEqualTo(UPDATED_WIND_SPEED);
        assertThat(testWeatherEnergy.getClouds()).isEqualTo(UPDATED_CLOUDS);
        assertThat(testWeatherEnergy.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void deleteWeatherEnergy() throws Exception {
        // Initialize the database
        weatherEnergyRepository.saveAndFlush(weatherEnergy);
        int databaseSizeBeforeDelete = weatherEnergyRepository.findAll().size();

        // Get the weatherEnergy
        restWeatherEnergyMockMvc.perform(delete("/api/weather-energies/{id}", weatherEnergy.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<WeatherEnergy> weatherEnergies = weatherEnergyRepository.findAll();
        assertThat(weatherEnergies).hasSize(databaseSizeBeforeDelete - 1);
    }
}
