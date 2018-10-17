package memtest.serializerfunctions;

import java.io.*;

public class JavaSerializerWholeObject implements SerializerInterface {
    @Override
    public byte[] serialize(Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.close();
        } catch (IOException e) {
            e.fillInStackTrace();
        }

        return baos.toByteArray();
    }

    @Override
    public Object deserialize(byte[] ba, Class cls) {
        Object object = null;

        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(ba));
            object = ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.fillInStackTrace();
        } catch (ClassNotFoundException e) {
            e.fillInStackTrace();
        }

        return object;
    }
}
