/*
 * Name       : 
 * Matric No. :
 * Plab Acct. :
 */
import java.util.*;

public class Mileage {
    private void run() {
    	Scanner input = new Scanner(System.in);
		int N = input.nextInt();
		int E = input.nextInt();
		ArrayList<ArrayList<Edge>> adjacencyList = new ArrayList<ArrayList<Edge>>();
        for(int i = 0; i < N; i++){
        	adjacencyList.add(new ArrayList<Edge>());
        }
        for(int i = 0; i < E; i++){
        	int first = input.nextInt() - 1;
        	int second = input.nextInt() - 1;
        	int distance = input.nextInt();
        	adjacencyList.get(first).add(new Edge(second, distance));
        	adjacencyList.get(second).add(new Edge(first, distance));
        }
        HashSet<Integer> set = new HashSet<>();
        PriorityQueue<Edge> queue = new PriorityQueue<>((e1, e2) -> e1.distance - e2.distance);
        set.add(0);
        for(int i = 0; i < adjacencyList.get(0).size(); i++){
            queue.add(adjacencyList.get(0).get(i));
		}
        int max = Integer.MIN_VALUE;
        while(set.size() < N){
        	Edge edge = queue.poll();
        	if(set.contains(edge.neighbor)){
        		continue;
        	} else {
        		set.add(edge.neighbor);
        		if(edge.distance > max){
        			max = edge.distance;
        		}
        		if(edge.neighbor == N - 1){
        			break;
        		}
        		for(int i = 0; i < adjacencyList.get(edge.neighbor).size(); i++){
        			queue.add(adjacencyList.get(edge.neighbor).get(i));
        		}
        	}
        }
        System.out.println(max);
    }
    public static void main(String[] args) {
        Mileage newMileage = new Mileage();
        newMileage.run();
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