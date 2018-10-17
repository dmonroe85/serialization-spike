package memtest.packing;

import memtest.common.Util;
import memtest.serializerfunctions.KryoSerializer;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.*;

public class PackingUtilTest {

    @Test
    public void serializeWithLength() {
        PackingUtil pu = new PackingUtil(new KryoSerializer());
        short data = 10;
        byte [] payload = pu.serializeWithLength(data);

        assertEquals(payload.length, 4);
    }

    @Test
    public void getDataLength() {
        PackingUtil pu = new PackingUtil(new KryoSerializer());
        short data1 = 10;
        byte data2 = 54;

        byte[] payload1 = pu.serializeWithLength(data1);
        byte[] payload2 = pu.serializeWithLength(data2);

        byte[] payload = Util.concatenateByteArrays(payload1, payload2);

        short dataLen1 = pu.getDataLength(payload, 0);
        short dataLen2 = pu.getDataLength(payload, 4);

        assertEquals(dataLen1, 2);
        assertEquals(dataLen2, 1);
    }

    @Test
    public void dataFromPayload() {
        PackingUtil pu = new PackingUtil(new KryoSerializer());

        String data1 = Util.newRandomString((byte)5);
        byte[] payload1 = pu.serializeWithLength(data1);

        String data2 = Util.newRandomString((byte)10);
        byte[] payload2 = pu.serializeWithLength(data2);

        byte[] payload = Util.concatenateByteArrays(payload1, payload2);

        String deserializedData1 =
                (String)pu.dataFromPayload(
                        payload,
                        0,
                        String.class
                );
        String deserializedData2 =
                (String)pu.dataFromPayload(
                        payload,
                        1,
                        String.class
                );

        assertEquals(data1, deserializedData1);
        assertEquals(data2, deserializedData2);
    }

    @Test
    public void roundTrips() {

        PackingUtil pu = new PackingUtil(new KryoSerializer());

        byte b = 120;
        assertEquals(pu.bytesToByte(pu.byteToBytes(b)), b);

        short s = 1024;
        assertEquals(pu.bytesToShort(pu.shortToBytes(s)), s);

        int i = 1024012;
        assertEquals(pu.bytesToInt(pu.intToBytes(i)), i);

        long l = 10241234012L;
        assertEquals(pu.bytesToLong(pu.longToBytes(l)), l);

        BigInteger bi = new BigInteger("123412151235123412131312312341234124121442423");
        assertEquals(pu.bytesToBigInteger(pu.bigIntegerToBytes(bi)), bi);

        float f = 1.2f;
        assertEquals(pu.bytesToFloat(pu.floatToBytes(f)), f, .01);

        double d = 1.2;
        assertEquals(pu.bytesToDouble(pu.doubleToBytes(d)), d, .01);

        BigDecimal bd = new BigDecimal(bi, 10);
        assertEquals(pu.bytesToBigDecimal(pu.bigDecimalToBytes(bd)), bd);

        String str = "alksdjfmn138hr 0192j3oialihdjvopzks  podinewq0934!@#$KL!J@#F()ß∂ƒƒœ∑´´£¡¢ƒ¡£¢";
        assertEquals(pu.bytesToString(pu.stringToBytes(str)), str);

    }
}