package memtest;

import memtest.domain.Simple;
import memtest.domain.SimplePacked;
import memtest.serializerfunctions.*;

public class LocalSerializerMain {

    private static void testSerializer(Object value,
                                       SerializerInterface serializer) {
        byte[] serialized = serializer.serialize(value);
        System.out.println(serializer.getClass().getName());
        System.out.println("Length: " + serialized.length + " bytes");
        System.out.println();

    }

    public static void main(String[] args) {
        System.out.println("Testing Serialization Libraries");
        Simple s = new Simple();

        testSerializer(s, new JavaSerializerWholeObject());
        testSerializer(s, new JavaSerializerSimpleByField());

        testSerializer(s, new PdxSerializerWholeObject());
        testSerializer(s, new PdxSerializerSimpleByField());

        testSerializer(s, new DataSerializerWholeObject());
        testSerializer(s, new DataSerializerSimpleByField());

        testSerializer(s, new KryoSerializer());

        System.out.println("Testing Manual Packing Serialization");
        SimplePacked sp = new SimplePacked(s);
        testSerializer(sp, new SimplePackedSerializer());
    }

}
