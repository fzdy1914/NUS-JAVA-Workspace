package lab1;
/**
 * Name         :WANG CHAO
 * Matric No.   :A0177381X
 * PLab Acct.   :
 */

import java.util.*;

import java.math.BigInteger;

//Replace this with your solution from the previous Take Home Lab

class Modulo {
    private int value;      //To store the value of the number, must be non-negative and less than the modulus
    private int modulus;    //To store the modulus of the Modulo
    
    // Add more attributes, constructor and methods here if required
    
    /**
     * Constructor class for Modulo
     * Pre-condition: Modulus cannot be zero and value cannot be negative
     */
    Modulo (int value, int modulus) {
        if (modulus == 0) {
            throw new IllegalArgumentException("Modulus cannot be zero.");
        }
        this.value = value % modulus;
        this.modulus = modulus;
    }
    
    /**
     * Returns the value of the Modulo value
     */
    public int getValue(){
        return value;
    }
    
    /**
     * Adds two Modulo values and returns a new Modulo value as a result
     * Pre-condition: Modulus must be the same
     */
    public Modulo plus(Modulo other) {
        if (modulus != other.modulus) {
            throw new IllegalArgumentException("Modulus do not match.");
        }
        int new_value = this.value + other.value;
        return new Modulo(new_value, modulus);    
    }
    
    /**
     * Subtracts two Modulo values and returns a new Modulo value as a result
     * Pre-condition: Modulus must be the same
     */
    public Modulo minus(Modulo other) {
        if (modulus != other.modulus) {
            throw new IllegalArgumentException("Modulus do not match.");
        }
        int new_value = this.value - other.value;
        for(;new_value < 0;){
        	new_value = new_value + modulus;
        }
        return new Modulo(new_value, modulus);    
    }
    
    /**
     * Multiples two Modulo values and returns a new Modulo value as a result
     * Pre-condition: Modulus must be the same
     */
    public Modulo times(Modulo other) {
        if (modulus != other.modulus) {
            throw new IllegalArgumentException("Modulus do not match.");
        }
        //Write code here
        int new_value = this.value * other.value;
        return new Modulo(new_value, modulus);     
    }
    
    
    /**
     * Divides two Modulo values and returns a new Modulo value as a result
     * Pre-condition: Modulus must be the same and division must be valid
     */
    public Modulo divide(Modulo other) {
        if (modulus != other.modulus) {
            throw new IllegalArgumentException("Modulus do not match.");
        }
        Modulo other_inverse = other.findInverse();
        return this.times(other_inverse);   
    }
    
    /**
     * Find modular inverse of the Modulo value
     * Pre-condition: Inverse must exist
     */
    
    public Modulo findInverse() {
        int xx = 0;
        int x = 1;
        int yy = 1;
        int y = 0;
        int a = this.value;
        int b = this.modulus;
        while (b > 0) {
            int q = a / b;
            int t = b; 
            b = a % b; 
            a = t;
            t = xx; 
            xx = x - q * xx; 
            x = t;
            t = yy;
            yy = y - q * yy; 
            y = t;
        }
        if (a > 1) {
            throw new ArithmeticException("Inverse does not exist.");
        } else {
            return new Modulo((x + this.modulus) % this.modulus, this.modulus);
        }
    }
}


class RSAUser {
    private String name;
    private int N;
    private int M;
    private int privateKey;
    private int publicKey;
    
    // Add more attributes, constructor and methods here if required
    
    /**
     * Constructor class for RSAUser
     * Pre-condition: prime1 and prime2 are positive primes
     */
    RSAUser (String name, int prime1, int prime2, int publicKey) {
        this.name = name;
        this.N = prime1 * prime2;
        this.M = (prime1 - 1) * (prime2 - 1);
        this.publicKey = publicKey;
        Modulo helper = new Modulo(publicKey, this.M);
        this.privateKey = helper.findInverse().getValue();
        
    }
    
