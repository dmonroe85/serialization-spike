package memtest;

import memtest.domain.Simple;
import memtest.domain.SimplePacked;
import memtest.serializerfunctions.*;

import java.io.IOException;

public class LocalSerializerMain {

    private static void testSerializer(Object value,
                                       AbstractSerializer serializer) throws IOException {
        byte[] serialized = serializer.serialize(value);
        System.out.println(serializer.getClass().getName());
        System.out.println("Length: " + serialized.length + " bytes");
        System.out.println();

    }

    public static void main(String[] args) throws IOException {
        System.out.println("Testing Serialization Libraries");
        Simple s = new Simple();

        testSerializer(s, new JavaSerializerByReflection());
        testSerializer(s, new JavaSerializerSimpleByField());

        testSerializer(s, new PdxSerializerByReflection());
        testSerializer(s, new PdxSerializerSimpleByField());

        testSerializer(s, new DataSerializerByReflection());
        testSerializer(s, new DataSerializerSimpleByField());

        testSerializer(s, new AvroSerializerSimple());

        testSerializer(s, new KryoSerializer());

        System.out.println("Testing Manual Packing Serialization");
        SimplePacked sp = new SimplePacked(s);
        testSerializer(sp, new SimplePackedSerializer());
    }

}
