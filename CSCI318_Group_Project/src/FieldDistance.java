
import java.lang.reflect.Field;
import java.math.BigDecimal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * compare these fields one by one, considering only “matching” fields
 * corresponding to the same attributes in __both objects__; non-matching fields
 * cause a difference but are captured by the type distance
 *
 * A measure entirely determined by the difference between the matching fields
 * of p and q
 */
//for getting numerical difference between fields. eg string vs string. bool vs bool etc
public class FieldDistance {
    
    //this will take the field list and compare to each other
    public static <T, K> double totalFieldDistance(T one, K two) {
        double totalDistance = 0;
        
        Field[] a = (Field[]) one;
        Field[] b = (Field[]) two;
        
        /*
        NEED TO CHECK IF OBBJECTS ARE THE SAME BEFORE THIS POINT IN TYPE DISTANCE BECAUSE NO CHECK HERE IF SAME NAMES
        eg pet_store name is different to a person object.!!!
        
        In summing individual distances over a set of attributes A, we will always take the arithmetic mean (the sum divided by the number of
        its elements) to avoid giving too much weight to objects that have large numbers of fields. The notation pg 3, right side
        */
        
        for (int i = 0; i < a.length; i++) {
            String currentField = a[i].getName();
            for (int j = 0; j < b.length; j++) {
                if (currentField == (b[j].getName())) {
                    System.out.println("MATCH:" + currentField + " matches " + b[j].getName());
                    
                }
            }
        }
        
        if (totalDistance == 0) {
            System.out.println("Object fields exactly identical");
        }
        return totalDistance;
    }

    /*
    Field[] fields = b1.getClass().getFields();
                for (int i = 0; i < fields.length; i++) {
                    System.out.println("FIELD: " + fields[i].getName());
                    // System.out.println("   Type: " + fields[i].getType());
  
     */
    //gets the type of an item and calls appriate method for distance???
    public static <T, K> double getDistance(T one, K two) {
        double distance = 0; //distance to return

        //gets two objects types
        String type1 = one.getClass().getSimpleName();
        String type2 = two.getClass().getSimpleName();

        //used for checking if a number
        Class class1 = one.getClass();
        Class class2 = two.getClass();

        /*
        //reference website https://coderanch.com/t/367647/java/cast-int-Number
        //code for converting numbers to things
        //need check if null if not a number
        //or use this instead: Math.abs(a)
         */
        //might optimize to set cases/inputs since type checks if equal 
        //at this point in the program the objects are matching but field value differences are getting calulated and checked now
        if (type1.equals("String") && type2.equals("String")) {
            System.out.println("STRING FOUND"); //debug
            //levenstien distance
            distance += stringDistance((String) one, (String) two);
        } else if (type1.equals("Boolean") && type2.equals("Boolean")) {
            System.out.println("boolean FOUND");//debug
            distance += booleanDistance((Boolean) one, (Boolean) two);
        } else if (Number.class.isAssignableFrom(class1) && Number.class.isAssignableFrom(class2)) { //GENERALISED ALL NUBMER TYPES under 1 class
            //could replace with public static <T, K> double getDistance(T one, K two)
            System.out.println("NUMBER FOUND");//debug
            Number numObj = (Number) one;
            Number numObj2 = (Number) two;
            distance += numberDistance(numObj, numObj2);
        } else {
            //type is a reference :D
            distance += compareObject(one, two);
        }
        return distance;
    }

    //gets levenshtein string distance
    public static int stringDistance(String s1, String s2) {
        return dist(s1.toCharArray(), s2.toCharArray());
    }

    //part of string distance
    public static int dist(char[] s1, char[] s2) {

        // memoize only previous line of distance matrix     
        int[] prev = new int[s2.length + 1];

        for (int j = 0; j < s2.length + 1; j++) {
            prev[j] = j;
        }

        for (int i = 1; i < s1.length + 1; i++) {

            // calculate current line of distance matrix     
            int[] curr = new int[s2.length + 1];
            curr[0] = i;

            for (int j = 1; j < s2.length + 1; j++) {
                int d1 = prev[j] + 1;
                int d2 = curr[j - 1] + 1;
                int d3 = prev[j - 1];
                if (s1[i - 1] != s2[j - 1]) {
                    d3 += 1;
                }
                curr[j] = Math.min(Math.min(d1, d2), d3);
            }

            // define current line of distance matrix as previous     
            prev = curr;
        }
        return prev[s2.length];
    }

    //compares two bools and ret dist
    public static int booleanDistance(boolean a, boolean b) {
        if (a == b) {
            return 0;
        }
        return 10; //B, arbitary chosen value
    }

    //might want to change returned data type for more precision aka BigDecimal
    //compares numbers and ret dist
    public static double numberDistance(Number a, Number b) {
        //does difference maths        
        Number difference = new BigDecimal(a.toString()).subtract(new BigDecimal(b.toString()));
        //converts result to positive 
        double distance = Math.abs(difference.doubleValue());
        System.out.println("Number distance: " + distance); //debug

        //check and print error if below zero - not allowed! breaks constraints
        if (distance < 0) {
            System.out.println("-======: ERROR: Distance below zero!!! ======-");
        }

        return distance;
    }

    /*   
    .equals compares objects AND contents, == compares references/memory location
     */
    //compare reference/object function 
    public static <T, K> int compareObject(T a, K b) {
        int distance = -1;
        if (a == b) {
            distance = 0;  //0 identical
            System.out.println("Identical"); //debug
        } else if ((a != b) && ((a != null) && (b != null))) {
            //different but none are void
            distance = 10;
            System.out.println("different but none are void"); //debug
        } else if (((a == null) && (b != null)) || ((b == null) && (a != null))) {
            //one obj is void
            distance = 10;
            System.out.println("one obj void"); //debug
        }

        if (distance == -1) {
            System.out.println("-======: ERROR: Objects distance/comparison wrong!!! ======-");
            System.exit(1);
        }
        return distance;
    }

}
