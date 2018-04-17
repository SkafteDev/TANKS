package dk.grp1.tanks.common.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PriorityWrapperComparatorTest {

    private PriorityWrapperComparator comparator;
    private PriorityWrapper<Object> p1;
    private PriorityWrapper<Object> p2;
    private Object o1;
    private Object o2;

    @Before
    public void setUp() throws Exception {
        o1 = new Object();
        o2 = new Object();
        p1 = new PriorityWrapper<>(1, o1);
        p2 = new PriorityWrapper<>(2, o2);
        comparator = new PriorityWrapperComparator();
    }

    @Test
    public void compare() {
        assertEquals(-1, comparator.compare(p1, p2));
        assertEquals(1, comparator.compare(p2, p1));
        p2 = p1;
        assertEquals(0, comparator.compare(p1, p2));
    }
}