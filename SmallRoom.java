public class SmallRoom extends Room {
    public static final int MAX_RESIDENTS = 1;

    public SmallRoom() {
        super(MAX_RESIDENTS);
    }

    @Override
    public String toString() {
        return "Маленькая комната";
    }
}
