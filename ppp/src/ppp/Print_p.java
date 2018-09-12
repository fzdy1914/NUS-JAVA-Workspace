package ppp;
import java.util.*;
public class Print_p {
	
	public static void print1(int n){
		if(n <= 3){
			System.out.println(n);
		} else {
			print1(n - 1);
			print1(n - 2);
			print1(n - 3);
			
		}
	}
	
	public static void print2(int n){
		Stack<Integer> stack= new Stack<>();
		stack.add(n);
		while(!stack.isEmpty()){
			int todo = stack.pop();
			if(todo <= 3){
				System.out.println(todo);
			} else {
				stack.push(todo - 3);
				stack.push(todo - 2);
				stack.push(todo - 1);
			}
		}
	}
	
	

	public static void main(String[] args) {
        print2(10);
	}

}
