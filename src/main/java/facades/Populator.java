/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.RenameMeDTO;
import entities.renameme.RenameMeRepository;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        RenameMeRepository repo = FacadeExample.getInstance(emf);
        repo.create(new RenameMeDTO("First 1", "Last 1"));
        repo.create(new RenameMeDTO("First 2", "Last 2"));
        repo.create(new RenameMeDTO("First 3", "Last 3"));

    }
    
    public static void main(String[] args) {
        populate();
    }
}
