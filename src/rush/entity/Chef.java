package rush.entity;

/** Does one preparation unit of one order per turn. */
public class Chef extends Staff {

    public Chef() {
        super("Chef");
    }

    public void prepare(Order order) throws InvalidActionException {
        checkCanWork();
        if (order == null || order.getStatus() != OrderStatus.PREPARING) {
            throw new InvalidActionException("That order is not waiting in the kitchen.");
        }
        order.prepareOneUnit();
        finishTask();
    }
}
