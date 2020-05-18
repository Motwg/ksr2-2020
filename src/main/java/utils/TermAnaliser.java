package utils;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.util.List;

public class TermAnaliser {
    private FIS fis;

    public TermAnaliser(FIS fis){
        this.fis = fis;
    }

    public double countSupp(String term) {
        List<String> vars = Consts.ALL_VAR_NAMES;
        for (String var: vars) {
            try {
                if(var.contains("temperature"))
                    return FunctionAnalyser.getSupp(
                            fis.getVariable(var + "_spring").getMembershipFunction(term)
                    );
                else
                    return FunctionAnalyser.getSupp(fis.getVariable(var).getMembershipFunction(term));
            } catch (Exception e) {}
        }
        return 0;
    }

    public double countIn(String term) {
        List<String> vars = Consts.ALL_VAR_NAMES;
        Variable var;
        double dim;
        for (String varName: vars) {
            try {
                if(varName.contains("temperature"))
                    var = fis.getVariable(varName + "_spring");
                else
                    var = fis.getVariable(varName);
                dim = var.getUniverseMax() - var.getUniverseMin();
                double value = FunctionAnalyser.getSupp(var.getMembershipFunction(term)) / dim;
                if (Double.isNaN(value))
                    System.out.println("var: " + varName + "  value: " + value);
                return value;
            } catch (Exception e) {}
        }
        return 0;
    }

    public double countX(String term) {
        List<String> vars = Consts.ALL_VAR_NAMES;
        Variable var;
        for (String varName: vars) {
            try {
                if(varName.contains("temperature"))
                    var = fis.getVariable(varName + "_spring");
                else
                    var = fis.getVariable(varName);
                return var.getUniverseMax() - var.getUniverseMin();
            } catch (Exception e) {}
        }
        return 0;
    }

    public double countQSupp(String term) {
        List<String> vars = Consts.ALL_VAR_NAMES;
        for (String var: vars) {
            try {
                if(var.contains("temperature"))
                    return FunctionAnalyser.getQSupp(
                            fis.getVariable(var + "_spring").getMembershipFunction(term)
                    );
                else
                    return FunctionAnalyser.getQSupp(fis.getVariable(var).getMembershipFunction(term));
            } catch (Exception e) {}
        }
        return 0;
    }
}
