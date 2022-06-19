package simulation_5;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WriteToFile {

    private final StringBuilder dataToWrite;

    public WriteToFile(){
        dataToWrite = new StringBuilder();
    }

    public void parseToString(double[] data){
        for (double value : data) dataToWrite.append(value).append(" ");
        dataToWrite.append("\n");
    }

    public void addHeaders(String header){
        dataToWrite.append(header).append("\n");
    }

    public void writeToFile(String fileName){

        try(PrintWriter bW = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))){
            bW.write(dataToWrite.toString());
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public List<double[]> readFromFile(String fileName){

        List<double[]> list = new ArrayList<>();
        try(BufferedReader bR = new BufferedReader(new FileReader(fileName))) {

            // headersLine
            String line = bR.readLine();
            String[] splitLine;
            while ((line = bR.readLine()) != null){
                splitLine = line.split(" ");
                list.add(new double[]{Integer.parseInt(splitLine[0]),
                        Double.parseDouble(splitLine[1])});
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
