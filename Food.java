public enum Food {
    CHICKEN("курица"),
    FISH("рыба"),
    SALAD("салат"),
    SOUP("суп");

    private final String name;

    Food(String name) {
        this.name = name;
    }

    public static Food generateRandomFood() {
        int randomValue = (int) (Math.random() * Food.values().length);
        return Food.values()[randomValue];
    }

    @Override
    public String toString() {
        return name;
    }
}
