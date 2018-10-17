package memtest.serializerfunctions;

import org.apache.geode.DataSerializer;

import java.io.*;

public class DataSerializerByReflection extends AbstractSerializer {

    @Override
    public byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        DataSerializer.writeObject(object, dos);

        dos.flush();
        dos.close();

        return baos.toByteArray();
    }

    @Override
    public Object deserialize(byte[] ba, Class cls) throws IOException, ClassNotFoundException {
        Object object = null;

        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        object = DataSerializer.readObject(dis);

        return object;
    }

}
