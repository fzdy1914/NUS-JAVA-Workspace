package cs2030.util;

public class Pair<T,U> {
  /** The first element in this pair. */
  public T first;
  
  /** The second element in this pair. */
  public U second;

  /**
   * Create a pair and initializes it.
   * @param t The first element in this pair.
   * @param u The second element in this pair.
   */
  public Pair(T t, U u) {
    this.first = t;
    this.second = u;
  }
}
