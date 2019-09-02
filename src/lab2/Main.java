package lab2;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final ArrayList<Cruise> cruiseArrayList = Main.readInputs();
        Main.parseCruises(cruiseArrayList);
    }

    private static ArrayList<Cruise> readInputs() {
        Scanner reader = new Scanner(System.in);
        final int numberOfInputsToRead = reader.nextInt();
        ArrayList<Cruise> cruiseArrayList = new ArrayList<>();

        for (int i = 0; i < numberOfInputsToRead; i++) {
            final String cruiseId = reader.next();
            final String cruiseType = cruiseId.substring(0, 1);
            if (cruiseType.equals("B")) {
                cruiseArrayList.add(new BigCruise(cruiseId, reader.nextInt(), reader.nextInt(), reader.nextInt()));
            } else {
                cruiseArrayList.add(new Cruise(cruiseId, reader.nextInt()));
            }
        }

        reader.close();

        return cruiseArrayList;
    }

    private static void parseCruises(ArrayList<Cruise> cruiseArrayList) {
        ArrayList<Loader> loaderArrayList = new ArrayList<>();
        final Loader firstLoader = new Loader(1);
        loaderArrayList.add(firstLoader);

        for (final Cruise cruise : cruiseArrayList) {
            for (int x = 0; x < cruise.getNumLoadersRequired(); x++) {
                boolean needNewLoader = true;
                for (int i = 0; i < loaderArrayList.size(); i++) {
                    final Loader loader = loaderArrayList.get(i);
                    final Loader loaderWithServing = loader.serve(cruise);

                    if (loaderWithServing != null) {
                        loaderArrayList.set(i, loaderWithServing);
                        needNewLoader = false;
                        System.out.println(loaderWithServing.toString());
                        break;
                    }
                }

                if (needNewLoader) {
                    final int newId = loaderArrayList.size() + 1;
                    // Every third one is made from recycled materials
                    if (newId % 3 == 0) {
                        final RecycledLoader recycledLoader = new RecycledLoader(newId).serve(cruise);
                        loaderArrayList.add(recycledLoader);
                        System.out.println(recycledLoader.toString());
                    } else {
                        final Loader loader = new Loader(newId).serve(cruise);
                        loaderArrayList.add(loader);
                        System.out.println(loader.toString());
                    }
                }
            }
        }
    }
}
