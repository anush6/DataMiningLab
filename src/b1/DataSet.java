
import java.io.*;
import java.util.ArrayList;

class DataSet {

    private ArrayList<String[]> rows;
    private String[] fieldNames;
    private boolean firstRowFields;
    public int count;

    public DataSet() {
        rows = new ArrayList<>();
    }
    public DataSet(String csvFile) {
        rows = new ArrayList<>();
        parseCSV(csvFile,",");
    }

    public DataSet(String csvFile, String separator) {
        rows = new ArrayList<>();
        parseCSV(csvFile,separator);
    }
    public int size(){ return count;}

    public boolean parseCSV(String file, String separator) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
            String line;
            while ((line = reader.readLine())!=null){
                String[] row = line.split(separator);
                rows.add(row);
            }
            this.count = rows.size();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public void setFirstAsFields(boolean status) {
        firstRowFields = status;
        if (status) {
            fieldNames = rows.get(0);
            rows.remove(0);
        }
    }

    public ArrayList<String[]> getRows() {
        return rows;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public static String printRow(String[] row){
        String rowString = java.util.Arrays.toString(row);
        return rowString.substring(1,rowString.length()-1);
    }

    public void printSummary(int maxRows) {

        for(String[] row : rows.subList(0,rows.size()>maxRows?maxRows:rows.size())) {
            System.out.println(printRow(row));
        }
    }

}
