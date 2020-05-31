package summaries.quantifiers;

import lombok.Getter;
import lombok.Setter;
import net.sourceforge.jFuzzyLogic.FIS;
import utils.Constants;

@Getter
@Setter
public class Absolute implements IQuantifier {

    private FIS fis;
    private String term;

    public Absolute(FIS fis, String term) {
        this.fis = fis;
        this.term = term;
    }

    public double quantify(double value) {
        fis.setVariable(Constants.ABSOLUTE_VAR_NAME, value);
        return fis.getVariable(Constants.ABSOLUTE_VAR_NAME).getMembership(term);
    }
}
