
import java.lang.reflect.Field;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author David For unequal references, a measure of the difference between the
 * corresponding objects. This will be the same notion of object distance,
 * applied recursively
 *
 *
 * Recursive distance: A measure entirely determined by the values of the
 * distances between (recursively) objects p.r and q.r, for all matching
 * reference attributes r such that p.r = q.r (both non-Void)
 */
public class RecursiveDistance {

    //accepts one single object
    public static <T> double distance(T one) {
        double distance = 0;
        Field[] allDeclaredFields = one.getClass().getDeclaredFields();

        //checks all fields for objects
        for (int i = 0; i < allDeclaredFields.length; i++) {
            //ignores all types that are not objects
            if (!allDeclaredFields[i].getType().isPrimitive() && !allDeclaredFields[i].getType().isArray() && !allDeclaredFields[i].getType().getName().equals("java.lang.String")) {
                
                //do something with class name in here
                String className = allDeclaredFields[i].getType().getName();
                System.out.println("Class name: " + className);
                
            }

        }

        /*
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i]); //debbug
            System.out.println(fields[i].getName()); //debug
            try {
                Class.forName(fields[i].getDeclaringClass().getName());
                System.out.println("Class found");
                //add to arraylist to check class object
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
            }
            System.out.println("-=-=-=-=");

        }
*/
        return distance;
    }

}
