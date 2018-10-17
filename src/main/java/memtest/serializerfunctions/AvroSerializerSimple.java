package memtest.serializerfunctions;

import memtest.common.Util;
import memtest.domain.Simple;
import memtest.domainserializers.AvroSchemas;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;
import org.apache.avro.util.Utf8;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class AvroSerializerSimple extends AbstractSerializer {

    private Schema simpleSchema = AvroSchemas.simpleSchema();

    @Override
    public byte[] serialize(Object object) throws IOException {
        Simple simple = (Simple)object;

        GenericRecord payload = new GenericData.Record(simpleSchema);

        payload.put("b", simple.getByte());
        payload.put("s", simple.getShort());
        payload.put("i", simple.getInt());
        payload.put("l", simple.getLong());
        payload.put("bi", ByteBuffer.wrap(Util.bigIntegerToBytes(simple.getBigInteger())));

        payload.put("f", simple.getFloat());
        payload.put("d", simple.getDouble());
        payload.put("bd", ByteBuffer.wrap(Util.bigDecimalToBytes(simple.getBigDecimal())));

        payload.put("str", simple.getString());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(baos, null);
        DatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(simpleSchema);

        writer.write(payload, encoder);
        encoder.flush();
        baos.close();

        return baos.toByteArray();
    }

    @Override
    public Object deserialize(byte[] ba, Class cls) throws IOException, ClassNotFoundException {
        GenericRecord payload = new GenericData.Record(simpleSchema);
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bais, null);
        DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(simpleSchema);

        reader.read(payload, decoder);

        byte b = ((Integer)payload.get("b")).byteValue();
        short s = ((Integer)payload.get("s")).shortValue();
        int i = (Integer)payload.get("i");
        long l = (Long)payload.get("l");
        BigInteger bi = Util.bytesToBigInteger(((ByteBuffer)payload.get("bi")).array());

        float f = (Float)payload.get("f");
        double d = (Double)payload.get("d");
        BigDecimal bd = Util.bytesToBigDecimal(((ByteBuffer)payload.get("bd")).array());

        String str = ((Utf8)payload.get("str")).toString();

        Simple simple = new Simple(b, s, i, l, bi, f, d, bd, str);

        bais.close();

        return simple;

    }
}
