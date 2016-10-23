package org.gpul.hackathino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A WeatherEnergy.
 */
@Entity
@Table(name = "weather_energy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WeatherEnergy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "iso_code")
    private String isoCode;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @NotNull
    @Column(name = "solar", nullable = false)
    private Double solar;

    @Column(name = "min_temp")
    private Double minTemp;

    @Column(name = "max_temp")
    private Double maxTemp;

    @Column(name = "temp")
    private Double temp;

    @Column(name = "sunset")
    private Integer sunset;

    @Column(name = "sunrise")
    private Integer sunrise;

    @NotNull
    @Column(name = "hidraulic", nullable = false)
    private Double hidraulic;

    @Column(name = "rain")
    private Double rain;

    @NotNull
    @Column(name = "eolic", nullable = false)
    private Double eolic;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "clouds")
    private Double clouds;

    @NotNull
    @Column(name = "created_date_time", nullable = false)
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

    public WeatherEnergy name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public WeatherEnergy isoCode(String isoCode) {
        this.isoCode = isoCode;
        return this;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public Double getLat() {
        return lat;
    }

    public WeatherEnergy lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public WeatherEnergy lon(Double lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getSolar() {
        return solar;
    }

    public WeatherEnergy solar(Double solar) {
        this.solar = solar;
        return this;
    }

    public void setSolar(Double solar) {
        this.solar = solar;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public WeatherEnergy minTemp(Double minTemp) {
        this.minTemp = minTemp;
        return this;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public WeatherEnergy maxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
        return this;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Double getTemp() {
        return temp;
    }

    public WeatherEnergy temp(Double temp) {
        this.temp = temp;
        return this;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Integer getSunset() {
        return sunset;
    }

    public WeatherEnergy sunset(Integer sunset) {
        this.sunset = sunset;
        return this;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public WeatherEnergy sunrise(Integer sunrise) {
        this.sunrise = sunrise;
        return this;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Double getHidraulic() {
        return hidraulic;
    }

    public WeatherEnergy hidraulic(Double hidraulic) {
        this.hidraulic = hidraulic;
        return this;
    }

    public void setHidraulic(Double hidraulic) {
        this.hidraulic = hidraulic;
    }

    public Double getRain() {
        return rain;
    }

    public WeatherEnergy rain(Double rain) {
        this.rain = rain;
        return this;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    public Double getEolic() {
        return eolic;
    }

    public WeatherEnergy eolic(Double eolic) {
        this.eolic = eolic;
        return this;
    }

    public void setEolic(Double eolic) {
        this.eolic = eolic;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public WeatherEnergy windSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
        return this;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getClouds() {
        return clouds;
    }

    public WeatherEnergy clouds(Double clouds) {
        this.clouds = clouds;
        return this;
    }

    public void setClouds(Double clouds) {
        this.clouds = clouds;
    }

    public LocalDate getCreatedDateTime() {
        return createdDateTime;
    }

    public WeatherEnergy createdDateTime(LocalDate createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
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
        WeatherEnergy weatherEnergy = (WeatherEnergy) o;
        if(weatherEnergy.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, weatherEnergy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WeatherEnergy{" +
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
