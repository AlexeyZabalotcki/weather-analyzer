package by.senla.zabalotcki.weatheranalyzer.controller;

import by.senla.zabalotcki.weatheranalyzer.dto.WeatherDto;
import by.senla.zabalotcki.weatheranalyzer.exception.CityNotFoundException;
import by.senla.zabalotcki.weatheranalyzer.model.DateRange;
import by.senla.zabalotcki.weatheranalyzer.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/")
    public void getWeather() throws IOException {
        weatherService.createAndSaveWeather();
    }

    @GetMapping("/name/{city}")
    public WeatherDto findByName(@PathVariable String city) {
        return weatherService.findByLocationName(city);
    }

    @GetMapping("/find/{id}")
    public WeatherDto findById(@PathVariable Long id) throws CityNotFoundException {
        return weatherService.findById(id);
    }

    @GetMapping("/temp")
    public ResponseEntity<String> getAverageTemperature(@RequestBody DateRange dateRange) {
        try {
            String averageTemperature = weatherService.findAvgTempByPeriod(dateRange.getFrom(), dateRange.getTo());
            return ResponseEntity.ok(averageTemperature);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
