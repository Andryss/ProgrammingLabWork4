public class Cook extends Shorty {

    public Cook(String name) {
        super(name);
    }

    private Food cooking() {
        return Food.generateRandomFood();
    }

    private void feedOnly(Shorty shorty) {
        Food food = cooking();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        shorty.eat(food);
    }

    public void feedAllGroup() throws StatusException {
        if (!isNormal()) {
            throw new StatusException(this, getStatus(), ShortyStatus.NORMAL);
        }
        if (hasGroup() && getGroup().getGroup().size() > 1) {
            System.out.println(this + " готовит еду на всю свою группу");
            for (Shorty shorty : getGroup().getGroup()) {
                feedOnly(shorty);
            }
            System.out.println();
        }
    }

    @Override
    public void spendFreeTime() {
        if (Math.random() > 0.95) {
            try {
                feedAllGroup();
            } catch (StatusException e) {
                System.out.println(this + " с удовольствием бы покормил всю группу, но не может. " + this + getAction());
            }
        }
    }

    @Override
    public String toString() {
        return "Коротышка-повар " + getName();
    }
}
