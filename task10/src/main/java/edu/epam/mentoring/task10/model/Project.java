package edu.epam.mentoring.task10.model;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.AUTO;

/**
 * Created by eugen on 11/9/2016.
 */
@Entity
@Table(name = "PROJECT")
public class Project {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "PROJECT_ID")
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "EMPLOYEE_PROJECTS", joinColumns = @JoinColumn(name = "PROJECT_ID"), inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
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
        if (!(o instanceof Project)) return false;

        Project project = (Project) o;

        return name.equals(project.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
