/*
We have a lot of servers our program is keeping track of. The schema for a server name is {type}-{number}.
For example: api-1, api-2, etc. Sometimes we need to decommission a server.
The next time we allocate a server of that type, we want to fill any gaps in server number left behind by
any decommissioned servers first. Gaps should be filled using the smallest missing server number first.

To that end, please implement a Tracker that implements the following:

Tracker -
init([list<string> existing_inventory])
allocate(string type) -> string
deallocate(string name) -> None(or Void)

Example input and output:
init([api-1, db-1])
allocate(api) -> api-2
allocate(db) -> db-2
allocate(api) -> api-3
deallocate(api-2)
deallocate(api-1)
allocate(api) -> api-1
*/
/*
1. 要使用hashmap
每一个不同的类型，都要有一个set和minheap
 set: 存储已经分配的服务器id
minheap: 存储已经被删除的服务器id
2.为什么用minheap? （动态分配问题Heap）
答：每一次想要分配一个新的id，要求最小
如果minheap不为空，那么说明有一些id(gap id)被删除了，放在minheap
从minheap就可以直接取出最小且没有使用的id (logk)
如果minheap不为空:
下一个最小id = 最大id + 1
*/

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