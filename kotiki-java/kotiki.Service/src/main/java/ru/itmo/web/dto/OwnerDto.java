package ru.itmo.web.dto;

import ru.itmo.persistence.model.Cat;

import java.sql.Timestamp;
import java.util.List;

public class OwnerDto {
    private Integer id;

    private String name;

    private Timestamp dateOfBirth;

    private List<Cat> cats;

    public List<Cat> getCats() { return cats; }

    public void setCats(List<Cat> cats) { this.cats = cats; }

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
