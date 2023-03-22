package by.senla.zabalotcki.weatheranalyzer.builder;

import by.senla.zabalotcki.weatheranalyzer.model.Condition;


public class ConditionBuilder implements TestBuilder<Condition> {

    private final Long id = 1L;
    private final String text = "Sunny";

    @Override
    public Condition build() {
        return Condition.builder()
                .id(this.id)
                .text(this.text)
                .build();
    }
}
