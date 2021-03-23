/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author peter
 */
public class PersonDTO {
    
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    
    public PersonDTO(Person p) {
        this.id = p.getId();
        this.email = p.getEmail();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
    }

    public PersonDTO(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public static List<PersonDTO> getDTOs(List<Person> pers) {
        List<PersonDTO> pdtos = new ArrayList();
        pers.forEach(per -> pdtos.add(new PersonDTO(per)));
        return pdtos;
    }

    public PersonDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
    
    
}
