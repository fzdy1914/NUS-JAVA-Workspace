package pepractice;
/*
 * Name       : He Zhenfeng
 * Matric No. : A0177327X
 * Plab Acct. : 4
 */
import java.util.*;

public class ForeverAlone {
    private void run() {
        //implement your "main" method here
        HashSet<Integer> hashSet = new HashSet<Integer>();
		Scanner scan = new Scanner(System.in);
		int N = scan.nextInt();
		int M = scan.nextInt();
		int ans = N;
		for(int i = 1; i <= M; i++) {
			int num1 = scan.nextInt();
			int num2 = scan.nextInt();
			if(num1 != num2) {
				if(!hashSet.contains(num1)) {
					hashSet.add(num1);
					ans--;
				}
				if(!hashSet.contains(num2)) {
					hashSet.add(num2);
					ans--;
				}
			}
		}
		System.out.println(ans);
    }
    public static void main(String[] args) {
        ForeverAlone youAreForeverAlone = new ForeverAlone();
        youAreForeverAlone.run();
    }
}
