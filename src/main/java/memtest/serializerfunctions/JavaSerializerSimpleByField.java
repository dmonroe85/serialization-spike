package memtest.serializerfunctions;

import memtest.domain.Simple;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JavaSerializerSimpleByField extends AbstractSerializer {
    @Override
    public byte[] serialize(Object object) throws IOException {
        Simple simple = (Simple)object;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeByte(simple.getByte());
        oos.writeShort(simple.getShort());
        oos.writeInt(simple.getInt());
        oos.writeLong(simple.getLong());
        oos.writeObject(simple.getBigInteger());

        oos.writeFloat(simple.getFloat());
        oos.writeDouble(simple.getDouble());
        oos.writeObject(simple.getBigDecimal());

        oos.writeUTF(simple.getString());

        oos.close();

        return baos.toByteArray();
    }

    @Override
    public Object deserialize(byte[] ba, Class cls) throws IOException, ClassNotFoundException {
        Simple simple = null;

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(ba));

        byte b = ois.readByte();
        short s = ois.readShort();
        int i = ois.readInt();
        long l = ois.readLong();
        BigInteger bi = (BigInteger) ois.readObject();
        float f = ois.readFloat();
        double d = ois.readDouble();
        BigDecimal bd = (BigDecimal) ois.readObject();
        String str = ois.readUTF();

        ois.close();

        simple = new Simple(b, s, i, l, bi, f, d, bd, str);

        return simple;
    }
}
