
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

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
    
    
    
    static int distanceBetweenNonSharedFields(Class<?> a, Class<?> b){
        int FiledsDistance = 0;
        int MethodsDistance = 0;
       
        Field[] classAFields = a.getDeclaredFields();
        Field[] classBFields = b.getDeclaredFields();
        Method[] classAMethods = a.getDeclaredMethods();
        Method[] classBMethods = b.getDeclaredMethods();
            
       
        //loop through the first class variables and compair it to the second class variables
        //still need fixing
        FiledsDistance = classAFields.length + classBFields.length;
        for(int i = 0; i < classAFields.length; i++){
                for(int j = 0; j < classBFields.length; j++){
                    if(classAFields[i].getName().equals(classBFields[j].getName()) && classAFields[i].getType().equals(classBFields[j].getType())){
                        FiledsDistance--;
                    }
                }
            }
        //
        MethodsDistance = classAMethods.length + classBMethods.length;
        for(int i = 0; i < classAMethods.length; i++){
            for(int j = 0; j < classBMethods.length; j++){
                if(classAMethods[i].getName().equals(classBMethods[j].getName())){
                    MethodsDistance--;
                }
            }
        }
        
        return FiledsDistance + MethodsDistance;
    }
    
    //used by bbelow method
     public static Set<Class<?>> getSuperclasses(Class<?> clazz) {
        final Set<Class<?>> result = new LinkedHashSet<>();
        final Queue<Class<?>> queue = new ArrayDeque<>();
        queue.add(clazz);
        if (clazz.isInterface()) {
            queue.add(Object.class); // optional
        }
        while (!queue.isEmpty()) {
            Class<?> c = queue.remove();
            if (result.add(c)) {
                Class<?> sup = c.getSuperclass();
                if (sup != null) {
                    queue.add(sup);
                }
                queue.addAll(Arrays.asList(c.getInterfaces()));
            }
        }
        return result;
    }

     //gets all common parents
    public static Set<Class<?>> commonSuperclasses(Iterable<Class<?>> classes) {
        Iterator<Class<?>> it = classes.iterator();
        if (!it.hasNext()) {
            return Collections.emptySet();
        }
        // begin with set from first hierarchy
        Set<Class<?>> result = getSuperclasses(it.next());
        // remove non-superclasses of remaining
        while (it.hasNext()) {
            Class<?> c = it.next();
            Iterator<Class<?>> resultIt = result.iterator();
            while (resultIt.hasNext()) {
                Class<?> sup = resultIt.next();
                if (!sup.isAssignableFrom(c)) {
                    resultIt.remove();
                }
            }
        }

        return result;
    }
    
    /*
    public static List<Class<?>> lowestCommonSuperclasses(Iterable<Class<?>> classes) {
        Collection<Class<?>> commonSupers = commonSuperclasses(classes);
        return lowestClasses(commonSupers);
    }

    public static List<Class<?>> lowestClasses(Collection<Class<?>> classes) {
        final LinkedList<Class<?>> source = new LinkedList<>(classes);
        final ArrayList<Class<?>> result = new ArrayList<>(classes.size());
        while (!source.isEmpty()) {
            Iterator<Class<?>> srcIt = source.iterator();
            Class<?> c = srcIt.next();
            srcIt.remove();
            while (srcIt.hasNext()) {
                Class<?> c2 = srcIt.next();
                if (c2.isAssignableFrom(c)) {
                    srcIt.remove();
                } else if (c.isAssignableFrom(c2)) {
                    c = c2;
                    srcIt.remove();
                }
            }
            result.add(c);
        }
        result.trimToSize();
        return result;
    }
*/
}
