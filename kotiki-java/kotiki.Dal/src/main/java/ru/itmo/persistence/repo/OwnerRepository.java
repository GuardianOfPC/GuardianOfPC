package ru.itmo.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.persistence.model.Owner;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    Owner findById(int id);
    List<Owner> findByName(String ownerName);
    List<Owner> findByDateOfBirth(Timestamp date);
}
