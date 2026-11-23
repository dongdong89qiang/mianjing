/*
There is a log recording vehicle highway trips throughout the day, documenting each checkpoint passed by every vehicle.
Log Format:
Checkpoint, License Plate, Time
checkpoint1, ABC123, 09:00
checkpoint2, ABC123, 09:15
checkpoint3, XYZ789, 09:30
checkpoint1, XYZ789, 09:50

We need to calculate the highway usage fee for each vehicle from this log.
Fee calculation formula:
Fee = 2 + 0.1 × Total Distance
Assume you can directly get the distance between two checkpoints via the API:
getDistance(a, b)
*/
import java.util.*;


class Log {
    String checkpoint;
    String licensePlate;
    String time;

    public Log(String checkpoint, String licensePlate, String time) {
        this.checkpoint = checkpoint;
        this.licensePlate = licensePlate;
        this.time = time;
    }
}

public class Main {

  public static Map<String, Double> calculateFees(List<Log> logs){
    // 1. Group logs by license plate
    Map<String,List<Log>> map=new HashMap<>();
    for(Log log : logs){
      map.putIfAbsent(log.licensePlate, new ArrayList<>());
      map.get(log.licensePlate).add(log);
    }
  
     // 2. Calculate fee for each vehicle
    Map<String, Double> result = new HashMap<>();

   for(Map.Entry<String, List<Log>> entry : map.entrySet()){
    String plate= entry.getKey();
    List<Log> list=entry.getValue();
   
    // Sort by time
    list.sort((a,b) -> a.time.compareTo(b.time));

     double totalDistance = 0;
     // Calculate distance between consecutive checkpoints
    for(int i=1;i<list.size();i++) {
          String prev = list.get(i - 1).checkpoint;
          String curr = list.get(i).checkpoint;

        totalDistance += getDistance(prev, curr);

    }
      double fee = 2 + 0.1 * totalDistance;
      result.put(plate, fee);
   }

     return result;
  }

  // Mock API for testing
    public static double getDistance(String a, String b) {
        if (a.equals("checkpoint1") && b.equals("checkpoint2"))
            return 10;

        if (a.equals("checkpoint2") && b.equals("checkpoint3"))
            return 20;

        if (a.equals("checkpoint1") && b.equals("checkpoint3"))
            return 30;

        return 0;
    }

    public static void main(String[] args) {
      List<Log> logs = Arrays.asList(
            new Log("checkpoint1", "ABC123", "09:00"),
            new Log("checkpoint2", "ABC123", "09:15"),
            new Log("checkpoint3", "XYZ789", "09:30"),
            new Log("checkpoint1", "XYZ789", "09:50")
        );

        Map<String, Double> fees = calculateFees(logs);

        System.out.println(fees);
    }
}