package ru.itmo.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itmo.persistence.model.Cat;
import ru.itmo.persistence.repo.CatRepository;
import ru.itmo.web.controller.exception.CatIdMismatchException;
import ru.itmo.web.controller.exception.CatNotFoundException;

@RestController
@RequestMapping("/api/cats")
public class CatController {

    private final CatRepository catRepository;

    public CatController(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @GetMapping
    public Iterable<Cat> findAll() {
        return catRepository.findAll();
    }

    @GetMapping("/name/{catName}")
    public Cat findByName(@PathVariable String catName) {
        return catRepository.findByName(catName);
    }

    @GetMapping("/{id}")
    public Cat findOne(@PathVariable long id) {
        return catRepository.findById(id)
                .orElseThrow(CatNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cat create(@RequestBody Cat cat) {
        return catRepository.save(cat);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        catRepository.findById(id)
                .orElseThrow(CatNotFoundException::new);
        catRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Cat updateBook(@RequestBody Cat cat, @PathVariable long id) {
        if (cat.getId() != id) {
            throw new CatIdMismatchException();
        }
        catRepository.findById(id)
                .orElseThrow(CatNotFoundException::new);
        return catRepository.save(cat);
    }
}
