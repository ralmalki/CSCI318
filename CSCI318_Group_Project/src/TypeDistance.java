/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 *
 * A measure of the difference between the types of
 * p and q, independent of the values of the objects themselves
 *
 *
 * Type distance principle. The distance between two types is
 * a monotonically increasing function of their path lengths to any
 * closest common ancestor, and of the number of their non-shared
 * features3.
 *
 *
 * page 4, top right
 */
//https://www.javatpoint.com/java-data-types


//This class will call field distance?? or no?
//-=-=-=-=-=-=-=-
//non-matching fields cause a difference but are captured by the type distance
//-=-=-=-=-=-=-=-=
public class TypeDistance {

    //gets sum of all type distances
    public static <T, K> double totalTypeDistance(T a, K b) {
        //return o.getClass().getSimpleName();
        double totalDistance = 0;

        //gets common class
        Class<?> klass = mostSpecificCommonSuperclass(a.getClass(), b.getClass());
        System.out.println(klass); //debug
        
        //gets difference between class objects (add methods soon)
        totalDistance += (((int) TypeDistance.distanceToSuperclass(a.getClass(), klass) + (int) TypeDistance.distanceToSuperclass(b.getClass(), klass)));

        if (totalDistance == 0) {
            System.out.println("Objects and methods/attributes are exactly identical"); //debug
        }
        return totalDistance;
    }

    /*
    use this to get the common superclass. and then call it again comparing each object to the FIRST result
    and then adding both numbers as the path distance of each to the superclass :D
    BUT NEED TO DO THIS IN ABOTHER METHOD BC NEED TO RETURN DOUBLE/NUMBER VALUE
     */
    //gets common class to both given objects 
    static Class<?> mostSpecificCommonSuperclass(Class<?> a, Class<?> b) {
        //need to test this with other dummy classses to ensure it gets minumul class
        Class<?> s = a;
        while (!s.isAssignableFrom(b)) {
            s = s.getSuperclass();
        }
        return s;
    }
    //MAYBE USE THIS INSTEAD
    //https://stackoverflow.com/questions/9797212/finding-the-nearest-common-superclass-or-superinterface-of-a-collection-of-cla

    //gets common class to both given objects and calulates/returns distance
    static int distanceToSuperclass(Class<?> a, Class<?> b) {
        Class<?> s = a;
        int differenceCount = 0;
        //if of same type, equal. Therefore distance = 0
        if (a.getClass() == b.getClass()) {
            System.out.println("Match, classes equal");
            return differenceCount;
        }
        while (!s.isAssignableFrom(b)) {
            //System.out.println("BEFORE:" + s);
            s = s.getSuperclass();
            //System.out.println("after:" + s);
            differenceCount++;
        }
        System.out.println("Path distance: " + differenceCount); //debug
        return differenceCount;
    }
}
