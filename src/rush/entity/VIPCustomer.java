package rush.entity;

/** A VIP is more patient (loses 5 per waiting turn) and gets 10% off the bill. */
public class VIPCustomer extends Customer {

    public VIPCustomer(int id, int arrivalTurn) {
        super(id, arrivalTurn);
    }

    public String getType() {
        return "VIP";
    }

    public int getSatisfactionLoss() {
        return 5;
    }

    public int applyDiscount(int priceCents) {
        return Money.multiply(priceCents, 0.9);
    }
}
