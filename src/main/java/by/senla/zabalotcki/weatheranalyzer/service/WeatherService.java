package by.senla.zabalotcki.weatheranalyzer.service;

import by.senla.zabalotcki.weatheranalyzer.dto.WeatherDto;
import by.senla.zabalotcki.weatheranalyzer.exception.CityNotFoundException;
import by.senla.zabalotcki.weatheranalyzer.model.Weather;
import by.senla.zabalotcki.weatheranalyzer.repository.WeatherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${weather-analyzer.city}")
    private String city;
    @Value("${weather-analyzer.base-url}")
    private String BASE_URL;
    @Value("${weather-analyzer.api-key}")
    private String API_KEY;

    @Scheduled(fixedRateString = "${weather-analyzer.fixedRate}")
    @Transactional
    public void createAndSaveWeather() throws IOException {
        Request request = getRequest();
        try (Response response = sendRequest(request)) {
            Weather weather = parseResponse(response);
            saveWeather(weather);
        }
    }

    private Response sendRequest(Request request) throws IOException {
        Response response = httpClient.newCall(request).execute();
        return response.isSuccessful() && response.body() != null ? response : null;

    }

    private Weather parseResponse(Response response) throws IOException {
        return response != null ? objectMapper.readValue(response.body().string(), Weather.class) : null;
    }

    private void saveWeather(Weather weather) {
        if (weather != null) {
            weather.setDate(LocalDate.now());
            weatherRepository.save(weather);
        }
    }

    @NotNull
    private Request getRequest() {
        String url = String.format("%s/current.json?q=%s", BASE_URL, city);
        return new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", API_KEY)
                .addHeader("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .build();
    }

    public WeatherDto findByLocationName(String name) {
        Weather weather = weatherRepository.findFirstByLocationNameOrderByIdDesc(name);
        return toDto(weather);
    }

    public WeatherDto findById(Long id) throws CityNotFoundException {
        Weather weather = weatherRepository.findById(id).orElseThrow(() -> new CityNotFoundException("Check id"));
        return toDto(weather);
    }

    public String findAvgTempByPeriod(LocalDate from, LocalDate to) {
        return String.format("average_temp: %s", weatherRepository.getAverageTemperatureByPeriod(from, to));
    }

    private WeatherDto toDto(Weather weather) {
        return WeatherDto.builder()
                .id(weather.getId())
                .date(weather.getDate())
                .condition(weather.getCondition())
                .windSpeed(weather.getWindSpeed())
                .humidity(weather.getHumidity())
                .pressure(weather.getPressure())
                .temperature(weather.getTemperature())
                .location(weather.getLocation())
                .build();
    }
}
