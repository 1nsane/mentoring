package edu.epam.mentoring.task10.model;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

/**
 * Created by eugen on 11/9/2016.
 */
@Entity
@Table(name = "EMPLOYEE")
public class Employee {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "EMPLOYEE_ID")
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    private boolean external;

    private String address;

    private String personal;

    private EmployeeStatus status;

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "EMPLOYEE_PROJECTS", joinColumns = @JoinColumn(name = "EMPLOYEE_ID"), inverseJoinColumns = @JoinColumn(name = "PROJECT_ID"))
    private Set<Project> projects;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;

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

    public boolean isExternal() {
        return external;
    }

    public void setExternal(boolean external) {
        this.external = external;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        return name.equals(employee.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
