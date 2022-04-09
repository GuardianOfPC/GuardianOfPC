package ru.itmo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.persistence.model.Owner;
import ru.itmo.web.controller.exception.OwnerIdMissmatchException;
import ru.itmo.web.controller.exception.OwnerNotFoundException;
import ru.itmo.web.controller.exception.UnauthorizedException;
import ru.itmo.web.dto.OwnerDto;
import ru.itmo.web.service.OwnerService;
import ru.itmo.web.util.DtoMappingUtil;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    @Autowired
    private final OwnerService ownerService;

    private final DtoMappingUtil mappingUtil = new DtoMappingUtil();

    public OwnerController(OwnerService ownerRepository) {
        this.ownerService = ownerRepository;
    }

    @GetMapping
    public List<OwnerDto> findAll() {
        List<Owner> owners = ownerService.findAllOwners();
        return owners.stream()
                .map(mappingUtil::mapToOwnerDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/name/{ownerName}")
    public List<OwnerDto> findByName(@PathVariable String ownerName) {
        try {
            List<Owner> owners = ownerService.findByName(ownerName);
            return owners.stream()
                    .map(mappingUtil::mapToOwnerDto)
                    .collect(Collectors.toList());
        }
        catch (OwnerNotFoundException ex) {
            throw new OwnerNotFoundException(ex);
        }
    }

    @GetMapping("/{id}")
    public OwnerDto findById(@PathVariable int id) {
        try {
            return mappingUtil.mapToOwnerDto(ownerService.findOwner(id));
        }
        catch (OwnerNotFoundException ex) {
            throw new OwnerNotFoundException(ex);
        }
    }

    @GetMapping("/dateOfBirth/{date}")
    public List<OwnerDto> findByDateOfBirth(@PathVariable Timestamp date) {
        try {
            List<Owner> owners = ownerService.findByDateOfBirth(date);
            return owners.stream()
                    .map(mappingUtil::mapToOwnerDto)
                    .collect(Collectors.toList());
        }
        catch (OwnerNotFoundException ex) {
            throw new OwnerNotFoundException(ex);
        }
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerDto create(@RequestBody OwnerDto owner) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("admin"))){
            throw new UnauthorizedException();
        }
        return mappingUtil.mapToOwnerDto(ownerService.saveOwner(mappingUtil.mapToOwnerEntity(owner)));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("admin"))){
            throw new UnauthorizedException();
        }
        try {
            ownerService.findOwner(id);
        }
        catch (OwnerNotFoundException ex) {
            throw new OwnerNotFoundException(ex);
        }
        ownerService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public OwnerDto updateOwner(@RequestBody OwnerDto owner, @PathVariable int id) {
        if (owner.getId() != id) {
            throw new UnauthorizedException();
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("admin"))){
            throw new UnauthorizedException();
        }
        try {
            ownerService.findOwner(id);
        }
        catch (OwnerNotFoundException ex) {
            throw new OwnerNotFoundException(ex);
        }
        return mappingUtil.mapToOwnerDto(ownerService.saveOwner(mappingUtil.mapToOwnerEntity(owner)));
    }
}