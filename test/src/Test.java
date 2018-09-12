import java.util.*;

public class Test {

	public static void main(String[] args) {
		int A = 12345;
		int result = 0;
		while(A!=0) {
			result = result * 10 + A % 10;
			A = A / 10;
		}
		System.out.println(result);
		
	}


}
class Pair {
	int x;
	int y;
	Pair(int x, int y){
		this.x = x;
		this.y = y;
	}
}
