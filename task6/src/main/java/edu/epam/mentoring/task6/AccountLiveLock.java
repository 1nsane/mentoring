package edu.epam.mentoring.task6;

import org.apache.log4j.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by eugen on 10/9/2016.
 */
public class AccountLiveLock {
    private static final Logger logger = Logger.getLogger(AccountLiveLock.class);

    private double balance;
    private int id;
    private Lock lock = new ReentrantLock();

    public AccountLiveLock(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    private boolean withdraw(double amount) {
        //to avoid livelock the simpliest solution may be replacing .tryLock() with .lock() and then
        //putting it into try {} block. In this case "return false" is not needed.
        //The same situation is in the deposit() method
        if (lock.tryLock()) {
            try {
                // Wait to simulate io like database access ...
                try {
                    Thread.sleep(10l);
                } catch (InterruptedException e) {}
                balance -= amount;
                return true;
            } finally {
//                lock.unlock();
            }
        }
        return false;
    }

    private boolean deposit(double amount) {
        if (lock.tryLock()) {
            try {
                // Wait to simulate io like database access ...
                try {
                    Thread.sleep(10l);
                } catch (InterruptedException e) {}
                balance += amount;
                return true;
            } finally {
//                lock.unlock();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        final AccountLiveLock account1 = new AccountLiveLock(1, 500d);
        final AccountLiveLock account2 = new AccountLiveLock(2, 500d);

        new Thread(new Transaction(account1, account2, 10d), "transaction-1").start();
        new Thread(new Transaction(account2, account1, 10d), "transaction-2").start();
    }

    private static class Transaction implements Runnable {
        private AccountLiveLock sourceAccount, destinationAccount;
        private double amount;

        Transaction(AccountLiveLock sourceAccount, AccountLiveLock destinationAccount, double amount) {
            this.sourceAccount = sourceAccount;
            this.destinationAccount = destinationAccount;
            this.amount = amount;
        }

        public boolean tryTransfer(double amount) {
            String currThreadName = Thread.currentThread().getName();

//            logger.info(currThreadName + ": transaction started");
            if (sourceAccount.withdraw(amount)) {
//                logger.info(currThreadName + ": withdraw " + amount + "$ from " + sourceAccount.id);
                if (destinationAccount.deposit(amount)) {
//                    logger.info(currThreadName + ": transaction successful");
                    return true;
                } else {
                    // destination account busy, refund source account.
//                    logger.error(currThreadName + ": failed to transfer " + amount + "$ from '" + sourceAccount.id + "' to '"
//                            + destinationAccount.id + "', rolling back");
                    sourceAccount.deposit(amount);
                }
            }
//            logger.error(currThreadName + ": transaction failed");
            return false;
        }

        public void run() {
            while (!tryTransfer(amount)) {}
        }
    }
}
