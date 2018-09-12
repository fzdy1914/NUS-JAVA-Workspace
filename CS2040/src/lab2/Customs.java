package lab2;
import java.util.*;

public class Customs {
	Stack<Integer> queue = new Stack<Integer>();

	private void run() {
    	int max = 0;
    	int number = 0;
    	Scanner input = new Scanner(System.in);
        int operation_number = input.nextInt();
    	for(int i = 0; i < operation_number; i++){
    		String op = input.next();
    		switch(op) {
			case "join":
				int height = input.nextInt();
				if(height > max){
					max = height;
					number++;
				}
				queue.push(height);
				System.out.println(number);
				break;
			case "leave":
				int num = input.nextInt();
				for(int j = 0; j < num; j++){
					queue.pop();
				}
				number = 0;
				max = 0;
				Iterator<Integer> iterator = queue.iterator();
				while(iterator.hasNext()){
					int thisone = iterator.next();
				    if(thisone > max){
				    	max = thisone;
				    	number++;
				    }
				}
				System.out.println(number);
				break;
			}				
    	}    	
    	input.close();
    }
    
    public static void main(String[] args) {
        Customs newCustoms = new Customs();
        newCustoms.run();
    }
    
}