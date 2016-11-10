package edu.epam.mentoring.task10.model;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

/**
 * Created by eugen on 11/9/2016.
 */
@Entity
@Table(name = "UNIT")
public class Unit {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "UNIT_ID")
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = EAGER, mappedBy = "unit")
    private Set<Employee> employees;

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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit)) return false;

        Unit unit = (Unit) o;

        return name.equals(unit.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
