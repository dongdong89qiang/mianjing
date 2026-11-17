/*
**Design a rate limiter that allows a maximum of 100 requests per user per minute.**

**Be prepared for these follow-up questions:**

**👉 Fixed Window vs. Sliding Window?**

**👉 How would you make the implementation thread-safe?**

**👉 How would you implement it across multiple application instances?**

**👉 How would you handle millions of users efficiently?**
https://app.notion.com/p/rate-limiter-followup-3a1171a06793805fb3c2d0c731f59a8f?v=399171a06793809f8ef0000c95562d01&source=copy_link
*/
import java.util.*;

public class Main {
  private static final long WINDOWMS=60*1000;
  private static final int MAX_REQUEST=100;
  private final Map<String, Deque<Long>> windows = new HashMap<>();

  public  synchronized boolean  rateLimiter(String userId){
         
         windows.putIfAbsent(userId,new ArrayDeque<>());
        Deque<Long> queue=windows.get(userId);
       long now=System.currentTimeMillis();
       while(!queue.isEmpty()&&queue.peekFirst()+WINDOWMS<=now){
             queue.pollFirst();
       }
      if(queue.size()>=MAX_REQUEST)
          return false;
      queue.addLast(now);
      return true;

  }

    public static void main(String[] args) {
         Main limiter = new Main();

        for (int i = 1; i <= 101; i++) {
            System.out.println(
                    "Request " + i + ": " + limiter.rateLimiter("user1")
            );
        }

        // user2 独立计数
        System.out.println("user2: " + limiter.rateLimiter("user2"));
    }
}