package dk.grp1.tanks.common.utils;

/**
 * A Wrapper created to be able to sort types given a priority.
 * A PriorityWrapper can be used with a Comparator.
 * @param <T> The type that needs a priority.
 */
public class PriorityWrapper<T>  {

    private int priority;
    private T type;

    public PriorityWrapper(int prio, T type) {
        this.priority = prio;
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public T getType() {
        return type;
    }

    public String toString() {
        return type.getClass().getCanonicalName() + " " + priority;
    }
}