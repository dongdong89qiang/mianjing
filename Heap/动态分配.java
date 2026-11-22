import java.util.*;

class Tracker{
private Map<String, PriorityQueue<Integer>> available= new HashMap<>();
private Map<String,Integer> nextId=new HashMap<>();

public void init(List<String> existingInventory){
   for(String name : existingInventory){
    int index=name.lastIndexOf("-");
    String type=name.substring(0,index);
    int id= Integer.parseInt(name.substring(index+1));
   available.putIfAbsent(type, new PriorityQueue<>());
   nextId.put(type,id+1);
   }

}
 // 2. 分配服务器
    public String allocate(String type) {
      available.putIfAbsent(type, new PriorityQueue<>());
      nextId.putIfAbsent(type, 1);
      PriorityQueue<Integer> minHeap = available.get(type);
      int id;
      if(!minHeap.isEmpty())
        id= minHeap.poll();
      else {
        id=nextId.get(type);
        nextId.put(type,id+1);
    }
    return type+'-'+id; 
    }

   // 3. 释放服务器
    public void deallocate(String name) {
     int index=name.lastIndexOf("-");
     String type=name.substring(0,index);
     int id=Integer.parseInt(name.substring(index+1));
     available.get(type).offer(id);

    }  


}


public class Main {
    public static void main(String[] args) {
      Tracker tracker = new Tracker();
    tracker.init(Arrays.asList("api-1", "db-1"));
    System.out.println(tracker.allocate("api")); // api-2
        System.out.println(tracker.allocate("db"));  // db-2
        System.out.println(tracker.allocate("api")); // api-3

        tracker.deallocate("api-2");
        tracker.deallocate("api-1");

        System.out.println(tracker.allocate("api")); // api-1
        System.out.println(tracker.allocate("api")); // api-2
        System.out.println(tracker.allocate("api")); // api-4
    }
}