package memtest.domain;

import memtest.common.Util;
import memtest.packing.AbstractPacked;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;


public class SimplePacked extends AbstractPacked {

    public void setClasses() {
        classes = new Class[]{
                byte.class,
                short.class,
                int.class,
                long.class,
                BigInteger.class,
                float.class,
                double.class,
                BigDecimal.class,
                String.class
        };
    }

    public SimplePacked(Simple s) throws IOException {
        super();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put(serialize(s.getByte()));
        buffer.put(serialize(s.getShort()));
        buffer.put(serialize(s.getInt()));
        buffer.put(serialize(s.getLong()));
        buffer.put(serialize(s.getBigInteger()));

        buffer.put(serialize(s.getFloat()));
        buffer.put(serialize(s.getDouble()));
        buffer.put(serialize(s.getBigDecimal()));

        buffer.put(serialize(s.getString()));

        int position = buffer.position();
        byte[] ba = buffer.array();

        setPayload(Util.byteArraySlice(ba, 0, position));
    }

    public SimplePacked(byte[] ba) {
        super();

        setPayload(ba);
    }

    public byte getByte() throws IOException, ClassNotFoundException {
        return Byte.parseByte(
                dataFromPayload(0).toString()
        );
    }

    public short getShort() throws IOException, ClassNotFoundException {
        return Short.parseShort(
                dataFromPayload(1).toString()
        );
    }

    public int getInt() throws IOException, ClassNotFoundException {
        return Integer.parseInt(
                dataFromPayload(2).toString()
        );
    }

    public long getLong() throws IOException, ClassNotFoundException {
        return Long.parseLong(
                dataFromPayload(3).toString()
        );
    }

    public BigInteger getBigInteger() throws IOException, ClassNotFoundException {
        return (BigInteger)dataFromPayload(4);
    }

    public float getFloat() throws IOException, ClassNotFoundException {
        return Float.parseFloat(
                dataFromPayload(5).toString()
        );
    }

    public double getDouble() throws IOException, ClassNotFoundException {
        return Double.parseDouble(
                dataFromPayload(6).toString()
        );
    }

    public BigDecimal getBigDecimal() throws IOException, ClassNotFoundException {
        return (BigDecimal)dataFromPayload(7);
    }

    public String getString() throws IOException, ClassNotFoundException {
        return dataFromPayload(8).toString();
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

            try {
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
            catch (IOException e) {
                return false;
            }
            catch (ClassNotFoundException e) {
                return false;
            }
        }
        else if (o.getClass() == SimplePacked.class) {
            SimplePacked that = (SimplePacked)o;

            try {
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
            catch (IOException e) {
                return false;
            }
            catch (ClassNotFoundException e) {
                return false;
            }
        }

        return false;
    }

}
