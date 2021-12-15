public enum RoomStatus {
    CLEANEST,
    CLEAN,
    NORMAL,
    DIRTY,
    DIRTIEST;

    public static RoomStatus cleanerThan(RoomStatus status) {
        return status.ordinal() == 0 ? RoomStatus.values()[status.ordinal()] : RoomStatus.values()[status.ordinal() - 1];
    }

    public static RoomStatus dirtierThan(RoomStatus status) {
        return status.ordinal() == RoomStatus.values().length - 1 ? RoomStatus.values()[status.ordinal()] : RoomStatus.values()[status.ordinal() + 1];
    }
}
