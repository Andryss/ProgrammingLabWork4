import java.io.FileWriter;
import java.util.Objects;

public class Painter extends Shorty {
    public static final String BASENAME = "Тюбик";
    private final Easel easel = new Easel();

    public Painter(String name) {
        super(name);
    }

    public Painter() {
        this(BASENAME);
    }

    public Easel getEasel() {
        return easel;
    }

    public void draw(Picture picture) {
        if (!isNormal()) {
            throw new StatusException(this, getStatus(), ShortyStatus.NORMAL);
        }
        easel.setPicture(picture);
        System.out.println(this + " нарисовал картину " + getEasel().getPicture() + "\n");
    }

    public void draw() {
        draw(Picture.getRandomPicture());
    }

    @Override
    public void spendFreeTime() {
        if (Math.random() > 0.5) {
            try {
                draw();
            } catch (StatusException e) {
                System.out.println(this + " с удовольствием бы порисовал, но не может. " + this + getAction());
            }
        }
    }

    @Override
    public String toString() {
        return "Которышка-художник " + getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Painter painter = (Painter) o;
        return easel.equals(painter.easel) && getName().equals(painter.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), easel);
    }

    public class Easel {
        private Picture picture;
        private final Shorty owner = Painter.this;

        private Easel() {
            picture = Picture.EMPTY;
        }

        public Picture getPicture() {
            return picture;
        }

        private void setPicture(Picture picture) {
            this.picture = picture;
        }

        public Shorty getOwner() {
            return owner;
        }

        public boolean isOwner(Shorty shorty) {
            return owner == shorty;
        }

        @Override
        public String toString() {
            return getPicture() == Picture.EMPTY ? "пустой мольберт" : "мольберт, на котором изображена картина \"" + getPicture() + "\"";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Easel easel = (Easel) o;
            return picture == easel.picture;
        }

        @Override
        public int hashCode() {
            return Objects.hash(picture);
        }
    }

    enum Picture {
        EMPTY("ничего"),
        MONA_LISA("Мона Лиза"),
        GIRL_WITH_A_PEARL_EARRING("Девушка с жемчужной сережкой"),
        THE_LAST_SUPPER("Тайная вечеря"),
        THE_SCREAM("Крик"),
        CREATION_OF_ADAM("Сотворение Адама"),
        LUNAR_LANDSCAPES("Лунные пейзажи");

        private final String name;

        Picture(String name) {
            this.name = name;
        }

        public static Picture getRandomPicture() {
            int randomIndex = (int) (1 + Math.random() * (Picture.values().length - 1));
            return Picture.values()[randomIndex];
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
