/* 2406
给你一个二维整数数组 intervals ，其中 intervals[i] = [lefti, righti] 表示 闭 区间 [lefti, righti] 。
你需要将 intervals 划分为一个或者多个区间 组 ，每个区间 只 属于一个组，且同一个组中任意两个区间 不相交 。
请你返回 最少 需要划分成多少个组。
如果两个区间覆盖的范围有重叠（即至少有一个公共数字），那么我们称这两个区间是 相交 的。比方说区间 [1, 5] 和 [5, 8] 相交。

示例 1：
输入：intervals = [[5,10],[6,8],[1,5],[2,3],[1,10]]
输出：3
解释：我们可以将区间划分为如下的区间组：
- 第 1 组：[1, 5] ，[6, 8] 。
- 第 2 组：[2, 3] ，[5, 10] 。
- 第 3 组：[1, 10] 。
可以证明无法将区间划分为少于 3 个组。
Followup：返回分组 group
*/

class Solution {

    class Group {
        int end;
        int groupId;

        public Group(int end, int groupId) {
            this.end = end;
            this.groupId = groupId;
        }
    }

    public List<List<int[]>> minGroups(int[][] intervals) {

        Arrays.sort(intervals,
            (a, b) -> Integer.compare(a[0], b[0]));

        PriorityQueue<Group> heap = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.end, b.end));

        List<List<int[]>> result = new ArrayList<>();

        for (int[] interval : intervals) {

            int groupId;

            if (!heap.isEmpty() && interval[0] > heap.peek().end) {

                // 复用已有的组
                Group group = heap.poll();
                groupId = group.groupId;

            } else {

                // 创建新组
                groupId = result.size();
                result.add(new ArrayList<>());
            }

            // 将当前区间加入对应的组
            result.get(groupId).add(interval);

            // 更新该组的结束时间
            heap.offer(new Group(interval[1], groupId));
        }

        return result;
    }
}
