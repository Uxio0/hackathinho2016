package org.gpul.hackathino.repository;

import org.gpul.hackathino.domain.WeatherEnergy;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WeatherEnergy entity.
 */
@SuppressWarnings("unused")
public interface WeatherEnergyRepository extends JpaRepository<WeatherEnergy,Long> {

}
