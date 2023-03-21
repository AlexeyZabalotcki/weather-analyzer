package by.senla.zabalotcki.weatheranalyzer.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
}
