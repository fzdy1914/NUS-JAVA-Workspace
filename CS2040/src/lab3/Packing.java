package lab3;
/*
 * Name       : 
 * Matric No. :
 * Plab Acct. :
 */
import java.util.*;

public class Packing {
	private boolean judge(int[]a, int x, int k) {
	    int group = 1, i;
	    int sum = 0;
	    for(i=0; i < a.length; i++) {
	        if(sum + a[i] <= x) {
	            sum += a[i];
	        }else {
	            group++;
	            sum = a[i];
	        }
	        if(group > k) return false;
	    }
	    return true;
	}
	
    private void run() {
    	Scanner input = new Scanner(System.in);
    	int max = 0, sum = 0, up, lower;
        int N = input.nextInt();
        int K = input.nextInt();
        int[] a = new int[N];
    	for(int i = 0; i < N; i++){
    		a[i] =input.nextInt();
    		sum+=a[i];
    		max = max > a[i] ? max : a[i];
    	}
    	up = sum;
    	lower = max;
    	while(up > lower) {
            int mid = (up + lower)/2;
            if(judge(a, mid, K)) {
                up=mid;
            } else {
            	lower=mid+1;
            }
        }
    	System.out.println(lower);
    }
    public static void main(String[] args) {
        Packing newPacking = new Packing();
        newPacking.run();
    }
}
