import analyzer.Analyzer;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PojoWithInjectedRegistry {
    @JacksonInject
    Registry myRegistry;

    @JsonProperty("analyzer")
    String analyzer;

    @JsonIgnore
    public Analyzer getAnalyzer() {
        return myRegistry.getAnalyzer(analyzer);
    }
}
