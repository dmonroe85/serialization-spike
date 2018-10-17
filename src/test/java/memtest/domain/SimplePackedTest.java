package memtest.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimplePackedTest {

    @Test
    public void simplePacked() {
        Simple s = new Simple();
        SimplePacked sp = new SimplePacked(s);

        assertEquals(sp, s);
    }

}