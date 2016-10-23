package org.gpul.hackathino.service;

import org.gpul.hackathino.service.util.OpenWeatherClient;
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


        String[] provinces = new String[]{"A Coruña", "Vitoria", "Albacete", "Alicante", "Almeria",
                "Asturias", "Avila", "Badajoz",
                "Palma de Mallorca", "Barcelona", "Bilbao", "Burgos", "Caceres", "Cadiz",
                "Cantabria", "Castellon", "Ciudad Real", "Cordoba", "Cuenca", "San Sebastian",
                "Girona", "Granada", "Guadalajara", "Huelva", "Huesca", "Jaen", "La Rioja",
                "Las Palmas", "Leon", "Lleida", "Lugo", "Madrid", "Malaga",
                "Murcia", "Navarra", "Ourense", "Palencia",
                "Pontevedra", "Salamanca", "Santa Cruz de Tenerife", "Segovia", "Sevilla",
                "Soria", "Tarragona", "Teruel", "Toledo", "Valencia", "Valladolid", "Zamora", "Zaragoza"};
        provinces = new String[]{"A Coruña", "Álava", "Albacete", "Alicante", "Almeria", "Asturias", "Avila", "Badajoz", "Palma de Mallorca", "Barcelona", "Vizcaya", "Burgos", "Caceres", "Cadiz", "Cantabria", "Castellon", "Ciudad Real", "Cordoba", "Cuenca", "Guipuzcoa", "Girona", "Granada", "Guadalajara", "Huelva", "Huesca", "Jaen", "La Rioja", "Las Palmas", "León", "Lleida", "Lugo", "Madrid", "Malaga", "Murcia", "Navarra", "Ourense", "Palencia", "Pontevedra", "Salamanca", "Santa Cruz de Tenerife", "Segovia", "Sevilla", "Soria", "Tarragona", "Teruel", "Toledo", "Valencia", "Valladolid", "Zamora", "Zaragoza"};
        int i=1;
        for (String s : Arrays.asList(provinces)) {
            System.out.println(i+": '"+s+"',");
            i++;
        }

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
