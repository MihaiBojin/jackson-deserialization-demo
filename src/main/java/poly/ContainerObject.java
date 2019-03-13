package poly;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NONE;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Map;

public class ContainerObject {
  public Map<String, SuperClass> supers;

  @JsonTypeInfo(use=NONE)
  public SubClassA onlyA;
}
