import java.util.*;
import java.io.*;

public class Perceptron{

    Double[] weights;
    ArrayList<Double[]>x;
    ArrayList<Double> y;
    int dimensions;

    public static void main(String[] args){
        Perceptron p = new Perceptron("data.csv");
        p.train(0.2,1000);
        Double[] test = {6.2,3.0,4.8,1.5};
        Double[] test2 = {3.4,4.9,2.7,0.1};
        System.out.println("Predicted class: "+ p.predict(test));
        System.out.println("Predicted class: "+ p.predict(test2));
    }

    public Perceptron(String filename){
        x = new ArrayList<>();
        y = new ArrayList<>();
        try{
            BufferedReader in = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while((line = in.readLine())!= null){
                String[] row = line.split(",");
                dimensions = row.length -1;
                Double[] attr = new Double[dimensions];
                for(int i=0;i<dimensions;i++){
                    attr[i] = Double.parseDouble(row[i]);
                }
                x.add(attr);
                y.add(Double.parseDouble(row[dimensions]));
            }

            in.close();

        }catch(Exception e){e.printStackTrace();}
    }

    public void train(double alpha, int epochs){
        dimensions = x.get(0).length;
        weights = new Double[dimensions+1];
        Random r = new Random();
        //initialise weights
        for(int i =0;i<=dimensions;i++){
            weights[i] = r.nextDouble();// between 0 & 1
        }
        System.out.println("Initial weights:");
        printWeights();
        while(epochs-->0){
            for(int i=0;i<x.size();i++){
                //compute perceptron output
                int output = predict(x.get(i));
                Double delta = alpha*(y.get(i)-output);
                //update weights
                weights[0] +=delta;
                for(int j=1;j<=dimensions;j++){
                    weights[j]+=delta*x.get(i)[j-1];
                }
            }
        }
        System.out.println("Final weights:");
        printWeights();
    }

    public int predict(Double[] sample){
        // Y = w*x + b
        Double output = weights[0];
        for(int j=1;j<=dimensions;j++){
            output+= weights[j]*sample[j-1];
        }
        //apply activation
        return output>=0?1:0;
    }

    public void printWeights(){
        String w = Arrays.toString(weights);
        System.out.println(w.substring(1, w.length() - 1));
    }
}