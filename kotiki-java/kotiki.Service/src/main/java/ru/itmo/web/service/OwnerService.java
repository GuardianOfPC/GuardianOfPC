package ru.itmo.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.itmo.persistence.model.MyUserDetails;
import ru.itmo.persistence.model.Owner;
import ru.itmo.persistence.repo.OwnerRepository;
import ru.itmo.web.controller.exception.UnauthorizedException;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService
{
    @Autowired
    private OwnerRepository ownerRepository;

    public OwnerService() {}

    public OwnerService(OwnerRepository ownerRepository) { this.ownerRepository = ownerRepository; }

    public Owner saveOwner(Owner owner) { return ownerRepository.save(owner); }

    public void deleteOwner(Owner owner) { ownerRepository.delete(owner); }

    public void deleteById(int id) { ownerRepository.deleteById(id);}

    public List<Owner> findAllOwners() {
        List<Owner> owners = ownerRepository.findAll();
        return filterOwners(owners);
    }

    public Owner findOwner(int id) {
        Owner owner = ownerRepository.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails currUserDetails = (MyUserDetails) auth.getPrincipal();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))){
            return owner;
        }
        if (owner.getId().equals(currUserDetails.getUser().getId())){
            return owner;
        }
        throw new UnauthorizedException();
    }

    public List<Owner> findByName(String ownerName) {
        List<Owner> owners = ownerRepository.findByName(ownerName);
        return filterOwners(owners);
    }

    public List<Owner> findByDateOfBirth(Timestamp date) {
        List<Owner> owners = ownerRepository.findByDateOfBirth(date);
        return filterOwners(owners);
    }

    private List<Owner> filterOwners(List<Owner> owners) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails currUserDetails = (MyUserDetails) auth.getPrincipal();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))){
            return owners;
        }
        return owners.stream()
                .filter(a -> a.getId().equals(currUserDetails.getUser().getId()))
                .collect(Collectors.toList());
    }
}