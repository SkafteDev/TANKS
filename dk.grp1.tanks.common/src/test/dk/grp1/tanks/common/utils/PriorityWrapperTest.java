package dk.grp1.tanks.common.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PriorityWrapperTest {

    private PriorityWrapper<Object> pw1;
    private Object o;

    @Before
    public void setUp() throws Exception {
        pw1 = new PriorityWrapper<>(1, o);
    }

    @Test
    public void getPriority() {
        assertEquals(1, pw1.getPriority());
    }

    @Test
    public void getType() {
        assertEquals(o, pw1.getType());
    }
}