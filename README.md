# Jackson Deserizalization demo

Showcases three ways to inject objects into deserialized JSON models.

See [AnalyzerDeserializerTest](./test/java/AnalyzerDeserializerTest.java) for details.

1\. inject an object into a custom Deserializer and use that object to construct the intended model (uses Guice)
2\. inject an object into the model and obtain the indented type via a method (uses Guice)
3\. use [polymorphic deserialization](https://github.com/FasterXML/jackson-docs/wiki/JacksonPolymorphicDeserialization) to inject the desired object directly into the model
