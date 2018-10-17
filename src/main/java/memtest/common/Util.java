package memtest.common;

import memtest.serializerfunctions.AbstractSerializer;
import memtest.serializerfunctions.HBaseValueEncoder;
import memtest.serializerfunctions.KryoSerializer;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;

public class Util {

    static private AbstractSerializer serializer = new HBaseValueEncoder();

    static private Random r = new Random();

    static public String newRandomString(int length) {
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

    static public byte[] prependLength(byte[] ba) {
        byte[] serializedLength = Bytes.toBytes((short)ba.length);
        return Util.concatenateByteArrays(serializedLength, ba);
    }


    static public byte[] bigIntegerToBytes(BigInteger bi) {
        return bi.toByteArray();
    }


    static public BigInteger bytesToBigInteger(byte[] ba) {
        return new BigInteger(ba);
    }


    static public byte[] bigDecimalToBytes(BigDecimal bd) throws IOException {
        byte[] unscaledBytes = bd.unscaledValue().toByteArray();
        byte[] scaleBytes = serializer.serialize(bd.scale());
        return Util.concatenateByteArrays(scaleBytes, unscaledBytes);
    }


    static public BigDecimal bytesToBigDecimal(byte[] ba) throws IOException, ClassNotFoundException {
        int scale = (Integer)serializer.deserialize(Util.byteArraySlice(ba, 0, 4), int.class);
        BigInteger bi = new BigInteger(Util.byteArraySlice(ba, 4, ba.length));
        return new BigDecimal(bi, scale);
    }


}
