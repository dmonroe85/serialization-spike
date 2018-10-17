package memtest.serializerfunctions;

import org.apache.geode.DataSerializer;

import java.io.*;

public class DataSerializerWholeObject implements SerializerInterface {
    @Override
    public byte[] serialize(Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            DataSerializer.writeObject(object, dos);

            dos.flush();
            dos.close();
        } catch (IOException e) {
            e.fillInStackTrace();
        }

        return baos.toByteArray();
    }

    @Override
    public Object deserialize(byte[] ba, Class cls) {
        Object object = null;

        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        try {
            object = DataSerializer.readObject(dis);
        } catch (IOException e) {
            e.fillInStackTrace();
        } catch (ClassNotFoundException e) {
            e.fillInStackTrace();
        }

        return object;
    }
}
