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
public class Dog extends Animal {

    public String breed;

    public Dog(PetStore pet_store, int age, String breed) {
        super(pet_store, age);
        this.breed = breed;
    }
    
    public String getBreed() {
        return breed;
    }

}
