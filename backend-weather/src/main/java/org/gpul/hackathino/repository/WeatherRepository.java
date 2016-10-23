package org.gpul.hackathino.repository;

import org.gpul.hackathino.domain.Weather;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Weather entity.
 */
@SuppressWarnings("unused")
public interface WeatherRepository extends JpaRepository<Weather,Long> {

}
