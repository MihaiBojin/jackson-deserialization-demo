import analyzer.PolymorphicAnalyzer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PojoWithPolymorphicSubtype {
    public PolymorphicAnalyzer analyzer;
}
