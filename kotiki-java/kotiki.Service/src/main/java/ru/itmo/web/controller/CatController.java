package ru.itmo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.persistence.model.Cat;
import ru.itmo.persistence.model.enums.CatColors;
import ru.itmo.web.controller.exception.CatIdMismatchException;
import ru.itmo.web.controller.exception.CatNotFoundException;
import ru.itmo.web.controller.exception.OwnerNotFoundException;
import ru.itmo.web.controller.exception.UnauthorizedException;
import ru.itmo.web.dto.CatDto;
import ru.itmo.web.service.CatService;
import ru.itmo.web.util.DtoMappingUtil;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cats")
public class CatController {

    @Autowired
    private final CatService catService;

    private final DtoMappingUtil mappingUtil = new DtoMappingUtil();

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping
    public List<CatDto> findAll() {
        List<Cat> cats = catService.findAllCats();
        return cats.stream()
                .map(mappingUtil::mapToCatDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/name/{catName}")
    public List<CatDto> findByName(@PathVariable String catName) {
        try {
            List<Cat> cats = catService.findByName(catName);
            return cats.stream()
                    .map(mappingUtil::mapToCatDto)
                    .collect(Collectors.toList());
        }
        catch (CatNotFoundException ex){
            throw new CatNotFoundException(ex);
        }
    }

    @GetMapping("/{id}")
    public CatDto findById(@PathVariable int id) {
        try {
            return mappingUtil.mapToCatDto(catService.findCat(id));
        }
        catch (OwnerNotFoundException ex) {
            throw new OwnerNotFoundException(ex);
        }

    }

    @GetMapping("/dateOfBirth/{dateOfBirth}")
    public List<CatDto> findByDateOfBirth(@PathVariable Timestamp dateOfBirth) {
        try {
            List<Cat> cats = catService.findByDateOfBirth(dateOfBirth);
            return cats.stream()
                    .map(mappingUtil::mapToCatDto)
                    .collect(Collectors.toList());
        }
        catch (CatNotFoundException ex){
            throw new CatNotFoundException(ex);
        }
    }

    @GetMapping("/breed/{breed}")
    public List<CatDto> findByBreed(@PathVariable String breed) {
        try {
            List<Cat> cats = catService.findByBreed(breed);
            return cats.stream()
                    .map(mappingUtil::mapToCatDto)
                    .collect(Collectors.toList());
        }
        catch (CatNotFoundException ex){
            throw new CatNotFoundException(ex);
        }
    }

    @GetMapping("/color/{color}")
    public List<CatDto> findByColor(@PathVariable CatColors color) {
        try {
            List<Cat> cats = catService.findByColor(color);
            return cats.stream()
                    .map(mappingUtil::mapToCatDto)
                    .collect(Collectors.toList());
        }
        catch (CatNotFoundException ex){
            throw new CatNotFoundException(ex);
        }
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public CatDto create(@RequestBody CatDto cat) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("admin"))){
            throw new UnauthorizedException();
        }
        return mappingUtil.mapToCatDto(catService.saveCat(mappingUtil.mapToCatEntity(cat)));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("admin"))){
            throw new UnauthorizedException();
        }
        try {
            catService.findCat(id);
        }
        catch (CatNotFoundException ex){
            throw new CatNotFoundException(ex);
        }
        catService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public CatDto updateCat(@RequestBody CatDto cat, @PathVariable int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (cat.getId() != id) {
            throw new CatIdMismatchException();
        }
        if (auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("admin"))){
            throw new UnauthorizedException();
        }
        try {
                catService.findCat(id);
        }
        catch (CatNotFoundException ex){
            throw new CatNotFoundException(ex);
        }
        return mappingUtil.mapToCatDto(catService.saveCat(mappingUtil.mapToCatEntity(cat)));
    }
}
