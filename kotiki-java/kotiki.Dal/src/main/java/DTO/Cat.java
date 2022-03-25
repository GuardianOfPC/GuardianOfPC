package DTO;

import Enums.CatColors;

import java.sql.Date;
import java.util.ArrayList;

public class Cat
{
    private Integer id;
    private String name;
    private Date dateOfBirth;
    private String breed;
    private CatColors color;
    private Owner owner;
    private ArrayList<Cat> friends;

    public Cat(){
    }

    public Cat(Integer id, String name, Date dateOfBirth, String breed, CatColors color, Owner owner, ArrayList<Cat> friends) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
        this.friends = friends;
    }

    public Cat(String name, Date dateOfBirth, String breed, CatColors color, Owner owner, ArrayList<Cat> friends) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
        this.friends = friends;
    }

    public Integer getId() { return id; }

    public String getName() { return name; }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBreed() {
        return breed;
    }

    public CatColors getColor() {
        return color;
    }

    public Owner getOwner() {
        return owner;
    }

    public ArrayList<Cat> getFriends() {
        return friends;
    }

    public void setId(Integer id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setColor(CatColors color) {
        this.color = color;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setFriends(ArrayList<Cat> friends) {
        this.friends = friends;
    }
}
