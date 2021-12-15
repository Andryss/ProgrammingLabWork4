import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class Room {
    private RoomStatus status = RoomStatus.NORMAL;
    private final List<Shorty> residents = new LinkedList<>();
    private int availablePlaces;

    public Room(int availablePlaces) {
        this.availablePlaces = availablePlaces;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public List<Shorty> getResidents() {
        return residents;
    }

    public boolean hasResident(Shorty resident) {
        return residents.contains(resident);
    }

    public boolean isEmpty() {
        return residents.isEmpty();
    }

    public int getMaxResidentsCount() {
        return residents.size();
    }

    public int getAvailablePlaces() {
        return availablePlaces;
    }

    public boolean hasAvailablePlaces() {
        return availablePlaces > 0;
    }

    private void setStatus(RoomStatus status) {
        this.status = status;
    }

    void cleanRoom() {
        setStatus(RoomStatus.cleanerThan(status));
    }

    void trashRoom() {
        setStatus(RoomStatus.dirtierThan(status));
    }

    void addResident(Shorty resident) {
        residents.add(resident);
        resident.setRoom(this);
        availablePlaces--;
    }

    void removeResident(Shorty resident) {
        if (residents.remove(resident)) {
            resident.setRoom(null);
            availablePlaces++;
        }
    }

    @Override
    public String toString() {
        return "Комната";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return availablePlaces == room.availablePlaces && status == room.status && residents.equals(room.residents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, residents, availablePlaces);
    }
}
