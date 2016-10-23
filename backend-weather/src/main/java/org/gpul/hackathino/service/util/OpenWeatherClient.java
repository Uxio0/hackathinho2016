package org.gpul.hackathino.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.gpul.hackathino.domain.Weather;
import org.gpul.hackathino.domain.util.WeatherDeserializer;

import java.io.IOException;

/**
 *
 */
public class OpenWeatherClient {

    public final String PUBLIC_URL = "http://api.openweathermap.org/data/2.5/weather?units=metric&mode=json&APPID=391b521b838f4497c93ccce8c5460e48";
    private OkHttpClient client;
    private  ObjectMapper objectMapperWeather;


    public OpenWeatherClient(OkHttpClient client){
        this.client = client;
        createObjectMappers();
    }

    /**
     * Creates and configure an object Mapper with its deserializer types and injected values
     * @return
     */
    private void createObjectMappers() {
        this.objectMapperWeather = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Weather.class, new WeatherDeserializer());
        objectMapperWeather.registerModule(module);

    }

    /**
     * Returns a command line API with optional parameters
     * @param province
     * @return String json
     * @throws IOException
     */
    private  String getJsonWithParameters(String province) throws IOException {
        StringBuilder urlRequest = new StringBuilder(PUBLIC_URL);
        urlRequest.append("&q=" + province);

        Request request = new Request.Builder()
                .url(urlRequest.toString())
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private  String getJsonWithParametersByCoords(Double lat, Double lon) throws IOException {
        StringBuilder urlRequest = new StringBuilder(PUBLIC_URL);
        urlRequest.append("&lat=" + lat);
        urlRequest.append("&lon=" + lon);

        Request request = new Request.Builder()
                .url(urlRequest.toString())
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public Weather getWeatherByProvince(String province) throws IOException {
        return objectMapperWeather.readValue(getJsonWithParameters(province), Weather.class);
    }

    public Weather getWeatherByCoords(Double lat, Double lon) throws IOException {
        return objectMapperWeather.readValue(getJsonWithParametersByCoords(lat, lon), Weather.class);
    }

}
