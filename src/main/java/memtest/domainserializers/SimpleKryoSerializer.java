package memtest.domainserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import memtest.domain.Simple;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SimpleKryoSerializer extends Serializer <Simple>{

    @Override
    public void write(Kryo kryo, Output output, Simple object) {

        output.writeByte(object.getByte());
        output.writeShort(object.getShort());
        output.writeInt(object.getInt());
        output.writeLong(object.getLong());
        kryo.writeObject(output, object.getBigInteger());

        output.writeFloat(object.getFloat());
        output.writeDouble(object.getDouble());
        kryo.writeObject(output, object.getBigDecimal());

        output.writeString(object.getString());
    }

    @Override
    public Simple read(Kryo kryo, Input input, Class<? extends Simple> type) {
        byte b = input.readByte();
        short s = input.readShort();
        int i = input.readInt();
        long l = input.readLong();
        BigInteger bi = kryo.readObject(input, BigInteger.class);

        float f = input.readFloat();
        double d = input.readDouble();
        BigDecimal bd = kryo.readObject(input, BigDecimal.class);

        String str = input.readString();

        return new Simple(b, s, i, l, bi, f, d, bd, str);
    }

}
