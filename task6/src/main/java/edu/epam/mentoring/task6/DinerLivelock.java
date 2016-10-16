package edu.epam.mentoring.task6;

import org.apache.log4j.Logger;

/**
 * Created by eugen on 10/10/2016.
 */
public class DinerLivelock {
    private static final Logger logger = Logger.getLogger(DinerLivelock.class);

    private static class Spoon {
        private Diner owner;
        public Spoon(Diner d) { owner = d; }
        public Diner getOwner() { return owner; }
        public synchronized void setOwner(Diner d) { owner = d; }
        public synchronized void use() {
//            logger.info(owner.name + " has eaten!");
        }
    }

    private static class Diner {
        private String name;
        private boolean isHungry;

        public Diner(String n) { name = n; isHungry = true; }
        public String getName() { return name; }
        public boolean isHungry() { return isHungry; }

        public void eatWith(Spoon spoon, Diner spouse) {
            while (isHungry) {
                // Don't have the spoon, so wait patiently for spouse.
                if (spoon.owner != this) {
                    try { Thread.sleep(1); }
                    catch(InterruptedException e) { continue; }
                    continue;
                }

                // If spouse is hungry, insist upon passing the spoon.
                if (spouse.isHungry()) {
//                    logger.info(name + ": You eat first my darling");
                    spoon.setOwner(spouse);
                    continue;
                }

                // Spouse wasn't hungry, so finally eat
                spoon.use();
                isHungry = false;
//                logger.info(name + ": I am stuffed, my darling!");
                spoon.setOwner(spouse);
            }
        }
    }

    public static void main(String[] args) {
        final Diner husband = new Diner("Husband");
        final Diner wife = new Diner("Wife");

        final Spoon s = new Spoon(husband);

        new Thread(new Runnable() {
            public void run() { husband.eatWith(s, wife); }
        }, husband.getName()).start();

        new Thread(new Runnable() {
            public void run() { wife.eatWith(s, husband); }
        }, wife.getName()).start();
    }
}
