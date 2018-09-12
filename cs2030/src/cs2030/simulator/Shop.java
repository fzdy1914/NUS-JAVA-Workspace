package cs2030.simulator;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;

/**
 * A shop object maintains the list of servers and support queries
 * for server.
 *
 * @author weitsang
 * @author atharvjoshi
 * @author Wang Chao
 * @version CS2030 AY17/18 Sem 2 Lab 4b
 */
class Shop {
  /** List of servers. */
  private final List<Server> servers;

  /**
   * Create a new shop with a given number of servers.
   * 
   * @param numOfServers The number of servers.
   */
  public Shop(int numOfServers) {
    this.servers = Stream.generate(() -> new Server())
        .limit(numOfServers)
        .collect(Collectors.toList());
  }
  
  /**
   * Create a new shop from a given list of servers.
   * 
   * @param servers The given list of servers. 
   */
  public Shop(List<Server> servers) {
    this.servers = servers;
  }

  /**
   * Return a server in the list which satisfies the predicate.
   *
   * @param predicate The predicate to test the servers.
   * @return An (optional) server which satisfies the predicate.
   */
  public Optional<Server> findServer(Predicate<Server> predicate) {
    return servers.stream().filter(predicate).findFirst();
  }

  
  /**
   * Get the waiting customer of the given server.
   * 
   * @param server The given server.
   * @return The waiting customer of the given server.
   */
  public Optional<Customer> getWaitingCustomer(Server server) {
    return this.findCurrentServer(server).getWaitingCustomer();
  }
  
  /**
   * Return the server with the same id of the given server.
   * 
   * @param server The given server.
   * @return The server with the same id of the given server.
   */
  public Server findCurrentServer(Server server) {
    return servers.stream().filter(x -> x.equals(server)).findFirst().get();
  }
  
  /**
   * Return a new Shop that updates the server passed in.
   * 
   * @param server The given server to update.
   * @return A new Shop that updates the server passed in.
   */
  public Shop updateShop(Server server) {
    return new Shop(servers.stream().map(x -> x.equals(server) ? server : x)
        .collect(Collectors.toList()));
  }

  /**
   * Return a string representation of this shop.
   * @return A string representation of this shop.
   */
  public String toString() {
    return servers.toString();
  }
}
