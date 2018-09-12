package lab1b;

import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

class LabOneB {

  //method to read event from input and schedule it in the simulator:
  public static void readInput(Scanner s, Simulator sim) {
    // The input file consists of a sequence of arrival timestamp
    // (not necessary in order).
    sim.createServers(s.nextInt());
    while (s.hasNextDouble()) {
      Event e = new Event(s.nextDouble(), Event.CUSTOMER_ARRIVE);
      boolean ok = sim.scheduleEventInSimulator(e);
      if (!ok) {
        System.err.printf("warning: too many events.  Skipping the rest.");
        s.close();
        break;
      }
    }
    s.close();  
  }

  /**
   * The main method for LabOneB.
   * Reads arrival time from either stdin or a file and insert the arrival event into an array
   * in the simulator.  Then, run the simulator.
   */
  public static void main(String[] args) {
    Simulator sim = new Simulator();
    Scanner s = createScanner(args);
    if (s == null) {
      return;
    }
    //Read from input
    readInput(s,sim);
    // Then run the simulator
    sim.runSimulator();

    // Print stats as three numbers:
    // <avg waiting time> <number of served customer> <number of lost customer>
    System.out.println(sim.stats);
  }

  /**
   * Create and return a scanner.  If a command line arguement is given,
   * treat the argument as a file, and open a scanner on the file.  Else,
   * open a scanner that reads from standard input.
   *
   * @return a scanner or `null` if a filename is given but cannot be open.
   */
  static Scanner createScanner(String[] args) {
    Scanner s = null;
    try {
      // Read from stdin if no filename is given, otherwise
      // read from the given file.
      if (args.length == 0) {
        s = new Scanner(System.in);
      } else {
        FileReader f = new FileReader(args[0]);
        s = new Scanner(f);
      }
    } catch (FileNotFoundException ex) {
      System.err.println("Unable to open file " + args[0] + " " + ex + "\n");
    } finally {
      return s;
    }
  }
}
