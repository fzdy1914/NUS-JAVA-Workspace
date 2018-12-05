import java.io.File;
import java.io.IOException;
import java.util.*;

public class Test {
	Test test = null;

	public static void main(String[] args) {
		Test t = new Test();
		t.test = new Test();
		t.test.test = t;
		System.out.println(t.test.test.test.test == t);


	}
	
	public void tt() {
		System.out.println("TT!");
	}

}
