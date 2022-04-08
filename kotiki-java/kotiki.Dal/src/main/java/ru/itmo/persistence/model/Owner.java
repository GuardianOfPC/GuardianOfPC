package ru.itmo.persistence.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "owners")
public class Owner {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "name")
        private String name;

        @Column(name = "date_of_birth")
        private Timestamp dateOfBirth;

        @OneToMany(mappedBy = "owner")
        private List<Cat> cats;

        public Owner(){
        }

        public Owner(Integer id, String name, Timestamp dateOfBirth) {
            this.id = id;
            this.name = name;
            this.dateOfBirth = dateOfBirth;
            cats = new ArrayList<>();
        }

        public Owner(String name, Timestamp dateOfBirth) {
            this.name = name;
            this.dateOfBirth = dateOfBirth;
            cats = new ArrayList<>();
        }

        public void addCat(Cat cat) {
            cat.setOwner(this);
            cats.add(cat);
        }

        public void removeCat(Cat cat) {
            cats.remove(cat);
        }

        public Integer getId() { return id; }

        public String getName() {
            return name;
        }

        public Timestamp getDateOfBirth() {
            return dateOfBirth;
        }

        public List<Cat> getCats() {
            return cats;
        }

        public void setId(Integer id) { this.id = id; }

        public void setName(String name) {
            this.name = name;
        }

        public void setDateOfBirth(Timestamp dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public void setCats(List<Cat> cats) {
            this.cats = cats;
        }
}
