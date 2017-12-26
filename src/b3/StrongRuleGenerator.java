import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Random;


class StrongRuleGenerator {
    Double support,confidence;
    String[] items;
    HashSet<String> rules = new HashSet<>();
    ArrayList<String> strongRules = new ArrayList<>();
    String [] freqItemSets;

    public StrongRuleGenerator(String i, double confT, double supT){
        items = i.split(",");
        support = supT;
        confidence = confT;
        freqItemSets = new String[items.length-1];
    }

    public void getRules(){



        //Scanner in = new Scanner(System.in);

        for (int i = 0;i<freqItemSets.length;i++){
            String[] f_k = freqItemSets[i].split(",");
            for(String item: f_k){

                if(item.length() == 1){
                    HashSet<Character> allItems = new HashSet<>();
                    for(Character c: String.join("", items).toCharArray())
                        allItems.add(c);
                    allItems.remove(item.charAt(0));
                    String anticendent = "["+ item.charAt(0)+"]";
                    String consequent = ""+allItems.toString();
                    String rule = anticendent+" -> "+consequent;
                    String opp_rule = consequent+" -> "+anticendent;
                    addRule(rule);
                    addRule(opp_rule);
                }
                if(item.length() == 2){
                    String anticendent = ""+ item.charAt(0);
                    String consequent = ""+item.charAt(1);
                    String rule = anticendent+" -> "+consequent;
                    String opp_rule = consequent+" -> "+anticendent;
                    addRule(rule);
                    addRule(opp_rule);
                    HashSet<Character> allItems = new HashSet<>();
                    for(Character c: String.join("", items).toCharArray()){
                        if(!item.contains(""+c))
                            allItems.add(c);
                    }
                    anticendent = "["+item.charAt(0)+", "+item.charAt(1)+"]";
                    consequent = allItems.toString();
                    rule = anticendent+" -> "+consequent;
                    opp_rule = consequent+" -> "+anticendent;
                    if(!rules.contains(opp_rule)){
                        addRule(rule);
                        addRule(opp_rule);
                    }

                }
                if(item.length() == 3){
                    HashSet<Character> allItems = new HashSet<>();
                    for(Character c: item.toCharArray()){
                            allItems.add(c);
                    }
                    for(Character c: item.toCharArray()){
                        allItems.remove(c);
                        String anticendent = ""+ c;
                        String consequent = allItems.toString();
                        String rule = anticendent+" -> "+consequent;
                        String opp_rule = consequent+" -> "+anticendent;
                        addRule(rule);
                        addRule(opp_rule);
                        allItems.add(c);
                    }
                }
            }
        }
        System.out.println("The strong rules are:");
        for (String rule : strongRules){
            System.out.println(rule);
        }
    }

    private void addRule(String rule){
        Random in = new Random();
        Double sup,conf;
        System.out.println(rule);
        rules.add(rule);
        sup = in.nextDouble();
        conf = in.nextDouble();
        if (sup > support && conf > confidence){
            strongRules.add(rule);
        }
    }

    public void generateFrequentSubsets()
    {
        // Generate k-1 item sets and multiply - using fk_1*fk_1 strategy
        // Hence k=1 => 1 item set
        //       k=2 => 2 item sets and so on upto n-1 where n = no. of items
        for (int i = 0; i<items.length-1;i++){
                genkitems(i);
                String[] temp = freqItemSets[i].split(",");
                System.out.print(Arrays.toString(temp));
                System.out.println();
        }
    }

    public void genkitems(int k){
        // To generate kth item set, cross multiply k-1 * k-1 item set
        if (k-1 < 0){
            freqItemSets[0] = String.join(",",items);
        }
        else {
            HashSet<String> kitems = new HashSet();
            String[] fk_1 = freqItemSets[k-1].split(",");
            for (int i = 0; i<fk_1.length ; i++){
                for (int j=i+1;j<fk_1.length;j++){
                    if(kitems.contains(fk_1[i]+fk_1[j])|| kitems.contains(fk_1[j]+fk_1[i]))
                        continue;
                    else
                    {
                        String elements = fk_1[i]+fk_1[j];
                        HashSet<Character>s = new HashSet<>();
                        for (Character c: elements.toCharArray()){
                            s.add(c);
                        }
                        elements = "";
                        for (Character c: s)
                            elements += c;
                        if(elements.length() == k+1)
                            kitems.add(elements);
                    }
                }
            }
            // convert the string of the form "[a, b, c]" to "a,b,c"
            freqItemSets[k] = kitems.toString().replaceAll(" ", "");
            freqItemSets[k] = freqItemSets[k].substring(1,freqItemSets[k].length()-1);
        }
    }


    public static void main(String args[])
    {
        String line;
        Double supportThreshold, confidenceThreshold;

        System.out.println("Enter the comma separted 4-frequent itemset elements:");
        Scanner in = new Scanner(System.in);
        line = in.nextLine();
        System.out.println("Enter the Support Threshold & Confidence Threshold");
        supportThreshold = in.nextDouble();
        confidenceThreshold = in.nextDouble();
        StrongRuleGenerator generator =
                                new StrongRuleGenerator(line, supportThreshold,confidenceThreshold);
        System.out.println("Frequent Subsets:");
        generator.generateFrequentSubsets();
        System.out.println("Candidate Rules:");
        generator.getRules();
    }
}