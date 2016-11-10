package edu.epam.mentoring.task10.service;

import edu.epam.mentoring.task10.model.Employee;
import edu.epam.mentoring.task10.model.Project;
import edu.epam.mentoring.task10.model.Unit;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hibernate.criterion.CriteriaSpecification.DISTINCT_ROOT_ENTITY;

/**
 * Created by eugen on 11/10/2016.
 */
@Service
@Transactional
public class EmployeeService {
    @Autowired
    private SessionFactory sessionFactory;

    public long save(Employee employee) {
        sessionFactory.getCurrentSession().save(employee);
        return employee.getId();
    }

    public long update(Employee employee) {
        sessionFactory.getCurrentSession().update(employee);
        return employee.getId();
    }

    public void delete(Employee employee) {
        sessionFactory.getCurrentSession().delete(employee);
    }

    public Employee get(long id) {
        return (Employee) sessionFactory.getCurrentSession().get(Employee.class, id);
    }

    public List<Employee> getAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Employee.class)
                .setResultTransformer(DISTINCT_ROOT_ENTITY)
                .list();
    }

    public void assignToProject(Employee employee, long projectId) {
        Project toAssign = (Project) sessionFactory.getCurrentSession().get(Project.class, projectId);
        employee.getProjects().add(toAssign);
        sessionFactory.getCurrentSession().update(employee);
    }

    public void assignToUnit(Employee employee, long unitId) {
        Unit toAssign = (Unit) sessionFactory.getCurrentSession().get(Unit.class, unitId);
        employee.setUnit(toAssign);
        sessionFactory.getCurrentSession().update(employee);
    }
}
