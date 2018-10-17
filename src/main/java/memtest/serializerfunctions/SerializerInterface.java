package memtest.serializerfunctions;

public interface SerializerInterface {

    byte[] serialize(Object object);

    Object deserialize(byte[] ba, Class cls);

}
