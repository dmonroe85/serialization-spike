package memtest.packing;

import memtest.common.Util;
import memtest.serializerfunctions.SerializerInterface;
import org.apache.hadoop.hbase.util.Bytes;

import java.math.BigDecimal;
import java.math.BigInteger;


public class PackingUtil {
    private int DataLengthSize = 2;
    public SerializerInterface serializer;

    public PackingUtil(SerializerInterface serializer_) {
        serializer = serializer_;
    }


    public short getDataLength(byte[] payload, int offset) {
        byte[] sizeSlice =
                Util.byteArraySlice(
                        payload,
                        offset,
                        offset + DataLengthSize
                );

        return Short.parseShort(
                serializer.deserialize(sizeSlice, short.class).toString()
        );
    }


    public byte[] serialize(Object value) {
        return serializer.serialize(value);
    }


    public byte[] serializeWithLength(Object value) {
        byte[] serialized = serializer.serialize(value);

        byte[] serializedLength = serializer.serialize(
                (short)serialized.length
        );

        return Util.concatenateByteArrays(serializedLength, serialized);
    }


    public Object dataFromPayload(byte[] payload,
                                  int fieldIndex,
                                  Class cls) {
        int currentOffset = 0;
        Object data = null;

        for (int i = 0; i <= fieldIndex; i++) {
            short dataLength = getDataLength(payload, currentOffset);

            int dataStart = currentOffset + DataLengthSize;
            int dataEnd = dataStart + dataLength;

            if (i == fieldIndex) {
                data = serializer.deserialize(
                        Util.byteArraySlice(payload, dataStart, dataEnd),
                        cls
                );
            }

            currentOffset = dataEnd;
        }

        return data;
    }


    public byte[] byteToBytes(byte b) { return new byte[]{b}; }
    public byte bytesToByte(byte[] ba) { return ba[0]; }

    public byte[] shortToBytes(short s) { return Bytes.toBytes(s); }
    public short bytesToShort(byte[] ba) { return Bytes.toShort(ba); }

    public byte[] intToBytes(int i) { return Bytes.toBytes(i); }
    public int bytesToInt(byte[] ba) { return Bytes.toInt(ba); }

    public byte[] longToBytes(long l) { return Bytes.toBytes(l); }
    public long bytesToLong(byte[] ba) { return Bytes.toLong(ba); }

    public byte[] floatToBytes(float f) { return Bytes.toBytes(f); }
    public float bytesToFloat(byte[] ba) { return Bytes.toFloat(ba); }

    public byte[] doubleToBytes(double d ) { return Bytes.toBytes(d); }
    public double bytesToDouble(byte[] ba) { return Bytes.toDouble(ba); }

    public byte[] bigDecimalToBytes(BigDecimal bd) { return Bytes.toBytes(bd); }
    public BigDecimal bytesToBigDecimal(byte[] ba) { return Bytes.toBigDecimal(ba); }

    public byte[] stringToBytes(String s) { return Bytes.toBytes(s); }
    public String bytesToString(byte[] ba) { return Bytes.toString(ba); }

    public byte[] bigIntegerToBytes(BigInteger bi) {
        return bi.toByteArray();
    }
    public BigInteger bytesToBigInteger(byte[] ba) { return new BigInteger(ba); }

}
