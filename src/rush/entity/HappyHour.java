package rush.entity;

/**
 * Happy Hour can be switched on once per game, at the start of a turn, and lasts
 * for that turn and the next. Orders taken while it is active get 20% off, and
 * customers still waiting recover 15 satisfaction at the start of each active turn.
 */
public class HappyHour {

    public static final int DURATION = 2;
    public static final int RECOVERY = 15;

    private boolean used = false;
    private int turnsLeft = 0;

    public boolean isAvailable() {
        return !used;
    }

    public boolean isActive() {
        return turnsLeft > 0;
    }

    public int getTurnsLeft() {
        return turnsLeft;
    }

    public void activate() throws InvalidActionException {
        if (used) {
            throw new InvalidActionException("Happy Hour can only be used once per game.");
        }
        used = true;
        turnsLeft = DURATION;
    }

    /** Applied after the customer's own discount, when the order is taken. */
    public int applyDiscount(int priceCents) {
        if (isActive()) {
            return Money.multiply(priceCents, 0.8);
        }
        return priceCents;
    }

    public void endTurn() {
        if (turnsLeft > 0) {
            turnsLeft--;
        }
    }
}
