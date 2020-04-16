package ru.itis.repositories;

import ru.itis.models.Honey;
import java.util.Optional;

public interface HoneyRepository extends CrudRepository<Long, Honey> {
    Optional<Honey> findByName(String name);
}