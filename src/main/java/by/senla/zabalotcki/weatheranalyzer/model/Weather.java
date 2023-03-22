package by.senla.zabalotcki.weatheranalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@Table(name = "weather")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @JsonProperty("current")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_weather_id")
    private CurrentWeather currentWeather;

    @JsonProperty("location")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    public Double getTemperature() {
        return currentWeather.getTemperature();
    }

    public Double getWindSpeed() {
        return currentWeather.getWindSpeed();
    }

    public Double getHumidity() {
        return currentWeather.getHumidity();
    }

    public Double getPressure() {
        return currentWeather.getPressure();
    }

    public String getCondition() {
        return currentWeather.getCondition().getText();
    }

    public LocalDate getDate() {
        return LocalDate.now();
    }

    public String getLocation() {
        return location.getName() + ", " + location.getCountry();
    }
}
