package memtest;

import memtest.domain.Simple;
import memtest.domain.SimplePacked;
import memtest.serializerfunctions.AbstractSerializer;
import memtest.serializerfunctions.HBaseValueEncoder;
import org.openjdk.jol.info.ClassLayout;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class EncodeSimpleTypesTest {

    private static AbstractSerializer as = new HBaseValueEncoder();

    private static void test(Object o, byte[] hba) throws IOException {
        byte[] kba = as.serialize(o);

        System.out.println(o.getClass().getName());
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        if(o.getClass() == BigInteger.class) {
            BigInteger bi = (BigInteger)o;
            System.out.println(ClassLayout.parseInstance(bi.toByteArray()).toPrintable());
        }
        else if(o.getClass() == BigDecimal.class) {
            BigDecimal bd = (BigDecimal)o;
            System.out.println(ClassLayout.parseInstance(bd.unscaledValue()).toPrintable());
            System.out.println(ClassLayout.parseInstance(bd.unscaledValue().toByteArray()).toPrintable());
        }

        System.out.println("Kryo Length=" + kba.length);
        System.out.println(ClassLayout.parseInstance(kba).toPrintable());

        System.out.println("Hbase Length=" + hba.length);
        System.out.println(ClassLayout.parseInstance(hba).toPrintable());
    }

    public static void main(String[] args) throws IOException {

        byte b = 124;
//        test(b, pu.byteToBytes(b));

        short s = 1;
//        test(s, pu.shortToBytes(s));

        int i = 1234523453;
//        test(i, pu.intToBytes(i));

        long l = 1123412341234211234L;
//        test(l, pu.longToBytes(l));

        float f = 1.0f;
//        test(f, pu.floatToBytes(f));

        double d = 1.0;
//        test(d, pu.doubleToBytes(d));

        BigInteger bi = new BigInteger("112341234112341234123421421342341234231423412341");
        test(bi, as.serialize(bi));

        BigDecimal bd = new BigDecimal("1.12341234112341234123421421342341234231423412341");
        test(bd, as.serialize(bd));

        String str = "sdfgsdfgsdfgsdfgs";
        test(str, as.serialize(str));

        Simple simple = new Simple(b, s, i, l, bi, f, d, bd, str);
        SimplePacked simplePacked = new SimplePacked(simple);
        test(simple, simplePacked.getPayload());
    }

}
