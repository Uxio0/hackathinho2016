package org.gpul.hackathino.web.rest;

import org.gpul.hackathino.WeatheropendataApp;

import org.gpul.hackathino.domain.Weather;
import org.gpul.hackathino.repository.WeatherRepository;
import org.gpul.hackathino.service.WeatherService;
import org.gpul.hackathino.service.dto.WeatherDTO;
import org.gpul.hackathino.service.mapper.WeatherMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WeatherResource REST controller.
 *
 * @see WeatherResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeatheropendataApp.class)
public class WeatherResourceIntTest {

    private static final Double DEFAULT_LON = 1D;
    private static final Double UPDATED_LON = 2D;

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;

    private static final String DEFAULT_CITY = "AAAAA";
    private static final String UPDATED_CITY = "BBBBB";

    private static final String DEFAULT_WEATHER_MAIN = "AAAAA";
    private static final String UPDATED_WEATHER_MAIN = "BBBBB";

    private static final String DEFAULT_WEATHER_DESCRIPTION = "AAAAA";
    private static final String UPDATED_WEATHER_DESCRIPTION = "BBBBB";

    private static final String DEFAULT_WEATHER_ICON = "AAAAA";
    private static final String UPDATED_WEATHER_ICON = "BBBBB";

    private static final String DEFAULT_BASE = "AAAAA";
    private static final String UPDATED_BASE = "BBBBB";

    private static final Double DEFAULT_MAIN_TEMP = 1D;
    private static final Double UPDATED_MAIN_TEMP = 2D;

    private static final Double DEFAULT_MAIN_PRESSURE = 1D;
    private static final Double UPDATED_MAIN_PRESSURE = 2D;

    private static final Double DEFAULT_MAIN_HUMIDITY = 1D;
    private static final Double UPDATED_MAIN_HUMIDITY = 2D;

    private static final Double DEFAULT_MAIN_TEMP_MIN = 1D;
    private static final Double UPDATED_MAIN_TEMP_MIN = 2D;

    private static final Double DEFAULT_MAIN_TEMP_MAX = 1D;
    private static final Double UPDATED_MAIN_TEMP_MAX = 2D;

    private static final Double DEFAULT_MAIN_SEA_LEVEL = 1D;
    private static final Double UPDATED_MAIN_SEA_LEVEL = 2D;

    private static final Double DEFAULT_MAIN_GROUND_LEVEL = 1D;
    private static final Double UPDATED_MAIN_GROUND_LEVEL = 2D;

    private static final Double DEFAULT_WIND_SPEED = 1D;
    private static final Double UPDATED_WIND_SPEED = 2D;

    private static final Double DEFAULT_WIND_DEG = 1D;
    private static final Double UPDATED_WIND_DEG = 2D;

    private static final Double DEFAULT_RAIN_3_H = 1D;
    private static final Double UPDATED_RAIN_3_H = 2D;

    private static final Integer DEFAULT_CLOUDS_ALL = 1;
    private static final Integer UPDATED_CLOUDS_ALL = 2;

    private static final Integer DEFAULT_DT = 1;
    private static final Integer UPDATED_DT = 2;

    private static final Double DEFAULT_SYS_MESSAGE = 1D;
    private static final Double UPDATED_SYS_MESSAGE = 2D;

    private static final String DEFAULT_SYS_COUNTRY = "AAAAA";
    private static final String UPDATED_SYS_COUNTRY = "BBBBB";

    private static final Integer DEFAULT_SYS_SUNRISE_AS_TIMESTAMP = 1;
    private static final Integer UPDATED_SYS_SUNRISE_AS_TIMESTAMP = 2;

    private static final Integer DEFAULT_SYS_SUNSET_AS_TIMESTAMP = 1;
    private static final Integer UPDATED_SYS_SUNSET_AS_TIMESTAMP = 2;

