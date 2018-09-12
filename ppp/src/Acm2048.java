import java.util.*;
public class Acm2048 {
	static int[][] a;

	static void print(int[][] b)
	{
		int i,j;
		for (i = 0; i < 4; i++)
		{
			for (j = 0; j < 4; j++)
			{
				System.out.print(b[i][j] + " ");
			}
			System.out.println("");
		}
	}
	static void up()
	{
		int i,check;
		for (i = 0; i < 4; i++)
		{
			if (a[0][i] == 0) { a[0][i] = a[1][i]; a[1][i] = a[2][i]; a[2][i] = a[3][i]; a[3][i] = 0; }
			if (a[0][i] == 0) { a[0][i] = a[1][i]; a[1][i] = a[2][i]; a[2][i] = a[3][i]; a[3][i] = 0; }
			if (a[0][i] == 0) {	a[0][i] = a[1][i]; a[1][i] = a[2][i]; a[2][i] = a[3][i]; a[3][i] = 0; }
			if (a[1][i] == 0) { a[1][i] = a[2][i]; a[2][i] = a[3][i]; a[3][i] = 0; }
			if (a[1][i] == 0) {	a[1][i] = a[2][i]; a[2][i] = a[3][i]; a[3][i] = 0; }
			if (a[2][i] == 0) { a[2][i] = a[3][i]; a[3][i] = 0; }


			if (a[0][i] == a[1][i] && a[2][i] == a[3][i]) 
			{
				a[0][i] *= 2; a[1][i] = 2 * a[2][i]; a[2][i] = 0; a[3][i] = 0; 
			}
			else if (a[0][i] == a[1][i]) 
			{
				a[0][i] *= 2; a[1][i] = a[2][i]; a[2][i] = a[3][i]; a[3][i] = 0;
			}
			else if (a[2][i] == a[1][i])
			{
				a[1][i] *= 2; a[2][i] = a[3][i]; a[3][i] = 0;
			}
			else if (a[2][i] == a[3][i])
			{
				a[2][i] *= 2; a[3][i] = 0;
			}
		}
	}

	static void down()
	{
		int i,check;
		for (i = 0; i < 4; i++)
		{
			if (a[3][i] == 0) { a[3][i] = a[2][i]; a[2][i] = a[1][i]; a[1][i] = a[0][i]; a[0][i] = 0; }
			if (a[3][i] == 0) { a[3][i] = a[2][i]; a[2][i] = a[1][i]; a[1][i] = a[0][i]; a[0][i] = 0; }
			if (a[3][i] == 0) { a[3][i] = a[2][i]; a[2][i] = a[1][i]; a[1][i] = a[0][i]; a[0][i] = 0; }
			if (a[2][i] == 0) { a[2][i] = a[1][i]; a[1][i] = a[0][i]; a[0][i] = 0; }
			if (a[2][i] == 0) { a[2][i] = a[1][i]; a[1][i] = a[0][i]; a[0][i] = 0; }
			if (a[1][i] == 0) { a[1][i] = a[0][i]; a[0][i] = 0; }


			if (a[0][i] == a[1][i] && a[2][i] == a[3][i])
			{
				a[3][i] *= 2; a[2][i] = 2 * a[1][i]; a[1][i] = 0; a[0][i] = 0;
			}
			else if (a[2][i] == a[3][i])
			{
				a[3][i] *= 2; a[2][i] = a[1][i]; a[1][i] = a[0][i]; a[0][i] = 0;
			}
			else if (a[2][i] == a[1][i])
			{
				a[2][i] *= 2; a[1][i] = a[0][i]; a[0][i] = 0;
			}
			else if (a[1][i] == a[0][i])
			{
				a[1][i] *= 2; a[0][i] = 0;
			}
		}
	}

	static void left()
	{
		int i,check;
		for (i = 0; i < 4; i++)
		{
			if (a[i][0] == 0) { a[i][0] = a[i][1]; a[i][1] = a[i][2]; a[i][2] = a[i][3]; a[i][3] = 0; }
			if (a[i][0] == 0) { a[i][0] = a[i][1]; a[i][1] = a[i][2]; a[i][2] = a[i][3]; a[i][3] = 0; }
			if (a[i][0] == 0) { a[i][0] = a[i][1]; a[i][1] = a[i][2]; a[i][2] = a[i][3]; a[i][3] = 0; }
			if (a[i][1] == 0) { a[i][1] = a[i][2]; a[i][2] = a[i][3]; a[i][3] = 0; }
			if (a[i][1] == 0) { a[i][1] = a[i][2]; a[i][2] = a[i][3]; a[i][3] = 0; }
			if (a[i][2] == 0) { a[i][2] = a[i][3]; a[i][3] = 0; }


			if (a[i][0] == a[i][1] && a[i][2] == a[i][3])
			{
				a[i][0] *= 2; a[i][1] = 2 * a[i][2]; a[i][2] = 0; a[i][3] = 0;
			}
			else if (a[i][0] == a[i][1])
			{
				a[i][0] *= 2; a[i][1] = a[i][2]; a[i][2] = a[i][3]; a[i][3] = 0;
			}
			else if (a[i][2] == a[i][1])
			{
				a[i][1] *= 2; a[i][2] = a[i][3]; a[i][3] = 0;
			}
			else if (a[i][2] == a[i][3])
			{
				a[i][2] *= 2; a[i][3] = 0;
			}
		}
	}

	static void right()
	{
		int i,check;
		for (i = 0; i < 4; i++)
		{
			if (a[i][3] == 0) { a[i][3] = a[i][2]; a[i][2] = a[i][1]; a[i][1] = a[i][0]; a[i][0] = 0; }
			if (a[i][3] == 0) { a[i][3] = a[i][2]; a[i][2] = a[i][1]; a[i][1] = a[i][0]; a[i][0] = 0; }
			if (a[i][3] == 0) { a[i][3] = a[i][2]; a[i][2] = a[i][1]; a[i][1] = a[i][0]; a[i][0] = 0; }
			if (a[i][2] == 0) { a[i][2] = a[i][1]; a[i][1] = a[i][0]; a[i][0] = 0; }
			if (a[i][2] == 0) { a[i][2] = a[i][1]; a[i][1] = a[i][0]; a[i][0] = 0; }
			if (a[i][1] == 0) { a[i][1] = a[i][0]; a[i][0] = 0; }


			if (a[i][0] == a[i][1] && a[i][2] == a[i][3])
			{
				a[i][3] *= 2; a[i][2] = 2 * a[i][1]; a[i][1] = 0; a[i][0] = 0;
			}
			else if (a[i][2] == a[i][3])
			{
				a[i][3] *= 2; a[i][2] = a[i][1]; a[i][1] = a[i][0]; a[i][0] = 0;
			}
			else if (a[i][2] == a[i][1])
			{
				a[i][2] *= 2; a[i][1] = a[i][0]; a[i][0] = 0;
			}
			else if (a[i][1] == a[i][0])
			{
				a[i][1] *= 2; a[i][0] = 0;
			}
		}
	}


	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		a = new int [4][4];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j ++) {
				a[i][j] = input.nextInt();
			}
		}
		switch(input.nextInt()) {
		case 0: left(); break;
		case 1: up(); break;
		case 2: right(); break;
		case 3: down(); break;
		}
		print(a);

	}

}
