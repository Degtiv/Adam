package space.deg.adam.domain.common;

import java.util.ArrayList;

public enum Status {
    PLANNED("Planned"),
    IN_PROGRESS("In progress"),
    DECLINED("Declined"),
    CONFIRMED("Confirmed"),
    DRAFT("Draft");

    private String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static String[] titles() {
        ArrayList<String> values = new ArrayList<>();
        for (Status status : Status.values()) {
            values.add(status.getTitle());
        }
        return (String[]) values.toArray();
    }

    public static Status byTitle(String title) {
        for (Status status : values()) {
            if (status.title.equals(title)) {
                return status;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return "Status {" +
                "title='" + title + '\'' +
                '}';
    }
}
