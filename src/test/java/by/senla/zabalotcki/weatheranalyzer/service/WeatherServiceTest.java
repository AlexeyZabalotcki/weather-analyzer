package by.senla.zabalotcki.weatheranalyzer.service;

import by.senla.zabalotcki.weatheranalyzer.builder.TestBuilder;
import by.senla.zabalotcki.weatheranalyzer.builder.WeatherBuilder;
import by.senla.zabalotcki.weatheranalyzer.builder.WeatherDtoBuilder;
import by.senla.zabalotcki.weatheranalyzer.dto.WeatherDto;
import by.senla.zabalotcki.weatheranalyzer.exception.CityNotFoundException;
import by.senla.zabalotcki.weatheranalyzer.model.Weather;
import by.senla.zabalotcki.weatheranalyzer.repository.WeatherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    private WeatherService weatherService;

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private OkHttpClient httpClientMock;

    @Mock
    private ObjectMapper objectMapperMock;

    private final Long id = 1L;
    private static WeatherDto expectedWeather;
    private static Weather weather;


    @BeforeEach
    void setUp() {
        TestBuilder<WeatherDto> weatherDtoTestBuilder = new WeatherDtoBuilder();
        TestBuilder<Weather> weatherTestBuilder = new WeatherBuilder();

        expectedWeather = weatherDtoTestBuilder.build();
        weather = weatherTestBuilder.build();

        weatherService = new WeatherService(weatherRepository, httpClientMock, objectMapperMock);
    }

    @Test
    public void checkFindByIdShouldReturnWeather() throws CityNotFoundException {
        when(weatherRepository.findById(any(Long.class))).thenReturn(Optional.of(weather));

        WeatherDto result = weatherService.findById(id);

        assertEquals(expectedWeather, result);
        assertNotNull(result);

    }

    @Test
    public void checkFindByIdShouldThrowCityNotFoundException() {
        when(weatherRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(CityNotFoundException.class, () -> {
            weatherService.findById(id);
        });
    }

    @Test
    public void checkFindByLocationNameShouldReturnWeather() {
        String city = "Minsk";

        when(weatherRepository.findFirstByLocationNameOrderByIdDesc(any(String.class))).thenReturn(weather);

        WeatherDto result = weatherService.findByLocationName(city);
        assertEquals(expectedWeather, result);
    }

    @Test
    public void checkFindAvgTempByPeriodShouldReturnAvgTemperature() {
        LocalDate from = LocalDate.now().minusDays(7);
        LocalDate to = LocalDate.now();
        Double avgTemp = 23.5;

        when(weatherRepository.getAverageTemperatureByPeriod(from, to)).thenReturn(avgTemp);

        String result = weatherService.findAvgTempByPeriod(from, to);
        assertEquals("average_temp: " + avgTemp, result);
    }
}
