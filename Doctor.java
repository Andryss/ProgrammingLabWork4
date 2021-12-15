import java.util.List;

public class Doctor extends Shorty {
    public static final String BASENAME = "Пилюлькин";
    private static final String criterion1 = "она вмещает больше коротышек",
                                criterion2 = "она имеет больше комнат";

    public Doctor(String name) {
        super(name);
    }

    public Doctor() {
        this(BASENAME);
    }

    public void analyzeRockets(Rocket rocket1, Rocket rocket2) {
        int rocket1MaxResidents = calculateMaxResidentsCount(rocket1),
                rocket2MaxResidents = calculateMaxResidentsCount(rocket2);
        if (rocket1MaxResidents > rocket2MaxResidents) {
            sayBestLocation(rocket1, criterion1);
            return;
        }
        if (rocket1MaxResidents < rocket2MaxResidents) {
            sayBestLocation(rocket2, criterion1);
            return;
        }

        int rocket1RoomsCount = rocket1.getRooms().length,
                rocket2RoomsCount = rocket2.getRooms().length;
        if (rocket1RoomsCount > rocket2RoomsCount) {
            sayBestLocation(rocket1, criterion2);
            return;
        }
        if (rocket1RoomsCount < rocket2RoomsCount) {
            sayBestLocation(rocket2, criterion2);
            return;
        }
        say("Ракеты " + rocket1 + " и " + rocket2 + " одинаково хороши");
    }

    public static int calculateMaxResidentsCount(Rocket rocket) {
        int maxResidentsCount = 0;
        for (Room room : rocket.getRooms()) {
            maxResidentsCount += room.getMaxResidentsCount();
        }
        return maxResidentsCount;
    }

    public void checkMess() throws InterruptedException, StatusException {
        if (!isNormal()) {
            throw new StatusException(this, getStatus(), ShortyStatus.NORMAL);
        }
        if (hasGroup() && hasRoom()) {
            System.out.println(this + " начал проверку нарушений режима");
            List<Room> groupRooms = getGroup().getLocation().getRooms(getGroup());
            for (Room room : groupRooms) {
                if (hasMess(room)) {
                    for (Shorty shorty : room.getResidents()) {
                        if (shorty.getGroup() == getGroup()) {
                            say("Коротышка " + shorty + " нарушил режим порядка! Ему нужно прибраться в комнате");
                        }
                    }
                }
            }
            Thread.sleep(1000L);
        }
    }

    public static boolean hasMess(Room room) {
        return room.getStatus().ordinal() > RoomStatus.values().length / 2;
    }

    private void sayBestLocation(AbleToLocate location, String reason) {
        say(location + " - лучшая локация, потому что " + reason);
    }

    public void unHug(Shorty shorty) {
        shorty.unHug();
    }

    @Override
    public void sleep() {
        super.sleep();
        System.out.println(this + " настолько громко храпит, что:");
        int wakedUpShorties = 0;
        for (Room room : getGroup().getLocation().getRooms()) {
            for (Shorty shorty : room.getResidents()) {
                if (shorty.isSleeping() && shorty != this) {
                    shorty.wakeUp(); wakedUpShorties++;
                }
            }
        }
        System.out.println(wakedUpShorties == 0 ? "Ничего не произошло\n" : "\n");
    }

    @Override
    public void spendFreeTime() {
        if (Math.random() > 0.5) {
            if (getGroup().hasLocation()) {
                sayBestLocation(getGroup().getLocation(), "в ней нахожусь я");
            }
        }
    }

    @Override
    public String toString() {
        return "Коротышка-доктор " + getName();
    }
}
