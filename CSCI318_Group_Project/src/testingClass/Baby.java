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
public class Baby extends Dog {

    public int seconds;

    public Baby(PetStore pet_store, int age, String breed, int months_old, int seconds) {
        super(pet_store, age, breed);
        this.seconds = seconds;
    }

}
