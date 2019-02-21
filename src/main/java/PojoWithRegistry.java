import analyzer.Analyzer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class PojoWithRegistry {
    @JsonDeserialize(using = AnalyzerDeserializer.class)
    Analyzer analyzer;
}
