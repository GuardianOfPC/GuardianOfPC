package ru.itmo.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.persistence.model.Cat;
import ru.itmo.persistence.model.enums.CatColors;
import ru.itmo.persistence.repo.CatRepository;
import ru.itmo.web.util.MappingUtil;

import java.sql.Timestamp;
import java.util.List;

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

    public void deleteById(int id) { catRepository.deleteById(id); }

    public Cat findCat(int id) {
        return catRepository.findById(id);
    }

    public List<Cat> findAllCats() {
        return catRepository.findAll();
    }

    public List<Cat> findByName(String name) { return catRepository.findByName(name); }

    public List<Cat> findByDateOfBirth(Timestamp dateOfBirth) { return catRepository.findByDateOfBirth(dateOfBirth); }

    public List<Cat> findByBreed(String breed) { return catRepository.findByBreed(breed); }

    public List<Cat> findByColor(CatColors color) { return catRepository.findByColor(color); }
}