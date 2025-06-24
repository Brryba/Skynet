package skynet;

import java.util.HashMap;
import java.util.concurrent.CyclicBarrier;

public class Faction {
    public Faction(String name, CyclicBarrier barrier) {
        factory = Factory.getInstance();
        storage = new HashMap<>();
        this.name = name;
        this.barrier = barrier;
    }

    private final Factory factory;
    private final HashMap<RobotPartType, Integer> storage;
    private final String name;
    private final CyclicBarrier barrier;


    public int startBuildingArmy() {
        int DAYS_AMOUNT = 100;

        for (int i = 0; i < DAYS_AMOUNT; i++) {
            try {
                barrier.await();
            } catch (Exception _) {}

            int DAILY_CAPACITY = 5;
            for (int j = 0; j < DAILY_CAPACITY; j++) {
                RobotPartType newPart = factory.giveSingleRobotPart();
                if (storage.containsKey(newPart)) {
                    storage.put(newPart, storage.get(newPart) + 1);
                } else {
                    storage.put(newPart, 1);
                }
            }
        }
        int robotsBuild = Math.min(
                Math.min(storage.get(RobotPartType.HEAD), storage.get(RobotPartType.TORSO)),
                Math.min(storage.get(RobotPartType.FEET) / 2, storage.get(RobotPartType.HAND) / 2));
        System.out.println("Faction " + name + " received " + storage + " parts. " +
                "It Build " + robotsBuild + " robots");
        return robotsBuild;
    }
}
