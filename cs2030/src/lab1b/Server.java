package lab1b;

public class Server {

  // The next three members are used to record the states of the simulation
  public boolean customerBeingServed; // is a customer currently being served?
  public boolean customerWaiting; // is a customer currently waiting?

  public Customer servedCustomer; //the customer who is being served
  public Customer waitingCustomer; //the customer who is waiting

  /**
   * Constructor of Server.
   */
  Server() {
    this.customerWaiting = false;
    this.customerBeingServed = false;
  }

  /**
   * Serve the current customer with given id at given time in the given simulator.
   * Precondition: no one must be served at this time.
   */
  public void serveCustomer(Simulator sim, double time, Customer thiscustomer) {
    assert this.customerBeingServed == false;
    this.customerBeingServed = true;
    this.servedCustomer = thiscustomer;
    System.out.printf("%6.3f %d served\n", time, thiscustomer.id);
    boolean ok = sim.scheduleEventInSimulator(new Event(time + Customer.SERVICE_TIME, 
        Event.CUSTOMER_DONE, this));
    if (!ok) {
      System.err.println("Warning: too many events.  Simulation result will not be correct.");
    }
    sim.stats.totalNumOfServedCustomer++;
    assert this.customerBeingServed == true;
  }

  /**
   * Make the current customer with given id wait starting at given time in the given simulator.
   * Precondition: someone is being served but noone is waiting
   * Postcondition: someone is being served, and someone is waiting
   */
  public void makeCustomerWait(Simulator sim, double time, Customer thiscustomer) {
    assert this.customerBeingServed == true;
    assert this.customerWaiting == false;
    this.waitingCustomer = thiscustomer;
    thiscustomer.timeStartedWaiting = time;
    System.out.printf("%6.3f %d waits\n", time, thiscustomer.id);
    this.customerWaiting = true;
    assert this.customerBeingServed == true;
    assert this.customerWaiting == true;
  }

  /**
   * Make the current customer with given id wait, starting at given time in the given simulator.
   * Precondition: someone must be waiting, and noone is being served.
   * Postcondition: noone is waiting, and someone is being served.
   */
  public void serveWaitingCustomer(Simulator sim, double time) {
    assert this.customerBeingServed == false;
    assert this.customerWaiting == true;
    this.customerWaiting = false;
    this.serveCustomer(sim, time, waitingCustomer);
    sim.stats.totalWaitingTime += (time - this.waitingCustomer.timeStartedWaiting);
    assert this.customerBeingServed == true;
    assert this.customerWaiting == false;
  }

  /**
   * Make the current customer with given id leave, at given time in the given simulator.
   * Precondition: someone must be waiting, and someone is being served.
   * Postcondition: someone must be waiting, and someone is being served.
   */
  public void customerLeaves(Simulator sim, double time, Customer thiscustomer) {
    assert this.customerBeingServed == true;
    assert this.customerWaiting == true;
    System.out.printf("%6.3f %d leaves\n", time, thiscustomer.id);
    sim.stats.totalNumOfLostCustomer++;
    assert this.customerBeingServed == true;
    assert this.customerWaiting == true;
  }
}
