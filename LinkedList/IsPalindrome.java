package Algorithm.leetcode.LinkedList;
/*请判断一个链表是否为回文链表。

	示例 1:

	输入: 1->2
	输出: false
	示例 2:

	输入: 1->2->2->1
	输出: true
	进阶：
	你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？*/
import java.util.ArrayList;
public class IsPalindrome {
	
	public static class ListNode {
		int val;
		ListNode next;
	    ListNode(int x) { val = x; }
	    ListNode(int x, ListNode next){
	    	this.val = x;
	    	this.next = next;
	    }
	}
     //反转链表
    public static ListNode reverseList(ListNode head){
        if(head == null || head.next == null)return head;
        ListNode p = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return p;
    }
    //方法一：反转后一半的链表，空间复杂度为O（1），时间复杂度为O（n)
    public boolean isPalindrome(ListNode head) {
        if(head == null || head.next == null) return true;
        ListNode fast = head;
        ListNode slow = head;
        while(fast != null && fast.next != null) {
        	slow = slow.next;
        	fast = fast.next.next;
        }
        //slow指向后一半的第一个结点值（总长度为偶数，则slow是第n/2+1个值，是奇数，则是第（n+1）/2个值
        fast = reverseList(slow);
        //定义两个辅助变量
        ListNode pre = head, last = fast;
        while(pre!=null && last!= null) {
        	if(pre.val != last.val) 
        		return false;
        	pre = pre.next;
        	last = last.next;
        }
        //恢复链表
        reverseList(fast);
        return true;
    }
    //方法二：借助列表存储链表数值
    public boolean isPalindrome1(ListNode head) {
        if(head == null || head.next == null) return true;
        ListNode cur = head;
        ArrayList<Integer> list = new ArrayList<>();
        while(cur != null) {
        	list.add(cur.val);
        	cur = cur.next;
        }
        //采用双指针法判断是否为回文数
        int l = 0, r = list.size() -1;
        while(l <= r) {
        	if(!list.get(l).equals(list.get(r)))return false;
        	l++;r--;
        }
        return true;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(5, new ListNode(4, new ListNode(1, null)))));
       
        System.out.println(new IsPalindrome().isPalindrome(head));
    }
}