    /**
     * Returns name of the RSAUser
     */
     public String getName(){
         return name;
     }
    
    /**
     * Encrypt an array of integers using RSA
     * Pre-condition: Each integer is non-negative and less than the modulus
     */
    public String encrypt(String message) {
        int[] intArray= StringToIntArray(message);
        for(int i = 0; i < intArray.length; i++){
        	Modulo thisModulo = new Modulo(intArray[i], this.N);
        	for(int j = 0; j < this.publicKey - 1; j++){
        		thisModulo = thisModulo.times(new Modulo(intArray[i], this.N));
        	}
        	intArray[i] = thisModulo.getValue();
        }
        return IntArrayToString(intArray);
    }
    
    /**
     * Decrypt an array of integers using RSA
     * Pre-condition: Each integer is non-negative and less than the modulus
     */
    public String decrypt(String message) {
    	int[] intArray= StringToIntArray(message);
        for(int i = 0; i < intArray.length; i++){
        	Modulo thisModulo = new Modulo(intArray[i], this.N);
        	for(int j = 0; j < this.privateKey - 1; j++){
        		thisModulo = thisModulo.times(new Modulo(intArray[i], this.N));
        	}
        	intArray[i] = thisModulo.getValue();
        }
        return IntArrayToString(intArray);
    }
    
    /**
     * Convert a string to an integer array for RSA
     * Pre-condition: Message only contains capital letters and spaces
     */
    private int[] StringToIntArray(String message) {
        BigInteger value = BigInteger.valueOf(0);
        for(int i = 0; i < message.length(); i++){
            int ch = 0;
            if(message.charAt(i) != ' '){
                ch = (int)message.charAt(i) - 64;
            }
            value = value.multiply(BigInteger.valueOf(27)).add(BigInteger.valueOf(ch));
        }
        ArrayList<Integer> array = new ArrayList<Integer>();
        while(value.compareTo(BigInteger.valueOf(0)) > 0){
            array.add(value.mod(BigInteger.valueOf(N)).intValue());
            value = value.divide(BigInteger.valueOf(N));
        }
        int[] intArray = new int[array.size()];
        for(int i = 0; i < array.size(); i++){
            intArray[i] = array.get(i);
        }
        return intArray;
    }
    
    /**
     * Convert an integer array back to a string
     * Pre-condition: Message contains non-negative numbers less than the modulus
     */
    private String IntArrayToString(int[] array) {
        BigInteger value = BigInteger.valueOf(0);
        for(int i = array.length - 1; i >= 0; i--){
            value = value.multiply(BigInteger.valueOf(N)).add(BigInteger.valueOf(array[i]));
        }
        String message = "";
        while(value.compareTo(BigInteger.valueOf(0)) > 0){
            int ch = value.mod(BigInteger.valueOf(27)).intValue();
            if(ch == 0){
                message = ' ' + message;
            } else{
                message = (char)(ch + 64) + message;
            }
            value = value.divide(BigInteger.valueOf(27));
        }
        return message;
    }
}

public class RSA {
    
    public static void main(String[] args) {
    	Scanner input = new Scanner(System.in);
    	RSAUser[] Users = new RSAUser[20];
    	int friends_number = input.nextInt();
    	for(int i = 0; i < friends_number; i++){
    		Users[i] = new RSAUser(input.next(), input.nextInt(), input.nextInt(),input.nextInt());
    	}
    	int operation_number = input.nextInt();
    	for(int i = 0; i < operation_number; i++){
    		String name = input.next();
    		String op = input.next();
    		input.nextLine();
    		String message = input.nextLine();
    		for(int j = 0; j < friends_number; j++){
    			if(Users[j].getName().equals(name)){
    				switch(op) {
    				case "ENCRYPT":
    					System.out.println(Users[j].encrypt(message));
    					break;
    				case "DECRYPT":
    					System.out.println(Users[j].decrypt(message));
    					break;
    				}
    			}
    		}
    	}
    	input.close();
    }
}