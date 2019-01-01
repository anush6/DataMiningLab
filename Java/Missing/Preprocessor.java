import java.util.*;
import java.io.*;

public class ReplaceMissingValues {
    ArrayList<String[]> data;
    Scanner sc;
    ReplaceMissingValues(String filename, String delimiter) {
        sc = new Scanner(System.in);
        data = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while((line = br.readLine())!=null){
                data.add(line.split(delimiter,-1));
            }
        }
        catch(Exception e) {
            System.out.println(e);
            System.exit(0);
        }
        System.out.println("Current data");
        printCsv();
    }

    public void printCsv() {
        for(String[] row:data) {
            for(String value:row) {
                System.out.print(value+" ");
            }
            System.out.println();
        }
    }

    public void replaceContinuousAttribute() {
        System.out.println("Enter column no to replace values in continuous attribute");
        int colno = sc.nextInt() - 1;
        int sum = 0,count = 0;
        for(String[] row:data) {
            if(row[colno].length()>0) {
                sum += Integer.parseInt(row[colno]);
                count++;
            }
        }
        int mean = sum / count;
        for(int i = 0;i<data.size();i++) {
            if(data.get(i)[colno].length() == 0) {
                data.get(i)[colno] = String.valueOf(mean);
            }
        }
        System.out.println("New data");
        printCsv();
    }

    public void replaceCategoricalValues() {
        System.out.println("Enter column no to replace values in categorical attribute");
        int colno = sc.nextInt() - 1;
        HashMap<String,Integer> hm = new HashMap<>();
        int max = Integer.MIN_VALUE;
        String mkey = "";
        for(int i = 0; i<data.size(); i++) {
            if(data.get(i)[colno].length() > 0) {
                String key = data.get(i)[colno];
                if(hm.containsKey(key)) {
                    hm.put(key,hm.get(key) + 1);
                }
                else {
                    hm.put(key,1);
                }
            }
        }
        for(String key:hm.keySet()) {
            if(max < hm.get(key)) {
                max = hm.get(key);
                mkey = key;
            }
        }
        for(int i = 0;i<data.size();i++) {
            if(data.get(i)[colno].length() == 0) {
                data.get(i)[colno] = mkey;
            }
        }
        System.out.println("New Data");
        printCsv();
    }

    public static void main(String[] args) {
        ReplaceMissingValues r = new ReplaceMissingValues("data.txt",",");
        r.replaceContinuousAttribute();
        r.replaceCategoricalValues();
    }
}
