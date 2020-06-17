package space.deg.adam.domain.transaction.filter;

public enum TransactionFilterType {
    TRANSACTION("Transaction"),
    GOAL("Goal");

    private String title;

    TransactionFilterType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static TransactionFilterType byTitle(String title) {
        for (TransactionFilterType transactionType : values()) {
            if (transactionType.title.equals(title)) {
                return transactionType;
            }
        }
        throw new IllegalArgumentException();
    }
}
