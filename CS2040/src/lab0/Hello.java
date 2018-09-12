package lab0;

import java.util.*;

public class Hello{
    public static void method1(String x){
	if(x.length()==7){
	    if(x.charAt(4)=='0'||x.charAt(6)=='0'){
            System.out.println("0");
        } else {
                System.out.println("1");
            }
    } else if(x.length()==6){   
            if(x.charAt(3)=='0'&&x.charAt(5)=='0'){
                System.out.println("0");
            } else {
                 System.out.println("1");
            }  
        } 
    }
public static void main(String[] args) {
    Scanner scan= new Scanner(System.in);
    int method= scan.nextInt();
    String x;
    switch(method){
        case 1:
            int number = scan.nextInt();
            scan.nextLine();
            for(int i = 0;i < number; i++){
                x = scan.nextLine();
                method1(x);
            }
            break;
        case 2:
            scan.nextLine();
            x = scan.nextLine();
            while(!x.equals("0")){
                method1(x);
        	x = scan.nextLine();
            }
            break;
        case 3:
            scan.nextLine();
            while(scan.hasNextLine()){
        	    x = scan.nextLine();
        	    method1(x);        	
            }
            break;
        }
        scan.close();
    }
}
