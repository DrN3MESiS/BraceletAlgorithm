import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author User
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Pulsar Pulsera = new Pulsar();
	}

}

class Node {
	int start;
	int end;
	boolean accessed = false;
	
	public Node(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	public String toString() {
		return "[" + start + ", " + end + "]";
	}
}

class Pulsar{
	Scanner input = new Scanner(System.in);
	ArrayList<Node> theList = new ArrayList<Node>();
	ArrayList<Node> theQueue = new ArrayList<Node>();
	Queue<Node> waitList = new LinkedList<Node>();
	int n;
	
	public Pulsar() {
		System.out.println("Entries:");
		double inf = Double.POSITIVE_INFINITY;
		double minStart = inf;
		double minEnd = inf;
		this.n = Integer.parseInt(input.nextLine());
		for (int i = 0; i < n; i++) {
			String[] split = input.nextLine().split(" ");
			int first = Integer.parseInt(split[0]);
			if(first < minStart) {
				minStart = (double)first;
			}
			int second = Integer.parseInt(split[1]);					
			theList.add(new Node(first, second));
		}
		Node minimum = null;
		for (Node node : theList) {
			if (node.start != minStart) {
				continue;
			} else {
				if(node.end < minEnd) {
					minEnd = node.end;
					minimum = node;
				}
			}
		}
		
		waitList.add(minimum);		
		
		for (int i = 0; i < this.n; i++) {
			Node toCheck = waitList.poll();
			
			if(toCheck != null) {
				System.out.println("==================");
				System.out.println("Checking: " + toCheck.toString());
				Node result = determinePair(toCheck);
				if (result != null) {
					System.out.println("Found pair: " + result.toString());
					waitList.add(result);
				}
			} else {
				break;
			}
		}

		isValid();
	}
	
	public Node determinePair(Node head) {
		for (Node curNode : theList) {
			if(curNode.accessed == true) {
				continue;
			} else if(curNode == head){
				continue;
			} else {
				int found = 0;
				if(curNode.start == head.end) {
					found = 1;
					if(!theQueue.contains(head)) {
						theQueue.add(head);
						head.accessed = true;
					}
					if(!theQueue.contains(curNode)) {
						theQueue.add(curNode);
						curNode.accessed = true;
					}
					return curNode;
				}
				
				if(found == 0) {
					Node reverseCurNode = new Node(curNode.end, curNode.start);
					if(reverseCurNode.start == head.end) {
						found = 1;
						if(!theQueue.contains(head)) {
							theQueue.add(head);
							head.accessed = true;
						}
						if(!theQueue.contains(curNode)) {
							theQueue.add(reverseCurNode);
							curNode.accessed = true;
						}
						return reverseCurNode;
					}
				}
			}
		}
		return null;
	}
	
	public void isValid() {
		Node firstElement = theQueue.get(0);
		Node lastElement = theQueue.get(theQueue.size()-1);
		ArrayList<Node> notUsed = new ArrayList<Node>();
		boolean anyLeft = false;
		for (Node node : theList) {
			if(node.accessed == false) {
				anyLeft = true;
				notUsed.add(node);
			}
		}
		
		if(lastElement.end == firstElement.start) {
			System.out.println("\nIS VALID!");
			for (Node node : theQueue) {
				System.out.println(node.toString());
			}
			
			if(anyLeft) {
				System.out.println("***** Not all entries were used! ****");
				System.out.println("NOT USED:");
				for (Node node : notUsed) {
					System.out.println(node.toString());
				}
			}
		} else {
			System.out.println("\nNOT VALID");
			for (Node node : theQueue) {
				System.out.println(node.toString());
			}
		}
	}
	
}