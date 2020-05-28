package utils;

import net.sourceforge.jFuzzyLogic.membership.MembershipFunction;

public class FunctionAnalyser {

    private static String mapPieceWiseLine(int length) {
        switch (length) {
            case 6: return "Triangle";
            case 8: return "Trapetzoidal";
            case 16: return "8";
            default: return "Custom";
        }
    }

    // nosnik
    public static double getSupp(MembershipFunction mf) {
        String fName;
        int m = 2;
        if(mf.getName().compareTo("PieceWiseLinear") == 0)
            fName = mapPieceWiseLine(mf.getParametersLength());
        else {
            m = 1;
            fName = mf.getName();
        }
        switch (fName)
        {
            case "Trapetzoidal": return mf.getParameter(3*m) - mf.getParameter(0*m);
            case "Triangle": return mf.getParameter(2*m) - mf.getParameter(0*m);
            case "8": return mf.getParameter(3*m) - mf.getParameter(0*m) +
                            mf.getParameter(7*m) - mf.getParameter(4*m);
            case "Gaussian":
                return mf.getParameter(0) * 2;
            case "Sigma":
                return Math.abs(mf.getParameter(0));
            default:
                System.out.println("Not implemented in getSupp: " + fName);
                return 0;
        }
    }
    // pole
    public static double getQSupp(MembershipFunction mf) {
        String fName;
        int m = 2;
        if(mf.getName().compareTo("PieceWiseLinear") == 0)
            fName = mapPieceWiseLine(mf.getParametersLength());
        else {
            m = 1;
            fName = mf.getName();
        }
        switch (fName)
        {
            case "Trapetzoidal": return (mf.getParameter(3*m) - mf.getParameter(0*m) +
                    mf.getParameter(2*m) - mf.getParameter(1*m)) / 2.0;
            case "Triangle": return (mf.getParameter(2*m) - mf.getParameter(0*m)) / 2.0;
            case "8": return
                    (mf.getParameter(3*m) - mf.getParameter(0*m) +
                    mf.getParameter(2*m) - mf.getParameter(1*m)) / 2.0 +
                    (mf.getParameter(7*m) - mf.getParameter(4*m) +
                    mf.getParameter(6*m) - mf.getParameter(5*m)) / 2.0;
            case "Gaussian":
            case "Sigma": return 1;
            default:
                System.out.println("Not implemented in getQSupp: " + fName);
                return 0;
        }
    }
}
