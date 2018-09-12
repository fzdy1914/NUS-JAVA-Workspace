package lab2;
/**
 * Name         :
 * Matric No.   :
 * PLab Acct.   :
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
// Check with a LabTA before you decide to import anything else...
// Using other Collection classes and arrays might result in 0 marks

public class Printer {
	private Stack<String> output= new Stack<String>();
	private LinkedList<Printjob> normal_queue = new LinkedList<Printjob>();
	private LinkedList<Printjob> priority_queue = new LinkedList<Printjob>();
	
	public void print_job(Printjob thisjob, int real_time){
		output.push("COVER " + real_time + " " + thisjob.user + " " + thisjob.number);
		for(int i : thisjob.joblist){
			output.push("PHOTO " + i);
		}
	}
	
	public void print_in(){
		int time = 0;
		while(!normal_queue.isEmpty() || !priority_queue.isEmpty()){
			Printjob to_print = null;
			if(priority_queue.isEmpty()){
				to_print = normal_queue.getFirst();
				normal_queue.removeFirst();
			} else if(normal_queue.isEmpty()){
				to_print = priority_queue.getFirst();
				priority_queue.removeFirst();
			} else {
				Printjob N = normal_queue.getFirst();
				Printjob P = priority_queue.getFirst();
				if(P.time < N.time){
					to_print = P;
					priority_queue.removeFirst();
				} else if(P.time > time){
					to_print = N;
					normal_queue.removeFirst();
				} else {
					to_print = P;
					priority_queue.removeFirst();
				}
			}
			if(time < to_print.time){
				print_job(to_print, to_print.time);
				time = to_print.time + to_print.number + 1;
			} else {
				print_job(to_print, time);
				time = time + to_print.number + 1;
			}
		}
	}
	
	public void print(){
		while(!output.isEmpty()){
			System.out.println(output.pop());
		}
	}
	
	private void run() {
		Scanner input = new Scanner(System.in);
        int operation_number = input.nextInt();
    	for(int i = 0; i < operation_number; i++){
    		int this_time = input.nextInt();
    		String this_user = input.next();
    		int this_number = input.nextInt();
    		Printjob this_printjob = new Printjob(this_time, this_user, this_number);
    		for(int j = 0; j < this_number; j++){
    			this_printjob.add_paper(input.nextInt());
    		}
    		if(this_user.equals("proftan")){
    			priority_queue.add(this_printjob);
    		} else {
    			normal_queue.add(this_printjob);
    		}
    	}
    	this.print_in();
    	this.print();
    	input.close();
	}
	public static void main(String[] args) {
		Printer newPrinter = new Printer();
		newPrinter.run();
	}
}

class Printjob{
	int time;
	int number;
	String user;
	LinkedList<Integer> joblist = new LinkedList<Integer>();
	
	Printjob(int time,String user, int number){
		this.time = time;
		this.number = number;
		this.user = user;
	}
	public void add_paper(int new_photo){
		joblist.add(new_photo);
	}
	
}