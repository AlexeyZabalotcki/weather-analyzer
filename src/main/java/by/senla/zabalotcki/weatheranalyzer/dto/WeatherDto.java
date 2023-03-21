package by.senla.zabalotcki.weatheranalyzer.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class WeatherDto {
    private Long id;
    private LocalDate date;
    private String condition;
    private Double windSpeed;
    private Double humidity;
    private Double pressure;
    private Double temperature;
    private String location;

}
