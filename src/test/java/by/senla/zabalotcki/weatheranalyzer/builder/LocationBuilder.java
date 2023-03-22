package by.senla.zabalotcki.weatheranalyzer.builder;

import by.senla.zabalotcki.weatheranalyzer.model.Location;


public class LocationBuilder implements TestBuilder<Location> {

    private final Long id = 1L;
    private final String name = "Minsk";
    private final String country = "Belarus";

    @Override
    public Location build() {
        return Location.builder()
                .id(this.id)
                .country(this.country)
                .name(this.name)
                .build();
    }
}
