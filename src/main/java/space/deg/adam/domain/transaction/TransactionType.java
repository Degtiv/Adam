package space.deg.adam.domain.transaction;

public enum TransactionType {
    INCOME("Income", "+"),
    COST("Cost",  "-");

    private String title;
    private String sign;

    TransactionType(String title, String sign) {
        this.title = title;
        this.sign = sign;
    }

    public String getTitle() {
        return title;
    }

    public String getSign() {
        return sign;
    }

    public static TransactionType bySign(String sign) {
        for (TransactionType transactionType : values()) {
            if (transactionType.sign.equalsIgnoreCase(sign))
                return transactionType;
        }
        throw new IllegalArgumentException();
    }

    public static TransactionType byTitle(String title) {
        for (TransactionType transactionType : values()) {
            if (transactionType.title.equals(title)) {
                return transactionType;
            }
        }
        throw new IllegalArgumentException();
    }
}
