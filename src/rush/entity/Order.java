package rush.entity;

/** One customer's order. The price is fixed when the order is taken. */
public class Order {

    private Customer customer;
    private MenuItem item;
    private int priceCents;
    private int unitsDone = 0;
    private OrderStatus status = OrderStatus.PREPARING;

    public Order(Customer customer, MenuItem item, int priceCents) {
        this.customer = customer;
        this.item = item;
        this.priceCents = priceCents;
    }

    /** Called by the Chef. The order becomes READY when its last unit is done. */
    public void prepareOneUnit() {
        unitsDone++;
        if (unitsDone >= item.getPrepUnits()) {
            status = OrderStatus.READY;
        }
    }

    public void markServed() {
        status = OrderStatus.SERVED;
    }

    public void markPaid() {
        status = OrderStatus.PAID;
    }

    /** Only an order that has not been served yet can be cancelled. */
    public void cancel() {
        if (status == OrderStatus.PREPARING || status == OrderStatus.READY) {
            status = OrderStatus.CANCELLED;
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public MenuItem getItem() {
        return item;
    }

    public int getPriceCents() {
        return priceCents;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getProgress() {
        return unitsDone + "/" + item.getPrepUnits();
    }

    public String toString() {
        return item.getName() + " " + Money.format(priceCents) + " [" + status + " " + getProgress() + "]";
    }
}
