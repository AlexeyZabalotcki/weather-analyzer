package by.senla.zabalotcki.weatheranalyzer.builder;

import by.senla.zabalotcki.weatheranalyzer.model.Condition;
import by.senla.zabalotcki.weatheranalyzer.model.CurrentWeather;


public class CurrentWeatherBuilder implements TestBuilder<CurrentWeather> {

    private final Long id = 1L;
    private final Double temperature = 12.0;
    private final Double windSpeed = 12.0;
    private final Double humidity = 123.0;
    private final Double pressure = 123.0;
    private final Condition condition = new ConditionBuilder().build();

    @Override
    public CurrentWeather build() {
        return CurrentWeather.builder()
                .id(this.id)
                .temperature(this.temperature)
                .windSpeed(this.windSpeed)
                .humidity(this.humidity)
                .pressure(this.pressure)
                .condition(condition)
                .build();
    }
}
