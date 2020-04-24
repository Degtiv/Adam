package space.deg.adam.domain.operation.operation_rule;

import space.deg.adam.domain.operation.operation_rule.strategies.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public enum OperationRule {
    EVERY_DAY("Every day", EveryDayStrategy.class),
    EVERY_WEEK("Every week", EveryWeekStrategy.class),
    EVERY_MONTH("Every month", EveryMonthStrategy.class),
    EVERY_YEAR("Every year", EveryYearStrategy.class),
    EVERY_N_DAYS("Every n days", EveryNDaysStrategy.class),
    FIRST_WORKING_DAY_AFTER("First working day after", FirstWorkingDayAfterStrategy.class),
    LAST_WORKING_DAY_BEFORE("Last working day before", LastWorkingDayBeforeStrategy.class);

    private String title;
    private Class strategyClass;

    OperationRule(String title, Class strategyClass) {
        this.title = title;
        this.strategyClass = strategyClass;
    }

    public String getTitle() {
        return title;
    }

    public OperationRuleStrategy getStrategyClass() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> cons = strategyClass.getConstructor();
        Object object = cons.newInstance();
        return (OperationRuleStrategy) object;
    }

    static public OperationRuleStrategy getStrategyByTitle(String title) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<?> cons = byTitle(title).strategyClass.getConstructor();
        Object object = cons.newInstance();
        return (OperationRuleStrategy) object;
    }

    public static String[] titles() {
        ArrayList<String> values = new ArrayList<>();
        for (OperationRule status : OperationRule.values()) {
            values.add(status.getTitle());
        }
        return (String[]) values.toArray();
    }

    public static OperationRule byTitle(String title) {
        for (OperationRule status : values()) {
            if (status.title.equals(title)) {
                return status;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return "Operation rule {" +
                "title='" + title + '\'' +
                "class='" + strategyClass.getName() + '\'' +
                '}';
    }
}
