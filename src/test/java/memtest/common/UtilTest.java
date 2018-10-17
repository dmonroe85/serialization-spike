package memtest.common;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class UtilTest {
    @Test
    public void byteArraySlice() {
        byte[] full = new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        byte[] slice = new byte[] {2, 3, 4};
        assertArrayEquals(slice, Util.byteArraySlice(full, 2, 5));
    }

    @Test
    public void concatenateByteArrays() {
        byte[] ba1 = new byte[] {0, 1, 2};
        byte[] ba2 = new byte[] {3, 4, 5};
        byte[] expected = new byte[] {0, 1, 2, 3, 4, 5};
        assertArrayEquals(expected, Util.concatenateByteArrays(ba1, ba2));
    }
}