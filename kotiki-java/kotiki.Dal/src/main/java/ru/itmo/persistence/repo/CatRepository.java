package ru.itmo.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.persistence.model.Cat;

public interface CatRepository extends JpaRepository<Cat, Long> {
    Cat findByName(String catName);
}
