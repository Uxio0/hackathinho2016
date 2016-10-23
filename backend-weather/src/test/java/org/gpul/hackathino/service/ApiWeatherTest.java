package org.gpul.hackathino.service;

import okhttp3.OkHttpClient;
import org.gpul.hackathino.domain.Weather;
import org.gpul.hackathino.domain.WeatherEnergy;
import org.gpul.hackathino.domain.enums.Factor;
import org.gpul.hackathino.service.util.Localizations;
import org.gpul.hackathino.service.util.OpenWeatherClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by david on 22/10/16.
 */
public class ApiWeatherTest {

    private static OpenWeatherClient openWeatherClient;

// TODO

    /**
     *
     * hidraulic = rain3h * 8 * factor_hidraulica
     eolic = windSpeed * factor_eolica
     solar = (sunset - sunrise) * mainTemp * (1- (cloudsAll/100)) * factor_solar
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException, JSONException {

        openWeatherClient = new OpenWeatherClient(new OkHttpClient());

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

        //JSONArray json = Localizations.getJson();
        for (int i = 0; i < json.length(); i++) {
            System.out.println(json.getJSONObject(i).get("isoCode"));
            System.out.println(json.getJSONObject(i).get("latitude"));
            System.out.println(json.getJSONObject(i).get("longitude"));
            System.out.println(json.getJSONObject(i).get("name"));

        }



        String[] provinces = new String[]{"A Coruña", "Álava", "Albacete", "Alicante", "Almeria", "Asturias", "Avila", "Badajoz", "Palma de Mallorca", "Barcelona", "Vizcaya", "Burgos", "Caceres", "Cadiz", "Cantabria", "Castellon", "Ciudad Real", "Cordoba", "Cuenca", "Guipuzcoa", "Girona", "Granada", "Guadalajara", "Huelva", "Huesca", "Jaen", "La Rioja", "Las Palmas", "León", "Lleida", "Lugo", "Madrid", "Malaga", "Murcia", "Navarra", "Ourense", "Palencia", "Pontevedra", "Salamanca", "Santa Cruz de Tenerife", "Segovia", "Sevilla", "Soria", "Tarragona", "Teruel", "Toledo", "Valencia", "Valladolid", "Zamora", "Zaragoza"};
        Arrays.asList(provinces).forEach(
                province -> {
                    try {
                        System.out.println(openWeatherClient.getWeatherByProvince(province));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );

        /*
        String province = "Madrid";

        Weather weather = openWeatherClient.getWeatherByProvince(province);
        System.out.println(weather.toString());
        */
    }


}
