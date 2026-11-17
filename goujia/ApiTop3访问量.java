/*
实现“滑动窗口统计过去 10 分钟内 API 访问量并展示 Top 3”的需求，

*/

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Main {

    // 每个 API 对应一个双端队列，存储访问的时间戳（毫秒）
private final Map<String, Deque<Long>> apiRecords = new ConcurrentHashMap<>();
private final long WINDOW_MS = 10 * 60 * 1000; // 10分钟

   /**
 * 记录一次 API 访问
 */
public  void recordAccess(String apiName) {
    long now = System.currentTimeMillis();

    // 获取或创建该 API 的队列
    apiRecords.putIfAbsent(apiName, new ConcurrentLinkedDeque<>());
    Deque<Long> dq = apiRecords.get(apiName);

    // 1. 记录当前访问
    dq.addLast(now);

    // 2. 清理过期数据（滑动窗口核心）
    cleanExpiredData(dq, now);
}


//获取过去 10 分钟访问量最高的 Top 3 API
public List<Map.Entry<String,Integer>> getTop3(){
long now = System.currentTimeMillis();
PriorityQueue<Map.Entry<String,Integer>> minHeap=new PriorityQueue<>(
  Comparator.comparingInt(Map.Entry::getValue)
);
for(Map.Entry<String, Deque<Long>> entry : apiRecords.entrySet()){
       Deque<Long> dq = entry.getValue();
        // 统计前再次清理，确保数据实时性
        cleanExpiredData(dq, now);
        int count = dq.size();
        minHeap.offer(Map.entry(entry.getKey(),count));
        if(minHeap.size()>3)
         minHeap.poll();

}
List<Map.Entry<String,Integer>> res=new ArrayList<>();
while(!minHeap.isEmpty()){
    res.add(minHeap.poll());
}
Collections.reverse(res);
return res;
}

   public void cleanExpiredData(Deque<Long> dq, long now) {
    while (!dq.isEmpty() && (now >=dq.peekFirst() +WINDOW_MS)) {
        dq.pollFirst();
    }
}
    public static void main(String[] args) {
   Main stats = new Main();
    // 模拟测试
    stats.recordAccess("/getUser");
    stats.recordAccess("/getUser");
    stats.recordAccess("/order");
    stats.recordAccess("/pay");
    stats.recordAccess("/pay");
    stats.recordAccess("/pay");
    System.out.println("Top 3 APIs in last 10 minutes:");
   stats.getTop3().forEach(e ->
    System.out.println(e.getKey() + ": " + e.getValue())
    );
    }
}