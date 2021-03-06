package memtest;

import memtest.domain.Simple;
import memtest.domain.SimplePacked;
import memtest.serializerfunctions.*;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.openjdk.jol.info.ClassLayout;

import java.io.IOException;

public class WriteToGeodeMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int numObjects = 100000;

        AbstractSerializer serializer = new SimplePackedSerializer();

        ClientCache cache = new ClientCacheFactory()
                .addPoolLocator("localhost", 10334)
                .create();

        Region<String, byte[]> region = cache
                .<String, byte[]>createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY_HEAP_LRU)
                .create("hello2");

        long dataSize = 0;

        for (int i = 0; i < numObjects; i ++) {
            Simple s = new Simple();
            SimplePacked sp = new SimplePacked(s);

            byte[] ba = serializer.serialize(sp);
            region.put(String.valueOf(i), ba);

            byte[] queried = region.get(String.valueOf(i));
            SimplePacked reconstructed = (SimplePacked)serializer.deserialize(queried, SimplePacked.class);

            if (!sp.equals(reconstructed)) {
                System.out.println("Unequal!");
            }

            dataSize += ClassLayout.parseInstance(s).instanceSize();
            dataSize += ClassLayout.parseInstance(s.getBigInteger()).instanceSize();
            dataSize += ClassLayout.parseInstance(s.getBigInteger().toByteArray()).instanceSize();

            dataSize += ClassLayout.parseInstance(s.getBigDecimal()).instanceSize();
            dataSize += ClassLayout.parseInstance(s.getBigDecimal().unscaledValue()).instanceSize();
            dataSize += ClassLayout.parseInstance(s.getBigDecimal().unscaledValue().toByteArray()).instanceSize();

            dataSize += ClassLayout.parseInstance(s.getString()).instanceSize();
            dataSize += ClassLayout.parseInstance(s.getString().toCharArray()).instanceSize();
        }

        System.out.println(((double)dataSize) / (1024.0 * 1024.0));

        cache.close();
    }

}
