package lab1a;
/**
 * Event encapsulations information about the time an event is supposed to occur, and its type.
 */
public class Event {
  private double time; // The time this event will occur
  private int eventType; // The type of event, indicates what should happen when an event occurs.
  /**
   * Constructor of Event.
   */
  Event(double when, int type){
	this.time = when;
	this.eventType = type;
  }
  //getter:time
  public double getTime(){
	  return this.time;
  }
  //getter:time
  public int getType(){
	  return this.eventType;
  }
}