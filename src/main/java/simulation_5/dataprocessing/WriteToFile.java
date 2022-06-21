package simulation_5.dataprocessing;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WriteToFile {

    private final String delimiter = ",";

    public void clearFile(String fileName){
        File file = new File(fileName);
        file.delete();
        createFile(fileName);
    }

    public void writeToCSV(int[] data, String fileName){

        createFile(fileName);
        try(CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(fileName, true),
                CSVFormat.DEFAULT)){
            printAllAsStrings(data, csvPrinter);

        } catch (IOException ex){
            ex.printStackTrace();
        }

    }

    private void printAllAsStrings(int[] data, CSVPrinter printer) throws IOException {
        for (int i : data){
            printer.print(i);
        }
        printer.println();
    }

    public void writeToCSV(double[] data, String fileName){

        createFile(fileName);
        try(CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(fileName, true),
                CSVFormat.DEFAULT)){
            printAllAsStrings(data, csvPrinter);

        } catch (IOException ex){
            ex.printStackTrace();
        }

    }

    private void printAllAsStrings(double[] data, CSVPrinter printer) throws IOException {
        for (double d : data){
            printer.print(Double.toString(d).replace('.', ','));
        }
        printer.println();
    }


    private void createFile(String fileName) {
        File file = new File(fileName);
         try {
             file.createNewFile();
         } catch (IOException ex) {
             ex.printStackTrace();
         }
    }
}
