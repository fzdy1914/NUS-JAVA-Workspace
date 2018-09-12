package lab0;


import java.util.Scanner;


public class Compare {
    public static int helper(int x){
    	if(x == 0){
    		return 0;
    	} else if (x > 0){
    		return 2;
    	} else {
    		return 1;
    	}
    }
	public static void main(String[] args) {
		Scanner scan= new Scanner(System.in);
		String s1 = scan.nextLine();
		s1 = s1.toLowerCase();
		String s2 = scan.nextLine();
		s2 = s2.toLowerCase();
		System.out.println(helper(s1.compareTo(s2)));
		scan.close();;
	}

}


