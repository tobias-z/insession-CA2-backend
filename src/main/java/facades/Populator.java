/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;


import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade facade = PersonFacade.getPersonFacade(emf);
        facade.addPerson("Test@Test.dk", "Test", "Test");        
    }
    
    public static void main(String[] args) {
        populate();
    }
}
