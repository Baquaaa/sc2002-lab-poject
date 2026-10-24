package rush.entity;

/** Helper for showing an amount stored in cents as dollars, e.g. 1080 -> "$10.80". */
public class Money {

    public static String format(int cents) {
        return String.format("$%d.%02d", cents / 100, cents % 100);
    }

    /** Multiplies an amount by a factor (e.g. 0.9 for 10% off), rounded to the nearest cent. */
    public static int multiply(int cents, double factor) {
        return (int) Math.round(cents * factor);
    }
}
