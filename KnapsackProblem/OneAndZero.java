package Algorithm.knapsackProblem;
import java.util.Scanner;

/*
 * 01背包问题：有N件物品和一个容量为V的背包，放入第i 件物品消耗费用为Ci,得到价值为Wi。求解将哪些物品放入背包使价值综合最大
 * 基本思路：01背包问题的特点是每种物品仅有一件，可以选择放或者不放。用动态规划做。
 * 定义状态：dp[i][v]表示前i件物品恰好放入容量为V的背包中，可以获得的最大价值，
 * 状态转移方程为：dp[i][v]= max(dp[i-1][v], dp[i-1][v-wi]+wi)也就是说，“将前i件物品放入容量v的背包中“这个子问题，只考虑第i件物品放与不放，
 * 			如果不放，该问题转换为”前i-1件物品放入容量为v的背包中“，价值为dp[i-1,v]，
 * 			如果放，该问题转换为“前i-1件物品放入v-Ci的背包中“，价值为dp[i-1,v-Ci]+Wi;
 * 初始值为：dp[0,0...V] = 0;
 * 伪代码：初始值:dp[0,0...V] <- 0;
 *       for i <- 1 to N
 *       	for v<-Ci to V
 *       		dp[i][v]= max{dp[i-1][V], dp[i-1][V-Ci]+Wi}
 * 时间复杂度为O（VN），空间复杂度为O（VN）
 * 
 * 优化空间复杂度为O（V）：针对外层循环i<- 1...N,保证每次得出二维数组dp[i,0...V],如果只用一个数组dp[0...V]可以保证每次内层循环结束后dp[v]是dp[i,v]??
 *      由于状态转移方程中需要用到dp[i-1,v],dp[i-1, v-Ci],可以将内层循环以v<- V ... 0 的递减顺序计算dp[v],
 *      这样可以保证计算dp[v]时dp[v-Ci]保存的是状态dp[i-1,v-Ci]的值。
 *      伪代码：初始值:dp[0,0...V] <- 0;
	 *       for i <- 1 to N
	 *       	for v<- V to Ci
	 *       		dp[v]= max{dp[V], dp[V-Ci]+Wi}
 * 初始化细节：1.如果问题是“恰好装满背包”时的最优解：除了dp[0] = 0,其他的dp[1...V] = 负无穷大，
 *             原因在于“只有容量为0的背包在什么也不装且价值为0的情况下恰好装满，其他容量的背包均没有合法的解
 *           2.如果题目中只是要求价值最大化，不要求背包装满：dp[0...V] = 0
 *             原因在于背包并非要求装满，那么任何容量的背包都有一个合法解“什么都不装”
 * 还可以继续优化：针对内层循环，在背包空间为0～max(Ci，前i-1 件物品容量之和）,价值都不会得到改变
 *          伪代码：初始值:dp[0,0...V] <- 0;
		 *       for i <- 1 to N
		 *       	for v<- V to max(Ci,V-后（N-i)件物品容量之后）
		 *       		dp[v]= max{dp[V], dp[V-Ci]+Wi}
 *       
 */
public class OneAndZero {
    //方法一：空间复杂度为O（VN）
	public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int num : nums){
            sum += num;
        }
        //特点：如果数组之和为奇数，一定是false，
        if(sum % 2 != 0) return false;
        //转换为背包问题：容量是target,一共有nums.length件物品，
        //问题是恰好将背包装满，也就是恰好等于target,初始值为false
        int target = sum / 2;
        int len = nums.length;
        //方法一：空间复杂度为O（VN）
        boolean[][] dp = new boolean[len][target+1];//未初始化，默认是false
        /*//先填第0行
        if(nums[0] <= target)dp[0][nums[0]] = true;
        //填写其它行
        for(int i = 1 ; i < len; i++){
            for(int j = 0; j <= target; j++){
                dp[i][j] = dp[i-1][j];//情况1，不放该元素
                if(nums[i] == j){//情况2，选择放该元素时，恰好等于
                    dp[i][j] = true;
                    continue;
                }
                if(nums[i] < j){//情况2特殊，选择放该元素，但是存在下标问题，必须使nums[i] < j
                    dp[i][j] = dp[i-1][j] || dp[i-1][j - nums[i]];
                }
            }
        }
        */
        //合并情况2，将上述代码优化，
        dp[0][0]  = true;
      //先填第0行
        if(nums[0] <= target)dp[0][nums[0]] = true;
        //填写其它行
        for(int i = 1 ; i < len; i++){
            for(int j = 0; j <= target; j++){
                dp[i][j] = dp[i-1][j];//情况1，不放该元素
                if(nums[i] <= j){//情况2特殊，选择放该元素，但是存在下标问题，必须使nums[i] <= j，由于dp[i][0] = true
                    dp[i][j] = dp[i-1][j] || dp[i-1][j - nums[i]];
                }
            }
            //如果已经满足分割等和子集，可以提前结束，返回布尔值
            if(dp[i][target])return true;
        }
        return dp[len-1][target];
    }
	
	//方法二，优化空间复杂度为O(V)(v = len)
	public boolean canPartition2(int[] nums) {
        int sum = 0;
        for(int num : nums){
            sum += num;
        }
        //特点：如果数组之和为奇数，一定是false，
        if(sum % 2 != 0) return false;
        //转换为背包问题：容量是target,一共有nums.length件物品，
        //问题是恰好将背包装满，也就是恰好等于target,初始值为false
        int target = sum / 2;
        int len = nums.length;
        boolean[] dp = new boolean[target+1];//未初始化，默认是false
        //合并情况2，将上述代码优化，
        dp[0]  = true;
      //先填第0行
        if(nums[0] <= target)dp[nums[0]] = true;
        //填写其它行
        for(int i = 1 ; i < len; i++){
            for(int j = target; j >= 0; j--){
                dp[j] = dp[j];//情况1，不放该元素
                if(nums[i] <= j){//情况2特殊，选择放该元素，但是存在下标问题，必须使nums[i] <= j，由于dp[i][0] = true
                    dp[j] = dp[j] || dp[j - nums[i]];
                }
            }
            //如果已经满足分割等和子集，可以提前结束，返回布尔值
            if(dp[target])return true;
        }
        return dp[target];
    }

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		//输入一个数组
		String str = sc.nextLine().toString();
		String[] arr = str.split(",");
		int[] a = new int[arr.length];
		for(int i = 0; i< arr.length; i++) {
			a[i] = Integer.parseInt(arr[i]);
		}
		OneAndZero test = new OneAndZero();
		System.out.println(test.canPartition(a));
	}
}
