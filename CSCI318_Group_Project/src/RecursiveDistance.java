
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
    /*
    public double recurseDistance(Class p1, Class p2, double prevDistance) {
        double returnDistance = 0;
        int commonFields = getCommonFields(p1, p2);
        returnDistance = (1 / commonFields) * FieldDistance.getDistance(p1, p2) + (0.5 * recurseDistance(p1, p2, returnDistance) ;
        return 0;
    }
    
    public int getCommonFields(Class<?> obj1, Class<?> obj2) {
        Field[] obj1Fields = obj1.getDeclaredFields();
        Field[] obj2Fields = obj2.getDeclaredFields();
        int commonFields = 0;
        
        
        
        
        for(int i = 0; i < obj1Fields.length; i++) {
            for(int j = 0; j < obj2Fields.length; j++) {
                if(obj1Fields[i].getName().equals(obj2Fields[j].getName()) && obj1Fields[i].getType().equals(obj2Fields[j].getType())) {
                    commonFields++;
                    break;
                }
            }
        }
        
        if(commonFields == 0) {
            commonFields = 1;
        }
        return commonFields;
    }
    */

}
