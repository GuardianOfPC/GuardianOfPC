package DTO;

import DTO.Cat;

import java.sql.Date;
import java.util.ArrayList;

public class Owner {
    private Integer id;
    private String name;
    private Date dateOfBirth;
    private ArrayList<Cat> cats;

    public Owner(){
    }

    public Owner(Integer id, String name, Date dateOfBirth, ArrayList<Cat> cats) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.cats = cats;
    }

    public Owner(String name, Date dateOfBirth, ArrayList<Cat> cats) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.cats = cats;
    }

    public Integer getId() { return id; }

    public String getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public ArrayList<Cat> getCats() {
        return cats;
    }

    public void setId(Integer id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setCats(ArrayList<Cat> cats) {
        this.cats = cats;
    }
}
