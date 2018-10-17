package memtest.serializerfunctions;

import memtest.domain.Simple;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.*;

public class AbstractSerializerTest {

    private void serializedPrimitiveTest(AbstractSerializer si) throws IOException, ClassNotFoundException {
        byte b = 120;
        assertEquals(si.deserialize(si.serialize(b), byte.class), b);

        short s = 1024;
        assertEquals(si.deserialize(si.serialize(s), short.class), s);

        int i = 1024012;
        assertEquals(si.deserialize(si.serialize(i), int.class), i);

        long l = 10241234012L;
        assertEquals(si.deserialize(si.serialize(l), long.class), l);

        BigInteger bi = new BigInteger("123412151235123412131312312341234124121442423");
        assertEquals(si.deserialize(si.serialize(bi), BigInteger.class), bi);

        float f = 1.2f;
        assertEquals((Float)si.deserialize(si.serialize(f), float.class), f, .01f);

        double d = 1.2;
        assertEquals((Double)si.deserialize(si.serialize(d), double.class), d, .01);

        BigDecimal bd = new BigDecimal(bi, 10);
        assertEquals(si.deserialize(si.serialize(bd), BigDecimal.class), bd);

        String str = "alksdjfmn138hr 0192j3oialihdjvopzks  podinewq0934!@#$KL!J@#F()ß∂ƒƒœ∑´´£¡¢ƒ¡£¢";
        assertEquals(si.deserialize(si.serialize(str), String.class), str);
    }

    private void serializedObjectTest(AbstractSerializer si) throws IOException, ClassNotFoundException {
        Simple s = new Simple();
        assertEquals(
                s,
                si.deserialize(si.serialize(s), Simple.class)
        );
    }

    @Test
    public void testPrimitives() throws IOException, ClassNotFoundException {
        serializedPrimitiveTest(new DataSerializerByReflection());
        serializedPrimitiveTest(new JavaSerializerByReflection());
        serializedPrimitiveTest(new KryoSerializer());
        serializedPrimitiveTest(new PdxSerializerByReflection());
        serializedPrimitiveTest(new HBaseValueEncoder());
    }

    @Test
    public void testObject() throws IOException, ClassNotFoundException {
        serializedObjectTest(new DataSerializerSimpleByField());
        serializedObjectTest(new DataSerializerByReflection());
        serializedObjectTest(new JavaSerializerSimpleByField());
        serializedObjectTest(new JavaSerializerByReflection());
        serializedObjectTest(new KryoSerializer());
        serializedObjectTest(new PdxSerializerSimpleByField());
        serializedObjectTest(new PdxSerializerByReflection());
        serializedObjectTest(new AvroSerializerSimple());
    }


}