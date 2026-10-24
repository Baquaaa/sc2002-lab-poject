package rush.entity;

/**
 * A food critic is impatient (loses 12 per waiting turn) and always orders the
 * dish that takes the kitchen the longest to prepare.
 */
public class CriticCustomer extends Customer {

    public CriticCustomer(int id, int arrivalTurn) {
        super(id, arrivalTurn);
    }

    public String getType() {
        return "Critic";
    }

    public int getSatisfactionLoss() {
        return 12;
    }

    public MenuItem chooseItem(Menu menu) {
        return menu.getDishWithMostPrepUnits();
    }
}
