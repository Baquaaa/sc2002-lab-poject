package rush.entity;

/** Collects at most one payment per turn, automatically, oldest READY_TO_PAY customer first. */
public class Cashier extends Staff {

    public Cashier() {
        super("Cashier");
    }

    /** Returns the customer who paid, or null if nobody was ready to pay. */
    public Customer collectPayment(Restaurant restaurant) {
        Customer customer = restaurant.nextToPay();
        if (isDoneThisTurn() || customer == null) {
            return null;
        }
        customer.getOrder().markPaid();
        customer.setStatus(CustomerStatus.PAID);
        restaurant.recordPayment(customer);
        finishTask();
        return customer;
    }
}
