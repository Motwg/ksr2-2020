package Readers;

import net.sourceforge.jFuzzyLogic.FIS;

import java.io.File;

public class FlcReader {
    public static FIS load(String filename) {
        // Load from 'FCL' file
        try {
            String fileName = "data" + File.separator + filename;
            FIS fis = FIS.load(fileName,true);

            if( fis == null ) {
                System.err.println("Can't load file: '"
                        + fileName + "'");
                throw new Exception();
            }
            init(fis);
            return fis;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void init(FIS fis) {
        fis.getVariable("dampness").setUniverseMax(100);
        fis.getVariable("dampness").setUniverseMin(0);
        fis.getVariable("day_time").setUniverseMin(0);
        fis.getVariable("day_time").setUniverseMax(24);
        fis.getVariable("percentage").setUniverseMax(100);
        fis.getVariable("percentage").setUniverseMin(0);
        fis.getVariable("absolute").setUniverseMax(18000);
        fis.getVariable("absolute").setUniverseMin(0);
        fis.getVariable("temperature_spring").setUniverseMin(-15);
        fis.getVariable("temperature_spring").setUniverseMax(35);
        fis.getVariable("temperature_wet_spring").setUniverseMin(-25);
        fis.getVariable("temperature_wet_spring").setUniverseMax(25);
        fis.getVariable("cloudiness").setUniverseMin(0);
        fis.getVariable("cloudiness").setUniverseMax(10);
        fis.getVariable("wind_velocity").setUniverseMin(0);
        fis.getVariable("wind_velocity").setUniverseMax(50);

        fis.getVariable("percentage").setUniverseMin(0);
        fis.getVariable("percentage").setUniverseMax(1);
        fis.getVariable("absolute").setUniverseMin(0);
        fis.getVariable("absolute").setUniverseMax(18000);
    }
}
