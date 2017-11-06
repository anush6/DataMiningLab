import java.io.*;
import java.util.*;

class BestSplit
{
    ArrayList<String[]> rows = new ArrayList<>();
    String [] fields;
    // Store Counts
    HashMap<String,Integer> parent = new HashMap<>();
    HashMap<String,HashMap<String,Integer>> a1Count,a2Count;
    public void readCSV(String filename)
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line;
            while((line = in.readLine())!=null){
                line = line.trim();
                rows.add(line.split(","));
            }
            fields = rows.get(0);
            rows.remove(0);

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public Double gini(HashMap<String,Integer> m) {
        double total = 0;
        double gini = 1;
        for (String s : m.keySet()){
            total += m.get(s);
        }

        for (String s : m.keySet()){
            gini -= Math.pow((m.get(s)/total), 2);
        }
        return gini;
    }
    public Double entropy(HashMap<String,Integer> m) {
        double total = 0;
        double entropy = 0;
        for (String s : m.keySet()){
            total += m.get(s);
        }

        for (String s : m.keySet()){
            Double temp = m.get(s)/total;
            // entropy = -Sigma(p(i/t)*log2(p(i/t)))
            entropy -= temp*(Math.log10(temp)/Math.log10(2.0));
        }
        return entropy;
    }

    public Double gain(Double parentI, HashMap<String,HashMap<String,Integer>> m,Boolean isGini){
        Double gain = parentI;
        Double total = 0.0;
        Double gainSuffix = 0.0;
        HashMap<String,Integer> inner;
        for (String node : m.keySet()){
            inner = m.get(node);
            for(Integer i : inner.values())
                total+= i;
        }
        for (String node : m.keySet()){
            inner = m.get(node);
            Double subGini = gini(inner);
            Double temp = 0.0;
            for(Integer i : inner.values())
                temp+= i;
            gainSuffix += (temp/total)*subGini;
        }
        gain -= gainSuffix;
        return gain;
    }
    public void chooseAttribute(int a1Index, int a2Index,int classIndex)
    {
        a1Count = new HashMap<>();
        a2Count = new HashMap<>();
        for (String[] row: rows){
            Integer count = parent.get(row[classIndex]);
            if(count == null){
                count = new Integer(0);
            }
            count ++;
            parent.put(row[classIndex],count);

            //attribute 1
            HashMap<String,Integer> a1Map = a1Count.get(row[a1Index]);
            if (a1Map == null){
                a1Map = new HashMap<String,Integer>();
            }
            Integer acount = a1Map.get(row[classIndex]);
            if(acount == null){
                acount = new Integer(0);
            }
            acount ++;
            a1Map.put(row[classIndex],acount);
            a1Count.put(row[a1Index],a1Map);

            //attribute 2
            HashMap<String,Integer> a2Map = a2Count.get(row[a2Index]);
            if (a2Map == null){
                a2Map = new HashMap<String,Integer>();
            }
            Integer acount2 = a2Map.get(row[classIndex]);
            if(acount2 == null){
                acount2 = new Integer(0);
            }
            acount2 ++;
            a2Map.put(row[classIndex],acount2);
            a2Count.put(row[a2Index],a2Map);
        }
        System.out.println("Parent Count:");
        System.out.println(parent);
        System.out.println("Attr1 contingency:");
        System.out.println(a1Count);
        System.out.println("Attr2 contingency:");
        System.out.println(a2Count);
        System.out.println("Iparent- Gini:");
        Double giniParent = gini(parent);
        System.out.println(giniParent);
        System.out.println("Iparent- Entropy:");
        Double entropyParent = entropy(parent);
        System.out.println(entropy(parent));
        System.out.println("A1 Gain- Gini:");
        System.out.println(gain(giniParent,a1Count,true));
        System.out.println("A1 Gain- Entropy:");
        Double deltaA = gain(entropyParent,a1Count,false);
        System.out.println(deltaA);
        System.out.println("A2 Gain- Gini:");
        System.out.println(gain(giniParent,a2Count,true));
        System.out.println("A2 Gain- Entropy:");
        Double deltaB = gain(entropyParent,a2Count,false);
        System.out.println(deltaB);
        if (deltaA > deltaB) System.out.println("Split by Attribute : "+fields[a2Index]);
        else System.out.println("Split by Attribute : "+fields[a1Index]);
    }


    public static void main(String[] args)
    {
        BestSplit tree = new BestSplit();
        tree.readCSV("data.csv");
        tree.chooseAttribute(1,2,3);
    }
}