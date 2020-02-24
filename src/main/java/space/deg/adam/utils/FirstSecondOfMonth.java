package space.deg.adam.utils;

import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class FirstSecondOfMonth implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(TemporalAdjusters.firstDayOfMonth())
                .with(ChronoField.MICRO_OF_SECOND, 0);
    }

    public static TemporalAdjuster adjust() {
        return (temporal) -> temporal.with(TemporalAdjusters.firstDayOfMonth())
                .with(ChronoField.MICRO_OF_SECOND, 0);
    }
}