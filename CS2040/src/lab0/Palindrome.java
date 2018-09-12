package lab0;
/*
 * author		: []
 * matric no.	: []
 */


import java.util.*;

public class Palindrome {
	/* use this method to check whether the string is palindrome word or not
	 * 		PRE-Condition  :
	 * 		POST-Condition :
	 */
	public static boolean isPalindrome(String word) {
		int length = word.length();
		for(int i = 0; i < length/2; i++){
			if(word.charAt(i) != word.charAt(length - 1 -i)){
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		Scanner scan= new Scanner(System.in);
		String s1 = scan.nextLine();
		String s2 = scan.nextLine();
		String s = s1 + s2;
		System.out.println(isPalindrome(s)? "YES" : "NO");
		scan.close();
	}
}