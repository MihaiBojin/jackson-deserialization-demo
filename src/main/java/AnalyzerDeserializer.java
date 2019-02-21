import analyzer.Analyzer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.inject.Inject;
import java.io.IOException;

public class AnalyzerDeserializer extends JsonDeserializer<Analyzer> {
    @Inject
    private static Registry myRegistry;

    public Analyzer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String analyzerNameFromJson = jsonParser.getValueAsString();
        return myRegistry.getAnalyzer(analyzerNameFromJson);
    }
}
