package memtest.serializerfunctions;

import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class HBaseValueEncoder extends AbstractSerializer {

    public HBaseValueEncoder() {
        addFixedLength(byte.class, 1);
        addFixedLength(Byte.class, 1);
        addFixedLength(short.class, 2);
        addFixedLength(Short.class, 2);
        addFixedLength(int.class, 4);
        addFixedLength(Integer.class, 4);
        addFixedLength(long.class, 8);
        addFixedLength(Long.class, 8);
        addFixedLength(float.class, 4);
        addFixedLength(Float.class, 4);
        addFixedLength(double.class, 8);
        addFixedLength(Double.class, 8);
    }

    // Basic data type Encoders
    private byte[] byteToBytes(byte b) { return new byte[]{b}; }
    private byte bytesToByte(byte[] ba) { return ba[0]; }

    private byte[] shortToBytes(short s) { return Bytes.toBytes(s); }
    private short bytesToShort(byte[] ba) { return Bytes.toShort(ba); }

    private byte[] intToBytes(int i) { return Bytes.toBytes(i); }
    private int bytesToInt(byte[] ba) { return Bytes.toInt(ba); }

    private byte[] longToBytes(long l) { return Bytes.toBytes(l); }
    private long bytesToLong(byte[] ba) { return Bytes.toLong(ba); }

    private byte[] floatToBytes(float f) { return Bytes.toBytes(f); }
    private float bytesToFloat(byte[] ba) { return Bytes.toFloat(ba); }

    private byte[] doubleToBytes(double d ) { return Bytes.toBytes(d); }
    private double bytesToDouble(byte[] ba) { return Bytes.toDouble(ba); }

    private byte[] bigIntegerToBytes(BigInteger bi) { return bi.toByteArray(); }
    private BigInteger bytesToBigInteger(byte[] ba) { return new BigInteger(ba); }

    private byte[] bigDecimalToBytes(BigDecimal bd) { return Bytes.toBytes(bd); }
    private BigDecimal bytesToBigDecimal(byte[] ba) { return Bytes.toBigDecimal(ba); }

    private byte[] stringToBytes(String s) { return Bytes.toBytes(s); }
    private String bytesToString(byte[] ba) { return Bytes.toString(ba); }


    // Value serialization / de-serialization
    public byte[] serialize(Object value) throws IOException {
        Class cls = value.getClass();
        byte[] output;

        if (cls == byte.class || cls == Byte.class) {
            output = byteToBytes((Byte) value);
        } else if (cls == short.class || cls == Short.class) {
            output = shortToBytes((Short) value);
        } else if (cls == int.class || cls == Integer.class) {
            output = intToBytes((Integer) value);
        } else if (cls == long.class || cls == Long.class) {
            output = longToBytes((Long) value);
        } else if (cls == float.class || cls == Float.class) {
            output = floatToBytes((Float) value);
        } else if (cls == double.class || cls == Double.class) {
            output = doubleToBytes((Double) value);
        } else if (cls == BigInteger.class) {
            output = bigIntegerToBytes((BigInteger) value);
        } else if (cls == BigDecimal.class) {
            output = bigDecimalToBytes((BigDecimal) value);
        } else if (cls == String.class) {
            output = stringToBytes((String) value);
        } else {
            throw new IOException("Encoder not available for type: " + cls.getName());
        }

        return output;
    }

    public Object deserialize(byte[] ba, Class cls) throws IOException {
        Object output = null;

        if (cls == byte.class || cls == Byte.class) {
            output = bytesToByte(ba);
        } else if (cls == short.class || cls == Short.class) {
            output = bytesToShort(ba);
        } else if (cls == int.class || cls == Integer.class) {
            output = bytesToInt(ba);
        } else if (cls == long.class || cls == Long.class) {
            output = bytesToLong(ba);
        } else if (cls == float.class || cls == Float.class) {
            output = bytesToFloat(ba);
        } else if (cls == double.class || cls == Double.class) {
            output = bytesToDouble(ba);
        } else if (cls == BigInteger.class) {
            output = bytesToBigInteger(ba);
        } else if (cls == BigDecimal.class) {
            output = bytesToBigDecimal(ba);
        } else if (cls == String.class) {
            output = bytesToString(ba);
        } else {
            throw new IOException("Decoder not available for type: " + cls.getName());
        }

        return output;
    }
}
