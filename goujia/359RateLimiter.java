/* 359 Logger Rate Limiter
Design a logger system that receives a stream of messages along with their timestamps. Each unique message should only be printed at most every 10 seconds (i.e. a message printed at timestamp t will prevent other identical messages from being printed until timestamp t + 10).

All messages will come in chronological order. Several messages may arrive at the same timestamp.

Implement the Logger class:

Logger() Initializes the logger object.
bool shouldPrintMessage(int timestamp, string message) Returns true if the message should be printed in the given timestamp, otherwise returns false.
 Input
["Logger", "shouldPrintMessage", "shouldPrintMessage", "shouldPrintMessage", "shouldPrintMessage", "shouldPrintMessage", "shouldPrintMessage"]
[[], [1, "foo"], [2, "bar"], [3, "foo"], [8, "bar"], [10, "foo"], [11, "foo"]]
Output
[null, true, true, false, false, false, true]

Explanation
Logger logger = new Logger();
logger.shouldPrintMessage(1, "foo");  // return true, next allowed timestamp for "foo" is 1 + 10 = 11
logger.shouldPrintMessage(2, "bar");  // return true, next allowed timestamp for "bar" is 2 + 10 = 12
logger.shouldPrintMessage(3, "foo");  // 3 < 11, return false
logger.shouldPrintMessage(8, "bar");  // 8 < 12, return false
logger.shouldPrintMessage(10, "foo"); // 10 < 11, return false
logger.shouldPrintMessage(11, "foo"); // 11 >= 11, return true, next allowed timestamp for "foo" is 11 + 10 = 21

*/

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

class LogEntry {
    public int time;  
    public String message;

    public LogEntry(int time, String message) {
        this.time = time;
        this.message = message;
    }
}

class Logger {
    private Deque<LogEntry> queue;
    private HashSet<String> set;

    public Logger() {
        queue = new ArrayDeque<>();
        set = new HashSet<>();
    }
//正确做法：每次先清理所有过期记录，然后通过 set 判断当前 message 是否还在最近 10 秒的窗口内。
public boolean shouldPrintMessage(int timestamp, String message) {
  // 清除已经过了 10 秒的记录
while(!queue.isEmpty()&&queue.peekFirst().time+10<=timestamp){
    LogEntry expired=queue.pollFirst();
    set.remove(expired.message);

}
 if (set.contains(message)) {
            return false;
        }
  queue.addLast(new LogEntry(timestamp, message));
        set.add(message);
        return true;

}

}

public class Main {
    public static void main(String[] args) {
        Logger logger = new Logger();

        System.out.println(logger.shouldPrintMessage(1, "foo"));  // true
        System.out.println(logger.shouldPrintMessage(2, "bar"));  // true
        System.out.println(logger.shouldPrintMessage(3, "foo"));  // false
        System.out.println(logger.shouldPrintMessage(8, "bar"));  // false
        System.out.println(logger.shouldPrintMessage(10, "foo")); // false
        System.out.println(logger.shouldPrintMessage(11, "foo")); // true
        System.out.println(logger.shouldPrintMessage(11, "bar")); // false
        System.out.println(logger.shouldPrintMessage(12, "bar")); // true
    }
}

