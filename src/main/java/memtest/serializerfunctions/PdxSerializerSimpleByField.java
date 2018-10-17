package memtest.serializerfunctions;

import memtest.domain.Simple;
import org.apache.geode.pdx.internal.PdxInputStream;
import org.apache.geode.pdx.internal.PdxOutputStream;

import java.math.BigDecimal;
import java.math.BigInteger;

public class PdxSerializerSimpleByField implements SerializerInterface {
    @Override
    public byte[] serialize(Object object) {
        Simple simple = (Simple)object;

        PdxOutputStream pos = new PdxOutputStream();

        pos.writeByte(simple.getByte());
        pos.writeShort(simple.getShort());
        pos.writeInt(simple.getInt());
        pos.writeLong(simple.getLong());
        pos.writeObject(simple.getBigInteger(), false);

        pos.writeFloat(simple.getFloat());
        pos.writeDouble(simple.getDouble());
        pos.writeObject(simple.getBigDecimal(), false);

        pos.writeString(simple.getString());

        return pos.toByteArray();
    }

    @Override
    public Object deserialize(byte[] ba, Class cls) {
        PdxInputStream pis = new PdxInputStream(ba);

        byte b = pis.readByte();
        short s = pis.readShort();
        int i = pis.readInt();
        long l = pis.readLong();
        BigInteger bi = (BigInteger)pis.readObject();

        float f = pis.readFloat();
        double d = pis.readDouble();
        BigDecimal bd = (BigDecimal)pis.readObject();

        String str = pis.readString();

        return new Simple(b, s, i, l, bi, f, d, bd, str);
    }
}
