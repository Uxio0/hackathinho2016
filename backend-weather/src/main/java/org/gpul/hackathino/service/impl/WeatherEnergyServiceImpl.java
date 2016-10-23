package org.gpul.hackathino.service.impl;

import okhttp3.OkHttpClient;
import org.gpul.hackathino.domain.Weather;
import org.gpul.hackathino.domain.WeatherEnergy;
import org.gpul.hackathino.domain.enums.Factor;
import org.gpul.hackathino.repository.WeatherEnergyRepository;
import org.gpul.hackathino.service.WeatherEnergyService;
import org.gpul.hackathino.service.dto.WeatherEnergyDTO;
import org.gpul.hackathino.service.mapper.WeatherEnergyMapper;
import org.gpul.hackathino.service.util.Localizations;
import org.gpul.hackathino.service.util.OpenWeatherClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Service Implementation for managing WeatherEnergy.
 */
@Service
@Transactional
public class WeatherEnergyServiceImpl implements WeatherEnergyService{

    private final Logger log = LoggerFactory.getLogger(WeatherEnergyServiceImpl.class);
    
    @Inject
    private WeatherEnergyRepository weatherEnergyRepository;

    @Inject
    private WeatherEnergyMapper weatherEnergyMapper;

    private OpenWeatherClient openWeatherClient;

    private static final Map<String, String> provincesList;
    static
    {
        provincesList = new HashMap<>();
        provincesList.put("ES-C", "A Coruña");
        provincesList.put("ES-VI", "Álava");
        provincesList.put("ES-AB", "Albacete");
        provincesList.put("ES-A", "Alicante");
        provincesList.put("ES-AL", "Almería");
        provincesList.put("ES-O", "Asturias");
        provincesList.put("ES-AV", "Ávila");
        provincesList.put("ES-BA", "Badajoz");
        provincesList.put("ES-PM", "Balears");
        provincesList.put("ES-B", "Barcelona");
        provincesList.put("ES-BI", "Bizkaia");
        provincesList.put("ES-BU", "Burgos");
        provincesList.put("ES-CC", "Cáceres");
        provincesList.put("ES-CA", "Cádiz");
        provincesList.put("ES-S", "Cantabria");
        provincesList.put("ES-CS", "Castellón");
        provincesList.put("ES-CR", "Ciudad Real");
        provincesList.put("ES-CO", "Córdoba");
        provincesList.put("ES-CU", "Cuenca");
        provincesList.put("ES-SS", "Gipuzkoa");
        provincesList.put("ES-GI", "Girona");
        provincesList.put("ES-GR", "Granada");
        provincesList.put("ES-GU", "Guadalajara");
        provincesList.put("ES-H", "Huelva");
        provincesList.put("ES-HU", "Huesca");
        provincesList.put("ES-J", "Jaén");
        provincesList.put("ES-LO", "La Rioja");
        provincesList.put("ES-GC", "Las Palmas");
        provincesList.put("ES-LE", "León");
        provincesList.put("ES-L", "Lleida");
        provincesList.put("ES-LU", "Lugo");
        provincesList.put("ES-M", "Madrid");
        provincesList.put("ES-MA", "Málaga");
        provincesList.put("ES-MU", "Murcia");
        provincesList.put("ES-NA", "Navarra");
        provincesList.put("ES-OR", "Ourense");
        provincesList.put("ES-P", "Palencia");
        provincesList.put("ES-PO", "Pontevedra");
        provincesList.put("ES-SA", "Salamanca");
        provincesList.put("ES-TF", "Santa Cruz de Tenerife");
        provincesList.put("ES-SG", "Segovia");
        provincesList.put("ES-SE", "Sevilla");
        provincesList.put("ES-SO", "Soria");
        provincesList.put("ES-T", "Tarragona");
        provincesList.put("ES-TE", "Teruel");
        provincesList.put("ES-TO", "Toledo");
        provincesList.put("ES-V", "Valencia");
        provincesList.put("ES-VA", "Valladolid");
        provincesList.put("ES-ZA", "Zamora");
        provincesList.put("ES-Z", "Zaragoza");
    }

    @PostConstruct
    private void init(){
        openWeatherClient = new OpenWeatherClient(new OkHttpClient());
    }

