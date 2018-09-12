package pepractice;
/*
 * Name       : 
 * Matric No. :
 * Plab Acct. :
 */
import java.util.*;

public class Puddles {
	private void infect(int[][] a, int i,int j){
		if(i < 0 || i >= a.length || j < 0 || j >= a[0].length){
			return;
		} else {
			a[i][j] = 0;
			if(i <= a.length - 2 && a[i + 1][j] == 1){
				infect(a, i + 1, j);
			}
			if(i >= 1 && a[i - 1][j] == 1){
				infect(a, i - 1, j);
			}
			if(j <= a[0].length - 2 && a[i][j + 1] == 1){
				infect(a, i, j + 1);
			}
			if(j >= 1 && a[i][j - 1] == 1){
				infect(a, i, j - 1);
			}
		}
	}

    private void run() {
    	Scanner input = new Scanner(System.in);
        int numOfLines = input.nextInt();
        int numOfColumns = input.nextInt();
        int [][] puddles = new int[numOfLines][numOfColumns];
        for(int i = 0; i < numOfLines; i++){
        	String temp = input.next();
        	for(int j = 0; j < numOfColumns; j++){
        		puddles[i][j] = temp.charAt(j) == 'W' ? 1 : 0;
        	}
        }
        int num = 0;
        for(int i = 0; i < puddles.length; i++){
        	for(int j = 0; j < puddles[0].length; j++){
        		if(puddles[i][j] == 1) {
        			infect(puddles, i, j);
        			num ++;
        		}
        	}
        }
        System.out.println(num);
    }
    public static void main(String[] args) {
        Puddles newPuddles = new Puddles();
        newPuddles.run();
    }
}
