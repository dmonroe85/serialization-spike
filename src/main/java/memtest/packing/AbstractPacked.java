package memtest.packing;

import memtest.common.Util;
import memtest.serializerfunctions.AbstractSerializer;
import memtest.serializerfunctions.HBaseValueEncoder;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public abstract class AbstractPacked {

    // List of your field types in order
    protected Class[] classes;

    // Implement this and call it from the constructor.
    public abstract void setClasses();

    public AbstractPacked() {
        setClasses();
    }

    private static int DataLengthSize = 2;
    private static AbstractSerializer serializer = new HBaseValueEncoder();

    // Serialization methods
    public byte[] serialize(Object o)
            throws IOException {
        Class cls = o.getClass();
        Integer fixedLength = serializer.getFixedLength(cls);
        byte[] serialized = serializer.serialize(o);

        if (fixedLength == null) {
            serialized = Util.prependLength(serialized);
        }

        return serialized;
    }

    private Object deserialize(byte[] b, Class cls)
            throws IOException, ClassNotFoundException {
        return serializer.deserialize(b, cls);
    }



    // Data stored
    private byte[] payload;

    // Get / Set
    protected void setPayload(byte[] newPayload) { payload = newPayload; }
    protected byte[] getPayload() { return payload; }


    // Unpacking Methods
    private int lengthFromPayload(byte[] payload, int offset) {
        byte[] sizeSlice =
                Util.byteArraySlice(
                        payload,
                        offset,
                        offset + DataLengthSize
                );

        return Bytes.toShort(sizeSlice);
    }


    protected Object dataFromPayload(int fieldIndex) throws IOException, ClassNotFoundException {
        int currentOffset = 0;
        int dataStart;
        int dataEnd;
        Object data = null;

        for (int i = 0; i <= fieldIndex; i++) {
            Class fieldClass = classes[i];
            Integer dataLength = serializer.getFixedLength(fieldClass);
            dataStart = currentOffset;

            if (dataLength == null) {
                dataLength = lengthFromPayload(payload, currentOffset);
                dataStart += DataLengthSize;
            }

            dataEnd = dataStart + dataLength;

            if (i == fieldIndex) {
                data = deserialize(
                        Util.byteArraySlice(payload, dataStart, dataEnd),
                        fieldClass
                );
            }

            currentOffset = dataEnd;
        }

        return data;
    }

}
