/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.person;

import dtos.PersonDTO;
import entities.Address;
import entities.Phone;
import entities.hobby.Hobby;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author peter
 */
@Entity
@NamedQuery(name = "Person.getAllRows", query = "SELECT p FROM Person p")
@NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String email;
    private String firstName;
    private String lastName;

    @OneToMany(
        mappedBy = "person",
        cascade = CascadeType.PERSIST
    )
    private List<Phone> phones;
    
    @ManyToMany
    @JoinTable(
            name = "person_hobby", 
            joinColumns = @JoinColumn(name = "person_id"), 
            inverseJoinColumns = @JoinColumn(name = "hobby_name")
    )
    private List<Hobby> hobbies;
    

    public Person(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phones = new ArrayList<>();
        this.hobbies = new ArrayList<>();
    }

    public Person() {
    }

    public void addPhone(Phone phone) {
        if (phone != null) {
            this.phones.add(phone);
            phone.setPerson(this);
        }
    }
    
    public void addHobby(Hobby hobby) {
        if (hobby != null) {
            this.hobbies.add(hobby);
            hobby.setPersons((List<Person>) this); // serPersons Hobby. ln.118 ??
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Phone> getPhones() {
        return phones;
    }
    
     public List<Hobby> getHobbies() {
        return hobbies;
    }

}
