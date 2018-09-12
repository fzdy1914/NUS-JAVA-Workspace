package ass0;
import java.util.*;
public class IPAddress {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
        String num = input.next();
        int sum[] = new int[4];

        for(int i = 0; i < 4; i++) {
        	sum[i] = 0;
        	for(int j = 8 * i ; j < 8 * i + 8; j++) {
        		sum[i] += (num.charAt(j) - '0') * Math.pow(2, 7 - j + 8 * i);
        	}
        }
        System.out.println(sum[0]+ "." + sum[1]+ "." + sum[2]+ "." +sum[3]);
	}

}
