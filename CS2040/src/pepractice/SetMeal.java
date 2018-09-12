package pepractice;
/*
 * Name       : 
 * Matric No. :
 * Plab Acct. :
 */
import java.util.*;

public class SetMeal {
    private void run() {
    	Scanner input = new Scanner(System.in);
        int numOfFriends = input.nextInt();
        int numOfMains = input.nextInt();
        int numOfSides = input.nextInt();
        PriorityQueue<Integer> mains = new PriorityQueue<>(numOfMains);
        PriorityQueue<Integer> sides = new PriorityQueue<>(numOfMains);
        int[] minMains = new int[numOfFriends];
        int[] minSides = new int[numOfFriends];
        for(int i = 0; i < numOfMains; i++){
        	mains.add(input.nextInt());
        }
        for(int i = 0; i < numOfSides; i++){
        	sides.add(input.nextInt());
        }
        for(int i = 0; i < numOfFriends; i++){
        	minMains[i] = mains.poll();
        }
        for(int i = 0; i < numOfFriends; i++){
        	minSides[i] = sides.poll();
        }
        int tempMax = 0;
        for(int i = 0; i < numOfFriends; i++){
        	int temp = minSides[i] + minMains[numOfFriends - 1 - i];
        	if(temp > tempMax) {
        		tempMax = temp;
        	}
        }
        System.out.println(tempMax);        
    }
    public static void main(String[] args) {
        SetMeal newSetMeal = new SetMeal ();
        newSetMeal.run();
    }
}
