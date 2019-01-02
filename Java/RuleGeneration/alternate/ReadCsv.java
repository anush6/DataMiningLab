import java.io.*;
import java.util.*;

public class ReadCsv {
    private String filename;
    private String delimiter;
    public ArrayList<String[]> csvData = new ArrayList<String[]>();
    BufferedReader br;

    ReadCsv(String filename, String delimiter) throws IOException {
        this.filename = filename;
        this.delimiter = delimiter;
        br = new BufferedReader(new FileReader(new File(this.filename)));
        String line;
        while((line=br.readLine())!=null) {
            String[] temp = line.split(this.delimiter,-1);
            csvData.add(temp);
        }
        System.out.println("Read file");
    }

    void printCsv() {
        for(String[] datapoint: csvData) {
            for(String values: datapoint) {
                System.out.print(values.length()+" ");
            }
            System.out.println();
        }
    }
}
