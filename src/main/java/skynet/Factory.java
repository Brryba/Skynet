package skynet;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;

public class Factory {
    private Factory() {
        robotParts = new CopyOnWriteArrayList<>();
        randomizer = new Random();
    }

    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    private static Factory instance;
    private final List<RobotPartType> robotParts;
    private final Random randomizer;
    private CyclicBarrier barrier;

    public static void setBarrier(CyclicBarrier barrier) {
        instance.barrier = barrier;
    }

    private RobotPartType producePart() {
        return RobotPartType.values()[randomizer.nextInt(RobotPartType.values().length)];
    }

    public void produceOneParts() {
        for (int i = 0; i < 100; i++) {
            int ONE_DAY_PARTS = 10;

            for (int j = 0; j < ONE_DAY_PARTS; j++) {
                robotParts.add(producePart());
            }
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException _) {}
        }
    }

    public RobotPartType giveSingleRobotPart() {
        return robotParts.removeLast();
    }
}
