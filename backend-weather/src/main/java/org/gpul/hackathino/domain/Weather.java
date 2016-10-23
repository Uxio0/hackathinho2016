package org.gpul.hackathino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Weather.
 */
@Entity
@Table(name = "weather")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Weather implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "lon", nullable = false)
    private Double lon;

    @NotNull
    @Column(name = "lat", nullable = false)
    private Double lat;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "weather_main", nullable = false)
    private String weatherMain;

    @NotNull
    @Column(name = "weather_description", nullable = false)
    private String weatherDescription;

    @NotNull
    @Column(name = "weather_icon", nullable = false)
    private String weatherIcon;

    @Column(name = "base")
    private String base;

    @NotNull
    @Column(name = "main_temp", nullable = false)
    private Double mainTemp;

    @NotNull
    @Column(name = "main_pressure", nullable = false)
    private Double mainPressure;

    @NotNull
    @Column(name = "main_humidity", nullable = false)
    private Double mainHumidity;

    @NotNull
    @Column(name = "main_temp_min", nullable = false)
    private Double mainTempMin;

    @NotNull
    @Column(name = "main_temp_max", nullable = false)
    private Double mainTempMax;

    @NotNull
    @Column(name = "main_sea_level", nullable = false)
    private Double mainSeaLevel;

    @NotNull
    @Column(name = "main_ground_level", nullable = false)
    private Double mainGroundLevel;

    @NotNull
    @Column(name = "wind_speed", nullable = false)
    private Double windSpeed;

    @NotNull
    @Column(name = "wind_deg", nullable = false)
    private Double windDeg;

    @NotNull
    @Column(name = "rain_3_h", nullable = false)
    private Double rain3H;

    @NotNull
    @Column(name = "clouds_all", nullable = false)
    private Integer cloudsAll;

    @Column(name = "dt")
    private Integer dt;

    @Column(name = "sys_message")
    private Double sysMessage;

    @NotNull
    @Column(name = "sys_country", nullable = false)
    private String sysCountry;

    @NotNull
    @Column(name = "sys_sunrise_as_timestamp", nullable = false)
    private Integer sysSunriseAsTimestamp;

    @NotNull
    @Column(name = "sys_sunset_as_timestamp", nullable = false)
    private Integer sysSunsetAsTimestamp;

    @NotNull
    @Column(name = "sys_sunrise", nullable = false)
    private ZonedDateTime sysSunrise;

    @NotNull
    @Column(name = "sys_sunset", nullable = false)
    private ZonedDateTime sysSunset;

    @Column(name = "cod")
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

    public Weather lon(Double lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public Weather lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getCity() {
        return city;
    }

    public Weather city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public Weather weatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
        return this;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public Weather weatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
        return this;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public Weather weatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
        return this;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getBase() {
        return base;
    }

    public Weather base(String base) {
        this.base = base;
        return this;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Double getMainTemp() {
        return mainTemp;
    }

    public Weather mainTemp(Double mainTemp) {
        this.mainTemp = mainTemp;
        return this;
    }

    public void setMainTemp(Double mainTemp) {
        this.mainTemp = mainTemp;
    }

    public Double getMainPressure() {
        return mainPressure;
    }

    public Weather mainPressure(Double mainPressure) {
        this.mainPressure = mainPressure;
        return this;
    }

    public void setMainPressure(Double mainPressure) {
        this.mainPressure = mainPressure;
    }

    public Double getMainHumidity() {
        return mainHumidity;
    }

    public Weather mainHumidity(Double mainHumidity) {
        this.mainHumidity = mainHumidity;
        return this;
    }

    public void setMainHumidity(Double mainHumidity) {
        this.mainHumidity = mainHumidity;
    }

    public Double getMainTempMin() {
        return mainTempMin;
    }

    public Weather mainTempMin(Double mainTempMin) {
        this.mainTempMin = mainTempMin;
        return this;
    }

    public void setMainTempMin(Double mainTempMin) {
        this.mainTempMin = mainTempMin;
    }

    public Double getMainTempMax() {
        return mainTempMax;
    }

    public Weather mainTempMax(Double mainTempMax) {
        this.mainTempMax = mainTempMax;
        return this;
    }

    public void setMainTempMax(Double mainTempMax) {
        this.mainTempMax = mainTempMax;
    }

    public Double getMainSeaLevel() {
        return mainSeaLevel;
    }

    public Weather mainSeaLevel(Double mainSeaLevel) {
        this.mainSeaLevel = mainSeaLevel;
        return this;
    }

    public void setMainSeaLevel(Double mainSeaLevel) {
        this.mainSeaLevel = mainSeaLevel;
    }

    public Double getMainGroundLevel() {
        return mainGroundLevel;
    }

    public Weather mainGroundLevel(Double mainGroundLevel) {
        this.mainGroundLevel = mainGroundLevel;
        return this;
    }

    public void setMainGroundLevel(Double mainGroundLevel) {
        this.mainGroundLevel = mainGroundLevel;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Weather windSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
        return this;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getWindDeg() {
        return windDeg;
    }

    public Weather windDeg(Double windDeg) {
        this.windDeg = windDeg;
        return this;
    }

    public void setWindDeg(Double windDeg) {
        this.windDeg = windDeg;
    }

    public Double getRain3H() {
        return rain3H;
    }

    public Weather rain3H(Double rain3H) {
        this.rain3H = rain3H;
        return this;
    }

    public void setRain3H(Double rain3H) {
        this.rain3H = rain3H;
    }

    public Integer getCloudsAll() {
        return cloudsAll;
    }

    public Weather cloudsAll(Integer cloudsAll) {
        this.cloudsAll = cloudsAll;
        return this;
    }

    public void setCloudsAll(Integer cloudsAll) {
        this.cloudsAll = cloudsAll;
    }

    public Integer getDt() {
        return dt;
    }

    public Weather dt(Integer dt) {
        this.dt = dt;
        return this;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Double getSysMessage() {
        return sysMessage;
    }

    public Weather sysMessage(Double sysMessage) {
        this.sysMessage = sysMessage;
        return this;
    }

    public void setSysMessage(Double sysMessage) {
        this.sysMessage = sysMessage;
    }

    public String getSysCountry() {
        return sysCountry;
    }

    public Weather sysCountry(String sysCountry) {
        this.sysCountry = sysCountry;
        return this;
    }

    public void setSysCountry(String sysCountry) {
        this.sysCountry = sysCountry;
    }

    public Integer getSysSunriseAsTimestamp() {
        return sysSunriseAsTimestamp;
    }

    public Weather sysSunriseAsTimestamp(Integer sysSunriseAsTimestamp) {
        this.sysSunriseAsTimestamp = sysSunriseAsTimestamp;
        return this;
    }

    public void setSysSunriseAsTimestamp(Integer sysSunriseAsTimestamp) {
        this.sysSunriseAsTimestamp = sysSunriseAsTimestamp;
    }

    public Integer getSysSunsetAsTimestamp() {
        return sysSunsetAsTimestamp;
    }

    public Weather sysSunsetAsTimestamp(Integer sysSunsetAsTimestamp) {
        this.sysSunsetAsTimestamp = sysSunsetAsTimestamp;
        return this;
    }

    public void setSysSunsetAsTimestamp(Integer sysSunsetAsTimestamp) {
        this.sysSunsetAsTimestamp = sysSunsetAsTimestamp;
    }

    public ZonedDateTime getSysSunrise() {
        return sysSunrise;
    }

    public Weather sysSunrise(ZonedDateTime sysSunrise) {
        this.sysSunrise = sysSunrise;
        return this;
    }

    public void setSysSunrise(ZonedDateTime sysSunrise) {
        this.sysSunrise = sysSunrise;
    }

    public ZonedDateTime getSysSunset() {
        return sysSunset;
    }

    public Weather sysSunset(ZonedDateTime sysSunset) {
        this.sysSunset = sysSunset;
        return this;
    }

    public void setSysSunset(ZonedDateTime sysSunset) {
        this.sysSunset = sysSunset;
    }

    public Integer getCod() {
        return cod;
    }

    public Weather cod(Integer cod) {
        this.cod = cod;
        return this;
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
        Weather weather = (Weather) o;
        if(weather.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, weather.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Weather{" +
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
