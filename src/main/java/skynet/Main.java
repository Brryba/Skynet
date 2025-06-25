package skynet;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3);
        Faction world = new Faction("World", barrier);
        Faction wednesday = new Faction("Wednesday", barrier);

        Factory.setBarrier(barrier);
        new Thread(() -> Factory.getInstance().produceOneParts()).start();

        try (ExecutorService executor = Executors.newCachedThreadPool()) {
            Future<Integer> wednesdayFuture = executor.submit(wednesday::startBuildingArmy);
            Future<Integer> worldFuture = executor.submit(world::startBuildingArmy);

            int wednesdayProduced = wednesdayFuture.get();
            int worldProduced = worldFuture.get();
            if (wednesdayProduced > worldProduced) {
                System.out.println("Faction Wednesday has bigger army");
            } else if (wednesdayProduced < worldProduced) {
                System.out.println("Faction World has bigger army");
            } else {
                System.out.println("Armies are equal");
            }
        } catch (Exception _) {}
    }
}