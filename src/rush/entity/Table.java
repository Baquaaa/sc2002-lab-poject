package rush.entity;

public class Table {

    private int number;
    private Customer customer; // null when the table is free

    public Table(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean isFree() {
        return customer == null;
    }

    public void seat(Customer customer) {
        this.customer = customer;
    }

    public void free() {
        customer = null;
    }
}
