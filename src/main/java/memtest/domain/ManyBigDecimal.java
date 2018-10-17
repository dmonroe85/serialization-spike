package memtest.domain;

import memtest.packing.PackingUtil;
import memtest.common.Util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Random;

public class ManyBigDecimal implements Serializable {

    private Random r = new Random();

    private BigDecimal bd = new BigDecimal(r.nextDouble());
    private String str =
            Util.newRandomString((byte)r.nextInt(Byte.MAX_VALUE));

    public BigDecimal getBigDecimal() { return bd; }
    public String getString() { return str; }

    public ManyBigDecimal() {}
    public ManyBigDecimal(BigDecimal bd_,
                          String str_) {
        bd = bd_;
        str = str_;
    }

    public byte[] buildPayload(PackingUtil pu) {
        byte[] payload = new byte[] {};

        payload = Util.concatenateByteArrays(
                payload,
                pu.serializeWithLength(getBigDecimal())
        );

        payload = Util.concatenateByteArrays(
                payload,
                pu.serializeWithLength(getString())
        );

        return payload;
    }

    @Override
    public int hashCode() { return super.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }

        if (!(o.getClass() == Simple.class)) { return false; }

        Simple that = (Simple)o;

        return (
                getBigDecimal().equals(that.getBigDecimal()) &
                getString().equals(that.getString())
        );
    }

    @Override
    public String toString() {
        return "(" + bd + "," + str + ")";
    }
}
