import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import poly.ContainerObject;
import poly.SubClassA;
import poly.SubClassB;

public class PolymorphicDeserializationTest {

  @Test
  public void testPolymorphicDeserialization() throws IOException {
    // ARRANGE

    ObjectMapper mapper = new ObjectMapper();

    // register subtypes
    mapper.registerSubtypes(SubClassA.class, SubClassB.class);

    // read the data
    InputStream is = this.getClass().getResourceAsStream("/example-three.json");

    // ACT
    ContainerObject pojos = mapper.readValue(is, ContainerObject.class);

    // ASSERT
    assertThat(pojos, notNullValue());
    assertThat(pojos.supers.entrySet(), hasSize(greaterThan(0)));
    assertThat(pojos.supers.get("canBeEitherButIsA"), instanceOf(SubClassA.class));
    assertThat(pojos.supers.get("canBeEitherButIsB"), instanceOf(SubClassB.class));
    assertThat(pojos.onlyA, instanceOf(SubClassA.class));
  }
}
