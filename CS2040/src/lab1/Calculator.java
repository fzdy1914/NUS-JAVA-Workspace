package lab1;
/**
 * Name         :
 * Matric No.   :
 * PLab Acct.   :
 */


import java.util.*;
import java.math.BigInteger;

class Fraction {
    private int numerator;      //To store value of numerator
    private int denominator;    //To store value of denominator
    
    // Add more attributes, constructor and methods here if required
    
    /**
     * Constructor class for Fraction
     * Pre-condition: Denominator cannot be zero
     */
    Fraction (int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
    }
    
    /**
     * Adds two fraction and returns a new Fraction as a result
     */
    public Fraction plus(Fraction other) {
        int new_numerator = this.numerator * other.denominator + other.numerator * this.denominator;
        int new_dominator = this.denominator * other.denominator;
        return new Fraction(new_numerator, new_dominator);    
    }
    
    /**
     * Subtracts two fraction and returns a new Fraction as a result
     */
    public Fraction minus(Fraction other) {
    	int new_numerator = this.numerator * other.denominator - other.numerator * this.denominator;
        int new_dominator = this.denominator * other.denominator;
        return new Fraction(new_numerator, new_dominator);    
    }
    
    /**
     * Multiplies two fraction and returns a new Fraction as a result
     */
    public Fraction times(Fraction other) {
        int new_numerator = this.numerator * other.numerator;
        int new_denominator = this.denominator * other.denominator;
        
        return new Fraction(new_numerator, new_denominator);    
    }
    
    
    /**
     * Divides two fraction and returns a new Fraction as a result
     */
    public Fraction divide(Fraction other) {
    	int new_numerator = this.numerator * other.denominator;
        int new_denominator = this.denominator * other.numerator;
        
        return new Fraction(new_numerator, new_denominator);  
    }
    
    /**
     * Given two Fractions, return the smaller one.
     * If two Fractions are the same, return the first one.
     * This is a static method.
     */
    public static Fraction min(Fraction first, Fraction second) {
       if (first.numerator*second.denominator <= second.numerator*first.denominator) {
           return first;
       } else {
           return second;
       }
    }
    
    /**
     * Given two Fractions, return the larger one.
     * If two Fractions are the same, return the first one.
     * This is a static method.
     */
    public static Fraction max(Fraction first, Fraction second) {
    	if (first.numerator*second.denominator <= second.numerator*first.denominator) {
            return second;
        } else {
            return first;
        }
    }
    
    /**
     * Private method to simplify the Fraction
     * Pre-condition: Denominator cannot be zero
     */
    private void simplify() {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        } else if (denominator < 0) {
            numerator = - numerator;
            denominator = -denominator;
        }
        if (numerator != 0) {
            int gcd = BigInteger.valueOf(Math.abs(numerator)).gcd(BigInteger.valueOf(denominator)).intValue();
            
            numerator /= gcd;
            denominator /= gcd;
        } else {
            denominator = 1;
        }
    }

    /**
     * Converts the Fraction to string so that it can be printed
     */
    @Override
    public String toString() {
        this.simplify();
        return this.numerator + " " + this.denominator;
    }
}

public class Calculator {
    
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        while (inputScanner.hasNext()) {
            Fraction a = new Fraction(inputScanner.nextInt(), inputScanner.nextInt());
            String op = inputScanner.next();
            Fraction b = new Fraction(inputScanner.nextInt(), inputScanner.nextInt());
            
            Fraction result;
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
                case "MAX":
                    result = Fraction.max(a, b);
                    break;
                case "MIN":
                    result = Fraction.min(a, b);
                    break;
                // to be continued...
                default:
                    result = new Fraction(0, 1);
                    break;
            }
            System.out.println(result);
        }
        inputScanner.close();   
    }
}