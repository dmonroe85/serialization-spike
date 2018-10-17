package memtest.serializerfunctions;

import memtest.common.Util;
import memtest.domain.Simple;
import org.apache.geode.pdx.internal.PdxInputStream;
import org.apache.geode.pdx.internal.PdxOutputStream;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class PdxSerializerSimpleByField extends AbstractSerializer {
    @Override
    public byte[] serialize(Object object) throws IOException {
        Simple simple = (Simple)object;

        PdxOutputStream pos = new PdxOutputStream();

        pos.writeByte(simple.getByte());
        pos.writeShort(simple.getShort());
        pos.writeInt(simple.getInt());
        pos.writeLong(simple.getLong());
        pos.writeByteArray(Util.bigIntegerToBytes(simple.getBigInteger()));

        pos.writeFloat(simple.getFloat());
        pos.writeDouble(simple.getDouble());
        pos.writeByteArray(Util.bigDecimalToBytes(simple.getBigDecimal()));

        pos.writeString(simple.getString());

        return pos.toByteArray();
    }

    @Override
    public Object deserialize(byte[] ba, Class cls) throws IOException, ClassNotFoundException{
        PdxInputStream pis = new PdxInputStream(ba);

        byte b = pis.readByte();
        short s = pis.readShort();
        int i = pis.readInt();
        long l = pis.readLong();
        BigInteger bi = Util.bytesToBigInteger(pis.readByteArray());

        float f = pis.readFloat();
        double d = pis.readDouble();
        BigDecimal bd = Util.bytesToBigDecimal(pis.readByteArray());

        String str = pis.readString();

        return new Simple(b, s, i, l, bi, f, d, bd, str);
    }
}
