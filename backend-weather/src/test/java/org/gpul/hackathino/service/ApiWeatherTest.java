package org.gpul.hackathino.service;

import okhttp3.OkHttpClient;
import org.gpul.hackathino.service.util.Localizations;
import org.gpul.hackathino.service.util.OpenWeatherClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;

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


        JSONArray json = Localizations.getJson();
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
