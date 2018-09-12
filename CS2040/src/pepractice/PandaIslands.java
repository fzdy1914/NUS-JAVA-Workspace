package pepractice;
/*
 * Name       : 
 * Matric No. :
 * Plab Acct. :
 */
import java.util.*;

public class PandaIslands {
	
    private void run() {
        Scanner input = new Scanner(System.in);
        int numOfIsland = input.nextInt();
        int numOfEdge = input.nextInt();
        ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<>();
        int[] areas = new int[numOfIsland];
        HashSet<Integer> all = new HashSet<>();
        Long sum = 0l;
        for(int i = 0; i < numOfIsland; i++){
        	adjacencyList.add(new ArrayList<Integer>());
        	areas[i] = input.nextInt();
        	all.add(i);
        }
        for(int i = 0; i < numOfEdge; i++){
        	int first = input.nextInt() - 1;
        	int second = input.nextInt() - 1;
        	adjacencyList.get(first).add(second);
        	adjacencyList.get(second).add(first);
        }
        while(!all.isEmpty()){
		    Stack<Integer> s = new Stack<>();
		    HashSet<Integer> setA = new HashSet<>();
		    Long sumA = 0l;
		    HashSet<Integer> setB = new HashSet<>();
		    Long sumB = 0l;
		    Iterator<Integer> iter = all.iterator();
		    int newone = iter.next();
		    s.push(newone);
		    setA.add(newone);
		    sumA += areas[newone];
		    all.remove(newone);
		    while(!s.isEmpty()){
		    	int temp = s.peek();
		    	boolean flag = true;
		    	if(setA.contains(temp)){
		    		for(int i = 0; i < adjacencyList.get(temp).size(); i++){
		        		int next = adjacencyList.get(temp).get(i);
		        		if(!setB.contains(next)){
		        			s.push(next);
		        			setB.add(next);
		        			sumB += areas[next];
		        			all.remove(next);
		        			adjacencyList.get(temp).remove(i);
		        			flag = false;
		        			break;
		        		}
		        	}
		    	} else {
		    		for(int i = 0; i < adjacencyList.get(temp).size(); i++){
		        		int next = adjacencyList.get(temp).get(i);
		        		if(!setA.contains(next)){
		        			s.push(next);
		        			setA.add(next);
		        			sumA += areas[next];
		        			all.remove(next);
		        			adjacencyList.get(temp).remove(i);
		        			flag = false;
		        			break;
		        		}
		        	}
		    	}
		    	if(flag){
		    		s.pop();
		    	}
		    }
		    sum += sumA > sumB ? sumB : sumA;
        }
        System.out.println(sum);
        
    }
    public static void main(String[] args) {
        PandaIslands newPandaIslands = new PandaIslands();
        newPandaIslands.run();
    }
}
