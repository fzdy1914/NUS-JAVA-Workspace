package lab3;
/*
 * Name       : 
 * Matric No. :
 * Plab Acct. :
 */
import java.util.*;

public class Olevel {
	public static void mergeSort(Student[] a, int i, int j){
		if (i < j) {
			int mid = (i+j)/2;
			mergeSort(a,i,mid);
			mergeSort(a,mid+1,j);
			merge(a,i,mid,j);
		}
	}
	
	public static void merge(Student[]a, int i, int mid,int j){
		Student[] t = new Student[j-i+1];
		int left=i, right=mid+1, it=0;
		
		while (left<=mid && right<=j) {
			if (a[left].score <=a[right].score){
				t[it++] = a[left++];
			}else{
				t[it++] = a[right++];
			}
		}
		while (left<=mid) {
			t[it++] = a[left++];
		}
		while (right<=j) {
			t[it++] = a[right++];
		}
		for (int k=0;k<t.length;k++){
			a[i+k] = t[k];
		}
	}
    private void run() {
    	Scanner input = new Scanner(System.in);
        int stuNum = input.nextInt();
        int courseNum = input.nextInt();
        Student[] students = new Student[stuNum];
        int[] choice = new int[stuNum];
        int[] course = new int[courseNum];
    	for(int i = 0; i < courseNum; i++){
    		course[i] =input.nextInt();
    	}
  	
    	for(int i = 0; i < stuNum; i++){
    		students[i] =new Student(input.nextInt(), i);
    		for(int j = 0; j < 5; j++){
    			students[i].choice[j] = input.nextInt() - 1;
    		}
    	}
    	mergeSort(students, 0, students.length - 1);
    	for(int i = 0; i < stuNum; i++){
    		boolean ok = false;
    	    for(int j = 0; j < 5; j++){
    		    if(course[students[i].choice[j]] > 0){
    		    	course[students[i].choice[j]] --;
    		    	choice[students[i].num] = students[i].choice[j];
    		    	ok = true;
    		    	break;
    		    } 	
    		}
    	    if(!ok){
    	    	choice[students[i].num] = -2;
    	    }
    	}
    	for(int i = 0; i < stuNum; i++){
    		System.out.println(choice[i] + 1);
    	}
    	
    	
    	
    }
    public static void main(String[] args) {
        Olevel newOlevel = new Olevel();
        newOlevel.run();
    }
}

class Student{
	public int score;
	public int num;
	public int[] choice = new int[5];
	
	Student(int score, int num){
		this.score = score;
		this.num = num;
	}
}