    /**
     * Donwload by pronvices list
     */
    @Scheduled(fixedDelay=86400000)
    public void downloadByProvinces(){

        List<WeatherEnergy> list = new ArrayList<>();

        for(Map.Entry<String, String> entry : provincesList.entrySet()) {
            String isoCode = entry.getKey();
            String province = entry.getValue();

            try {
                Weather weather = openWeatherClient.getWeatherByProvince(province);

                WeatherEnergy weatherEnergy = new WeatherEnergy();
                weatherEnergy.setName(province);
                weatherEnergy.setIsoCode(isoCode);
                weatherEnergy.setCreatedDateTime(LocalDate.now());
                weatherEnergy.setLat(weather.getLat());
                weatherEnergy.setLon(weather.getLon());

                weatherEnergy.setRain(weather.getRain3H());
                weatherEnergy.setClouds(new Double(weather.getCloudsAll()));
                weatherEnergy.setMaxTemp(weather.getMainTempMax());
                weatherEnergy.setMinTemp(weather.getMainTempMin());
                weatherEnergy.setTemp(weather.getMainTemp());
                weatherEnergy.setSunset(weather.getSysSunsetAsTimestamp());
                weatherEnergy.setSunrise(weather.getSysSunriseAsTimestamp());
                weatherEnergy.setWindSpeed(weather.getWindSpeed());


                weatherEnergy.setHidraulic(weather.getRain3H() * 8 * Factor.HIDRAULIC.getValue());

                weatherEnergy.setEolic(weather.getWindSpeed() * Factor.EOLIC.getValue());

                weatherEnergy.setSolar(
                        new Double(weather.getSysSunsetAsTimestamp()-weather.getSysSunriseAsTimestamp())
                                * weather.getMainTemp()
                                * new Double(1-(weather.getCloudsAll()/100))
                                * Factor.SOLAR.getValue());

                list.add(weatherEnergy);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        weatherEnergyRepository.save(list);
    }

    /**
     * Donwload each 24h
     * @throws JSONException
     * @throws IOException
     */
    //@Scheduled(fixedDelay=86400000)
    public void download() throws JSONException, IOException {

        List<WeatherEnergy> list = new ArrayList<>();
        JSONArray json = Localizations.getJson();
        System.out.println(json.length());
        for (int i = 0; i < json.length(); i++) {

            String isoCode = json.getJSONObject(i).getString("isoCode");
            Double lat = json.getJSONObject(i).getDouble("latitude");
            Double lon = json.getJSONObject(i).getDouble("longitude");
            String name = json.getJSONObject(i).getString("name");

            Weather weather = openWeatherClient.getWeatherByCoords(lat, lon);
            WeatherEnergy weatherEnergy = new WeatherEnergy();
            weatherEnergy.setName(name);
            weatherEnergy.setIsoCode(isoCode);
            weatherEnergy.setCreatedDateTime(LocalDate.now());
            weatherEnergy.setLat(lat);
            weatherEnergy.setLon(lon);

            weatherEnergy.setRain(weather.getRain3H());
            weatherEnergy.setClouds(new Double(weather.getCloudsAll()));
            weatherEnergy.setMaxTemp(weather.getMainTempMax());
            weatherEnergy.setMinTemp(weather.getMainTempMin());
            weatherEnergy.setTemp(weather.getMainTemp());
            weatherEnergy.setSunset(weather.getSysSunsetAsTimestamp());
            weatherEnergy.setSunrise(weather.getSysSunriseAsTimestamp());
            weatherEnergy.setWindSpeed(weather.getWindSpeed());

            /**
            hidraulic = rain3h * 8 * factor_hidraulica
            eolic = windSpeed * factor_eolica
            solar = (sunset - sunrise) * mainTemp * (1- (cloudsAll/100)) * factor_solar
            **/

            weatherEnergy.setHidraulic(weather.getRain3H() * 8 * Factor.HIDRAULIC.getValue());

            weatherEnergy.setEolic(weather.getWindSpeed() * Factor.EOLIC.getValue());

            weatherEnergy.setSolar(
                    new Double(weather.getSysSunsetAsTimestamp()-weather.getSysSunriseAsTimestamp())
                    * weather.getMainTemp()
                    * new Double(1-(weather.getCloudsAll()/100))
                    * Factor.SOLAR.getValue());

            list.add(weatherEnergy);

        }

        weatherEnergyRepository.save(list);
    }

    /**
     * Save a weatherEnergy.
     *
     * @param weatherEnergyDTO the entity to save
     * @return the persisted entity
     */
    public WeatherEnergyDTO save(WeatherEnergyDTO weatherEnergyDTO) {
        log.debug("Request to save WeatherEnergy : {}", weatherEnergyDTO);
        WeatherEnergy weatherEnergy = weatherEnergyMapper.weatherEnergyDTOToWeatherEnergy(weatherEnergyDTO);
        weatherEnergy = weatherEnergyRepository.save(weatherEnergy);
        WeatherEnergyDTO result = weatherEnergyMapper.weatherEnergyToWeatherEnergyDTO(weatherEnergy);
        return result;
    }

    /**
     *  Get all the weatherEnergies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<WeatherEnergyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WeatherEnergies");
        Page<WeatherEnergy> result = weatherEnergyRepository.findAll(pageable);
        return result.map(weatherEnergy -> weatherEnergyMapper.weatherEnergyToWeatherEnergyDTO(weatherEnergy));
    }

    /**
     *  Get one weatherEnergy by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public WeatherEnergyDTO findOne(Long id) {
        log.debug("Request to get WeatherEnergy : {}", id);
        WeatherEnergy weatherEnergy = weatherEnergyRepository.findOne(id);
        WeatherEnergyDTO weatherEnergyDTO = weatherEnergyMapper.weatherEnergyToWeatherEnergyDTO(weatherEnergy);
        return weatherEnergyDTO;
    }

    /**
     *  Delete the  weatherEnergy by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WeatherEnergy : {}", id);
        weatherEnergyRepository.delete(id);
    }
}
