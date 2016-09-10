package edu.epam.mentoring.task5;

import org.apache.log4j.Logger;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Yevgeniy_Vtulkin on 9/7/2016.
 */
public class Car implements Runnable {
    private static final long MAX_DISTANCE = 10000;

    private Logger log = Logger.getLogger(getClass());

    private long friction;
    private long distance;
    private String name;

    private CountDownLatch countDown;
    private Queue<String> finishQueue;

    public Car(String name, long friction, CountDownLatch countDown, Queue<String> finishQueue) {
        this.name = name;
        this.friction = friction;
        this.countDown = countDown;
        this.finishQueue = finishQueue;
    }

    @Override
    public void run() {
        try {
            countDown.countDown(); //wait for other competitors

            while (distance < MAX_DISTANCE) {
                Thread.sleep(friction);
                distance += 100;
                log.info(name + " " + distance);
            }

            finishQueue.add(name);
            log.info("CAR " + name + " HAS FINISHED");
        } catch (InterruptedException e) {
            log.error("CAR " + name + " HAS BEEN INTERRUPTED");
        }
    }
}
