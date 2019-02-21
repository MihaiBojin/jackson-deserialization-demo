import analyzer.Analyzer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class PojoWithResolvedAnalyzer {
    @JsonDeserialize(using = AnalyzerDeserializer.class)
    Analyzer analyzer;
}
