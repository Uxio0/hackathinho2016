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

    @NotNull
    private Double hidraulic;

    @NotNull
    private Double eolic;

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
    public Double getHidraulic() {
        return hidraulic;
    }

    public void setHidraulic(Double hidraulic) {
        this.hidraulic = hidraulic;
    }
    public Double getEolic() {
        return eolic;
    }

    public void setEolic(Double eolic) {
        this.eolic = eolic;
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
            ", hidraulic='" + hidraulic + "'" +
            ", eolic='" + eolic + "'" +
            ", createdDateTime='" + createdDateTime + "'" +
            '}';
    }
}
