package memtest.serializerfunctions;

import java.io.*;

public class JavaSerializerByReflection extends AbstractSerializer {

    @Override
    public byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.close();

        return baos.toByteArray();
    }

    @Override
    public Object deserialize(byte[] ba, Class cls) throws IOException, ClassNotFoundException {
        Object object = null;

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(ba));
        object = ois.readObject();
        ois.close();

        return object;
    }
}
