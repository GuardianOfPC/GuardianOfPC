package Models;

import Models.Enums.CatColors;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cat
{
    public String getName()
    {
        return name;
    }

    public LocalDate getDateOfBirth()
    {
        return dateOfBirth;
    }

    public String getBreed()
    {
        return breed;
    }

    public CatColors getColor()
    {
        return color;
    }

    public Owner getOwner()
    {
        return owner;
    }

    public ArrayList<Cat> getFriends()
    {
        return friends;
    }

    private String name;
    private LocalDate dateOfBirth;
    private String breed;
    private CatColors color;
    private Owner owner;
    private ArrayList<Cat> friends;
}
