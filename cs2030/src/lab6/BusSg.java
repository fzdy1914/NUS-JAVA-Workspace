package lab6;
import java.util.Optional;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * A BusSg class encapsulate the data related to the bus services and
 * bus stops in Singapore, and supports queries to the data.
 */
class BusSg {

  /**
   * Given a bus stop and a name, find the bus services that serve between
   * the given stop and any bus stop with matching mame.
   * @param  stop The bus stop
   * @param  name The (partial) name of other bus stops.
   * @return The (optional) bus routes between the stops.
   */
  public static Optional<BusRoutes> findBusServicesBetween(BusStop stop, String name) {
    if (stop == null || name == null) {
      return Optional.empty();
    }
    return CompletableFuture
        .supplyAsync(() -> stop.getBusServices())
        .thenApplyAsync(set -> {
          HashMap<BusService, CompletableFuture<Set<BusStop>>> map = new HashMap<>();
          set.forEach(service -> map.put(service, 
              CompletableFuture.supplyAsync(() -> service.findStopsWith(name))));
          Map<BusService,Set<BusStop>> validServices = new HashMap<>();
          map.forEach((service, future) -> {
            Set<BusStop> stops = future.join();
            if (!stops.isEmpty()) {
              validServices.put(service, stops);
            }
          });
          return validServices;
        })
        .handle((result, exception) -> {
          if (exception != null) { 
            System.err.println(exception);
            return new HashMap<BusService,Set<BusStop>>();
          } else {
            return result;
          }
        })
        .thenApplyAsync(services -> Optional.of(new BusRoutes(stop, name,services)))
        .join();
  }
}
