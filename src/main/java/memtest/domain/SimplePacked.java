package memtest.domain;

import memtest.packing.PackingUtil;
import memtest.serializerfunctions.KryoSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;


public class SimplePacked implements Serializable {

    private byte[] payload;

    private PackingUtil pu = new PackingUtil(new KryoSerializer());

    public SimplePacked(Simple s) {
        payload = s.buildPayload(pu);
    }

    public SimplePacked(byte[] ba) {
        setPayload(ba);
    }

    public void setPayload(byte[] newPayload) {
        payload = newPayload;
    }

    public byte[] getPayload() {
        return payload;
    }


    public byte getByte() {
        return Byte.parseByte(
                pu.dataFromPayload(payload, 0, byte.class).toString()
        );
    }

    public short getShort() {
        return Short.parseShort(
                pu.dataFromPayload(payload, 1, short.class).toString()
        );
    }

    public int getInt() {
        return Integer.parseInt(
                pu.dataFromPayload(payload, 2, int.class).toString()
        );
    }

    public long getLong() {
        return Long.parseLong(
                pu.dataFromPayload(payload, 3, long.class).toString()
        );
    }

    public BigInteger getBigInteger() {

        return (BigInteger)pu.dataFromPayload(payload, 4, BigInteger.class);
    }

    public float getFloat() {
        return Float.parseFloat(
                pu.dataFromPayload(payload, 5, float.class).toString()
        );
    }

    public double getDouble() {
        return Double.parseDouble(
                pu.dataFromPayload(payload, 6, double.class).toString()
        );
    }

    public BigDecimal getBigDecimal() {

        return (BigDecimal)pu.dataFromPayload(payload, 7, BigDecimal.class);
    }

    public String getString() {
        return pu.dataFromPayload(payload, 8, String.class).toString();
    }

    @Override
    public int hashCode() { return super.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        else if (o.getClass() == Simple.class) {
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
        else if (o.getClass() == SimplePacked.class) {
            SimplePacked that = (SimplePacked)o;

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

        return false;
    }

}
