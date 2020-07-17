package Algorithm.leetcode.LinkedList;

//import Algorithm.Solution.ListNode;
/*
 * 反转一个单链表
 * 输入：1->2->3->4->5->null
 * 输出：5->4->3->2->1->NULL
 */
public class ReverseList {
//Definition for singly-linked list.
	public static class ListNode{
		int val;
		ListNode next;
		ListNode(){
		}
		
		ListNode(int x){
			this.val = x;
		}
		
		ListNode(int x, ListNode next){
			this.val = x;
			this.next = next;
		}
	}
	
	//思路一头插法（无头结点）时间复杂度空间复杂度为O（n）
	public static ListNode reverseList(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode list = head,p = head.next, q;
        list.next = null;
        //头插法,没有头结点
        while(p != null){
            q = p.next;
            p.next = list;
            list = p;
            p = q;
        }
        return list;
    }
	//依然是头插法，但是空间复杂度变为O（1）
	public ListNode reverseList1(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode pre,cur, pro;
        pre = head;
        cur = head.next;
        pre.next = null;
        while(cur != null){
            pro = cur.next;
            cur.next = pre;
            pre = cur;
            cur = pro;
        }
        return pre;
    }
	//方法三——迭代
	public ListNode reverseList2(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode p = reverseList(head.next);//递归表达式
        //p 和当前结点关系,以4->5为例，head = 4, head.next = 5, 
        //head.next.next = head也就是   5.next =  4;地址值唯一
        head.next.next = head;//此时循环了
        head.next = null;//防止循环
        return p;
    }
	
	public static void main(String[] args) {
		ListNode list = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, null)))));
		ListNode ret = ReverseList.reverseList(list);
		//System.out.println(ret.next);
		while(ret!= null) {
			System.out.print(ret.val + " ");
			ret = ret.next;
		}
	}
	//public static void main(String[] args) {
		//Scanner sc = new Scanner(System.in);
		//ReverseList test = new ReverseList();
		//ListNode head = new ListNode();内部类访问时会出错
		//解决方法一：访问非静态内部类
		//解决方法类：将非静态内部类变为静态内部类
		//ListNode head = test.new ListNode();//访问非静态内部类
		
		//sc.hasNext()用来判断是否还有下一个输入
		//ListNode temp = head;
		//head = null;
		//解决sc.hasNext()陷入循环问题
		//方法一：从在键盘上按Ctrl+Z。这样输入会读取到EOF，表示读取结束。
		//方法二：如下：设置一个终止符，调用hasNext()的重载方法hasNext(String patten)：
		//如果下一个标记与从指定字符串构造的模式匹配，则返回 true。扫描器不执行任何输入。
		/*while(!sc.hasNext("e")) {
			int c = sc.nextInt();
		    temp.val = c;
		    temp.next = head;
		    head = temp;
		}
		ListNode ret = test.reverseList(head);
		
		//System.out.println(ret);//调用非静态方法，需实例化对象，对象名.方法名，输出的是地址解决方法如下
		while(ret != null) {
			System.out.print(ret.val +" ");
			ret = ret.next;
		}*/
		
		
	//}
}
