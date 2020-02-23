package space.deg.adam.domain.common;

import java.util.ArrayList;

public enum Category {
    BASE("Base"),
    COMFORT("Comfort"),
    LUXURY("Luxury"),
    PRESIGE("Prestige");

    private String title;

    Category(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static String[] titles() {
        ArrayList<String> values = new ArrayList<>();
        for (Category category : Category.values()) {
            values.add(category.getTitle());
        }
        return (String[]) values.toArray();
    }

    public static Category byTitle(String title) {
        for (Category category : values()) {
            if (category.title.equals(title)) {
                return category;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return "Categories{" +
                "title='" + title + '\'' +
                '}';
    }
}
