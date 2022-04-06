package ru.itmo.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itmo.persistence.model.Owner;
import ru.itmo.persistence.repo.OwnerRepository;
import ru.itmo.web.controller.exception.CatIdMismatchException;
import ru.itmo.web.controller.exception.CatNotFoundException;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerRepository ownerRepository;

    public OwnerController(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @GetMapping
    public Iterable<Owner> findAll() {
        return ownerRepository.findAll();
    }

    @GetMapping("/name/{ownerName}")
    public Owner findByName(@PathVariable String ownerName) {
        return ownerRepository.findByName(ownerName);
    }

    @GetMapping("/{id}")
    public Owner findOne(@PathVariable long id) {
        return ownerRepository.findById(id)
                .orElseThrow(CatNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Owner create(@RequestBody Owner owner) {
        return ownerRepository.save(owner);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        ownerRepository.findById(id)
                .orElseThrow(CatNotFoundException::new);
        ownerRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Owner updateBook(@RequestBody Owner owner, @PathVariable long id) {
        if (owner.getId() != id) {
            throw new CatIdMismatchException();
        }
        ownerRepository.findById(id)
                .orElseThrow(CatNotFoundException::new);
        return ownerRepository.save(owner);
    }
}