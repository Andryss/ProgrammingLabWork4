public class BigRoom extends Room {
    public static final int MAX_RESIDENTS = 12;

    public BigRoom() {
        super(MAX_RESIDENTS);
    }

    @Override
    public String toString() {
        return "Большая комната";
    }
}
