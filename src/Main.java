import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * The LabOFourA class is the entry point into Lab 4a.
 *
 * @author atharvjoshi
 * @author weitsang
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
class Main {
    /**
     * The main method for Lab 4a. Reads data from file and
     * then run a simulation based on the input data.
     *
     * @param args two arguments, first an integer specifying number of servers
     *             in the shop. Second a file containing a sequence of double values, each
     *             being the arrival time of a customer (in any order).
     */
    public static void main(String[] args) {
        final Optional<Scanner> scanner = createScanner(args);
        if (scanner.isPresent()) {
            final SimState state = initSimState(scanner.get()).run();
            System.out.println(state);
        }
    }

    /**
     * Read from inputs, populate the simulator with events, and run.
     *
     * @param scanner The scanner to read inputs from.
     */
    public static SimState initSimState(Scanner scanner) {
        // Read the first line of input as number of servers in the shop
        final int numOfServers = scanner.nextInt();
        final SimState state = new SimState(numOfServers);

        if (scanner.hasNextDouble()) {
            final Pair<Double, SimState> p = Stream.iterate(
                    Pair.of(scanner.nextDouble(), state),
                    simStatePair -> simStatePair.first >= 0,
                    simStatePair -> {
                        if (scanner.hasNextDouble()) {
                            final double arrivalTime = scanner.nextDouble();
                            return Pair.of(arrivalTime, simStatePair.second
                                    .addEvent(simStatePair.first, innerState -> innerState.simulateArrival(simStatePair.first))
                            );
                        }
                        return Pair.of(-1.0, simStatePair.second);
                    }
            ).reduce((first, second) -> second).get();
            return p.second.addEvent(p.first, innerState -> innerState.simulateArrival(p.first));
        }

        return state;
    }

    /**
     * Create and return a scanner. If a command line argument is given,
     * treat the argument as a file and open a scanner on the file. Else,
     * create a scanner that reads from standard input.
     *
     * @param args The arguments provided for simulation.
     * @return A scanner or {@code null} if a filename is provided but the file
     * cannot be open.
     */
    private static Optional<Scanner> createScanner(String[] args) {
        try {
            // Read from stdin if no filename is given, otherwise read from the
            // given file.
            if (args.length == 0) {
                // If there is no argument, read from standard input.
                return Optional.of(new Scanner(System.in));
            } else {
                // Else read from file
                FileReader fileReader = new FileReader(args[0]);
                return Optional.of(new Scanner(fileReader));
            }
        } catch (FileNotFoundException exception) {
            System.err.println("Unable to open file " + args[0] + " "
                    + exception);
        }

        return Optional.empty();
    }
}
