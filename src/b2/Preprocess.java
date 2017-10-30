
import java.io.IOException;
import java.util.*;

public class Preprocess {
    private DataSet data;

    public Preprocess(String filename) {
        data = new DataSet(filename,",");
        data.setFirstAsFields(true);
    }
    public void handleMissing (int col, String naIndicator, Boolean isNumeric,Boolean isInteger){
        ArrayList<String[]> update = new ArrayList<>();
        if (isNumeric){
            float meanValue = 0;
            for (String[] row: data.getRows())
            {
                if (col > row.length){
                    System.out.println("Invalid col index");
                    return;
                }
                if(!row[col].equalsIgnoreCase(naIndicator))
                    meanValue += Float.parseFloat(row[col]);

            }
            meanValue /= data.size();
            System.out.println("Replacement for missing in field: "+data.getFields()[col]+" is: "+ String.valueOf(meanValue));
            for (String[] row: data.getRows())
            {
                if(row[col].equalsIgnoreCase(naIndicator)){
                    if (isInteger){
                        row[col] = Integer.toString((int) meanValue);
                    }
                    else
                    {
                        row[col] = Float.toString(meanValue);
                    }
                }
                update.add(row);
            }
            data.setRows(update);
        }
        else
        {
            HashMap<String,Integer> freqMatrix = new HashMap<>();
            for(String[] row: data.getRows())
            {
                if(!row[col].equalsIgnoreCase(naIndicator)){
                    Integer count = freqMatrix.get(row[col]);
                    if(count == null) count = new Integer(0);
                    count++;
                    freqMatrix.put(row[col],count);
                }
            }
            Map.Entry<String,Integer> mostFrequent = null;
            for(Map.Entry<String,Integer> e : freqMatrix.entrySet())
            {
                if (mostFrequent == null || e.getValue() > mostFrequent.getValue())
                {
                    mostFrequent = e;
                }
            }
            System.out.println("Replacement for missing in field: "+data.getFields()[col]+" is: "+ mostFrequent.getKey());
            for (String[] row: data.getRows())
            {
                if(row[col].equalsIgnoreCase(naIndicator)){
                    row[col] = mostFrequent.getKey();
                }
                update.add(row);
            }
            data.setRows(update);

        }
    }
    public void writeCSV(String filename){
        data.writeCSV(filename);
    }

    public static void main(String[] args)
    {
        Preprocess processor = new Preprocess("dataset.csv");
        processor.handleMissing(1, "NA", true, true);//age
        processor.handleMissing(3, "NA", true, true);//pincode
        processor.handleMissing(4, "NA", false, false);//Month
        processor.handleMissing(5, "NA", false, true);//Year
        processor.handleMissing(6, "NA", false, false);//Gender
        processor.writeCSV("afterMissing.csv");

    }

}
