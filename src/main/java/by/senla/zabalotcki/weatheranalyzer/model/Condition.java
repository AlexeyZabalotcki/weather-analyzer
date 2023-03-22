package by.senla.zabalotcki.weatheranalyzer.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "condition")
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
}
