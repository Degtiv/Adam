package space.deg.adam.domain.operation;

import java.util.ArrayList;

public enum OperationRule {
    EVERY_DAY("Every day"),
    EVERY_WEEK("Every week"),
    EVERY_MONTH("Every month"),
    EVERY_YEAR("Every year"),
    EVERY_N_DAYS("Every n days"),
    SOME_DAYS_OF_THE_WEEK("Some days of the week"),
    SOME_DAYS_OF_THE_MONTH("Some days of the month"),
    FIRST_WORKING_DAY_AFTER("First working day after"),
    LAST_WORKING_DAY_BEFORE("Last working day before");

    private String title;

    OperationRule(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
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
                '}';
    }
}
