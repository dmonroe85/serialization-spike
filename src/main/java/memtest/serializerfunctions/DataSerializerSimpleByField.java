package memtest.serializerfunctions;

import memtest.common.Util;
import memtest.domain.Simple;
import org.apache.geode.DataSerializer;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;

public class DataSerializerSimpleByField extends AbstractSerializer {
    @Override
    public byte[] serialize(Object object) throws IOException {
        Simple simple = (Simple)object;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeByte(simple.getByte());
        dos.writeShort(simple.getShort());
        dos.writeInt(simple.getInt());
        dos.writeLong(simple.getLong());
        DataSerializer.writeByteArray(Util.bigIntegerToBytes(simple.getBigInteger()), dos);

        dos.writeFloat(simple.getFloat());
        dos.writeDouble(simple.getDouble());
        DataSerializer.writeByteArray(Util.bigDecimalToBytes(simple.getBigDecimal()), dos);

        dos.writeUTF(simple.getString());

        dos.flush();
        dos.close();

        return baos.toByteArray();
    }

    @Override
    public Object deserialize(byte[] ba, Class cls) throws IOException, ClassNotFoundException{
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        byte b = dis.readByte();
        short s = dis.readShort();
        int i = dis.readInt();
        long l = dis.readLong();
        BigInteger bi = Util.bytesToBigInteger(DataSerializer.readByteArray(dis));
        float f = dis.readFloat();
        double d = dis.readDouble();
        BigDecimal bd = Util.bytesToBigDecimal(DataSerializer.readByteArray(dis));
        String str = dis.readUTF();

        return new Simple(b, s, i, l, bi, f, d, bd, str);
    }
}
