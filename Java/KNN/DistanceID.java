public class DistanceID implements Comparable<DistanceID>{
    double id;
    double distance;
    public DistanceID(double distance, double id){
        this.distance = distance;
        this.id = id;
    }

    @Override
    public int compareTo(DistanceID d)
    {
        if(distance == d.distance)
            return 0;
        else if(distance > d.distance)
            return 1;
        else
            return -1;
    }
}
