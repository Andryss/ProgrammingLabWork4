import java.util.*;

public class GroupOfShorties{
    private List<Shorty> group;
    private AbleToLocate location;

    public GroupOfShorties(Shorty ... group) {
        this(Arrays.asList(group));
    }

    public GroupOfShorties(List<Shorty> group) {
        if (group.size() < 1) {
            throw new IllegalArgumentException("Нельзя создать пустую группу");
        }
        for (Shorty shorty : group) {
            if (shorty.hasGroup()) {
                throw new IllegalArgumentException("Коротышка " + shorty + " уже состоит в какой-то группе");
            }
        }

        this.group = new LinkedList<>(group);
        for (Shorty shorty : this.group) shorty.setGroup(this);
    }

    public AbleToLocate getLocation() {
        return location;
    }

    public List<Shorty> getGroup() {
        return group;
    }

    public boolean hasLocation() {
        return location != null;
    }

    public boolean hasShorty(Shorty shorty) {
        return group.contains(shorty);
    }

    public void addShorty(Shorty newShorty) {
        if (group == null) {
            throw new NullPointerException("Группа расформирована, заново никто уже не соберется :(");
        }
        if (hasLocation()) {
            throw new IllegalArgumentException("В группу нельзя добавить коротышку, когда она находиться в каком-то месте");
        }
        if (hasShorty(newShorty)) {
            throw new IllegalArgumentException("Коротышка " + newShorty + " уже состоит в этой группе");
        }
        if (newShorty.hasGroup()) {
            throw new IllegalArgumentException("Нельзя добавить коротышку " + newShorty + ", так как он состоит в другой группе");
        }
        appendShorty(newShorty);
        System.out.println(newShorty + " теперь состоит в группе" + "\n");
    }

    public void removeShorty(Shorty oldShorty) {
        if (hasLocation()) {
            throw new IllegalArgumentException("Из группы нельзя исключить коротышку, когда она находиться в каком-то месте");
        }
        if (group.size() == 1) {
            throw  new IllegalArgumentException("Из группы нельзя исключить последнего коротышку. Если хотите расформировать группу, воспользуйтесь методом disband()");
        }
        if (!hasShorty(oldShorty)) {
            throw new IllegalArgumentException("Нельзя исключить из группы коротышку, которого в ней нет");
        }
        delShorty(oldShorty);
        System.out.println(oldShorty + " теперь не состоит в группе" + "\n");
    }

    public void disband() {
        if (hasLocation()) {
            throw new IllegalArgumentException("Нельзя расформировать группу, находящуюся в каком-то месте");
        }
        System.out.println("Группа ( " + this.toString().substring(0, 20) + "... ) расформирована" + "\n");
        for (Shorty shorty : group) {
            delShorty(shorty);
        }
        group = null;
    }

    public void goOut() {
        if (hasLocation()) getLocation().dislocate(this);
    }

    public void goTo(AbleToLocate location) {
        location.locate(this);
    }

    public void setLocation(AbleToLocate location) {
        this.location = location;
    }

    public void spendFreeTime() {
        System.out.println(this + "\nпроводит свободное время:");
        for (Shorty shorty : group) {
            shorty.spendFreeTime();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }

    public void haveDinner() throws CookNotFoundException {
        Cook cook = getCook();
        if (cook != null) {
            cook.feedAllGroup();
        } else {
            throw new CookNotFoundException(this);
        }
    }

    private void appendShorty(Shorty shorty) {
        shorty.setGroup(this);
        group.add(shorty);
    }

    private void delShorty(Shorty shorty) {
        shorty.setGroup(null);
        group.remove(shorty);
    }

    private Cook getCook() {
        for (Shorty shorty : group) {
            if (shorty instanceof Cook) {
                return (Cook) shorty;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (Shorty shorty : group) stringJoiner.add(shorty.toString());
        return "Группа коротышек: " + stringJoiner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupOfShorties group1 = (GroupOfShorties) o;
        return group.equals(group1.group) && location.equals(group1.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, location);
    }
}
