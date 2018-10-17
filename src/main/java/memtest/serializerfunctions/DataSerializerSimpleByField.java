package memtest.serializerfunctions;

import memtest.domain.Simple;
import org.apache.geode.DataSerializer;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;

public class DataSerializerSimpleByField implements SerializerInterface {
    @Override
    public byte[] serialize(Object object) {
        Simple simple = (Simple)object;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeByte(simple.getByte());
            dos.writeShort(simple.getShort());
            dos.writeInt(simple.getInt());
            dos.writeLong(simple.getLong());
            DataSerializer.writeObject(simple.getBigInteger(), dos);

            dos.writeFloat(simple.getFloat());
            dos.writeDouble(simple.getDouble());
            DataSerializer.writeObject(simple.getBigDecimal(), dos);

            dos.writeUTF(simple.getString());

            dos.flush();
            dos.close();
        } catch (IOException e) {
            e.fillInStackTrace();
        }

        return baos.toByteArray();
    }

    @Override
    public Object deserialize(byte[] ba, Class cls) {
        Simple simple = null;

        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        try {
            byte b = dis.readByte();
            short s = dis.readShort();
            int i = dis.readInt();
            long l = dis.readLong();
            BigInteger bi = DataSerializer.readObject(dis);
            float f = dis.readFloat();
            double d = dis.readDouble();
            BigDecimal bd = DataSerializer.readObject(dis);
            String str = dis.readUTF();

            simple = new Simple(b, s, i, l, bi, f, d, bd, str);

        } catch (IOException e) {
            e.fillInStackTrace();
        } catch (ClassNotFoundException e) {
            e.fillInStackTrace();
        }

        return simple;
    }
}
