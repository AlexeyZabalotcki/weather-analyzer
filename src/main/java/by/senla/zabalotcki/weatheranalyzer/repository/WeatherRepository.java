package by.senla.zabalotcki.weatheranalyzer.repository;

import by.senla.zabalotcki.weatheranalyzer.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Weather findFirstByLocationNameOrderByIdDesc(String name);

    @Query("SELECT AVG(w.currentWeather.temperature) FROM Weather w WHERE w.date >= :from AND w.date <= :to")
    Double getAverageTemperatureByPeriod(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
