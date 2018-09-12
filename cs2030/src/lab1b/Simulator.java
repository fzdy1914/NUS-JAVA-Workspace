package lab1b;

/**
 * Simulator encapsulates all the information relevant to the current simulation.
 */
public class Simulator {

  // The first two members are constants, used to configure the simulator.
  public static final int MAX_NUMBER_OF_EVENTS = 100; // Maximum number of events

  // The next two members are used to store scheduled events
  private Event[] events; // Array of events, order of events not guaranteed.
  private int numOfEvents; // The number of events in the event array.
  private Server[] servers;
  public Statistics stats;
  
  /**
   * Constructor of Simulator.
   */
  Simulator() {
    this.events = new Event[MAX_NUMBER_OF_EVENTS];
    this.numOfEvents = 0;
    Customer.lastCustomerId = 0;
    this.stats = new Statistics();
  }

  public void createServers(int number) {
    this.servers = new Server[number];
    for (int i = 0; i < number; i++) {
      servers[i] = new Server();
    }
  }

  /**
   * Run the simulator until there is no more events scheduled.
   */
  public void runSimulator() {
    while (this.numOfEvents > 0) {
      Event e = this.getNextEarliestEvent();
      this.simulateEvent(e);
    }
  }

  /**
   * Find the next event with the earliest timestamp, breaking
   * ties arbitrarily.  The event is then deleted from the array.
   * This is an O(n) algorithm.  Better algorithm exists.  To be
   * improved in later labs using a min heap.
   *
   * @return the next event
   */
  private Event getNextEarliestEvent() {
    int nextEventIndex = -1;

    // Scan linearly through the array to find the event
    // with earliest (smallest) timestamp.
    double minTime = Double.MAX_VALUE;
    for (int i = 0; i < this.numOfEvents; i++) {
      if (this.events[i].getTime() < minTime) {
        minTime = this.events[i].getTime();
        nextEventIndex = i;
      }
    }

    // Get the earliest event
    Event e = this.events[nextEventIndex];

    // Replace the earliest event with the last element in
    // the array.
    this.events[nextEventIndex] = this.events[this.numOfEvents - 1];
    this.numOfEvents--;
    return e;
  }

  private Server checkIdleServer() {
    for (Server thisServer: this.servers) {
      if (!thisServer.customerBeingServed) {
        return thisServer;
      }
    }
    return null;
  }

  private Server checkWaitingServer() {
    for (Server thisServer: this.servers) {
      if (!thisServer.customerWaiting) {
        return thisServer;
      }
    }
    return null;
  }

  /**
   * Schedule the event with the simulator.  The simulator maintains
   * an array of event (in arbitrary order) and this method simply
   * appends the given event to the end of the array.
   *
   * @return true if the event is added successfully; false otherwise.
   */
  public boolean scheduleEventInSimulator(Event e) {
    if (this.numOfEvents >= MAX_NUMBER_OF_EVENTS) {
      return false;
    } else {
      // append e as the last element in array sim.events.
      this.events[this.numOfEvents] = e;
      this.numOfEvents++;
      return true;
    }
  }

  /**
   * Simulate the event based on event type.
   */
  private void simulateEvent(Event e) {
    switch (e.getType()) {
      case Event.CUSTOMER_ARRIVE:   
        // A customer has arrived.  Increase the ID and assign it to this customer.
        Customer.lastCustomerId++;
        Customer currentCustomer = new Customer(Customer.lastCustomerId);
        System.out.printf("%6.3f %d arrives\n", e.getTime(), Customer.lastCustomerId);

        Server idleServer = this.checkIdleServer();
        Server waitingServer = this.checkWaitingServer();
        if (idleServer != null) {
          idleServer.serveCustomer(this, e.getTime(), currentCustomer);
        } else if (waitingServer != null) {
          // If there is a customer currently being served, and noone is waiting, wait.
          waitingServer.makeCustomerWait(this, e.getTime(), currentCustomer);
        } else {
          // If there is a customer currently being served, and someone is waiting, the
          // customer just leaves and go elsewhere (maximum only one waiting customer).
          servers[0].customerLeaves(this, e.getTime(), currentCustomer);
        }
        break;
      case Event.CUSTOMER_DONE:
        // A customer is done being served.
        System.out.printf("%6.3f %d done\n", e.getTime(), e.server.servedCustomer.id);
        if (e.server.customerWaiting) {
          // Someone is waiting, serve this waiting someone.
          e.server.serveWaitingCustomer(this, e.getTime());
        } else {
          // Server idle
          e.server.customerBeingServed = false;
        }
        break;
      default:
        System.err.printf("Unknown event type %d\n", e.getType());
    }
  }
}
