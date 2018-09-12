/*
 * Name       : 
 * Matric No. :
 * Plab Acct. :
 */
import java.util.*;

public class Supplies {
	class Edge{
		public int neighbor;
		public int distance;	
		Edge(int neighbor, int distance){
			this.neighbor = neighbor;
			this.distance = distance;
		}
	}
    private void run() {
    	Scanner input = new Scanner(System.in);
		int N = input.nextInt();
		int E = input.nextInt();
		int H = input.nextInt();
		int T = input.nextInt();
		ArrayList<ArrayList<Edge>> adjacencyList = new ArrayList<ArrayList<Edge>>();
		DijkstraHeap heap = new DijkstraHeap(N);
        int[] distances = new int[N];
        for(int i = 0; i < N; i++){
        	adjacencyList.add(new ArrayList<Edge>());
        	distances[i] = Integer.MAX_VALUE;
        }
        for(int i = 0; i < E; i++){
        	int first = input.nextInt();
        	int second = input.nextInt() ;
        	int distance = input.nextInt();
        	adjacencyList.get(first).add(new Edge(second, distance));
        }
        
        distances[H] = 0;
        heap.add(new DijkstraNode(H, 0));
        while(heap.size != 0){
        	DijkstraNode node = heap.remove();
        	for(int i = 0; i < adjacencyList.get(node.number).size(); i++){
    			Edge edge = adjacencyList.get(node.number).get(i);
    			int newDistance = node.distance + edge.distance;
    			if(newDistance < distances[edge.neighbor]){
    				distances[edge.neighbor] = newDistance;
    				heap.add(new DijkstraNode(edge.neighbor, newDistance));
    			}
    		}
        }
        int result = distances[T];
        if(result == Integer.MAX_VALUE) {
        	System.out.println(-1);
        } else {
        	for(int i = 0; i < N; i++){
            	distances[i] = Integer.MAX_VALUE;
            }
        	distances[T] = 0;
            heap.add(new DijkstraNode(T, 0));
            while(heap.size != 0){
            	DijkstraNode node = heap.remove();
            	for(int i = 0; i < adjacencyList.get(node.number).size(); i++){
        			Edge edge = adjacencyList.get(node.number).get(i);
        			int newDistance = node.distance + edge.distance;
        			if(newDistance < distances[edge.neighbor]){
        				distances[edge.neighbor] = newDistance;
        				heap.add(new DijkstraNode(edge.neighbor, newDistance));
        			}
        		}
            }
        	if(distances[H] == Integer.MAX_VALUE){
        		System.out.println(-1);
        	} else {
        		System.out.println(distances[H] + result);
        	}
        }
        
    }
    public static void main(String[] args) {
        Supplies newSupplies = new Supplies();
        newSupplies.run();
    }
}

class DijkstraNode {
	public int distance;
	public int number;
	public DijkstraNode parent;
	
	DijkstraNode(int number, int distance){
	    this.distance = distance;
	    this.number = number;
	}
}

class DijkstraHeap {
	public DijkstraNode[] array;
	public int size;
	public Integer[] indexs;
	
	public DijkstraHeap(int capacity) {
        array = new DijkstraNode[capacity];
        size = 0;
        indexs = new Integer[capacity];
    }
	
	public void add(DijkstraNode value) {
		if(indexs[value.number] != null){
			this.decreasekey(value.number, value.distance);
		} else {
		    int index = size;
		    array[index] = value;
		    indexs[value.number] = index;
		    size++;
		    bubbleUp(this.size - 1);
		}
    }
	
	public void swap(int index1, int index2) {
		indexs[array[index1].number] = index2;
		indexs[array[index2].number] = index1;
		DijkstraNode tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
       
    }
	
	public void bubbleDown(int index) {        
        while (index * 2 + 1 < size) {
            int smallerChild = index * 2 + 1;            
            if (index * 2 + 2 < size
                && array[index * 2 + 1].distance > array[index * 2 + 2].distance) {
                smallerChild = index * 2 + 2;
            }          
            if (array[index].distance > array[smallerChild].distance) {
                swap(index, smallerChild);
            } else {
                break;
            }
            index = smallerChild;
        }
    }
	
    public void bubbleUp(int index) {  
        while (index > 0
           && array[(index - 1) / 2].distance > array[index].distance) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }
    
    public DijkstraNode remove() {
    	DijkstraNode result = array[0];  
        array[0] = array[size - 1];
        indexs[array[0].number] = 0;
        array[size - 1] = null;
        size--;
        bubbleDown(0);
        indexs[result.number] = null;
        return result;
    }
    
    public void decreasekey(int index, int distance){
    	array[indexs[index]].distance = distance;
    	bubbleUp(indexs[index]);
    }
}


