package memtest.serializerfunctions;

import org.apache.geode.pdx.internal.PdxInputStream;
import org.apache.geode.pdx.internal.PdxOutputStream;

public class PdxSerializerByReflection extends AbstractSerializer {
    @Override
    public byte[] serialize(Object object) {
        PdxOutputStream pos = new PdxOutputStream();
        pos.writeObject(object, false);
        return pos.toByteArray();
    }

    @Override
    public Object deserialize(byte[] ba, Class cls) {
        PdxInputStream pis = new PdxInputStream(ba);
        return pis.readObject();
    }
}
