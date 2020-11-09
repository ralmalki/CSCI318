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
public class Bird extends Animal {

    public boolean is_sick;

    public Bird(PetStore pet_store, int age, boolean sick) {
        super(pet_store, age);
        this.is_sick = sick;
    }

}
