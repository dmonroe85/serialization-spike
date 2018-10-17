# serialization-spike

## Basic Build Commands

`gradle build`

`gradle test`

## Main Methods

### EncodeSimpleTypesTest

The purpose of this was to profile the memory usage of basic data types and measure their serialized size.  This compares Kryo serialization and HBase byte encoding.

### LocalSerializerMain

This main evaluates several different types of serialization including PDX, Avro, Java, Kryo, HBase Encoding, and Gemfire DataSerializable.

### WriteToGeodeMain

Writes `Simple` (src/main/java/memtest/domain/Simple.java) objects to a local geode instance for testing.

#### Geode Testing

Need to set up Geode and create a writable region that can measure memory utilization.

1. [Download and setup](https://geode.apache.org/docs/guide/latest/prereq_and_install.html) Geode to match the `build.gradle` version.
2. From the `gfsh` utility
    * `start locator`
    * `start server`
    * `start pulse`
    * `create region --name hello --type=PARTITION`
3. Run the `WriteToGeodeMain` class and try changing the serializer type (located in `memtest.serializerfunctions`)
4. Check the region status in [pulse](http://localhost:7070/pulse/regionDetail.html?regionFullPath=/hello)

## Python?

This is a script that has the scaffolding to read in a template and build out large classes requiring a lot of boilerplate code.  Work in progress.