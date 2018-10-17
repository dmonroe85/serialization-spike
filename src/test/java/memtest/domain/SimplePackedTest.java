package memtest.domain;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SimplePackedTest {

    @Test
    public void simplePacked() throws IOException {
        Simple s = new Simple();
        SimplePacked sp = new SimplePacked(s);
        assertEquals(sp, s);
    }

}
