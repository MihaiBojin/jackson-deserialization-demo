import analyzer.Analyzer;
import java.util.List;
import java.util.Objects;
import javax.inject.Singleton;

@Singleton
public class Registry {
    private final List<Analyzer> analyzers;

    public Registry(Analyzer... analyzers) {
        this.analyzers = List.of(analyzers);
    }

    public Analyzer getAnalyzer(final String name) {
        return this.analyzers.stream().filter(clz -> Objects.equals(clz.getClass().getSimpleName(), name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown analyzer: " + name));
    }
}