    private static final ZonedDateTime DEFAULT_SYS_SUNRISE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_SYS_SUNRISE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_SYS_SUNRISE_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_SYS_SUNRISE);

    private static final ZonedDateTime DEFAULT_SYS_SUNSET = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_SYS_SUNSET = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_SYS_SUNSET_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_SYS_SUNSET);

    private static final Integer DEFAULT_COD = 1;
    private static final Integer UPDATED_COD = 2;

    @Inject
    private WeatherRepository weatherRepository;

    @Inject
    private WeatherMapper weatherMapper;

    @Inject
    private WeatherService weatherService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWeatherMockMvc;

    private Weather weather;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WeatherResource weatherResource = new WeatherResource();
        ReflectionTestUtils.setField(weatherResource, "weatherService", weatherService);
        this.restWeatherMockMvc = MockMvcBuilders.standaloneSetup(weatherResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Weather createEntity(EntityManager em) {
        Weather weather = new Weather()
                .lon(DEFAULT_LON)
                .lat(DEFAULT_LAT)
                .city(DEFAULT_CITY)
                .weatherMain(DEFAULT_WEATHER_MAIN)
                .weatherDescription(DEFAULT_WEATHER_DESCRIPTION)
                .weatherIcon(DEFAULT_WEATHER_ICON)
                .base(DEFAULT_BASE)
                .mainTemp(DEFAULT_MAIN_TEMP)
                .mainPressure(DEFAULT_MAIN_PRESSURE)
                .mainHumidity(DEFAULT_MAIN_HUMIDITY)
                .mainTempMin(DEFAULT_MAIN_TEMP_MIN)
                .mainTempMax(DEFAULT_MAIN_TEMP_MAX)
                .mainSeaLevel(DEFAULT_MAIN_SEA_LEVEL)
                .mainGroundLevel(DEFAULT_MAIN_GROUND_LEVEL)
                .windSpeed(DEFAULT_WIND_SPEED)
                .windDeg(DEFAULT_WIND_DEG)
                .rain3H(DEFAULT_RAIN_3_H)
                .cloudsAll(DEFAULT_CLOUDS_ALL)
                .dt(DEFAULT_DT)
                .sysMessage(DEFAULT_SYS_MESSAGE)
                .sysCountry(DEFAULT_SYS_COUNTRY)
                .sysSunriseAsTimestamp(DEFAULT_SYS_SUNRISE_AS_TIMESTAMP)
                .sysSunsetAsTimestamp(DEFAULT_SYS_SUNSET_AS_TIMESTAMP)
                .sysSunrise(DEFAULT_SYS_SUNRISE)
                .sysSunset(DEFAULT_SYS_SUNSET)
                .cod(DEFAULT_COD);
        return weather;
    }

    @Before
    public void initTest() {
        weather = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeather() throws Exception {
        int databaseSizeBeforeCreate = weatherRepository.findAll().size();

        // Create the Weather
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isCreated());

        // Validate the Weather in the database
        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeCreate + 1);
        Weather testWeather = weathers.get(weathers.size() - 1);
        assertThat(testWeather.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testWeather.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testWeather.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testWeather.getWeatherMain()).isEqualTo(DEFAULT_WEATHER_MAIN);
        assertThat(testWeather.getWeatherDescription()).isEqualTo(DEFAULT_WEATHER_DESCRIPTION);
        assertThat(testWeather.getWeatherIcon()).isEqualTo(DEFAULT_WEATHER_ICON);
        assertThat(testWeather.getBase()).isEqualTo(DEFAULT_BASE);
        assertThat(testWeather.getMainTemp()).isEqualTo(DEFAULT_MAIN_TEMP);
        assertThat(testWeather.getMainPressure()).isEqualTo(DEFAULT_MAIN_PRESSURE);
        assertThat(testWeather.getMainHumidity()).isEqualTo(DEFAULT_MAIN_HUMIDITY);
        assertThat(testWeather.getMainTempMin()).isEqualTo(DEFAULT_MAIN_TEMP_MIN);
        assertThat(testWeather.getMainTempMax()).isEqualTo(DEFAULT_MAIN_TEMP_MAX);
        assertThat(testWeather.getMainSeaLevel()).isEqualTo(DEFAULT_MAIN_SEA_LEVEL);
        assertThat(testWeather.getMainGroundLevel()).isEqualTo(DEFAULT_MAIN_GROUND_LEVEL);
        assertThat(testWeather.getWindSpeed()).isEqualTo(DEFAULT_WIND_SPEED);
        assertThat(testWeather.getWindDeg()).isEqualTo(DEFAULT_WIND_DEG);
        assertThat(testWeather.getRain3H()).isEqualTo(DEFAULT_RAIN_3_H);
        assertThat(testWeather.getCloudsAll()).isEqualTo(DEFAULT_CLOUDS_ALL);
        assertThat(testWeather.getDt()).isEqualTo(DEFAULT_DT);
        assertThat(testWeather.getSysMessage()).isEqualTo(DEFAULT_SYS_MESSAGE);
        assertThat(testWeather.getSysCountry()).isEqualTo(DEFAULT_SYS_COUNTRY);
        assertThat(testWeather.getSysSunriseAsTimestamp()).isEqualTo(DEFAULT_SYS_SUNRISE_AS_TIMESTAMP);
        assertThat(testWeather.getSysSunsetAsTimestamp()).isEqualTo(DEFAULT_SYS_SUNSET_AS_TIMESTAMP);
        assertThat(testWeather.getSysSunrise()).isEqualTo(DEFAULT_SYS_SUNRISE);
        assertThat(testWeather.getSysSunset()).isEqualTo(DEFAULT_SYS_SUNSET);
        assertThat(testWeather.getCod()).isEqualTo(DEFAULT_COD);
    }

    @Test
    @Transactional
    public void checkLonIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setLon(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setLat(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setCity(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeatherMainIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setWeatherMain(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeatherDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setWeatherDescription(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeatherIconIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setWeatherIcon(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMainTempIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setMainTemp(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMainPressureIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setMainPressure(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMainHumidityIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setMainHumidity(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMainTempMinIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setMainTempMin(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMainTempMaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setMainTempMax(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMainSeaLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setMainSeaLevel(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMainGroundLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setMainGroundLevel(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWindSpeedIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setWindSpeed(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWindDegIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setWindDeg(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRain3HIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setRain3H(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCloudsAllIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setCloudsAll(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSysCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setSysCountry(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSysSunriseAsTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setSysSunriseAsTimestamp(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSysSunsetAsTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setSysSunsetAsTimestamp(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSysSunriseIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setSysSunrise(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSysSunsetIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setSysSunset(null);

        // Create the Weather, which fails.
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(weather);

        restWeatherMockMvc.perform(post("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isBadRequest());

        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWeathers() throws Exception {
        // Initialize the database
        weatherRepository.saveAndFlush(weather);

        // Get all the weathers
        restWeatherMockMvc.perform(get("/api/weathers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(weather.getId().intValue())))
                .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
                .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].weatherMain").value(hasItem(DEFAULT_WEATHER_MAIN.toString())))
                .andExpect(jsonPath("$.[*].weatherDescription").value(hasItem(DEFAULT_WEATHER_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].weatherIcon").value(hasItem(DEFAULT_WEATHER_ICON.toString())))
                .andExpect(jsonPath("$.[*].base").value(hasItem(DEFAULT_BASE.toString())))
                .andExpect(jsonPath("$.[*].mainTemp").value(hasItem(DEFAULT_MAIN_TEMP.doubleValue())))
                .andExpect(jsonPath("$.[*].mainPressure").value(hasItem(DEFAULT_MAIN_PRESSURE.doubleValue())))
                .andExpect(jsonPath("$.[*].mainHumidity").value(hasItem(DEFAULT_MAIN_HUMIDITY.doubleValue())))
                .andExpect(jsonPath("$.[*].mainTempMin").value(hasItem(DEFAULT_MAIN_TEMP_MIN.doubleValue())))
                .andExpect(jsonPath("$.[*].mainTempMax").value(hasItem(DEFAULT_MAIN_TEMP_MAX.doubleValue())))
                .andExpect(jsonPath("$.[*].mainSeaLevel").value(hasItem(DEFAULT_MAIN_SEA_LEVEL.doubleValue())))
                .andExpect(jsonPath("$.[*].mainGroundLevel").value(hasItem(DEFAULT_MAIN_GROUND_LEVEL.doubleValue())))
                .andExpect(jsonPath("$.[*].windSpeed").value(hasItem(DEFAULT_WIND_SPEED.doubleValue())))
                .andExpect(jsonPath("$.[*].windDeg").value(hasItem(DEFAULT_WIND_DEG.doubleValue())))
                .andExpect(jsonPath("$.[*].rain3H").value(hasItem(DEFAULT_RAIN_3_H.doubleValue())))
                .andExpect(jsonPath("$.[*].cloudsAll").value(hasItem(DEFAULT_CLOUDS_ALL)))
                .andExpect(jsonPath("$.[*].dt").value(hasItem(DEFAULT_DT)))
                .andExpect(jsonPath("$.[*].sysMessage").value(hasItem(DEFAULT_SYS_MESSAGE.doubleValue())))
                .andExpect(jsonPath("$.[*].sysCountry").value(hasItem(DEFAULT_SYS_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].sysSunriseAsTimestamp").value(hasItem(DEFAULT_SYS_SUNRISE_AS_TIMESTAMP)))
                .andExpect(jsonPath("$.[*].sysSunsetAsTimestamp").value(hasItem(DEFAULT_SYS_SUNSET_AS_TIMESTAMP)))
                .andExpect(jsonPath("$.[*].sysSunrise").value(hasItem(DEFAULT_SYS_SUNRISE_STR)))
                .andExpect(jsonPath("$.[*].sysSunset").value(hasItem(DEFAULT_SYS_SUNSET_STR)))
                .andExpect(jsonPath("$.[*].cod").value(hasItem(DEFAULT_COD)));
    }

    @Test
    @Transactional
    public void getWeather() throws Exception {
        // Initialize the database
        weatherRepository.saveAndFlush(weather);

        // Get the weather
        restWeatherMockMvc.perform(get("/api/weathers/{id}", weather.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(weather.getId().intValue()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.doubleValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.weatherMain").value(DEFAULT_WEATHER_MAIN.toString()))
            .andExpect(jsonPath("$.weatherDescription").value(DEFAULT_WEATHER_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.weatherIcon").value(DEFAULT_WEATHER_ICON.toString()))
            .andExpect(jsonPath("$.base").value(DEFAULT_BASE.toString()))
            .andExpect(jsonPath("$.mainTemp").value(DEFAULT_MAIN_TEMP.doubleValue()))
            .andExpect(jsonPath("$.mainPressure").value(DEFAULT_MAIN_PRESSURE.doubleValue()))
            .andExpect(jsonPath("$.mainHumidity").value(DEFAULT_MAIN_HUMIDITY.doubleValue()))
            .andExpect(jsonPath("$.mainTempMin").value(DEFAULT_MAIN_TEMP_MIN.doubleValue()))
            .andExpect(jsonPath("$.mainTempMax").value(DEFAULT_MAIN_TEMP_MAX.doubleValue()))
            .andExpect(jsonPath("$.mainSeaLevel").value(DEFAULT_MAIN_SEA_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.mainGroundLevel").value(DEFAULT_MAIN_GROUND_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.windSpeed").value(DEFAULT_WIND_SPEED.doubleValue()))
            .andExpect(jsonPath("$.windDeg").value(DEFAULT_WIND_DEG.doubleValue()))
            .andExpect(jsonPath("$.rain3H").value(DEFAULT_RAIN_3_H.doubleValue()))
            .andExpect(jsonPath("$.cloudsAll").value(DEFAULT_CLOUDS_ALL))
            .andExpect(jsonPath("$.dt").value(DEFAULT_DT))
            .andExpect(jsonPath("$.sysMessage").value(DEFAULT_SYS_MESSAGE.doubleValue()))
            .andExpect(jsonPath("$.sysCountry").value(DEFAULT_SYS_COUNTRY.toString()))
            .andExpect(jsonPath("$.sysSunriseAsTimestamp").value(DEFAULT_SYS_SUNRISE_AS_TIMESTAMP))
            .andExpect(jsonPath("$.sysSunsetAsTimestamp").value(DEFAULT_SYS_SUNSET_AS_TIMESTAMP))
            .andExpect(jsonPath("$.sysSunrise").value(DEFAULT_SYS_SUNRISE_STR))
            .andExpect(jsonPath("$.sysSunset").value(DEFAULT_SYS_SUNSET_STR))
            .andExpect(jsonPath("$.cod").value(DEFAULT_COD));
    }

    @Test
    @Transactional
    public void getNonExistingWeather() throws Exception {
        // Get the weather
        restWeatherMockMvc.perform(get("/api/weathers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeather() throws Exception {
        // Initialize the database
        weatherRepository.saveAndFlush(weather);
        int databaseSizeBeforeUpdate = weatherRepository.findAll().size();

        // Update the weather
        Weather updatedWeather = weatherRepository.findOne(weather.getId());
        updatedWeather
                .lon(UPDATED_LON)
                .lat(UPDATED_LAT)
                .city(UPDATED_CITY)
                .weatherMain(UPDATED_WEATHER_MAIN)
                .weatherDescription(UPDATED_WEATHER_DESCRIPTION)
                .weatherIcon(UPDATED_WEATHER_ICON)
                .base(UPDATED_BASE)
                .mainTemp(UPDATED_MAIN_TEMP)
                .mainPressure(UPDATED_MAIN_PRESSURE)
                .mainHumidity(UPDATED_MAIN_HUMIDITY)
                .mainTempMin(UPDATED_MAIN_TEMP_MIN)
                .mainTempMax(UPDATED_MAIN_TEMP_MAX)
                .mainSeaLevel(UPDATED_MAIN_SEA_LEVEL)
                .mainGroundLevel(UPDATED_MAIN_GROUND_LEVEL)
                .windSpeed(UPDATED_WIND_SPEED)
                .windDeg(UPDATED_WIND_DEG)
                .rain3H(UPDATED_RAIN_3_H)
                .cloudsAll(UPDATED_CLOUDS_ALL)
                .dt(UPDATED_DT)
                .sysMessage(UPDATED_SYS_MESSAGE)
                .sysCountry(UPDATED_SYS_COUNTRY)
                .sysSunriseAsTimestamp(UPDATED_SYS_SUNRISE_AS_TIMESTAMP)
                .sysSunsetAsTimestamp(UPDATED_SYS_SUNSET_AS_TIMESTAMP)
                .sysSunrise(UPDATED_SYS_SUNRISE)
                .sysSunset(UPDATED_SYS_SUNSET)
                .cod(UPDATED_COD);
        WeatherDTO weatherDTO = weatherMapper.weatherToWeatherDTO(updatedWeather);

        restWeatherMockMvc.perform(put("/api/weathers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weatherDTO)))
                .andExpect(status().isOk());

        // Validate the Weather in the database
        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeUpdate);
        Weather testWeather = weathers.get(weathers.size() - 1);
        assertThat(testWeather.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testWeather.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testWeather.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testWeather.getWeatherMain()).isEqualTo(UPDATED_WEATHER_MAIN);
        assertThat(testWeather.getWeatherDescription()).isEqualTo(UPDATED_WEATHER_DESCRIPTION);
        assertThat(testWeather.getWeatherIcon()).isEqualTo(UPDATED_WEATHER_ICON);
        assertThat(testWeather.getBase()).isEqualTo(UPDATED_BASE);
        assertThat(testWeather.getMainTemp()).isEqualTo(UPDATED_MAIN_TEMP);
        assertThat(testWeather.getMainPressure()).isEqualTo(UPDATED_MAIN_PRESSURE);
        assertThat(testWeather.getMainHumidity()).isEqualTo(UPDATED_MAIN_HUMIDITY);
        assertThat(testWeather.getMainTempMin()).isEqualTo(UPDATED_MAIN_TEMP_MIN);
        assertThat(testWeather.getMainTempMax()).isEqualTo(UPDATED_MAIN_TEMP_MAX);
        assertThat(testWeather.getMainSeaLevel()).isEqualTo(UPDATED_MAIN_SEA_LEVEL);
        assertThat(testWeather.getMainGroundLevel()).isEqualTo(UPDATED_MAIN_GROUND_LEVEL);
        assertThat(testWeather.getWindSpeed()).isEqualTo(UPDATED_WIND_SPEED);
        assertThat(testWeather.getWindDeg()).isEqualTo(UPDATED_WIND_DEG);
        assertThat(testWeather.getRain3H()).isEqualTo(UPDATED_RAIN_3_H);
        assertThat(testWeather.getCloudsAll()).isEqualTo(UPDATED_CLOUDS_ALL);
        assertThat(testWeather.getDt()).isEqualTo(UPDATED_DT);
        assertThat(testWeather.getSysMessage()).isEqualTo(UPDATED_SYS_MESSAGE);
        assertThat(testWeather.getSysCountry()).isEqualTo(UPDATED_SYS_COUNTRY);
        assertThat(testWeather.getSysSunriseAsTimestamp()).isEqualTo(UPDATED_SYS_SUNRISE_AS_TIMESTAMP);
        assertThat(testWeather.getSysSunsetAsTimestamp()).isEqualTo(UPDATED_SYS_SUNSET_AS_TIMESTAMP);
        assertThat(testWeather.getSysSunrise()).isEqualTo(UPDATED_SYS_SUNRISE);
        assertThat(testWeather.getSysSunset()).isEqualTo(UPDATED_SYS_SUNSET);
        assertThat(testWeather.getCod()).isEqualTo(UPDATED_COD);
    }

    @Test
    @Transactional
    public void deleteWeather() throws Exception {
        // Initialize the database
        weatherRepository.saveAndFlush(weather);
        int databaseSizeBeforeDelete = weatherRepository.findAll().size();

        // Get the weather
        restWeatherMockMvc.perform(delete("/api/weathers/{id}", weather.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Weather> weathers = weatherRepository.findAll();
        assertThat(weathers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
