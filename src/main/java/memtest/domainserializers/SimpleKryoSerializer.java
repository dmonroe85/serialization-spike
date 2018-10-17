package memtest.domainserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import memtest.domain.Simple;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SimpleKryoSerializer extends Serializer <Simple>{

    boolean verbose = false;

    @Override
    public void write(Kryo kryo, Output output, Simple object) {
        int last = 0;
        int current = 0;

        current = output.position();
//        System.out.println(current - last);
        last = current;

        output.writeByte(object.getByte());
        current = output.position();
        if (verbose) {
            System.out.println(current - last);
        }
        last = current;

        output.writeShort(object.getShort());
        current = output.position();
        if (verbose) {
            System.out.println(current - last);
        }
        last = current;

        output.writeInt(object.getInt());
        current = output.position();
        if (verbose) {
            System.out.println(current - last);
        }
        last = current;

        output.writeLong(object.getLong());
        current = output.position();
        if (verbose) {
            System.out.println(current - last);
        }
        last = current;

        kryo.writeObject(output, object.getBigInteger());
        current = output.position();
        if (verbose) {
            System.out.println(current - last);
        }
        last = current;

        output.writeFloat(object.getFloat());
        current = output.position();
        if (verbose) {
            System.out.println(current - last);
        }
        last = current;

        output.writeDouble(object.getDouble());
        current = output.position();
        if (verbose) {
            System.out.println(current - last);
        }
        last = current;

        kryo.writeObject(output, object.getBigDecimal());
        current = output.position();
        if (verbose) {
            System.out.println(current - last);
        }
        last = current;

        output.writeString(object.getString());
        current = output.position();
        if (verbose) {
            System.out.println(current - last);
        }
        last = current;
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
