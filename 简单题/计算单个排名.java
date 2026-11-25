/*计算单个排名
For a given array which can contain duplicates, and a given number, 
write a method which returns the index/indices (position/s) of the given number in the array, 
as if the input array was sorted.
input: [33, 22, 33, 11, 22]  22
(how it would look like if it was sorted)
output: [1, 2]
*/
/*
思路：起始下标 = lessCount;
结束下标 = lessCount + equalCount - 1;
I can iterate through the array once and count how many elements are smaller than the target and how many elements are equal to the target.
The number of smaller elements determines the starting index, and the number of equal elements determines how many consecutive indices we need to return.
This approach reduces the time complexity from O(n log n) to O(n), with O(1) extra space, excluding the output.
*/

import java.util.*;

public class Main {

 public List<Integer> targetIndices(int[] nums, int target) {
  int lessCount=0;
  int equalCount=0;
   for(int i=0;i<nums.length;i++){
         if(nums[i]<target)
           lessCount++;
          if(nums[i]==target)
           equalCount++;
   }
    List<Integer> res = new ArrayList<>();
    for (int i = lessCount; i < lessCount + equalCount; i++) {
            res.add(i);
        }

        return res;

 }

    public static void main(String[] args) {
      System.out.println("Hello, World!");
    }
}
//https://app.notion.com/p/423171a06793800182d2ef14497356bc?v=3b5171a06793805dba62000c1db7b96d&source=copy_link