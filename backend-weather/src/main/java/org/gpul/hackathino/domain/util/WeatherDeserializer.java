package org.gpul.hackathino.domain.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import org.gpul.hackathino.domain.Weather;

import java.io.IOException;

/**
 * Created by david on 22/10/16.
 */
public class WeatherDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<Weather> {

    @Override
    public Weather deserialize(JsonParser parser, DeserializationContext context) throws IOException {

        JsonNode node = parser.getCodec().readTree(parser);
        Weather weather = new Weather();

        weather.setLon(node.get("coord").get("lon").asDouble());
        weather.setLat(node.get("coord").get("lat").asDouble());

        weather.setWeatherMain(node.get("weather").get(0).get("main").asText());
        weather.setWeatherDescription(node.get("weather").get(0).get("description").asText());
        weather.setWeatherIcon(node.get("weather").get(0).get("icon").asText());

        weather.setBase(node.get("base").asText());
        weather.setCity(node.get("name").asText());
        weather.setCod(node.get("cod").asInt());

        weather.setMainTemp(node.get("main").get("temp").asDouble());
        weather.setMainPressure(node.get("main").get("pressure").asDouble());
        weather.setMainHumidity(node.get("main").get("humidity").asDouble());
        weather.setMainTempMin(node.get("main").get("temp_min").asDouble());
        weather.setMainTempMax(node.get("main").get("temp_max").asDouble());
        weather.setMainSeaLevel(node.get("main").get("sea_level").asDouble());
        weather.setMainGroundLevel(node.get("main").get("temp").asDouble());

        if(node.has("wind")){
            weather.setWindDeg(node.get("wind").get("deg").asDouble());
            weather.setWindSpeed(node.get("wind").get("speed").asDouble());
        }else{
            weather.setWindDeg(0.0);
            weather.setWindSpeed(0.0);
        }

        if(node.has("rain")){
            weather.setRain3H(node.get("rain").get("3h").asDouble());
        }else{
            weather.setRain3H(0.0);
        }

        if(node.has("clouds")){
            weather.setCloudsAll(node.get("clouds").get("all").asInt());
        }else{
            weather.setCloudsAll(0);
        }

        weather.setDt(node.get("dt").asInt());

        weather.setSysCountry(node.get("sys").get("country").asText());
        weather.setSysMessage(node.get("sys").get("message").asDouble());
        weather.setSysSunriseAsTimestamp(node.get("sys").get("sunrise").asInt());
        weather.setSysSunsetAsTimestamp(node.get("sys").get("sunset").asInt());
        weather.setSysMessage(node.get("sys").get("message").asDouble());

        weather.setSysSunrise(ZonedDateTimeUtils.timestampToZonedDateTime(node.get("sys").get("sunrise").asLong()));
        weather.setSysSunset(ZonedDateTimeUtils.timestampToZonedDateTime(node.get("sys").get("sunset").asLong()));

        return weather;
    }
}
