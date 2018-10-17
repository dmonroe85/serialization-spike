package memtest;

import memtest.domain.Simple;
import memtest.serializerfunctions.AbstractSerializer;
import memtest.serializerfunctions.KryoSerializer;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;

import java.io.IOException;

public class WriteToGeodeMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int numObjects = 10000;

        // Kryo: Starting Memory 72M + 94M, Ending Memory 130M + 313M
        AbstractSerializer serializer = new KryoSerializer();

        ClientCache cache = new ClientCacheFactory()
                .addPoolLocator("localhost", 10334)
                .create();

        Region<String, byte[]> region = cache
                .<String, byte[]>createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
                .create("hello");

        for (int i = 0; i < numObjects; i ++) {
            Simple s = new Simple();

            byte[] ba = serializer.serialize(s);
            region.put(String.valueOf(i), ba);
            byte[] queried = region.get(String.valueOf(i));

            Simple reconstructed = (Simple)serializer.deserialize(queried, Simple.class);

            if (!s.equals(reconstructed)) {
                System.out.println("Unequal!");
            }
        }

        cache.close();
    }

}
