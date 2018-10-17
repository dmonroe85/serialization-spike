package memtest.serializerfunctions;

import memtest.domain.SimplePacked;

public class SimplePackedSerializer implements SerializerInterface {

    @Override
    public byte[] serialize(Object object) {
        SimplePacked sp = (SimplePacked)object;
        return sp.getPayload();
    }

    @Override
    public Object deserialize(byte[] ba, Class cls) {

        return new SimplePacked(ba);
    }

}
