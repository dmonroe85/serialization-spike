package memtest.serializerfunctions;

import memtest.domain.Simple;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class SerializerInterfaceTest {

    private void serializedPrimitiveTest(SerializerInterface si) {
        BigDecimal data = new BigDecimal("1234123423452345345645692378509872349583270958723094857.01");

        byte[] serialized = si.serialize(data);

        assertEquals(
                data,
                si.deserialize(serialized, BigDecimal.class)
        );
    }

    private void serializedObjectTest(SerializerInterface si) {
        Simple s = new Simple();
        assertEquals(
                s,
                si.deserialize(si.serialize(s), Simple.class)
        );
    }

    @Test
    public void testPrimitives() {
        serializedPrimitiveTest(new DataSerializerWholeObject());
        serializedPrimitiveTest(new JavaSerializerWholeObject());
        serializedPrimitiveTest(new KryoSerializer());
        serializedPrimitiveTest(new PdxSerializerWholeObject());
    }

    @Test
    public void testObject() {
        serializedObjectTest(new DataSerializerSimpleByField());
        serializedObjectTest(new DataSerializerWholeObject());
        serializedObjectTest(new JavaSerializerSimpleByField());
        serializedObjectTest(new JavaSerializerWholeObject());
        serializedObjectTest(new KryoSerializer());
        serializedObjectTest(new PdxSerializerSimpleByField());
        serializedObjectTest(new PdxSerializerWholeObject());
//        serializedObjectTest(new AvroSerializerSimple());
    }


}