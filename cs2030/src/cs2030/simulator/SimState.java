package cs2030.simulator;

import cs2030.util.PriorityQueue;
import cs2030.util.Pair;
import java.util.Optional;

/**
 * This class encapsulates all the simulation states.  There are four main
 * components: (i) the event queue, (ii) the statistics, (iii) the shop
 * (the servers) and (iv) the event logs.
 *
 * @author atharvjoshi
 * @author weitsang
 * @author Wang Chao
 * @version CS2030 AY17/18 Sem 2 Lab 4b
 */
public class SimState {
  /** The priority queue of events. */
  private final PriorityQueue<Event> events;

  /** The statistics maintained. */
  private final Statistics stats;

  /** The shop of servers. */
  private final Shop shop;
  
  /** The eventlog of this state. */
  private final String eventLog;
   
  /**
   * Constructor for creating the simulation state from scratch.
   * 
   * @param numOfServers The number of servers.
   */
  public SimState(int numOfServers) {
    this.shop = new Shop(numOfServers);
    this.stats = new Statistics();
    this.events = new PriorityQueue<Event>();
    this.eventLog = "";
  }
  
  /**
   * Constructor for creating the simulation state.
   * 
   * @param shop The shop of servers.
   * @param stats The statistics maintained.
   * @param events The priority queue of events.
   * @param eventLog The new eventLog.
   */
  private SimState(Shop shop, Statistics stats, PriorityQueue<Event> events, String eventLog) {
    this.shop = shop;
    this.stats = stats;
    this.events = events;
    this.eventLog = eventLog;
  }

  /**
   * Add an event to the simulation's event queue.
   * 
   * @param  e The event to be added to the queue.
   * @return The new simulation state.
   */
  public SimState addEvent(Event e) {
    return new SimState(this.shop, this.stats, this.events.add(e), this.eventLog);
  }

  /**
   * Retrieve the next event with earliest time stamp from the
   * priority queue, and a new state.  If there is no more event, an
   * Optional.empty will be returned.
   * 
   * @return A pair object with an (optional) event and the new simulation
   *     state.
   */
  public Pair<Optional<Event>, SimState> nextEvent() {
    Pair<Optional<Event>, PriorityQueue<Event>> result = this.events.poll();
    return new Pair<>(result.first, 
        new SimState(this.shop, this.stats, result.second, this.eventLog));
  }

  /**
   * Called when a customer arrived in the simulation.
   * 
   * @param time The time the customer arrives.
   * @param c The customer that arrives.
   * @return A new state of the simulation after the customer arrives.
   */
  private SimState customerArrives(double time, Customer c) {
    return this.log(String.format("%6.3f %s arrives\n", time, c));
  }

  /**
   * Called when a customer waits in the simulation.  This methods update
   * the logs of simulation.
   * 
   * @param time The time the customer starts waiting.
   * @param s The server the customer is waiting for.
   * @param c The customer who waits.
   * @return A new state of the simulation after the customer waits.
   */
  private SimState customerWaits(double time, Server s, Customer c) {
    return this.server(s).log(String.format("%6.3f %s waits for %s\n", time, c, s));
  }

  /**
   * Called when a customer is served in the simulation.  This methods
   * update the logs and the statistics of the simulation.
   * 
   * @param time The time the customer arrives.
   * @param s The server that serves the customer.
   * @param c The customer that is served.
   * @return A new state of the simulation after the customer is served.
   */
  private SimState customerServed(double time, Server s, Customer c) {
    SimState tempState = this.server(s).log(String.format("%6.3f %s served by %s\n", time, c, s));
    return new SimState(tempState.shop, 
        this.stats.serveOneCustomer().customerWaitedFor(time - c.timeArrived()), 
        tempState.events, tempState.eventLog);
  }

  /**
   * Called when a customer is done being served in the simulation.
   * This methods update the logs of the simulation.
   * 
   * @param time The time the customer arrives.
   * @param s The server that serves the customer.
   * @param c The customer that is served.
   * @return A new state of the simulation after the customer is done being
   *     served.
   */
  private SimState customerDone(double time, Server s, Customer c) {
    Server temp = this.shop.findCurrentServer(s);
    return this.log(String.format("%6.3f %s done served by %s\n",  time, c, temp));
  }

