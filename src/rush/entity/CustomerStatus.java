package rush.entity;

/** Where a customer is in the visit, from arriving to leaving. */
public enum CustomerStatus {
    WAITING,      // in the waiting queue
    SEATED,       // at a table, order not taken yet
    ORDERED,      // order taken, food not served yet
    SERVED,       // food served, eating
    READY_TO_PAY, // finished eating
    PAID,
    LEFT          // left unhappy without paying
}
