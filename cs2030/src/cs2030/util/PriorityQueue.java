package cs2030.util;

import java.util.Optional;

public class PriorityQueue<T> {
  /** The PriorityQueue in java.util to simulate this immutable PriorityQueue. */
  private final java.util.PriorityQueue<T> pq;

  /**
   * Create a immutable PriorityQueue and initializes it.
   */
  public PriorityQueue() {
    pq = new java.util.PriorityQueue<T>();
  }
  
  /**
   * Create a immutable PriorityQueue and initializes it
   * with a given PriorityQueue in java.util
   * 
   * @param pq The PriorityQueue in java.util to simulate this immutable PriorityQueue.
   */
  private PriorityQueue(java.util.PriorityQueue<T> pq) {
    this.pq = pq;
  }
  
  /**
   * Add a new object to this immutable PriorityQueue.
   * 
   * @param object The object to add in this immutable PriorityQueue.
   * @return A new immutable PriorityQueue with this object added.
   */
  public PriorityQueue<T> add(T object) {
    java.util.PriorityQueue<T> temp = new java.util.PriorityQueue<T>(pq);
    temp.add(object);
    return new PriorityQueue<T>(temp);
  }

  /**
   * Remove the first element in this immutable PriorityQueue.
   * @return A Pair whose first element is the element being moved
   *     and second element is the new immutable PriorityQueue.
   */
  public Pair<Optional<T>, PriorityQueue<T>> poll() {
    java.util.PriorityQueue<T> temp = new java.util.PriorityQueue<T>(pq);
    T t = temp.poll();
    return new Pair<>(Optional.ofNullable(t), new PriorityQueue<T>(temp));
  }
}
