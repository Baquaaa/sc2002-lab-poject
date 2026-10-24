package rush.entity;

public class RegularCustomer extends Customer {

    public RegularCustomer(int id, int arrivalTurn) {
        super(id, arrivalTurn);
    }

    public String getType() {
        return "Regular";
    }

    public int getSatisfactionLoss() {
        return 8;
    }
}
