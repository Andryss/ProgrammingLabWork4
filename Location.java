import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class Location implements AbleToLocate {
    private final String name;
    private final Room[] rooms;
    private int availablePlaces;
    private final List<GroupOfShorties> locatedGroups = new LinkedList<>();

    Location(String name, Room ... rooms) {
        if (rooms.length < 1) {
            throw new IllegalArgumentException("У локации обязательно должна быть хотя бы одна комната");
        }

        this.name = name;
        this.rooms = rooms;
        availablePlaces = Location.calculateAvailablePlaces(rooms);
    }

    public static int calculateAvailablePlaces(Room[] rooms) {
        int places = 0;
        for (Room room : rooms) places += room.getAvailablePlaces();
        return places;
    }

    public String getName() {
        return name;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public List<Room> getRooms(GroupOfShorties group) {
        if (!hasLocatedGroup(group)) {
            throw new IllegalArgumentException("В локации - " + name + " - нет данной группы коротышек");
        }
        List<Room> groupRooms = new LinkedList<>();
        for (Room room : getRooms()) {
            for (Shorty shorty : room.getResidents()) {
                if (shorty.getGroup() == group) {
                    groupRooms.add(room);
                    break;
                }
            }
        }
        return groupRooms;
    }

    public int getAvailablePlaces() {
        return availablePlaces;
    }

    public List<GroupOfShorties> getLocatedGroups() {
        return locatedGroups;
    }

    public boolean hasLocatedGroup(GroupOfShorties group) {
        return locatedGroups.contains(group);
    }

    public boolean isEmpty() {
        return locatedGroups.isEmpty();
    }

    public void locate(GroupOfShorties group) {
        if (hasLocatedGroup(group)) {
            throw new IllegalArgumentException("В локации - " + name + " - уже находиться данная группа коротышек");
        }
        if (group.hasLocation()) {
            throw new IllegalArgumentException("Группа может находиться только в одной локации");
        }
        if (availablePlaces < group.getGroup().size()) {
            throw new IndexOutOfBoundsException(name + " не может вместить в себя группу из " + group.getGroup().size() + " коротышек");
        }
        for (Shorty shorty : group.getGroup()) {
            if (!shorty.isNormal()) {
                throw new StatusException(shorty, shorty.getStatus(), ShortyStatus.NORMAL);
            }
        }
        locateResidents(group.getGroup());
        availablePlaces = calculateAvailablePlaces(rooms);
        addLocatedGroup(group);
        System.out.println(group + "\n - теперь находится в - " + this + "\n");
    }

    private void locateResidents(List<Shorty> group) {
        for (Shorty shorty : group) {
            for (Room room : rooms) {
                if (room.hasAvailablePlaces()) {
                    room.addResident(shorty);
                    break;
                }
            }
        }
    }

    private void addLocatedGroup(GroupOfShorties group) {
        group.setLocation(this);
        locatedGroups.add(group);
    }

    public void dislocate(GroupOfShorties group) {
        if (!hasLocatedGroup(group)) {
            throw new IllegalArgumentException("В локации - " + name + " - нет данной группы коротышек");
        }
        for (Shorty shorty : group.getGroup()) {
            if (!shorty.isNormal()) {
                throw new StatusException(shorty, shorty.getStatus(), ShortyStatus.NORMAL);
            }
        }
        dislocateResidents(group.getGroup());
        availablePlaces = calculateAvailablePlaces(rooms);
        removeLocatedGroup(group);
        System.out.println(group + "\n - теперь не находится в - " + this + "\n");
    }

    private void dislocateResidents(List<Shorty> group) {
        for (Shorty shorty : group) {
            for (Room room : rooms) {
                if (room.hasResident(shorty)) {
                    room.removeResident(shorty);
                    break;
                }
            }
        }
    }

    private void removeLocatedGroup(GroupOfShorties group) {
        group.setLocation(null);
        locatedGroups.remove(group);
    }

    @Override
    public String toString() {
        return "Локация " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return availablePlaces == location.availablePlaces && name.equals(location.name) &&
                Arrays.equals(rooms, location.rooms) && locatedGroups.equals(location.locatedGroups);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, availablePlaces, locatedGroups);
        result = 31 * result + Arrays.hashCode(rooms);
        return result;
    }
}
