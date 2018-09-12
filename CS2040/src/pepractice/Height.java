package pepractice;
/*
 * Name       : 
 * Matric No. :
 * Plab Acct. :
 */
import java.util.*;

public class Height {
	HashMap<Integer, Long> map;
	ArrayList<ArrayList<Edge>> adjacencyList;
	private void search(int x){
		Long height = map.get(x);
		for(int i = 0; i < adjacencyList.get(x).size(); i++){
			Edge edge = adjacencyList.get(x).get(i);
			if(map.get(edge.neighbor) == Long.MAX_VALUE){
				map.put(edge.neighbor, edge.distance + height);
				search(edge.neighbor);
			}
		}
	}
	
    private void run() {
    	Scanner input = new Scanner(System.in);
        int N = input.nextInt();
        map = new HashMap<>();
        adjacencyList = new ArrayList<ArrayList<Edge>>();
        for(int i = 0; i < N; i++){
        	adjacencyList.add(new ArrayList<Edge>());
        	map.put(i, Long.MAX_VALUE);
        }
        for(int i = 0; i < N - 1; i++){
        	int first = input.nextInt() - 1;
        	int second = input.nextInt() - 1;
        	int distance = input.nextInt();
        	adjacencyList.get(first).add(new Edge(second, distance));
        	adjacencyList.get(second).add(new Edge(first, -distance));
        }
        map.put(0, 0l);
        search(0);
        int Q = input.nextInt();
        for(int i = 0; i < Q; i++){
        	int first = input.nextInt() - 1;
        	int next = input.nextInt() - 1;
        	System.out.println(map.get(next) - map.get(first));
        }
        
    }
    public static void main(String[] args) {
        Height newHeight = new Height();
        newHeight.run();
    }
}

class Edge{
	public int neighbor;
	public int distance;	
	Edge(int neighbor, int distance){
		this.neighbor = neighbor;
		this.distance = distance;
	}
}
