
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.io.File;

/**
 * Test parsing an FCL file
 * @author pcingola@users.sourceforge.net
 * http://jfuzzylogic.sourceforge.net/html/manual.html
 */
public class FuzzyLogicExample {
    public static void main(String[] args) {
        // Load from 'FCL' file
        String fileName = "files" + File.separator + "tipper.fcl";
        FIS fis = FIS.load(fileName,true);
        // Error while loading?
        if( fis == null ) {
            System.err.println("Can't load file: '"
                    + fileName + "'");
            return;
        }

        // Show
        JFuzzyChart.get().chart(fis);
        //fis.chart();

        // Set inputs
        fis.setVariable("service", 3);
        fis.setVariable("food", 7);

        // Evaluate
        fis.evaluate();

        // Show output variable's chart 
        //fis.getVariable("tip").chartDefuzzifier(true);
        Variable tip = fis.getVariable("tip");
        JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);

        // Print ruleSet
        System.out.println(fis);
    }
}