import java.util.*;

public class Radiation {
	Queue<Element> element_queue = new LinkedList<Element>();

	public void run() {
		int min = Integer.MAX_VALUE;
		int latestmin = Integer.MAX_VALUE;;
		int latestminlevel = 0;
		int max = Integer.MIN_VALUE;
		int decreasingnumber = 0;
		int thisnumber = 0;
		int lastlevel = 0;
		int lastnumber = 0;
		Scanner input = new Scanner(System.in);
        int operation_number = input.nextInt();
    	for(int i = 0; i < operation_number; i++){
    		thisnumber = input.nextInt();
    		if(thisnumber <= min){
    			min = thisnumber;
    			latestmin = min;
    			latestminlevel = 0;
    			lastnumber = thisnumber;
    			lastlevel = 0;
    			element_queue.add(new Element(thisnumber, 0));
    		} else if(thisnumber > lastnumber){
    			latestmin = lastnumber;
    			latestminlevel = lastlevel;
    			decreasingnumber = 0;
    			lastnumber=thisnumber;
    			lastlevel = 1;
    			element_queue.add(new Element(thisnumber, 1));
    		} else if(thisnumber <= lastnumber && thisnumber > latestmin){
    			decreasingnumber++;
       			lastnumber=thisnumber;
    			lastlevel = decreasingnumber + 1;
    			element_queue.add(new Element(thisnumber, lastlevel));
    		} else {
    			if(lastnumber > latestmin){
    				decreasingnumber++;
    				lastnumber=thisnumber;
        			lastlevel = decreasingnumber > latestminlevel? decreasingnumber + 1 : latestminlevel + 1;
        			element_queue.add(new Element(thisnumber, lastlevel));
    			} else {
    				decreasingnumber++;
    				lastnumber=thisnumber;
        			lastlevel++;
        			element_queue.add(new Element(thisnumber, lastlevel));
    			}    			
    		}
	    }
    	for(Element e: element_queue){
    		System.out.println(e.getYearsBeforeDecay());
    		if(e.getYearsBeforeDecay() > max){
    			max = e.getYearsBeforeDecay();
    		}
    	}
    	System.out.println(max);
	}
	public static void main(String[] args) {
		Radiation myChemicalElements = new Radiation();
		myChemicalElements.run();
	}
}

class Element {
	private int strength;
	private int yearsBeforeDecay;
	
	public Element(int strength, int yearsBeforeDecay) {
		this.strength = strength;
		this.yearsBeforeDecay = yearsBeforeDecay;
	}
	
	public int getStrength() {
		return this.strength;
	}
	
	public int getYearsBeforeDecay() {
		return this.yearsBeforeDecay;
	}
}