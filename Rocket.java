public class Rocket extends Location {
    public static final String BASENAME = "Ракета";

    public Rocket(String name, Room ... rooms) {
        super(name, rooms);
    }

    public Rocket(Room ... rooms) {
        this(BASENAME, rooms);
    }

    @Override
    public String toString() {
        return "Ракета \"" + getName() + "\"";
    }

    public static class FISRocket extends Rocket {
        private static final Room[] placement = new BigRoom[1];
        private static final String BASENAME = "Ракета ФИС";

        static {
            for (int i = 0; i < placement.length; i++) {
                placement[i] = new BigRoom();
            }
        }

        public FISRocket(String name) {
            super(name, placement);
        }

        public FISRocket() {
            super(BASENAME, placement);
        }

        @Override
        public String toString() {
            return "Ракета ФИС \"" + getName() + "\"";
        }
    }

    public static class NIPRocket extends Rocket {
        private static final Room[] placement = new SmallRoom[12];
        private static final String BASENAME = "Ракета НИП";

        static {
            for (int i = 0; i < placement.length; i++) {
                placement[i] = new SmallRoom();
            }
        }

        public NIPRocket(String name) {
            super(name, placement);
        }

        public NIPRocket() {
            super(BASENAME, placement);
        }

        @Override
        public String toString() {
            return "Ракета НИП \"" + getName() + "\"";
        }
    }
}
