package edu.epam.mentoring.task8.model;

/**
 * Created by eugen on 17.09.2016.
 */
public class Account {
    private long id;
    private String name;
    private int status;
    private long balance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", balance=" + balance +
                '}';
    }
}
