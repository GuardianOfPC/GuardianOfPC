package ru.itmo.web.util;

import ru.itmo.persistence.model.Cat;
import ru.itmo.persistence.model.Owner;
import ru.itmo.web.dto.CatDto;
import ru.itmo.web.dto.OwnerDto;

public class MappingUtil {
    public CatDto mapToCatDto(Cat cat){
        CatDto dto = new CatDto();
        dto.setId(cat.getId());
        dto.setName(cat.getName());
        dto.setDateOfBirth(cat.getDateOfBirth());
        dto.setBreed(cat.getBreed());
        dto.setColor(cat.getColor());
        return dto;
    }

    public Cat mapToCatEntity(CatDto dto){
        Cat cat = new Cat();
        cat.setId(dto.getId());
        cat.setName(dto.getName());
        cat.setDateOfBirth(dto.getDateOfBirth());
        cat.setBreed(dto.getBreed());
        cat.setColor(dto.getColor());
        return cat;
    }

    public OwnerDto mapToOwnerDto(Owner owner){
        OwnerDto dto = new OwnerDto();
        dto.setId(owner.getId());
        dto.setName(owner.getName());
        dto.setDateOfBirth(owner.getDateOfBirth());
        return dto;
    }

    public Owner mapToOwnerEntity(OwnerDto ownerDto){
        Owner owner = new Owner();
        owner.setId(ownerDto.getId());
        owner.setName(ownerDto.getName());
        owner.setDateOfBirth(ownerDto.getDateOfBirth());
        return owner;
    }
}
