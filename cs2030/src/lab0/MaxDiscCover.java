package lab0;
import java.lang.*;
import java.io.*;
import java.util.*;

/**
 * Solver for Maximum Disc Coverage.
 * Given a set of points,  find the maximum number of points
 * contained in the unit disc.  The input is read from the standard
 * input: the first line indicates the number of points n.
 * The next n lines contains the x and y coodinates of the points,
 * separated by a space, one point per line.
 *
 * @author Ooi Wei Tsang
 * @author WANG CHAO (students: put your name here)
 * @version CS2030 AY17/18 Sem 2 Lab 0
 */
public class MaxDiscCover {


  /**
   * Find the unit disc that contains the most points,
   * and return the number of points.
   */

  public static int solve(Point[] points) {
    int num = points.length;
    int Max = 0;
    for(int i = 0; i < num; i++){// Loop through all points
      for(int j = 0; j < num; j++){// Loop through all points
    	Circle thiscircle = new Circle(points[i], points[j], 1);// use 2 points to construct a Circle	
    	if(Circle.isValidCircle(thiscircle)){
    	  int sum = 0;
  		  for(int k = 0; k < num; k++){// Loop through all points
  		    if(thiscircle.contains(points[k])){// check how many points are inside the circle
  		    	sum++;	
  		    }
  		  }
  		  if(Max < sum){// record down max coverage so far
  			  Max = sum;
  		  }
  	    }
      }
    }
    
    // Hint: if the code gets too verbose, start think about
    // how you can maintain an abstraction barrier and ecapsulate
    // logics inside each respective class.

    return Max;
  }
}
