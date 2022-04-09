package ru.itmo.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.itmo.persistence.model.Cat;
import ru.itmo.persistence.model.MyUserDetails;
import ru.itmo.persistence.model.User;
import ru.itmo.persistence.model.enums.CatColors;
import ru.itmo.persistence.repo.CatRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatService
{
    @Autowired
    private CatRepository catRepository;

    public CatService() {}

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Cat saveCat(Cat cat) {
        return catRepository.save(cat);
    }

    public void deleteCat(Cat cat) {
        catRepository.delete(cat);
    }

    public void deleteById(int id) {
        catRepository.deleteById(id);
    }

    public List<Cat> findAllCats() {
        List<Cat> cats = catRepository.findAll();
        return filterCats(cats);
    }

    public Cat findCat(int id) {
        Cat cat = catRepository.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails currUserDetails = (MyUserDetails) auth.getPrincipal();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))){
            return cat;
        }
        if (cat.getOwner().getId().equals(currUserDetails.getUser().getOwner().getId())){
            return cat;
        }
        return new Cat();
    }

    public List<Cat> findByName(String name) {
        List<Cat> cats = catRepository.findByName(name);
        return filterCats(cats);
    }

    public List<Cat> findByDateOfBirth(Timestamp dateOfBirth) {
        List<Cat> cats = catRepository.findByDateOfBirth(dateOfBirth);
        return filterCats(cats);
    }

    public List<Cat> findByBreed(String breed) {
        List<Cat> cats = catRepository.findByBreed(breed);
        return filterCats(cats);
    }

    public List<Cat> findByColor(CatColors color) {
        List<Cat> cats = catRepository.findByColor(color);
        return filterCats(cats);
    }

    private List<Cat> filterCats(List<Cat> cats) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails currUserDetails = (MyUserDetails) auth.getPrincipal();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))){
            return cats;
        }
        return cats.stream()
                .filter(a -> a.getOwner().getId().equals(currUserDetails.getUser().getId()))
                .collect(Collectors.toList());
    }
}