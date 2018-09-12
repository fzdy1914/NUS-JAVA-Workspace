package lab1b;

public class Statistics {
  public double totalWaitingTime;
  public int totalNumOfServedCustomer;
  public int totalNumOfLostCustomer;

  /**
   * Constructor of Statics
   */
  Statistics() {
    totalWaitingTime = 0;
    totalNumOfServedCustomer = 0;
    totalNumOfLostCustomer = 0;
  }

  public String toString() {
    return String.format("%.3f", this.totalWaitingTime / this.totalNumOfServedCustomer) + 
      " " + this.totalNumOfServedCustomer + " " + this.totalNumOfLostCustomer;
  }

}
