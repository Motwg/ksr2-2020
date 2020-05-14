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

            return fis;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
