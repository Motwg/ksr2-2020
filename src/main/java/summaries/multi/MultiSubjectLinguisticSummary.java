package summaries.multi;

import java.util.List;

public interface MultiSubjectLinguisticSummary {
    double t1();
    double overallT(List<Double> weights);
}
