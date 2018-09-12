package lab1b;

/**
 * Event encapsulations information about the time an event is supposed to occur, and its type.
 */
public class Event {
  /**
   * We support two types of events for now, when a customer arrives, and when a customer leaves.
   */
  public static final int CUSTOMER_ARRIVE = 1;
  public static final int CUSTOMER_DONE = 2;

  Server server; //The server of the event(if have).
  private double time; // The time this event will occur.
  private int eventType; // The type of event, indicates what should happen when an event occurs.

  /**
   * Constructor of Event without a server
   */
  Event(double when, int state) {
    this.time = when;
    this.eventType = state;
  }

  /**
   * Constructor of Event with a server
   */
  Event(double when, int state, Server server) {
    this.time = when;
    this.eventType = state;
    this.server = server;
  }

  //getter:time
  public double getTime() {
    return this.time;
  }

  //getter:eventtype
  public int getType() {
    return this.eventType;
  }
}
