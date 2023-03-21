package by.senla.zabalotcki.weatheranalyzer.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "condition")
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
}
