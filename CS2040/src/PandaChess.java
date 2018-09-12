/*
 * Name       : 
 * Matric No. :
 * Plab Acct. :
 */
import java.util.*;

public class PandaChess {
	
	private void run() {
		Scanner input = new Scanner(System.in);
		int N = input.nextInt();
		int E = input.nextInt();
		ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<ArrayList<Integer>>();
		HashSet<Integer> set = new HashSet<>();
		int[] inOrders = new int[N];
        for(int i = 0; i < N; i++){
        	adjacencyList.add(new ArrayList<Integer>());
        	set.add(i);
        }
        for(int i = 0; i < E; i++){
        	int first = input.nextInt() - 1;
        	int second = input.nextInt() - 1;
        	adjacencyList.get(first).add(second);
        	inOrders[second] ++;
        	set.remove(second);
        }
        Queue<Integer> queue= new LinkedList<>(set);
        List<Integer> list = new LinkedList<>();
        while(list.size() < N){
        	if(queue.size() == 0){
        		System.out.println("No possible ranking.");
        		break;
        	} else if (queue.size() > 1){
        		System.out.println("Ranking is not unique.");
        		break;
        	} 
        	int node = queue.poll();
        	list.add(node);
        	for(int i = 0; i < adjacencyList.get(node).size(); i++){
    			int neighbor = adjacencyList.get(node).get(i);
    			inOrders[neighbor] --;
    			if(inOrders[neighbor] == 0){
    				queue.add(neighbor);
    			}
    		}      	
        }
        if(list.size() == N){
        	for(int i : list){
            	System.out.println(i + 1);
            }
        }
    }
	public static void main(String[] args) {
		PandaChess newPandaChess = new PandaChess();
		newPandaChess.run();
	}
}