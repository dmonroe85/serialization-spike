package memtest.common;

public class ByteArray {

    private byte[] payload = new byte[]{};

    public void put(byte[] ba) {
        payload = Util.concatenateByteArrays(payload, ba);
    }

    public byte[] toByteArray() {
        return payload;
    }
}
