package rush.entity;

/** Seats a customer, takes an order or serves a READY order (one of these per turn). */
public class Waiter extends Staff {

    public Waiter() {
        super("Waiter");
    }

    public void seatCustomer(Customer customer, Restaurant restaurant) throws InvalidActionException {
        checkCanWork();
        restaurant.seatCustomer(customer);
        finishTask();
    }

    public Order takeOrder(Customer customer, Menu menu, HappyHour happyHour) throws InvalidActionException {
        checkCanWork();
        if (customer.getStatus() != CustomerStatus.SEATED) {
            throw new InvalidActionException(customer + " is not seated and waiting to order.");
        }
        MenuItem item = customer.chooseItem(menu);
        int price = customer.applyDiscount(item.getPriceCents()); // VIP discount first
        price = happyHour.applyDiscount(price);                     // then Happy Hour
        Order order = new Order(customer, item, price);
        customer.setOrder(order);
        customer.setStatus(CustomerStatus.ORDERED);
        finishTask();
        return order;
    }

    public void serve(Customer customer, int turn) throws InvalidActionException {
        checkCanWork();
        Order order = customer.getOrder();
        if (order == null || order.getStatus() != OrderStatus.READY) {
            throw new InvalidActionException(customer + " has no READY order to serve.");
        }
        order.markServed();
        customer.setStatus(CustomerStatus.SERVED);
        customer.setServedTurn(turn);
        finishTask();
    }
}
