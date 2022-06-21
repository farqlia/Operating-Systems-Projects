import simulation_5.dataprocessing.WriteToFile;

public class CSVWriterTest {

    public static void main(String[] args) {

        WriteToFile wTF = new WriteToFile();
        wTF.writeToCSV(new double[]{1.0,2.45,3.23}, "file.csv");

    }

}
