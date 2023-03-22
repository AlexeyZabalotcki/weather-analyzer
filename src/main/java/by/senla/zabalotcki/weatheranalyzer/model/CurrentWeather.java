package by.senla.zabalotcki.weatheranalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "current_weather")
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CurrentWeather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("temp_c")
    private Double temperature;

    @JsonProperty("wind_kph")
    private Double windSpeed;

    @JsonProperty("humidity")
    private Double humidity;

    @JsonProperty("pressure_mb")
    private Double pressure;

    @JsonProperty("condition")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "condition_id")
    private Condition condition;
}