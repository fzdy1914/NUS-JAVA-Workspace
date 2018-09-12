package pepractice;
/*
 * Name       : 
 * Matric No. :
 * Plab Acct. :
 */
import java.util.*;

public class Brackets {
    private void run() {
    	Scanner input = new Scanner(System.in);
        int numOfOp = input.nextInt();
        for(int i = 0; i < numOfOp; i++){
        	int numOfBrackets = input.nextInt();
        	boolean check = true;
        	Stack<Character> brackets = new Stack<>();
        	String tempString =input.next();
        	for(int j = 0; j < numOfBrackets; j++){
        		char temp = tempString.charAt(j);
        		if(!check){
        			continue;
        		}
        		switch(temp){
        		    case '(': 
        		    	brackets.push(temp);
        		    	break;
        		    case '{': 
        		    	brackets.push(temp);
        		    	break;
        		    case '[': 
        		    	brackets.push(temp);
        		    	break;
        		    case ')': 
        		    	if(brackets.isEmpty()){
        		    		check = false;
        		    		continue;
        		    	}
        		    	if(brackets.peek() == '('){
        		    		brackets.pop();
        		    	} else {
        		    		check =false;
        		    	}
        		    	break;
        		    case '}':
        		    	if(brackets.isEmpty()){
        		    		check = false;
        		    		continue;
        		    	}
        		    	if(brackets.peek() == '{'){
        		    		brackets.pop();
        		    	} else {
        		    		check =false;
        		    	}
        		    	break;
        		    case ']': 
        		    	if(brackets.isEmpty()){
        		    		check = false;
        		    		continue;
        		    	}
        		    	if(brackets.peek() == '['){
        		    		brackets.pop();
        		    	} else {
        		    		check =false;
        		    	}
        		    	break;
        		    
        		}
        	}
        	if(!brackets.isEmpty()){
	    		check = false;
	    	}
        	System.out.println(check ? "Valid" : "Invalid");
        }
    }
    public static void main(String[] args) {
        Brackets newBrackets = new Brackets();
        newBrackets.run();
    }
}
