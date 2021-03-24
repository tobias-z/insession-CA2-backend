/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Phone;
import entities.person.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author peter
 */
public class PersonDTO {
    
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private List<PhoneDTO> phones;
    
    public PersonDTO(Person p) {
        this.id = p.getId();
        this.email = p.getEmail();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.phones = PhoneDTO.getFromList(p.getPhones());
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

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", phones=" + phones +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonDTO)) {
            return false;
        }
        PersonDTO personDTO = (PersonDTO) o;
        return getId() == personDTO.getId() && Objects.equals(getEmail(), personDTO.getEmail())
            && Objects.equals(getFirstName(), personDTO.getFirstName()) && Objects
            .equals(getLastName(), personDTO.getLastName()) && Objects
            .equals(getPhones(), personDTO.getPhones());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getFirstName(), getLastName(), getPhones());
    }
}
