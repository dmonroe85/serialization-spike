package memtest.domainserializers;

import com.google.gson.JsonParser;
import org.apache.avro.Schema;

import java.io.File;
import java.io.IOException;

public class AvroSchemas {

    private static JsonParser parser = new JsonParser();

    private static Schema loadSchema(String schemaFile) {
        Schema schema = null;

        File file = new File(schemaFile);

        try {
            schema = new Schema.Parser().parse(file);
        }
        catch (IOException e) {
            System.out.println(e.toString());
        }

        return schema;
    }

    public static Schema simpleSchema() {
        return loadSchema("./src/main/resources/simple.json");
    }

    public static void main(String args[]) {
        System.out.println(simpleSchema());
    }

}