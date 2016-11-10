package edu.epam.mentoring.task10.service;

import edu.epam.mentoring.task10.model.Project;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
public class ProjectService {
    @Autowired
    private SessionFactory sessionFactory;

    public long save(Project project) {
        sessionFactory.getCurrentSession().save(project);
        return project.getId();
    }

    public long update(Project project) {
        sessionFactory.getCurrentSession().update(project);
        return project.getId();
    }

    public void delete(Project project) {
        sessionFactory.getCurrentSession().delete(project);
    }

    public Project get(long id) {
        return (Project) sessionFactory.getCurrentSession().get(Project.class, id);
    }

    public List<Project> getAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Project.class)
                .setResultTransformer(DISTINCT_ROOT_ENTITY)
                .list();
    }

    public List<Project> getAllInternalEmployeesProjects() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Project.class, "project")
                .createAlias("project.employees", "employee")
                .add(Restrictions.eq("employee.external", false))
                .list();
    }
}
