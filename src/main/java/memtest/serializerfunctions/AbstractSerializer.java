package memtest.serializerfunctions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSerializer {

    abstract public byte[] serialize(Object object) throws IOException;

    abstract public Object deserialize(byte[] ba, Class cls) throws IOException, ClassNotFoundException;

    private Map<Class, Integer> fixedSizeMap = new HashMap<Class, Integer>();

    public void addFixedLength(Class key, int value){
        fixedSizeMap.put(key, value);
    }

    public Integer getFixedLength(Class key) {
        return fixedSizeMap.get(key);
    }

}
