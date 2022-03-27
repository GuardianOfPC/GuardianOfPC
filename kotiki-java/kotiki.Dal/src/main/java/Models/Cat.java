package Models;

import enums.CatColors;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cats")
public class Cat
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column (name = "name")
        private String name;

        @Column(name = "date_of_birth")
        private Timestamp dateOfBirth;

        @Column(name = "breed")
        private String breed;

        @Enumerated(EnumType.STRING)
        private CatColors color;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "owner_id")
        private Owner owner;

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinColumn(name = "friends_id")
        private List<Cat> friends;

        public Cat(){
        }

        public Cat(Integer id, String name, Timestamp dateOfBirth, String breed, CatColors color) {
            this.id = id;
            this.name = name;
            this.dateOfBirth = dateOfBirth;
            this.breed = breed;
            this.color = color;
            friends = new ArrayList<>();
        }

        public Cat(String name, Timestamp dateOfBirth, String breed, CatColors color) {
            this.name = name;
            this.dateOfBirth = dateOfBirth;
            this.breed = breed;
            this.color = color;
            friends = new ArrayList<>();
        }

        public void addFriend(Cat cat) {
            cat.addFriend(this);
            friends.add(cat);
        }

        public void removeFriend(Cat cat) {
            friends.remove(cat);
        }

        public Integer getId() { return id; }

        public String getName() { return name; }

        public Timestamp getDateOfBirth() {
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

        public List<Cat> getFriends() {
            return friends;
        }

        public void setId(Integer id) { this.id = id; }

        public void setName(String name) {
            this.name = name;
        }

        public void setDateOfBirth(Timestamp dateOfBirth) {
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
