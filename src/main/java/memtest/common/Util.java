package memtest.common;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;

public class Util {

    static private Random r = new Random();


    static public String newRandomString(byte length) {
        byte[] array = new byte[length];
        r.nextBytes(array);
        return new String(
                array,
                0,
                array.length,
                Charset.forName("UTF-8")
        );
    }


    static public byte[] byteArraySlice(byte[] array, int start, int end) {

        return Arrays.copyOfRange(array, start, end);
    }


    /*
        Shamelessly stolen from:
        https://stackoverflow.com/questions/80476/how-can-i-concatenate-two-arrays-in-java
     */
    static public byte[] concatenateByteArrays(byte[] a, byte[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        byte[] c = (byte[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }


}
