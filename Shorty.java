import java.util.Objects;

public abstract class Shorty {
    private final String name;
    private GroupOfShorties group;
    private Room room;
    private ShortyStatus status = ShortyStatus.NORMAL;
    private Shorty huggedShorty;

    public Shorty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public GroupOfShorties getGroup() {
        return group;
    }

    public boolean hasGroup() {
        return group != null;
    }

    void setGroup(GroupOfShorties group) {
        this.group = group;
    }

    public Room getRoom() {
        return room;
    }

    public boolean hasRoom() {
        return room != null;
    }

    void setRoom(Room room) {
        this.room = room;
    }

    public ShortyStatus getStatus() {
        return status;
    }

    public boolean isNormal() {
        return status == ShortyStatus.NORMAL;
    }

    public boolean isSleeping() {
        return status == ShortyStatus.SLEEPING;
    }

    public boolean isHugging() {
        return status == ShortyStatus.HUGGING;
    }

    public String getAction() {
        return status.toString();
    }

    private void setStatus(ShortyStatus status) {
        this.status = status;
    }

    public Shorty getHuggedShorty() {
        return huggedShorty;
    }

    private void setHuggedShorty(Shorty aim) {
        huggedShorty = aim;
    }

    private void setHug(Shorty aim) {
        setStatus(ShortyStatus.HUGGING); setHuggedShorty(aim);
    }

    private void setUnHug() {
        setStatus(ShortyStatus.NORMAL); setHuggedShorty(null);
    }

    public ShortyInfo getInfo() {
        class ShortyContainer implements ShortyInfo {
            private final String name = Shorty.this.getName();
            private final GroupOfShorties group = Shorty.this.getGroup();
            private final Room room = Shorty.this.getRoom();
            private final ShortyStatus status = Shorty.this.getStatus();

            @Override
            public String getName() {
                return name;
            }

            @Override
            public GroupOfShorties getGroup() {
                return group;
            }

            @Override
            public Room getRoom() {
                return room;
            }

            @Override
            public ShortyStatus getStatus() {
                return status;
            }
        }
        return new ShortyContainer();
    }

    public void say(String text) {
        String action = isSleeping() ? "говорит во сне" : "говорит";
        System.out.println(name + " " + action + ": " + text + "\n");
    }

    public void eat(Food food) {
        if (!isSleeping()) {
            System.out.println(name + " съел: " + food);
        } else {
            System.out.println(name + " " + status);
        }
    }

    public void cleanRoom() {
        if (!isNormal()) {
            throw new StatusException(this, status, ShortyStatus.NORMAL);
        }
        if (hasRoom()) {
            room.cleanRoom();
            System.out.println(this + " прибрался в комнате");
        }
    }

    public void trashRoom() {
        if (!isNormal()) {
            throw new StatusException(this, status, ShortyStatus.NORMAL);
        }
        if (hasRoom() && isNormal()) {
            room.trashRoom();
            System.out.println(this + " раскидал мусор по комнате");
        }
    }

    public void hug(Shorty shorty) {
        if (!isNormal()) {
            throw new StatusException(this, getStatus(), ShortyStatus.NORMAL);
        }
        if (this == shorty) {
            throw new IllegalArgumentException("коротышка не может обнять сам себя, ручки маленькие");
        }
        if (!shorty.isNormal()) {
            throw new StatusException(shorty, shorty.getStatus(), ShortyStatus.NORMAL);
        }
        setHug(shorty); shorty.setHug(this);
        System.out.println(this + " и " + shorty + " обнимаются\n");
    }

    public void unHug() {
        if (!isHugging()) {
            throw new StatusException(this, getStatus(), ShortyStatus.HUGGING);
        }
        if (!huggedShorty.isHugging()) {
            throw new StatusException(huggedShorty, huggedShorty.getStatus(), ShortyStatus.HUGGING);
        }
        System.out.println(this + " и " + huggedShorty + " теперь не обнимаются\n");
        huggedShorty.setUnHug(); setUnHug();
    }

    public void sleep() {
        if (!isNormal()) {
            throw new StatusException(this, getStatus(), ShortyStatus.NORMAL);
        }
        if (!hasRoom()) {
            throw new IllegalArgumentException("Коротышка может заснуть только в комнате");
        }
        setStatus(ShortyStatus.SLEEPING);
        System.out.println(this + " уснул\n");
    }

    public void wakeUp() {
        if (!isSleeping()) {
            throw new StatusException(this, getStatus(), ShortyStatus.SLEEPING);
        }
        setStatus(ShortyStatus.NORMAL);
        System.out.println(this + " проснулся\n");
    }

    public abstract void spendFreeTime();

    @Override
    public String toString() {
        return "Коротышка " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shorty shorty = (Shorty) o;
        return name.equals(shorty.name) && group.equals(shorty.group) && room.equals(shorty.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, group, room);
    }
}
