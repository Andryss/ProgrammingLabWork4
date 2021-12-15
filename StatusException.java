public class StatusException extends RuntimeException {
    private final Shorty shorty;
    private final ShortyStatus currentStatus;
    private final ShortyStatus requiredStatus;

    public StatusException(Shorty shorty, ShortyStatus currentStatus, ShortyStatus requiredStatus) {
        this.shorty = shorty;
        this.currentStatus = currentStatus;
        this.requiredStatus = requiredStatus;
    }

    @Override
    public String getMessage() {
        return shorty + " " + currentStatus
                + " сейчас, а для этого действия нужно, чтобы было: "
                + shorty + " " + requiredStatus;
    }
}
