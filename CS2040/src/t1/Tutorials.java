package t1;
import java.util.*;

interface TutorialGroup {
	/* get this tutorial group's name e.g. "T05" */
	String getGroupNum();
	/* get Student name at specified sNo, i.e. position in classlist
	*/
	String getStudentAt(int sNo);
	/* add Std. name after the last Student (including any dummies)
	*/
	void addStudent(String student);
	/* replace Std. name at given sNo if that posn. is within classlist;
	* otherwise pad dummy Student(s) with name "" (empty String)
	*/
	void setStudentAt(int sNo, String newStudent);
	/* truncate classlist to given size without reordering any Student;
	* will not expand the classlist
	*/
	void shrinkClassSize(int newSize);
	/* return representation of class list with the format below:
	* e.g. "[1:Ridi, 2:Van, 3:Abdul, 4:Samuel, 5:, 6:Ian, 7:, 8:]"
	*/
	String toString();
}

class TutorialGroupImpl implements TutorialGroup {
	private String _groupNum;
	private List<String> _students;
	/* implement c'tor
	* use array-based list to store student names
	* name with sNo 1 should physically be at index 0 of the list
	*/
	public TutorialGroupImpl(String groupNum) {
		_students = new ArrayList<String>();
		_groupNum = groupNum;
		
	}
	/* implement the 6 methods */
	public String getGroupNum() {
		return _groupNum; 
	}
	public String getStudentAt(int sNo) {
		return _students.get(sNo - 1); 
	}
	public void addStudent(String student) {
		_students.add(student);
	}
	public void setStudentAt(int sNo, String newStudent) {
		if(sNo <= _students.size()){
			_students.set(sNo - 1, newStudent);
		} else {
			_students.add("");
			setStudentAt(sNo, newStudent);
		}
	}
	public void shrinkClassSize(int newSize) {
		if(newSize <= _students.size()){
			_students = _students.subList(0, newSize);
		} else {
			System.out.println("Do not expand the list!!!");
		}
	}
	public String toString() {
		String out = "[";
		for(int i = 0; i < _students.size() - 1; i++){
			out = out + (i + 1) + ":" + _students.get(i) + ", ";
		}
		out = out + _students.size() + ":" + _students.get(_students.size() - 1) +"]";
		return out;
    }
}



public class Tutorials {

	public static void main(String[] args) {
		Tutorials tut = new Tutorials();
		tut.runTest();

	}
	
	public void runTest() {
		TutorialGroupImpl tut5 = new TutorialGroupImpl("T05");
		tut5.addStudent("Ridi");
		tut5.addStudent("Van");
		tut5.addStudent("Abdul");
		tut5.addStudent("Samuel");
		tut5.addStudent("");
		tut5.addStudent("Ian");
		tut5.addStudent("");
		tut5.addStudent("");
		tut5.shrinkClassSize(10);
		System.out.println(tut5.getGroupNum());
        System.out.println(tut5);
	}

}
