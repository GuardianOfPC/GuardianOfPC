package ru.itmo.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.persistence.model.Owner;
import ru.itmo.persistence.repo.OwnerRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OwnerService
{
    @Autowired
    private OwnerRepository ownerRepository;

    public OwnerService() {}

    public OwnerService(OwnerRepository ownerRepository) { this.ownerRepository = ownerRepository; }

    public Owner findOwner(int id) { return ownerRepository.findById(id); }

    public Owner saveOwner(Owner owner) { return ownerRepository.save(owner); }

    public void deleteOwner(Owner owner) { ownerRepository.delete(owner); }

    public void deleteById(int id) { ownerRepository.deleteById(id);}

    public List<Owner> findAllOwners() { return ownerRepository.findAll(); }

    public List<Owner> findByName(String ownerName) { return ownerRepository.findByName(ownerName); }

    public List<Owner> findByDateOfBirth(Timestamp date) { return ownerRepository.findByDateOfBirth(date); }
}