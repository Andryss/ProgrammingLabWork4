public class CookNotFoundException extends Exception {
    private final GroupOfShorties group;

    public CookNotFoundException(GroupOfShorties group) {
        this.group = group;
    }

    @Override
    public String getMessage() {
        return group + "\n не может пообедать, потому что в ней нет повара";
    }
}