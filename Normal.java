public class Normal extends Shorty {

    public Normal(String name) {
        super(name);
    }

    @Override
    public void spendFreeTime() {
        if (Math.random() > 0.5) {
            try {
                cleanRoom();
            } catch (StatusException e) {
                System.out.println(this + " с удовольствием бы прибрался в комнате, но не может. " + this + getAction());
            }
        } else if (Math.random() > 0.2) {
            try {
                trashRoom();
            } catch (StatusException e) {
                System.out.println(this + " с удовольствием бы загрязнил комнату, но не может. " + this + getAction());
            }
        }
    }

}
