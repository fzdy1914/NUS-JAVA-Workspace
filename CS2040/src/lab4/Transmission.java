package lab4;
/*
 * Name       : 
 * Matric No. :
 * Plab Acct. :
 */
import java.util.*;

public class Transmission {
    private void run() {
    	Scanner input = new Scanner(System.in);
        long numOfCats = input.nextLong();
        int numOfOp = input.nextInt();
        TreeSet<Long> tree = new  TreeSet<>();
        for(int i = 0; i < numOfOp; i++){
        	String op = input.next();
        	switch (op){
        		case "TRANSMIT":
        			long lower = input.nextLong();
        			long up = input.nextLong();
        			Long check = tree.ceiling(lower);
        			if(check == null || check > up){
        				System.out.println("YES");
        			} else {
        				System.out.println("NO");
        			}
        			break;
        		case "SLEEP":
        			tree.add(input.nextLong());
        			break;
        		case "WAKE":
        			tree.remove(input.nextLong());
        			break;
        	}
    	}
    }
    public static void main(String[] args) {
        Transmission newTransmission = new Transmission();
        newTransmission.run();
    }
}
