package ppp;
import java.util.*;
public class Stick {
	static int min = 99999999;
	public static boolean find(int[] a, int j, int sum){
		if(a.length == 0){
			return false;
		}
		if(sum == 0){
			Stick.min = Stick.min<j? Stick.min:j;
			return true;
		} else if(sum < 0){
			return false;
		} else {
			int thisone = a[a.length - 1];
			int[] next = new int[a.length - 1];
			for(int i = 0; i < a.length - 1; i ++){
				next[i]=a[i];
			}
			boolean aa = find(next, j, sum);
			boolean bb = find(next, j + 1, sum - thisone);
			return  aa||bb; 
		}
		
	}
	public static void main(String[] args) {
		int[] a = {1,1,1,3};


		int sum = 5;
		System.out.println(find(a, 0, sum));
		System.out.println(min);
		
	}

}
