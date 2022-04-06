package ru.itmo.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.persistence.model.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findByName(String ownerName);
}
