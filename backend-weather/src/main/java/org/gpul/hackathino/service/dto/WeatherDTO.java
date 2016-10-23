package org.gpul.hackathino.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Weather entity.
 */
public class WeatherDTO implements Serializable {

    private Long id;

    @NotNull
    private Double lon;

    @NotNull
    private Double lat;

    @NotNull
    private String city;

    @NotNull
    private String weatherMain;

    @NotNull
    private String weatherDescription;

    @NotNull
    private String weatherIcon;

    private String base;

    @NotNull
    private Double mainTemp;

    @NotNull
    private Double mainPressure;

    @NotNull
    private Double mainHumidity;

    @NotNull
    private Double mainTempMin;

    @NotNull
    private Double mainTempMax;

    @NotNull
    private Double mainSeaLevel;

    @NotNull
    private Double mainGroundLevel;

    @NotNull
    private Double windSpeed;

    @NotNull
    private Double windDeg;

    @NotNull
    private Double rain3H;

    @NotNull
    private Integer cloudsAll;

    private Integer dt;

    private Double sysMessage;

    @NotNull
    private String sysCountry;

    @NotNull
    private Integer sysSunriseAsTimestamp;

    @NotNull
    private Integer sysSunsetAsTimestamp;

    @NotNull
    private ZonedDateTime sysSunrise;

    @NotNull
    private ZonedDateTime sysSunset;

    private Integer cod;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }
    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }
    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
    public Double getMainTemp() {
        return mainTemp;
    }

    public void setMainTemp(Double mainTemp) {
        this.mainTemp = mainTemp;
    }
    public Double getMainPressure() {
        return mainPressure;
    }

    public void setMainPressure(Double mainPressure) {
        this.mainPressure = mainPressure;
    }
    public Double getMainHumidity() {
        return mainHumidity;
    }

    public void setMainHumidity(Double mainHumidity) {
        this.mainHumidity = mainHumidity;
    }
    public Double getMainTempMin() {
        return mainTempMin;
    }

    public void setMainTempMin(Double mainTempMin) {
        this.mainTempMin = mainTempMin;
    }
    public Double getMainTempMax() {
        return mainTempMax;
    }

    public void setMainTempMax(Double mainTempMax) {
        this.mainTempMax = mainTempMax;
    }
    public Double getMainSeaLevel() {
        return mainSeaLevel;
    }

    public void setMainSeaLevel(Double mainSeaLevel) {
        this.mainSeaLevel = mainSeaLevel;
    }
    public Double getMainGroundLevel() {
        return mainGroundLevel;
    }

    public void setMainGroundLevel(Double mainGroundLevel) {
        this.mainGroundLevel = mainGroundLevel;
    }
    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }
    public Double getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(Double windDeg) {
        this.windDeg = windDeg;
    }
    public Double getRain3H() {
        return rain3H;
    }

    public void setRain3H(Double rain3H) {
        this.rain3H = rain3H;
    }
    public Integer getCloudsAll() {
        return cloudsAll;
    }

    public void setCloudsAll(Integer cloudsAll) {
        this.cloudsAll = cloudsAll;
    }
    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }
    public Double getSysMessage() {
        return sysMessage;
    }

    public void setSysMessage(Double sysMessage) {
        this.sysMessage = sysMessage;
    }
    public String getSysCountry() {
        return sysCountry;
    }

    public void setSysCountry(String sysCountry) {
        this.sysCountry = sysCountry;
    }
    public Integer getSysSunriseAsTimestamp() {
        return sysSunriseAsTimestamp;
    }

    public void setSysSunriseAsTimestamp(Integer sysSunriseAsTimestamp) {
        this.sysSunriseAsTimestamp = sysSunriseAsTimestamp;
    }
    public Integer getSysSunsetAsTimestamp() {
        return sysSunsetAsTimestamp;
    }

    public void setSysSunsetAsTimestamp(Integer sysSunsetAsTimestamp) {
        this.sysSunsetAsTimestamp = sysSunsetAsTimestamp;
    }
    public ZonedDateTime getSysSunrise() {
        return sysSunrise;
    }

    public void setSysSunrise(ZonedDateTime sysSunrise) {
        this.sysSunrise = sysSunrise;
    }
    public ZonedDateTime getSysSunset() {
        return sysSunset;
    }

    public void setSysSunset(ZonedDateTime sysSunset) {
        this.sysSunset = sysSunset;
    }
    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WeatherDTO weatherDTO = (WeatherDTO) o;

        if ( ! Objects.equals(id, weatherDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WeatherDTO{" +
            "id=" + id +
            ", lon='" + lon + "'" +
            ", lat='" + lat + "'" +
            ", city='" + city + "'" +
            ", weatherMain='" + weatherMain + "'" +
            ", weatherDescription='" + weatherDescription + "'" +
            ", weatherIcon='" + weatherIcon + "'" +
            ", base='" + base + "'" +
            ", mainTemp='" + mainTemp + "'" +
            ", mainPressure='" + mainPressure + "'" +
            ", mainHumidity='" + mainHumidity + "'" +
            ", mainTempMin='" + mainTempMin + "'" +
            ", mainTempMax='" + mainTempMax + "'" +
            ", mainSeaLevel='" + mainSeaLevel + "'" +
            ", mainGroundLevel='" + mainGroundLevel + "'" +
            ", windSpeed='" + windSpeed + "'" +
            ", windDeg='" + windDeg + "'" +
            ", rain3H='" + rain3H + "'" +
            ", cloudsAll='" + cloudsAll + "'" +
            ", dt='" + dt + "'" +
            ", sysMessage='" + sysMessage + "'" +
            ", sysCountry='" + sysCountry + "'" +
            ", sysSunriseAsTimestamp='" + sysSunriseAsTimestamp + "'" +
            ", sysSunsetAsTimestamp='" + sysSunsetAsTimestamp + "'" +
            ", sysSunrise='" + sysSunrise + "'" +
            ", sysSunset='" + sysSunset + "'" +
            ", cod='" + cod + "'" +
            '}';
    }
}
