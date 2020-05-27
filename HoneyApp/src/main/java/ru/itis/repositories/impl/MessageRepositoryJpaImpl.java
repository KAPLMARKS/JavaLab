package ru.itis.repositories.impl;

import org.springframework.stereotype.Component;
import ru.itis.models.Chat;
import ru.itis.models.Message;
import ru.itis.repositories.MessageRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class MessageRepositoryJpaImpl implements MessageRepository {
    //language=HQL
    private final static String HQL_FIND_ALL = "SELECT message FROM Message message";

    //language=HQL
    private final static String HQL_FIND_BY_OWNER_ID = "SELECT message FROM Message message " +
            "join Chat chat on chat.owner.id = message.user.id where chat.owner.id =: id";

    @PersistenceContext
    private EntityManager entityManagerFactory;

    @Override
    public List<Message> getAllByChat(Chat chat) {
        Query query = entityManagerFactory.createQuery(HQL_FIND_BY_OWNER_ID);
        query.setParameter("id", chat.getOwner().getId());
        return query.getResultList();
    }

    @Override
    public Optional<Message> find(Long id) {
        return Optional.ofNullable(entityManagerFactory.find(Message.class, id));
    }

    @Override
    public List<Message> findAll() {
        return entityManagerFactory.createQuery(HQL_FIND_ALL).getResultList();
    }

    @Override
    @Transactional
    public void save(Message entity) {
        entityManagerFactory.merge(entity);
    }

    @Override
    public void delete(Long id) {
        Message message = entityManagerFactory.find(Message.class, id);
        entityManagerFactory.remove(message);
    }
}