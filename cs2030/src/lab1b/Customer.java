package lab1b;

public class Customer {
  public static final double SERVICE_TIME = 1.0; // Time spent serving a customer
  public static int lastCustomerId;
  public int id;
  public double timeStartedWaiting;

  /**
   * Constructor of Customer
   */
  Customer(int id) {
    this.id = id;
  }

}
