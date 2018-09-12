package lab4;
/*
 * Name       : 
 * Matric No. :
 * Plab Acct. :
 */
import java.util.*;

public class Ballot {
    private void run() {
    	Scanner input = new Scanner(System.in);
        int numOfStudent = input.nextInt();
        int numOfChoise = input.nextInt();
        TreeMap<Integer, Integer> tree = new TreeMap<>();
    	for(int i = 0; i < numOfStudent; i++){
    		tree.put(input.nextInt(), i + 1);
    	}
    	for(int i = 0; i < numOfChoise; i++){
    		int choice = input.nextInt();
    		Map.Entry<Integer, Integer> floorentry = tree.floorEntry(choice);
    		Map.Entry<Integer, Integer> ceilentry = tree.ceilingEntry(choice);
    		if(floorentry == null){
    			System.out.println(ceilentry.getValue());
    			tree.remove(ceilentry.getKey());
    		} else if (ceilentry == null){
    			System.out.println(floorentry.getValue());
    			tree.remove(floorentry.getKey());
    		} else if((choice - floorentry.getKey()) <= (ceilentry.getKey() - choice)){
    			System.out.println(floorentry.getValue());
    			tree.remove(floorentry.getKey());
    		} else {
    			System.out.println(ceilentry.getValue());
    			tree.remove(ceilentry.getKey());
    		}
    	}
    }
    public static void main(String[] args) {
        Ballot newBallot = new Ballot();
        newBallot.run();
    }
}
