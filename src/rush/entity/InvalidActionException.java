package rush.entity;

/** Thrown when the manager picks an action that the rules do not allow. The message explains why. */
public class InvalidActionException extends Exception {

    public InvalidActionException(String message) {
        super(message);
    }
}
