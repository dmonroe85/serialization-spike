package memtest.serializerfunctions;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.*;
import memtest.domain.Simple;
import memtest.domainserializers.SimpleKryoSerializer;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class KryoSerializer implements SerializerInterface {
    private Kryo kryo;

    public KryoSerializer()  {
        kryo = new Kryo();
        kryo.register(short.class, new ShortSerializer());
        kryo.register(BigInteger.class, new BigIntegerSerializer());
        kryo.register(BigDecimal.class, new BigDecimalSerializer());
        kryo.register(Simple.class, new SimpleKryoSerializer());
    }

    @Override
    public byte[] serialize(Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, object);
        output.flush();
        output.close();
        return baos.toByteArray();
    }

    @Override
    public Object deserialize(byte[] ba, Class cls) {
        Input input = new Input(ba);
        Object value = kryo.readObject(input, cls);
        input.close();
        return value;
    }
}
