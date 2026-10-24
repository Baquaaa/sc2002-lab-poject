package rush.control;

import rush.boundary.ConsoleUI;
import rush.entity.Cashier;
import rush.entity.Chef;
import rush.entity.Customer;
import rush.entity.InvalidActionException;
import rush.entity.Money;
import rush.entity.Order;
import rush.entity.OrderStatus;
import rush.entity.RegularCustomer;
import rush.entity.Restaurant;
import rush.entity.Waiter;

/** Runs the 18 turns in the fixed order of steps and checks the result at the end. */
public class GameController {

    public static final int MAX_TURNS = 18;
    public static final int TABLES = 2;
    public static final int[] ARRIVAL_TURNS = {1, 4, 7, 10, 13, 16};

    public static final int TARGET_PAID = 4;
    public static final int TARGET_REVENUE_CENTS = 4500;
    public static final int TARGET_SATISFACTION = 60;

    private Restaurant restaurant = new Restaurant(TABLES);
    private Waiter waiter = new Waiter();
    private Chef chef = new Chef();
    private Cashier cashier = new Cashier();
    private ConsoleUI ui;
    private int turn;
    private int nextCustomerId = 1;

    public GameController(ConsoleUI ui) {
        this.ui = ui;
    }

    public void play() {
        ui.showWelcome(restaurant, MAX_TURNS);
        for (turn = 1; turn <= MAX_TURNS; turn++) {
            playTurn();
        }
        showResult();
    }

    private void playTurn() {
        waiter.startTurn();
        chef.startTurn();
        cashier.startTurn();
        ui.showTurnHeader(turn, MAX_TURNS);

        // Step 1: arrivals
        if (isArrivalTurn(turn)) {
            Customer customer = createCustomer(nextCustomerId++);
            restaurant.addCustomer(customer);
            ui.showMessage(customer + " arrives and joins the waiting queue (satisfaction 100).");
        }
        ui.showTurnState(restaurant);

        // Steps 2 and 3: manager choices. The Waiter acts before the Chef.
        waiterStep();
        chefStep();

        // Step 4: payment and eating. Paying first means a customer who becomes
        // READY_TO_PAY this turn pays next turn at the earliest.
        Customer paid = cashier.collectPayment(restaurant);
        if (paid != null) {
            ui.showMessage("Cashier collects " + Money.format(paid.getOrder().getPriceCents()) + " from "
                    + paid + ". PAID with satisfaction " + paid.getSatisfaction() + ", table freed.");
        }
        for (Customer c : restaurant.finishEating(turn)) {
            ui.showMessage(c + " finishes eating and is READY_TO_PAY.");
        }

        // Step 5: waiting and abandonment
        for (Customer c : restaurant.applyWaitingLoss()) {
            ui.showMessage(c + " reached satisfaction 0 and leaves without paying.");
        }
        ui.showEndOfTurn(turn, MAX_TURNS, restaurant);
    }

    private boolean isArrivalTurn(int turn) {
        for (int t : ARRIVAL_TURNS) {
            if (t == turn) {
                return true;
            }
        }
        return false;
    }

    /** The arrival line-up. Every customer is a Regular customer in Stage 1. */
    private Customer createCustomer(int id) {
        return new RegularCustomer(id, turn);
    }

    /** The Waiter does at most one task. An invalid choice is explained and asked again. */
    private void waiterStep() {
        while (true) {
            int task = ui.readInt("Waiter task (1 seat, 2 take order, 3 serve, 0 wait): ", 0, 3);
            if (task == 0) {
                return;
            }
            int id = ui.readInt("Which customer? (number, 0 = back): ", 0, 99);
            if (id == 0) {
                continue;
            }
            try {
                Customer customer = restaurant.findCustomer(id);
                if (task == 1) {
                    waiter.seatCustomer(customer, restaurant);
                    ui.showMessage("Waiter seats " + customer + " at Table " + customer.getTable().getNumber() + ".");
                } else if (task == 2) {
                    Order order = waiter.takeOrder(customer, restaurant.getMenu());
                    ui.showMessage(customer + " orders " + order.getItem().getName() + " for "
                            + Money.format(order.getPriceCents()) + ".");
                } else {
                    waiter.serve(customer, turn);
                    ui.showMessage("Waiter serves " + customer.getOrder().getItem().getName()
                            + " to " + customer + ". Order SERVED.");
                }
                return;
            } catch (InvalidActionException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /** The Chef advances at most one order by one preparation unit. */
    private void chefStep() {
        while (true) {
            int id = ui.readInt("Chef: prepare whose order? (number, 0 = wait): ", 0, 99);
            if (id == 0) {
                return;
            }
            try {
                Order order = restaurant.findCustomer(id).getOrder();
                chef.prepare(order);
                String ready = order.getStatus() == OrderStatus.READY ? " - READY" : "";
                ui.showMessage("Chef prepares C" + id + "'s " + order.getItem().getName() + " ("
                        + order.getProgress() + ")" + ready + ".");
                return;
            } catch (InvalidActionException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /** Step 6, after the last turn: victory only if all three targets are met. */
    private void showResult() {
        boolean paidOk = restaurant.getPaidCount() >= TARGET_PAID;
        boolean revenueOk = restaurant.getRevenueCents() >= TARGET_REVENUE_CENTS;
        boolean satisfactionOk = restaurant.getAverageSatisfaction() >= TARGET_SATISFACTION;
        String[] lines = {
            "Paid customers:       " + restaurant.getPaidCount() + " (need " + TARGET_PAID + ") "
                    + (paidOk ? "OK" : "MISSED"),
            "Revenue:              " + Money.format(restaurant.getRevenueCents()) + " (need "
                    + Money.format(TARGET_REVENUE_CENTS) + ") " + (revenueOk ? "OK" : "MISSED"),
            "Average satisfaction: " + ui.formatAverage(restaurant.getAverageSatisfaction()) + " (need "
                    + TARGET_SATISFACTION + ") " + (satisfactionOk ? "OK" : "MISSED"),
            "Turns used:           " + MAX_TURNS + "/" + MAX_TURNS
        };
        ui.showResult(paidOk && revenueOk && satisfactionOk, restaurant, lines);
    }
}
