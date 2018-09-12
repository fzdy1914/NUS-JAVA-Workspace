package lab2;
/**
 * Name         :WANG CHAO
 * Matric No.   :A0177381X
 * PLab Acct.   :
 */

import java.util.*;

import java.util.NoSuchElementException;
// Check with a LabTA before you decide to import anything else...
// Using other Collection classes and arrays might result in 0 marks

public class Album {
    private LinkedList<Integer> albumA= new LinkedList<Integer>();
    private LinkedList<String> operationstackA= new LinkedList<String>();
    private LinkedList<Integer> indexstackA= new LinkedList<Integer>();
    private LinkedList<Integer> albumB= new LinkedList<Integer>();
    private LinkedList<String> operationstackB= new LinkedList<String>();
    private LinkedList<Integer> indexstackB= new LinkedList<Integer>();
    // define your own attributes, constructor, and methods here
    private void insert(String albumID, int index,int photoID){
    	switch(albumID){
		case "A":
			if(index<=albumA.size()){
				albumA.add(index, photoID);
				operationstackA.add("INSERT");
				indexstackA.add(index);
				System.out.println("Photo " + photoID +" inserted after position " + index +" of album A.");
			} else {
				System.out.println("Invalid position, album A only has " + albumA.size() + " photos.");
			}
			break;
		case "B":
			if(index<=albumB.size()){
				albumB.add(index, photoID);
				operationstackB.add("INSERT");
				indexstackB.add(index);
				System.out.println("Photo " + photoID +" inserted after position " + index +" of album B.");
			} else {
				System.out.println("Invalid position, album B only has " + albumB.size() + " photos.");
			}
			break;
		}
    }
    
    private void delete(String albumID, int index){
    	switch(albumID){
		case "A":
            if(index == 0){
            	System.out.println("Invalid position 0.");
            } else if(index<=albumA.size()){
            	indexstackA.add(index);
				indexstackA.add(albumA.get(index - 1));
				albumA.remove(index - 1);
				operationstackA.add("DELETE");
				System.out.println("Photo deleted from position " + index +" of album A.");
			} else {
				System.out.println("Invalid position, album A only has " + albumA.size() + " photos.");
			}
			break;
		case "B":
            if(index == 0){
            	System.out.println("Invalid position 0.");
            } else if(index<=albumB.size()){
            	indexstackB.add(index);
				indexstackB.add(albumB.get(index - 1));
				albumB.remove(index - 1);
				operationstackB.add("DELETE");
				System.out.println("Photo deleted from position " + index +" of album B.");
			} else {
				System.out.println("Invalid position, album B only has " + albumB.size() + " photos.");
			}
			break;
		}
    }
    
    private void preview(String albumID){
    	switch(albumID){
		case "A":
			System.out.println("Album A: " + albumA.toString() + ".");
			break;
		case "B":
			System.out.println("Album B: " + albumB.toString() + ".");
			break;
    	}
    }
    
    private void count(){
    	LinkedList<Integer> counter = new LinkedList<Integer>();
    	for(int i: albumA){
    		if(!counter.contains(i)){
    			counter.add(i);
    		}
    	}
    	for(int i: albumB){
    		if(!counter.contains(i)){
    			counter.add(i);
    		}
    	}
    	System.out.println("Number of distinct photos: " + counter.size() + ".");
    }
    
    private void undo(String albumID){
        String operation;
        int index;
        int photoID;
    	switch(albumID){
		case "A":
            if(operationstackA.size() == 0){
            	System.out.println("No changes in album A to undo.");
            } else {
				operation = operationstackA.getLast();
				operationstackA.removeLast();
				switch(operation){
				case "DELETE":
					photoID = indexstackA.getLast();
					indexstackA.removeLast();
					index = indexstackA.getLast();
					indexstackA.removeLast();
					albumA.add(index - 1, photoID);
					break;
				case "INSERT":
					index = indexstackA.getLast();
					indexstackA.removeLast();
					albumA.remove(index);
					break;
				}
				System.out.println("Album A has been undone.");
			}
			break;
		case "B":
			if(operationstackB.size() == 0){
            	System.out.println("No changes in album B to undo.");
            } else {
				operation = operationstackB.getLast();
				operationstackB.removeLast();
				switch(operation){
				case "DELETE":
					photoID = indexstackB.getLast();
					indexstackB.removeLast();
					index = indexstackB.getLast();
					indexstackB.removeLast();
					albumB.add(index - 1, photoID);
					break;
				case "INSERT":
					index = indexstackB.getLast();
					indexstackB.removeLast();
					albumB.remove(index);
					break;
				}
				System.out.println("Album B has been undone.");
			}
			break;
    	}
    }
    
