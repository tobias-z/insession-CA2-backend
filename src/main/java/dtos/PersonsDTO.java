/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.person.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author peter
 */
public class PersonsDTO {
    private List<PersonDTO> all = new ArrayList();

    public PersonsDTO(List<Person> persons) {
        persons.forEach((p) -> {
        all.add(new PersonDTO(p));
    });
    }

    public List<PersonDTO> getAll() {
        return all;
    }
    
    
     
    
    
    
}