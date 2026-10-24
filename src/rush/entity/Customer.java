package rush.entity;

/**
 * A customer of the restaurant. Subclasses decide how fast the customer loses
 * satisfaction, and can change what they order and how much they pay.
 */
public abstract class Customer {

    public static final int MAX_SATISFACTION = 100;

    private int id;
    private int arrivalTurn;
    private int satisfaction = MAX_SATISFACTION;
    private CustomerStatus status = CustomerStatus.WAITING;
    private Table table;
    private Order order;
    private int servedTurn;

    public Customer(int id, int arrivalTurn) {
        this.id = id;
        this.arrivalTurn = arrivalTurn;
    }

    /** Name of the customer type, shown on screen. */
    public abstract String getType();

    /** Satisfaction lost for every turn spent waiting without food. */
    public abstract int getSatisfactionLoss();

    /** The customer picks their own food: customer n orders dish (n - 1) of the menu, wrapping around. */
    public MenuItem chooseItem(Menu menu) {
        return menu.getDish(id - 1);
    }

    /** The bill for an item at the given price. Regular customers pay full price. */
    public int applyDiscount(int priceCents) {
        return priceCents;
    }

    /** Still waiting for a table or for food (so loses satisfaction at turn end). */
    public boolean isWaitingForService() {
        return status == CustomerStatus.WAITING || status == CustomerStatus.SEATED
                || status == CustomerStatus.ORDERED;
    }

    public void loseSatisfaction() {
        satisfaction = Math.max(0, satisfaction - getSatisfactionLoss());
    }

    public int getId() {
        return id;
    }

    public int getArrivalTurn() {
        return arrivalTurn;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getServedTurn() {
        return servedTurn;
    }

    public void setServedTurn(int servedTurn) {
        this.servedTurn = servedTurn;
    }

    public String toString() {
        return "C" + id + " (" + getType() + ")";
    }
}
