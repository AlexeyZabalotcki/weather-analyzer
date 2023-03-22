package by.senla.zabalotcki.weatheranalyzer.builder;

import by.senla.zabalotcki.weatheranalyzer.dto.WeatherDto;

import java.time.LocalDate;

public class WeatherDtoBuilder implements TestBuilder<WeatherDto> {

    private Long id = 1L;
    private LocalDate date = LocalDate.now();
    private String condition = "Sunny";
    private final Double temperature = 12.0;
    private final Double windSpeed = 12.0;
    private final Double humidity = 123.0;
    private final Double pressure = 123.0;
    private String location = "Minsk, Belarus";

    @Override
    public WeatherDto build() {
        return WeatherDto.builder()
                .id(this.id)
                .date(this.date)
                .condition(this.condition)
                .windSpeed(this.windSpeed)
                .humidity(this.humidity)
                .pressure(this.pressure)
                .temperature(this.temperature)
                .location(this.location)
                .build();
    }
}
