package dk.grp1.tanks.common.utils;

import java.util.Comparator;

/**
 * An implementation of a Comparator for the PriorityWrapper.
 */
public class PriorityWrapperComparator implements Comparator<PriorityWrapper> {

    @Override
    public int compare(PriorityWrapper o1, PriorityWrapper o2) {

        if (o1.getPriority() == o2.getPriority()) {
            return 0;
        }

        if (o1.getPriority() < o2.getPriority()) {
            return -1;
        } else {
            return 1;
        }

    }

}