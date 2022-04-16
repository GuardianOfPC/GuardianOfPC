package ru.itmo.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.persistence.model.Cat;
import ru.itmo.persistence.model.enums.CatColors;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Integer> {
    Cat findById(int id);
    List<Cat> findByName(String catName);
    List<Cat> findByDateOfBirth(Timestamp dateOfBirth);
    List<Cat> findByBreed(String breed);
    List<Cat> findByColor(CatColors color);
}
