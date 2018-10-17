package memtest.domain;

import memtest.common.ByteArray;
import memtest.packing.PackingUtil;
import memtest.common.Util;
import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Random;

public class Simple implements Serializable {

    private Random r = new Random();

    private byte b = (byte) r.nextInt();
    private short s = (short) r.nextInt();
    private int i = r.nextInt();
    private long l = r.nextLong();
    private BigInteger bi =
            new BigInteger((byte)r.nextInt(Byte.MAX_VALUE), r);
    private float f = r.nextFloat();
    private double d = r.nextDouble();
    private BigDecimal bd = new BigDecimal(r.nextDouble());
    private String str =
            Util.newRandomString((byte)r.nextInt(Byte.MAX_VALUE));

    public byte getByte() { return b; }
    public short getShort() { return s; }
    public int getInt() { return i; }
    public long getLong() { return l; }
    public BigInteger getBigInteger() { return bi; }
    public float getFloat() { return f; }
    public double getDouble() { return d; }
    public BigDecimal getBigDecimal() { return bd; }
    public String getString() { return str; }

    public void setByte(byte b_) { b = b_; }
    public void setShort(short s_) { s = s_; }
    public void setInt(int i_) { i = i_; }
    public void setLong(long l_) { l = l_; }
    public void setBigInteger(BigInteger bi_) { bi = bi_; }
    public void setFloat(float f_) { f = f_; }
    public void setDouble(double d_) { d = d_; }
    public void setBigDecimal(BigDecimal bd_) { bd = bd_; }
    public void setString(String str_) { str = str_; }

    public Simple() {}
    public Simple(byte b_,
           short s_,
           int i_,
           long l_,
           BigInteger bi_,
           float f_,
           double d_,
           BigDecimal bd_,
           String str_) {
        b = b_;
        s = s_;
        i = i_;
        l = l_;
        bi = bi_;
        f = f_;
        d = d_;
        bd = bd_;
        str = str_;
    }

    public byte[] buildPayload(PackingUtil pu) {
        ByteBuffer payload = ByteBuffer.allocate(1024);

        payload.put(pu.byteToBytes(getByte()));
        payload.put(pu.shortToBytes(getShort()));
        payload.put(pu.intToBytes(getInt()));
        payload.put(pu.longToBytes(getLong()));
        payload.put(pu.bigIntegerToBytes(getBigInteger()));

        payload.put(pu.floatToBytes(getFloat()));
        payload.put(pu.doubleToBytes(getDouble()));
        payload.put(pu.bigDecimalToBytes(getBigDecimal()));

        payload.put(pu.stringToBytes(getString()));

        int position = payload.position();
        byte[] ba = payload.array();

        return Util.byteArraySlice(ba, 0, position);
    }

    @Override
    public int hashCode() { return super.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }

        if (!(o.getClass() == Simple.class)) { return false; }

        Simple that = (Simple)o;

        return (
                getByte() == that.getByte() &
                getShort() == that.getShort() &
                getInt() == that.getInt() &
                getLong() == that.getLong() &
                getBigInteger().equals(that.getBigInteger()) &
                getFloat() == that.getFloat() &
                getDouble() == that.getDouble() &
                getBigDecimal().equals(that.getBigDecimal()) &
                getString().equals(that.getString())
        );
    }

    @Override
    public String toString() {
        return "(" + b + "," + s + "," + i + "," + l + "," + bi + "," + f + "," + d + "," + bd + "," + str + ")";
    }


}
