package space.deg.adam.domain.rule.rule_strategy;

import space.deg.adam.domain.rule.rule_strategy.strategies.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public enum RuleStrategy {
    EVERY_DAY("Every day", EveryDayStrategy.class),
    EVERY_WEEK("Every week", EveryWeekStrategy.class),
    EVERY_MONTH("Every month", EveryMonthStrategy.class),
    EVERY_YEAR("Every year", EveryYearStrategy.class),
    EVERY_N_DAYS("Every n days", EveryNDaysStrategy.class),
    FIRST_WORKING_DAY_AFTER("First working day after", FirstWorkingDayAfterStrategy.class),
    LAST_WORKING_DAY_BEFORE("Last working day before", LastWorkingDayBeforeStrategy.class);

    private String title;
    private Class strategyClass;

    RuleStrategy(String title, Class strategyClass) {
        this.title = title;
        this.strategyClass = strategyClass;
    }

    public String getTitle() {
        return title;
    }

    public AbstractStrategy getStrategyClass() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> cons = strategyClass.getConstructor();
        Object object = cons.newInstance();
        return (AbstractStrategy) object;
    }

    static public AbstractStrategy getStrategyByTitle(String title) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<?> cons = byTitle(title).strategyClass.getConstructor();
        Object object = cons.newInstance();
        return (AbstractStrategy) object;
    }

    public static String[] titles() {
        ArrayList<String> values = new ArrayList<>();
        for (RuleStrategy status : RuleStrategy.values()) {
            values.add(status.getTitle());
        }
        return (String[]) values.toArray();
    }

    public static RuleStrategy byTitle(String title) {
        for (RuleStrategy status : values()) {
            if (status.title.equals(title)) {
                return status;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return "Rule Strategy {" +
                "title='" + title + '\'' +
                "class='" + strategyClass.getName() + '\'' +
                '}';
    }
}
