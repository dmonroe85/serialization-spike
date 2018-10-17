package memtest.serializerfunctions;

import memtest.domain.Simple;
import memtest.domainserializers.AvroSchemas;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AvroSerializerSimple implements SerializerInterface {

    private Schema simpleSchema = AvroSchemas.simpleSchema();

    @Override
    public byte[] serialize(Object object) {
        Simple simple = (Simple)object;

        GenericRecord payload = new GenericData.Record(simpleSchema);

        payload.put("b", simple.getByte());
        payload.put("s", simple.getShort());
        payload.put("i", simple.getInt());
        payload.put("l", simple.getLong());
        payload.put("bi", simple.getBigInteger().toByteArray());

        payload.put("f", simple.getFloat());
        payload.put("d", simple.getDouble());
        payload.put("bd", simple.getBigDecimal());

        payload.put("str", simple.getString());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(baos, null);
        DatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(simpleSchema);

        try {
            writer.write(payload, encoder);
            encoder.flush();
            baos.close();
        }
        catch (IOException e) {}

        return baos.toByteArray();
    }

    @Override
    public Object deserialize(byte[] ba, Class cls) {
        return null;
    }
}
