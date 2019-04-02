import java.io.File;
import java.io.IOException;
import java.util.*;

public class Test {
	Test test = null;

	static String a = "1";
	static String b = a + "c";

	public static void main(String[] args) {
		System.out.println(a);
		System.out.println(b);
		a = "111";
		System.out.println(a);
		System.out.println(b);

	}
	
	public void tt() {
		System.out.println("TT!");
	}

}
