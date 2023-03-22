package by.senla.zabalotcki.weatheranalyzer.controller;

import by.senla.zabalotcki.weatheranalyzer.builder.TestBuilder;
import by.senla.zabalotcki.weatheranalyzer.builder.WeatherDtoBuilder;
import by.senla.zabalotcki.weatheranalyzer.dto.WeatherDto;
import by.senla.zabalotcki.weatheranalyzer.exception.CityNotFoundException;
import by.senla.zabalotcki.weatheranalyzer.model.DateRange;
import by.senla.zabalotcki.weatheranalyzer.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    private final Long id = 1L;
    private static WeatherDto expectedWeather;

    @BeforeEach
    void setUp() {
        TestBuilder<WeatherDto> weatherDtoTestBuilder = new WeatherDtoBuilder();

        expectedWeather = weatherDtoTestBuilder.build();
    }

    @Test
    void checkGetWeatherShouldReturnWeather() throws IOException {
        doNothing().when(weatherService).responseProcessing();

        weatherController.getWeather();

        verify(weatherService, times(1)).responseProcessing();
    }

    @Test
    void checkFindByNameShouldReturnWeather() {
        String cityName = "Minsk";
        when(weatherService.findByLocationName(cityName)).thenReturn(expectedWeather);

        ResponseEntity<WeatherDto> responseEntity = weatherController.findByName(cityName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedWeather, responseEntity.getBody());
    }

    @Test
    void checkFindByIdShouldReturnWeather() throws CityNotFoundException {
        when(weatherService.findById(id)).thenReturn(expectedWeather);

        ResponseEntity<WeatherDto> responseEntity = weatherController.findById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedWeather, responseEntity.getBody());
    }

    @Test
    void checkFindByIdShouldThrowCityNotFoundException() throws CityNotFoundException {
        when(weatherService.findById(id)).thenThrow(new CityNotFoundException("Check city id"));

        ResponseEntity<WeatherDto> response = weatherController.findById(id);

        assertEquals("Check city id", response.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());

        verify(weatherService).findById(id);

    }

    @Test
    void checkGetAverageTemperatureShouldReturnAverageTemperature() {
        DateRange dateRange = new DateRange();
        String averageTemperature = "20";

        when(weatherService.findAvgTempByPeriod(dateRange.getFrom(), dateRange.getTo())).thenReturn(averageTemperature);

        ResponseEntity<String> responseEntity = weatherController.getAverageTemperature(dateRange);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(averageTemperature, responseEntity.getBody());
    }

    @Test
    void checkGetAverageTemperatureShouldReturnIllegalArgumentException() {
        DateRange dateRange = new DateRange();

        doThrow(IllegalArgumentException.class).when(weatherService).findAvgTempByPeriod(dateRange.getFrom(), dateRange.getTo());

        ResponseEntity<String> responseEntity = weatherController.getAverageTemperature(dateRange);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
