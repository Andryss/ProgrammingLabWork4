public enum ShortyStatus {
    NORMAL("ничего не делает"),
    SLEEPING("крепко спит"),
    HUGGING("обнимается");

    private final String action;

    ShortyStatus(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return action;
    }
}
