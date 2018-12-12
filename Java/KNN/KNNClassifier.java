import java.io.*;
import java.util.*;
public class KNNClassifier {
    int k;
    ArrayList<Double[]> data;
    ArrayList<DistanceID> distances;

    public KNNClassifier(String filename){
        data = new ArrayList<>();
        k = 3;
        parseCSV(filename);
        distances = new ArrayList<>();
    }

    public void parseCSV(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while ((line = reader.readLine()) != null) {
                String row[] = line.split(",");
                int i = 0;
                Double row_double[] = new Double[row.length];
                for (String attr : row) {
                    row_double[i] = Double.parseDouble(attr);
                    i++;
                }
                data.add(row_double);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void calculateDistance(Double new_row[] ){
        for ( int i = 0; i < data.size(); i++){
            Double point[] = data.get(i);
            Double dist = EuclideanDistance(point, new_row);
            distances.add(new DistanceID(dist, point[3]));
        }
    }

    public double EuclideanDistance(Double[] first, Double[] second){
        double sum = 0;
        for( int i = 0; i < second.length; i++ ){
            sum += Math.pow( (first[i]-second[i]) , 2);
        }
        return Math.sqrt(sum);
    }



    public void nearestNeighbours() {
        double cls;
        Collections.sort(distances);
        for(int i = 0; i < distances.size(); i++){
            System.out.println(distances.get(i).distance+" "+distances.get(i).id);
        }
        HashMap<Double, Integer> hm = new HashMap<>();
        for(int i = 0; i < k; i++){
            cls = distances.get(i).id;
            if(hm.containsKey(cls)) {
                int temp = hm.get(cls);
                hm.put(cls,temp + 1);
            }
            else {
                hm.put(cls, 1);
            }
        }
        double max = Integer.MIN_VALUE;
        double mkey = 0;
        for( double key: hm.keySet()) {
            if(hm.get(key) > max) {
                max = hm.get(key);
                mkey = key;
            }
        }
        System.out.println("Predicted class is "+mkey);
    }

    public static void main(String[] args) {
        String filename = "data1.csv";
        KNNClassifier knn = new KNNClassifier(filename);
        Double[] new_row = {6.0,3.5,4.1};
        knn.calculateDistance(new_row);
        knn.nearestNeighbours();

    }
}
