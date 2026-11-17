/* robolx的ratelimiter followup问题：
user 和 experience 各一个窗口：
答：每个请求带 userId 和 experienceId，每个 user、每个 experience 各有独立窗口，两边都有空位才放行。
两个 dict 映射到各自的 deque，各自清过期，都没满才放行，放行时两边一起 append。
	
[1,2,3,4,5]，user [1,1,2,1,2]，exp [A,A,A,A,B]，W=3，M=1 → [T,F,F,T,T]	
t=3 的 user 2 是第一次来，照样被拒，因为 A 在 t=1 放行过。

*/

import java.util.*;

public class Main {
   private static final int W=3; //滑动窗口长度
   private static final int M=1; //请求最大次数
  private static final Map<Integer, Deque<Long>> userWindows = new HashMap<>();
 private static final Map<String, Deque<Long>> expWindows = new HashMap<>();

  public static synchronized boolean rateLimiter(
            long time, int userId, String experienceId) {
       // 获取该 user 的队列，不存在则创建
        userWindows.putIfAbsent(userId, new ArrayDeque<>());
        Deque<Long> userQueue = userWindows.get(userId);

        // 获取该 experience 的队列，不存在则创建
        expWindows.putIfAbsent(experienceId, new ArrayDeque<>());
        Deque<Long> expQueue = expWindows.get(experienceId);
            // 分别清理两个队列的过期记录
        removeExpired(userQueue, time);
        removeExpired(expQueue, time);
        // 任意一边满了，就拒绝；两边都不添加记录
        if (userQueue.size() >= M
                || expQueue.size() >= M) {
            return false;
        }
         // 两边都有空位，一起记录本次放行
        userQueue.addLast(time);
        expQueue.addLast(time);
        return true;
            }

      private static void removeExpired(Deque<Long> queue, long time) {
        while (!queue.isEmpty()
                && queue.peekFirst() <= time - W) {
            queue.pollFirst();
        }
    }


    public static void main(String[] args) {
     long[] times = {1, 2, 3, 4, 5};
        int[] users = {1, 1, 2, 1, 2};
        String[] experiences = {"A", "A", "A", "A", "B"};
         List<Boolean> result = new ArrayList<>();

        for (int i = 0; i < times.length; i++) {
            result.add(rateLimiter(
                    times[i], users[i], experiences[i]));
        }  // [true, false, false, true, true]

        System.out.println(result);
    }
}