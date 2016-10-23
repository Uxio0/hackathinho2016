package org.gpul.hackathino.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the WeatherEnergy entity.
 */
public class WeatherEnergyDTO implements Serializable {

    private Long id;

    private String name;

    private String isoCode;

    private Double lat;

    private Double lon;

    @NotNull
    private Double solar;

    private Double minTemp;

    private Double maxTemp;

    private Double temp;

    private Integer sunset;

    private Integer sunrise;

    @NotNull
    private Double hidraulic;

    private Double rain;

    @NotNull
    private Double eolic;

    private Double windSpeed;

    private Double clouds;

    @NotNull
    private LocalDate createdDateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
    public Double getSolar() {
        return solar;
    }

    public void setSolar(Double solar) {
        this.solar = solar;
    }
    public Double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }
    public Double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }
    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }
    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }
    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }
    public Double getHidraulic() {
        return hidraulic;
    }

    public void setHidraulic(Double hidraulic) {
        this.hidraulic = hidraulic;
    }
    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }
    public Double getEolic() {
        return eolic;
    }

    public void setEolic(Double eolic) {
        this.eolic = eolic;
    }
    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }
    public Double getClouds() {
        return clouds;
    }

    public void setClouds(Double clouds) {
        this.clouds = clouds;
    }
    public LocalDate getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDate createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WeatherEnergyDTO weatherEnergyDTO = (WeatherEnergyDTO) o;

        if ( ! Objects.equals(id, weatherEnergyDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WeatherEnergyDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", isoCode='" + isoCode + "'" +
            ", lat='" + lat + "'" +
            ", lon='" + lon + "'" +
            ", solar='" + solar + "'" +
            ", minTemp='" + minTemp + "'" +
            ", maxTemp='" + maxTemp + "'" +
            ", temp='" + temp + "'" +
            ", sunset='" + sunset + "'" +
            ", sunrise='" + sunrise + "'" +
            ", hidraulic='" + hidraulic + "'" +
            ", rain='" + rain + "'" +
            ", eolic='" + eolic + "'" +
            ", windSpeed='" + windSpeed + "'" +
            ", clouds='" + clouds + "'" +
            ", createdDateTime='" + createdDateTime + "'" +
            '}';
    }
}
