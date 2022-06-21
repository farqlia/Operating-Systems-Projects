package simulation_5.dataprocessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFromFile {

    private final String delimiter = ",";
    private final String root = "src/main/resources/results/";
    String[] filesToRead = {"/averages.txt"};

    public Object[][] getData(String fileName, int row, int rowCount){

        Object[][] data = new Object[filesToRead.length][];

        for (int i = 1; i < data.length; i++){
            data[i] = readFromFile(root + fileName + filesToRead[i], row,  rowCount);
        }
        return data;
    }

    private String[] readFromFile(String fileName, int startRow,
                                  int rowCount) {

        String[] data = new String[rowCount];

        try (BufferedReader fR = new BufferedReader(new FileReader(fileName))){

            while (startRow-- > 0) fR.readLine();

            data = fR.readLine().trim().split(delimiter);

        } catch (IOException ex){
            ex.printStackTrace();
        }

        return data;
    }

}
