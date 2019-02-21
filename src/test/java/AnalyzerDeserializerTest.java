import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;

import analyzer.AnotherAnalyzer;
import analyzer.DefaultAnalyzer;
import analyzer.SomeAnalyzer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;

class AnalyzerDeserializerTest {
    Injector injector;

    @Test
    void deserializationWithInjectedRegistryObject() throws IOException {
        // ARRANGE

        // configure Guice
        injector = Guice.createInjector(
                binder -> {
                    // initialize your registry object; this can also be done via a Guice Provider
                    // the advantage of using this method is that you have full control over the initialization of analyzer objects
                    final Registry registry = new Registry(new DefaultAnalyzer(), new AnotherAnalyzer());
                    binder.bind(Registry.class).toInstance(registry); // bind the registry as a singleton

                    // configure static injection and bind the deserializer as a singleton
                    binder.requestStaticInjection(AnalyzerDeserializer.class);
                    binder.bind(AnalyzerDeserializer.class).asEagerSingleton();
                }
        );

        // read the data
        InputStream is = this.getClass().getResourceAsStream("/example-one.json");

        // ACT
        ObjectMapper mapper = injector.getInstance(ObjectMapper.class);

        List<PojoWithResolvedAnalyzer> pojos = mapper.readValue(is, new TypeReference<List<PojoWithResolvedAnalyzer>>() {
        });

        // ASSERT
        assertThat(pojos, hasSize(2));
        assertThat(pojos.get(0).analyzer, instanceOf(DefaultAnalyzer.class));
        assertThat(pojos.get(1).analyzer, instanceOf(AnotherAnalyzer.class));
    }

    @Test
    void deserializationWithInjectedRegistryObjectStraightIntoThePojo() throws IOException {
        // ARRANGE

        // configure Guice
        injector = Guice.createInjector(
                new ObjectMapperModule(),
                binder -> {
                    // initialize your registry object; this can also be done via a Guice Provider
                    // the advantage of using this method is that you have full control over the initialization of analyzer objects
                    final Registry registry = new Registry(new DefaultAnalyzer(), new AnotherAnalyzer());
                    binder.bind(Registry.class).toInstance(registry); // bind the registry as a singleton

                    // no deserializers are required
                    // the registry object will be injected directly into the POJO and can be used via the getAnalyzer() method
                }
        );

        // read the data
        InputStream is = this.getClass().getResourceAsStream("/example-one.json");

        // ACT
        ObjectMapper mapper = injector.getInstance(ObjectMapper.class);

        List<PojoWithInjectedRegistry> pojos = mapper.readValue(is, new TypeReference<List<PojoWithInjectedRegistry>>() {
        });

        // ASSERT
        assertThat(pojos, hasSize(2));
        assertThat(pojos.get(0).getAnalyzer(), instanceOf(DefaultAnalyzer.class));
        assertThat(pojos.get(1).getAnalyzer(), instanceOf(AnotherAnalyzer.class));
    }

    @Test
    void polymorphicDeserialization() throws IOException {
        // ARRANGE

        // register the analyzers so that the object mapper is aware of them and can properly deserialize
        ObjectMapper mapper = new ObjectMapper();

        // one advantage of this method is that it doesn't require Guice
        // one disadvantage of this method is that Jackson will need to be able to initialize the analyzer objects
        // this will most likely mean you will need default constructors, or pass all parameters from the JSON definition (e.g. @JsonCreator annotated constructors)

        // register a type with a dynamic name
        mapper.registerSubtypes(new NamedType(SomeAnalyzer.class, "SomeAnalyzer"));
        // register a type annotated with @JsonTypeName
        mapper.registerSubtypes(AnotherAnalyzer.class);

        // read the data
        InputStream is = this.getClass().getResourceAsStream("/example-two.json");

        // ACT
        List<PojoWithPolymorphicSubtype> pojos = mapper.readValue(is, new TypeReference<List<PojoWithPolymorphicSubtype>>() {
        });

        // ASSERT
        assertThat(pojos, hasSize(2));
        assertThat(pojos.get(0).analyzer, instanceOf(SomeAnalyzer.class));
        assertThat(pojos.get(1).analyzer, instanceOf(AnotherAnalyzer.class));
    }
}
