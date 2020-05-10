package ru.itis.repositories.impl;

import org.springframework.stereotype.Component;
import ru.itis.models.Honey;
import ru.itis.models.Message;
import ru.itis.repositories.HoneyRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class HoneyRepositoryJpaImpl implements HoneyRepository {

    //language=HQL
    private final static String HQL_FIND_ALL = "SELECT honey FROM Honey honey";
    //language=HQL
    private final static String HQL_FIND_BY_NAME = "SELECT honey FROM Honey honey WHERE honey.name =:name";

    @PersistenceContext
    private EntityManager entityManagerFactory;

    @Override
    public Optional<Honey> findByName(String name) {
        Query query = entityManagerFactory.createQuery(HQL_FIND_BY_NAME);
        query.setParameter("name", name);
        try {
            return Optional.of((Honey) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Honey> find(Long id) {
        return Optional.ofNullable(entityManagerFactory.find(Honey.class, id));
    }

    @Override
    public List<Honey> findAll() {
        return entityManagerFactory.createQuery(HQL_FIND_ALL).getResultList();
    }

    @Override
    @Transactional
    public void save(Honey entity) {
        entityManagerFactory.merge(entity);
    }

    @Override
    public void delete(Long id) {
        Honey honey = entityManagerFactory.find(Honey.class, id);
        entityManagerFactory.remove(honey);
    }
}