    private void run() {
    	Scanner input = new Scanner(System.in);
        int operation_number = input.nextInt();
    	for(int i = 0; i < operation_number; i++){
    		String op = input.next();
    		String albumID;
    		int index;
    		int photoID;
				switch(op) {
				case "INSERT":
					albumID = input.next();
					index = input.nextInt();
					photoID = input.nextInt();
					this.insert(albumID, index, photoID);
					break;
				case "DELETE":
					albumID = input.next();
					index = input.nextInt();
					this.delete(albumID, index);
					break;
				case "PREVIEW":
					albumID = input.next();
					this.preview(albumID);
					break;
				case "COUNT":
					this.count();
					break;
				case "UNDO":
					albumID = input.next();
					this.undo(albumID);
					break;
				}
			}    	
    	input.close();
    }

    public static void main(String[] args) {
        Album newAlbum = new Album();
        newAlbum.run();
    }
}


/* List node for ExtendedLinkedList */
/* You may modify this! */
/* Add comments for any methods you have added */
class ListNode <E> {
	protected E element;
	protected ListNode <E> next;

	/* constructors */
	public ListNode(E item) { element = item; next = null; }
	public ListNode(E item, ListNode <E> n) { element = item; next=n;}

	/* get the next ListNode */
	public ListNode <E> getNext() {
		return this.next;
	}

	/* get the element of the ListNode */
	public E getElement() {
		return this.element;
	}
}

/* ExtendedLinkedList discussed in Lectue 5B */
/* You may modify this! */
/* Add comments for any methods you have added */
class ExtendedLinkedList <E> {
	private ListNode <E> head = null;
	private int num_nodes = 0;

	public boolean isEmpty() { return (num_nodes == 0); }
	public int size() { return num_nodes; }
	public E getFirst() throws NoSuchElementException {
		if (head == null) 
			throw new NoSuchElementException("can't get from an empty list");
		else return head.getElement();
	}
	public boolean contains(E item) {
		for (ListNode <E> n = head; n!= null; n=n.next)
			if (n.getElement().equals(item)) return true;
		return false;
	}

	public void addFirst(E item) {
		head = new ListNode <E> (item, head);
		num_nodes++;
	}

	public E removeFirst() throws NoSuchElementException {
		ListNode <E> ln;
		if (head == null) 
			throw new NoSuchElementException("can't remove from empty list");
		else { 
			ln = head;
			head = head.next;
			num_nodes--;
			return ln.element;
		}
	}
	public ListNode <E> getFirstPtr() { 
		return head; 
	}

	public void addAfter(ListNode <E> current, E item) {
		if (current != null) { 
			current.next = new ListNode <E> (item, current.next);
		} else {
			head = new ListNode <E> (item, head);
		}
		num_nodes++;
	}

	public E removeAfter(ListNode <E> current) throws NoSuchElementException {
		E temp;
		if (current != null) {
			if (current.next != null) {// current is not pointing to last node
				temp = current.next.element;
				current.next = current.next.next;
				num_nodes--;  return temp;
			} else throw new NoSuchElementException("No next node to remove");
		} else { // if current is null, assume we want to remove head
			if (head != null) {
				temp = head.element;
				head = head.next; 
				num_nodes--;  return temp;
			} else throw new NoSuchElementException("No next node to remove");
		}
	}
	public void print() {
		ListNode <E> ln = head;
		System.out.print("List is: " + ln.element);
		for (int i=1; i < num_nodes; i++) {
			ln = ln.next;
			System.out.print(", " + ln.element);}
			System.out.println(".");
	}
}
