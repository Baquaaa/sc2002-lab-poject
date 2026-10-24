package rush.entity;

/** Seats at most one queued customer per turn, in addition to the Waiter's task. */
public class Host extends Staff {

    public Host() {
        super("Host");
    }

    public void seatCustomer(Customer customer, Restaurant restaurant) throws InvalidActionException {
        checkCanWork();
        restaurant.seatCustomer(customer);
        finishTask();
    }
}
