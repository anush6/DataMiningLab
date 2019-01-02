import java.util.ArrayList;

public class Rule {
    double support;
    double confidence;
    ArrayList<Character> lhs;
    ArrayList<Character> rhs;
    Rule()	{
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();
    }
    void setLhs(String strLhs)	{
        for(char s: strLhs.toCharArray())	{
            lhs.add(s);
        }
    }
    void setRhs(String strRhs)	{
        for(char s: strRhs.toCharArray())	{
            rhs.add(s);
        }
    }
    void setConfSup(double support, double confidence){
        this.support = support;
        this.confidence = confidence;
    }
    boolean isStrongRule(double thresholdSup, double thresholdConf)	{
        if(support >= thresholdSup && confidence >= thresholdConf){
            return true;
        }
        return false;
    }
    void printRule(){
        for(char s: lhs)
            System.out.print(s + " ");
        System.out.print(" -> ");
        for(char s: rhs)
            System.out.print(s + " ");
        //System.out.println();
    }
}