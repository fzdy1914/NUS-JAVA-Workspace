package lab3;

/*
 * Name       : 
 * Matric No. :
 * Plab Acct. :
 */
import java.util.*;

public class Counting {
	public static void mergeSort(int[] a, int i, int j){
		if (i < j) {
			int mid = (i+j)/2;
			mergeSort(a,i,mid);
			mergeSort(a,mid+1,j);
			merge(a,i,mid,j);
		}
	}
	
	public static void merge(int[]a, int i, int mid, int j){
		int[] t = new int[j-i+1];
		int left=i, right=mid+1, it=0;
		
		while (left<=mid && right<=j) {
			if (a[left] <=a[right]){
				t[it++] = a[left++];
			}else{
				t[it++] = a[right++];
			}
		}
		while (left<=mid) {
			t[it++] = a[left++];
		}
		while (right<=j) {
			t[it++] = a[right++];
		}
		for (int k=0;k<t.length;k++){
			a[i+k] = t[k];
		}
	}
	
	public static int binaryfindmin(int [] a, int x, int low, int high){
		
		if (low >= high){
		    return low;
		}
		int mid = (low + high) / 2;
		if (a[mid] < x && a[mid + 1] < x){
			return binaryfindmin (a, x, mid + 1, high);
		} else if (a[mid] >= x) {
			return binaryfindmin (a, x, low, mid -1);
		} else {
			return mid;
		}
	}
	
	public static int binaryfindmin (int[] a, int x) {
		return binaryfindmin (a,x,0,a.length-1) + 1;
	}
	
    private void run() {
    	Scanner input = new Scanner(System.in);
        int num = input.nextInt();
        int[] a = new int[num];
    	for(int i = 0; i < num; i++){
    		a[i] =input.nextInt();
    	}
    	
    	mergeSort(a, 0, a.length - 1);
    	int opnum = input.nextInt();
    	for(int i = 0; i < opnum; i++){
    		int q = input.nextInt();
    		if(q < a[0]){
    			System.out.println("Smaller: 0, Greater: " +a.length);
    		} else if (q == a[0]){
    			System.out.println("Smaller: 0, Greater: " + (a.length - binaryfindmin(a,q+1)));
    		} else {
    			System.out.println("Smaller: "+binaryfindmin(a, q)+ ", Greater: " + (a.length - binaryfindmin(a,q+1)));
    		}
    		
    	}
    	
    }
    
    
    public static void main(String[] args) {
        Counting newCounting = new Counting();
        newCounting.run();
    }
}

