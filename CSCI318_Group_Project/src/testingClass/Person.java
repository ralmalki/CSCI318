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
public class Person {

    public String name;
    public Animal animal;
    public Person spouse;

    public Person(String name, Animal animal, Person spouse) {
        this.name = name;
        this.animal = animal;
        this.spouse = spouse;
    }

}
