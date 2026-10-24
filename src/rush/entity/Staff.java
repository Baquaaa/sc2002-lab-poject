package rush.entity;

/** A staff member who can do one task per turn. */
public abstract class Staff {

    private String role;
    private boolean doneThisTurn = false;

    public Staff(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void startTurn() {
        doneThisTurn = false;
    }

    public boolean isDoneThisTurn() {
        return doneThisTurn;
    }

    /** Called by a subclass before doing its task. */
    protected void checkCanWork() throws InvalidActionException {
        if (doneThisTurn) {
            throw new InvalidActionException(role + " has already done a task this turn.");
        }
    }

    /** Called by a subclass after its task succeeded. */
    protected void finishTask() {
        doneThisTurn = true;
    }
}
