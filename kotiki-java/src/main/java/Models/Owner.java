package Models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Owner
{
    public String getName()
    {
        return name;
    }

    public LocalDate getDateOfBirth()
    {
        return dateOfBirth;
    }

    public ArrayList<Cat> getCats()
    {
        return cats;
    }

    private String name;
    private LocalDate dateOfBirth;
    private ArrayList<Cat> cats;
}
