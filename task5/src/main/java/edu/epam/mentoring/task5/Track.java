package edu.epam.mentoring.task5;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Yevgeniy_Vtulkin on 9/7/2016.
 */
public class Track {
    private static Logger log = Logger.getLogger(Track.class);

    private static final int CARS_COUNT = 10;
    private static final int CAR_ID_TO_STOP = 6;

    private static final int MINIMAL_FRICTION = 80;
    private static final int MAXIMAL_FRICTION = 100;

    private static List<Thread> competitors = new ArrayList<>(CARS_COUNT);
    private static CountDownLatch countDown = new CountDownLatch(CARS_COUNT);
    private static Queue<String> finishQueue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < CARS_COUNT; i++) {
            String carName = "Car-" + i;
            long carFriction = new Random().nextInt(MAXIMAL_FRICTION - MINIMAL_FRICTION) + MINIMAL_FRICTION;

            competitors.add(new Thread(new Car(carName, carFriction, countDown, finishQueue)));
            competitors.get(i).start();
        }

        Thread.currentThread().sleep(5000); //sleep 5 sec
        competitors.get(CAR_ID_TO_STOP).interrupt(); //interrupt the #CAR_ID_TO_STOP car

        //wait for all competitors comes to finish
        //or being interrupted
        for (int i = 0; i < CARS_COUNT; i++) {
            competitors.get(i).join();
        }

        log.info("WINNER IS " + finishQueue.peek());
    }
}
