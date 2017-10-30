
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static java.io.File.separator;

public class DataSet {

    private ArrayList<String[]> rows;
    private String[] fields;
    public DataSet(){
        rows = new ArrayList<>();
    }
    public DataSet(String filename, String separator){
        rows = new ArrayList<>();
        parseCSV(filename,separator);
    }

    public Boolean parseCSV(String filename,String separator) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while ((line = reader.readLine())!=null) {
                rows.add(line.split(separator));
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public void setFirstAsFields (Boolean set){
        if (set){
            fields = rows.get(0);
            rows.remove(0);
        }
        else
            fields = null;
    }
    public ArrayList<String[]> getRows() {
        return rows;
    }
    public void setRows(ArrayList<String[]> update){
        rows =  update;
    }

    public String[] getFields(){
        return fields;
    }
    public Boolean writeCSV(String filename){
        try{
            FileWriter writer = new FileWriter(filename);
            for (String[] row : rows){
                String rowstring = java.util.Arrays.toString(row);
                writer.write(rowstring.substring(1,rowstring.length()-1));
                writer.write("\n");
            }
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public int size(){
        return rows.size();
    }


}
