package space.deg.adam.domain.goals;

import java.util.ArrayList;

public enum Status {
    BASE("Planned"),
    COMFORT("In progress"),
    LUXURY("Come true");

    private String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static String[] statuses() {
        ArrayList<String> values = new ArrayList<>();
        for (Status status : Status.values()) {
            values.add(status.getTitle());
        }
        return (String[]) values.toArray();
    }

    @Override
    public String toString() {
        return "Statuses{" +
                "title='" + title + '\'' +
                '}';
    }
}
