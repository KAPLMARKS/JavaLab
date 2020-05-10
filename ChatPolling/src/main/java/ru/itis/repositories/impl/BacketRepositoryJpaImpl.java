package ru.itis.repositories.impl;

import org.springframework.stereotype.Component;
import ru.itis.models.Backet;
import ru.itis.models.Message;
import ru.itis.repositories.BacketRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class BacketRepositoryJpaImpl implements BacketRepository {
    //language=HQL
    private final static String HQL_FIND_ALL = "SELECT backet FROM Backet backet";
    @PersistenceContext
    private EntityManager entityManagerFactory;


    @Override
    public Optional<Backet> find(Long id) {
        return Optional.ofNullable(entityManagerFactory.find(Backet.class, id));
    }

    @Override
    public List<Backet> findAll() {
        return entityManagerFactory.createQuery(HQL_FIND_ALL).getResultList();
    }

    @Override
    @Transactional
    public void save(Backet entity) {
        entityManagerFactory.merge(entity);
    }

    @Override
    public void delete(Long id) {
        Backet backet = entityManagerFactory.find(Backet.class, id);
        entityManagerFactory.remove(backet);
    }
}