/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testingClass;

/**
 *
 * @author David
 */
public class Puppy extends Dog {

    public int months_old;

    public Puppy(PetStore pet_store, int age, String breed, int months_old) {
        super(pet_store, age, breed);
        this.months_old = months_old;
    }

}
