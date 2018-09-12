package sg.edu.nus.cs2020;

public class StringInitialization {

	public static void main(String[] args) {
		String a = "Singiaipioire";
		String b = "Singiaipioire";

		String[] c = a.split("i");
		String[] d = a.split("i");

		System.out.println(a == b);
		System.out.println(c == d);
		System.out.println(c[1]);
		// You should see the difference between ways of string initialization.
	}

}