  /**
   * Called when a customer leaves the shops without service.
   * Update the log and statistics.
   * @param  time  The time this customer leaves.
   * @param  customer The customer who leaves.
   * @return A new state of the simulation.
   */
  private SimState customerLeaves(double time, Customer customer) {
    SimState temp = this.log(String.format("%6.3f %s leaves\n", time, customer));
    return new SimState(temp.shop, temp.stats.lostOneCustomer(), temp.events, temp.eventLog);
  }

  /**
   * Simulates the logic of what happened when a customer arrives.
   * The customer is either served, waiting to be served, or leaves.
   * 
   * @param time The time the customer arrives.
   * @return A new state of the simulation.
   */
  public SimState simulateArrival(double time) {
    Customer customer = new Customer(time);
    return this.customerArrives(time, customer).servedOrLeave(time, customer);
  }

  /**
   * Called from simulateArrival.  Handles the logic of finding
   * idle servers to serve the customer, or a server that the customer
   * can wait for, or leave.
   * 
   * @param time The time the customer arrives.
   * @param customer The customer to be served.
   * @return A new state of the simulation.
   */
  private SimState servedOrLeave(double time, Customer customer) {
    return shop.findServer(x -> x.isIdle()).map(x -> serveCustomer(time, x, customer))
        .orElseGet(() -> shop.findServer(x -> !x.customerWaiting())
        .map(y -> makeCustomerWait(time, y, customer))
        .orElseGet(() -> customerLeaves(time, customer)));
  }

  /**
   * Simulates the logic of what happened when a customer is done being
   * served.  The server either serve the next customer or becomes idle.
   * 
   * @param time The time the service is done.
   * @param server The server serving the customer.
   * @param customer The customer being served.
   * @return A new state of the simulation.
   */
  public SimState simulateDone(double time, Server server, Customer customer) {
    return this.customerDone(time, server, customer).serveNextOrIdle(time, server);
  }

  /**
   * Called from simulateDone.  Handles the logic of checking if there is
   * a waiting customer, if so serve the customer, otherwise make the
   * server idle.
   * 
   * @param time The time the service is done.
   * @param server The server serving the next customer.
   * @return A new state of the simulation.
   */
  private SimState serveNextOrIdle(double time, Server server) {
    return shop.getWaitingCustomer(server)
        .map(c -> serveCustomer(time, server, c))
        .orElseGet(() -> this.server(server.makeIdle()));
  }

  /**
   * Handle the logic of server serving customer.  A new done event
   * is generated and scheduled.
   * 
   * @param  time  The time this customer is served.
   * @param  server The server serving this customer.
   * @param  customer The customer being served.
   * @return A new state of the simulation.
   */
  private SimState serveCustomer(double time, Server server, Customer customer) {
    double doneTime = time + Simulator.SERVICE_TIME;  
    return this.addEvent(new Event(doneTime, x -> x.simulateDone(doneTime, server, customer)))
        .customerServed(time, server.serve(customer), customer);
  }

  /**
   * Handle the logic of queuing up customer for server.   Make the
   * customer waits for server.
   * 
   * @param  time  The time this customer started waiting.
   * @param  server The server this customer is waiting for.
   * @param  customer The customer who waits.
   * @return A new state of the simulation.
   */
  private SimState makeCustomerWait(double time, Server server, Customer customer) {
    return this.customerWaits(time, server, customer).server(server.askToWait(customer));
  }
  
  /**
   * Returns a new SimState that has a new Shop that updates the server passed in.
   * 
   * @param s The server to update the shop.
   * @return A new SimState that has a new Shop that updates the server passed in  
   */
  private SimState server(Server s) {
    return new SimState(shop.updateShop(s), this.stats, this.events, this.eventLog);
  }
  
  /**
   * Returns a new SimState that has the updated eventlog.
   * 
   * @param s The string to update the eventlog.
   * @return A new SimState that has the updated eventlog.
   */
  private SimState log(String s) {
    return new SimState(this.shop, this.stats, this.events, this.eventLog + s);
  }

  /**
   * Return a string representation of the simulation state, which
   * consists of all the logs and the stats.
   * 
   * @return A string representation of the simulation.
   */
  public String toString() {
    return eventLog + stats.toString();
  }
}
