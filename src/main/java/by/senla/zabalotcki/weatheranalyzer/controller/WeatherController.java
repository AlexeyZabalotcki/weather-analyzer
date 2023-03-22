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

    @PostMapping("/")
    public void getWeather() throws IOException {
        weatherService.responseProcessing();
    }

    @GetMapping("/name/{city}")
    public ResponseEntity<WeatherDto> findByName(@PathVariable String city) {
        return ResponseEntity.ok(weatherService.findByLocationName(city));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<WeatherDto> findById(@PathVariable Long id) {
        WeatherDto weatherDto;
        try {
            weatherDto = weatherService.findById(id);
        } catch (CityNotFoundException ex) {
            ex.printStackTrace();
            return new ResponseEntity("Check city id", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(weatherDto);
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
