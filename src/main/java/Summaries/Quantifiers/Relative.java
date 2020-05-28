package Summaries.Quantifiers;

import lombok.Getter;
import lombok.Setter;
import net.sourceforge.jFuzzyLogic.FIS;
import utils.Constants;


@Getter
@Setter
public class Relative implements IQuantifier {

    private FIS fis;
    private String term;

    public Relative(FIS fis, String term) {
        this.fis = fis;
        this.term = term;
    }

    public double quantify(double value) {
        fis.setVariable(Constants.RELATIVE_VAR_NAME, value);
        return fis.getVariable(Constants.RELATIVE_VAR_NAME).getMembership(term);
    }

}
