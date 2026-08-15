package benchmark;

import controller.search.Condition;
import controller.search.Operator;
import model.corso.CorsoQuery;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class CorsoSearchBenchmark {

    private CorsoQuery query;
    private List<Condition> conditions;

    @Setup
    public void setup() {
        query = new CorsoQuery("corso");
        conditions = List.of(
                new Condition("nome", Operator.MATCH, "Java", "corso"),
                new Condition("prezzoBase", Operator.GE, "10", "corso"),
                new Condition("nomeCategoria", Operator.MATCH, "tecnologia", "corsoCategoria")
        );
    }

    @Benchmark
    public String benchmarkSearch() {
        return query.search(conditions);
    }
}
