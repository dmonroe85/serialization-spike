package memtest.common;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UtilTest {

    @Test
    public void newRandomString() {
        assertNotEquals(
                Util.newRandomString((byte)10),
                Util.newRandomString((byte)10)
        );
    }

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

    @Test
    public void prependLength() {
        byte[] ba = new byte[]{1, 2, 3, 4, 5};
        byte[] prepended = Util.prependLength(ba);

        assertEquals(prepended[0], 0);
        assertEquals(prepended[1], 5);
        assertEquals(prepended.length, 7);
    }

    @Test
    public void bigIntegerRoundTrip() {
        Random r = new Random();
        BigInteger bi = new BigInteger(r.nextInt(Byte.MAX_VALUE), r);
        assertEquals(bi, Util.bytesToBigInteger(Util.bigIntegerToBytes(bi)));
    }

    @Test
    public void bigDecimalRoundTrip() throws IOException, ClassNotFoundException {
        Random r = new Random();
        BigDecimal bd = new BigDecimal(r.nextDouble());
        assertEquals(bd, Util.bytesToBigDecimal(Util.bigDecimalToBytes(bd)));
    }
}