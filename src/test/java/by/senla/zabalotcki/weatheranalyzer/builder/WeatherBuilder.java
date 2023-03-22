package by.senla.zabalotcki.weatheranalyzer.builder;

import by.senla.zabalotcki.weatheranalyzer.model.CurrentWeather;
import by.senla.zabalotcki.weatheranalyzer.model.Location;
import by.senla.zabalotcki.weatheranalyzer.model.Weather;

import java.time.LocalDate;


public class WeatherBuilder implements TestBuilder<Weather> {

    private final Long id = 1L;
    private final LocalDate date = LocalDate.now();
    private final CurrentWeather currentWeather = new CurrentWeatherBuilder().build();
    private final Location location = new LocationBuilder().build();

    @Override
    public Weather build() {
        return Weather.builder()
                .id(this.id)
                .date(this.date)
                .currentWeather(this.currentWeather)
                .location(this.location)
                .build();
    }
}
