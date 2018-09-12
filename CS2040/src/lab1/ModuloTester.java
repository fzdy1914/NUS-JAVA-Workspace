package lab1;
/**
 * Name         :
 * Matric No.   :
 * PLab Acct.   :
 */

import java.util.*;

public class ModuloTester {
    
    public static void main(String[] args) {
    	Scanner inputScanner = new Scanner(System.in);
        while (inputScanner.hasNext()) {
            int first_value = inputScanner.nextInt();
            String op = inputScanner.next();
            int second_value = inputScanner.nextInt();
            int modulo = inputScanner.nextInt();
        	Modulo a = new Modulo(first_value, modulo);
        	Modulo b = new Modulo(second_value, modulo);
            Modulo result = new Modulo(1,1);
            switch (op) {
                case "TIMES":
                    result = a.times(b);
                    break;
                case "DIVIDE":
                    result = a.divide(b);
                    break;
                case "PLUS":
                    result = a.plus(b);
                    break;
                case "MINUS":
                    result = a.minus(b);
                    break;
                default:
                    break;
            }
            System.out.println(result.getValue());
        }
        inputScanner.close();   
    }
}