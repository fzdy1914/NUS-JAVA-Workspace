package lab0;
import java.lang.Math;

/**
 *  Encapsulates a point on a 2D plane.
 *  @author Ooi Wei Tsang
 *  @author WANG CHAO (students: put your name here)
 *  @version CS2030 AY17/18 Sem 2 Lab 0
 */
public class Point {
  private double x;
  private double y;

  /**
   * Create a point at coordinate (x, y)
   */
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Create a Point at the midpoint between two given points p and q
   *
   * @param p the first point
   * @param q the second point
   * @return an instance of a point
   */
  public static Point midPoint(Point p, Point q) {
    double new_x = (p.x + q.x) / 2;
    double new_y = (p.y + q.y) / 2;
    // Hint:
    // Find the mid value of x and y and use them to construct a new point.

    return new Point(new_x, new_y);
  }


  /**
   * Calculate the Euclidean distance of this point to a given point q.
   *
   * @param q another point
   * @return the Euclidean distance between this point and q.
   */
  public double distanceTo(Point q) {
    double dx = this.x - q.x;
    double dy = this.y - q.y;
    // Hint:
    // you need sqrt method from Math library
    // https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html

    return Math.sqrt(dx * dx + dy * dy);
  }

  /**
   * Calculate the angle between this point and a given point q
   *
   * @param q another point
   * @return the angle between this point and the given point q
   */
  public double angleTo(Point q) {
    double dx = q.x - this.x;
    double dy = q.y - this.y;

    // Hint:
    // You will need atan2 from Math library
    // https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html
    // Essentially, you should pass in dx and dy into atan2() and
    // return the result.
    // Please do note that dx should be obtained from q.x - this.x instead of
    // this.x - q.x

    // Would it be better to package dx and dy into methods to enhance
    // reusability(OO concept)?
    return Math.atan2(dy, dx);
  }

  /**
   * Move the current point by a distance d at a given elevation(angle).
   * The elevation is measured with respect to horizontal line.
   *
   * @param theta the angle of the direction to move, in radian.
   * @param d the distance to move
   */
  public void move(double theta, double d) {
    this.x = this.x + d * Math.cos(theta);
    this.y = this.y + d * Math.sin(theta);


    // Hint:
    // You will need cos and sin methods from Math library
    // https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html
    // More detailed description can be found in lab notes
  }

  /**
   * Return a string representation of the point, showing its
   * coordinates.
   */
  @Override
  public String toString() {
    return "[" + super.toString() + String.format(" (%.2f, %.2f)", x, y) + "]";
  }

  /**
   * Check if a given point has the same coordinate as this point.
   *
   * @param obj The point to check against
   * @return true if obj is a point and has the same coordinate; false
   *     otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Point) {
      Point p = (Point)obj;
      return (Math.abs(p.x - x) < 1E-15) && (Math.abs(p.y - y) < 1E-15);
    } else {
      return false;
    }
  }
}
