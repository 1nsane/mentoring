package edu.epam.mentoring.task10.service;

import edu.epam.mentoring.task10.model.Unit;
import org.hibernate.SessionFactory;
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
public class UnitService {
    @Autowired
    private SessionFactory sessionFactory;

    public long save(Unit unit) {
        sessionFactory.getCurrentSession().save(unit);
        return unit.getId();
    }

    public long update(Unit unit) {
        sessionFactory.getCurrentSession().update(unit);
        return unit.getId();
    }

    public void delete(Unit unit) {
        sessionFactory.getCurrentSession().delete(unit);
    }

    public Unit get(long id) {
        return (Unit) sessionFactory.getCurrentSession().get(Unit.class, id);
    }

    public List<Unit> getAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Unit.class)
                .setResultTransformer(DISTINCT_ROOT_ENTITY)
                .list();
    }
}
