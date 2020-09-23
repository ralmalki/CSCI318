
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import testingClass.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author David
 */
public class MainScreen extends javax.swing.JFrame {

    /**
     * Creates new form MainScreen
     */
    public MainScreen() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 950, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 542, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreen().setVisible(true);

                //sample object testing list to use for comparison
                PetStore ps1 = new PetStore("Store1");
                PetStore ps2 = new PetStore("Store2");

                Bird bird1 = new Bird(ps1, 3, false);
                Bird bird2 = new Bird(ps1, 1, false);

                Dog dog1 = new Dog(ps2, 10, "");

                //Might need deep copy constructor??
                Person p2 = null;
                Person p1 = null;
                p2 = new Person("Mary", dog1, p1);
                p1 = new Person("Steve", bird1, p2);
                Person p3 = new Person("Jenna", null, p2);
                Person p4 = new Person("Kelly", bird2, null);

                //working on code that gets all the fields of a class (inc inherited ones)
                //needed for getting types and values for distance forumlas
                Bird b1 = new Bird(new PetStore("Store1"), 11, false);
                Bird b2 = new Bird(new PetStore("Store1"), 11, false);
                //FieldDistance.compareObject(b1, b2);
                b1 = b2;
                //FieldDistance.compareObject(b1, b2);

                Bird b3 = null;

                Field[] fields = b1.getClass().getFields();
                for (int i = 0; i < fields.length; i++) {
//                    System.out.println("FIELD: " + fields[i].getName());
//                    System.out.println("   Type: " + fields[i].getType());

                }

                Dog dog2 = new Dog(new PetStore("Store2"), 22, "Doggo");
                Puppy puppy1 = new Puppy(new PetStore("Store3"), 33, "Cutie", 12);

                int nonShared = TypeDistance.distanceBetweenNonSharedFields(p3.getClass(), dog2.getClass());
                System.out.println("Check if two classes have different Fields " + nonShared);

               // Class<?> clazz = p1.getClass();
                //Class<?> clazz2 = dog2.getClass();
                //Class<?> clazz3 = puppy1.getClass();

                //Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
               //classes.add(clazz);
               // classes.add(clazz2);
                //classes.add(clazz3);
                //System.out.println(classes);
/*
                System.out.println("Common classes of " + classes + " :");
                Set<Class<?>> classes22 = commonSuperclasses(classes);
                System.out.println(classes22);
                System.out.println("---");
                System.out.println(lowestCommonSuperclasses(classes));

                System.out.println("");
                System.out.println("");
                System.out.println("=-=-=-=-=-=");
*/
                //
               // dist(classes22, p1.getClass(), dog2.getClass());

                //FieldDistance.totalFieldDistance(b1.getClass().getFields(), b2.getClass().getFields());
                //System.out.println(mostSpecificCommonSuperclass(bird1.getClass(), dog2.getClass()));
                // Class<?> klass = mostSpecificCommonSuperclass(bird1.getClass(), bird1.getClass());
                //System.out.println(klass);
                //System.out.println("DIST: " + ((int) TypeDistance.distanceToSuperclass(dog2.getClass(), klass) + (int) TypeDistance.distanceToSuperclass(bird1.getClass(), klass)));
                /*
                //thing for adding all distances together
                p equiv q = sum of( typeDistance(p.type, q.type) + fieldDistance(p, q) + recursive_distance({[p.r equiv q.r]
                    |  for all r in reference_attributes(p.type, q.type)}));
                 */
            }
        });
    }

    static int dist(Set<Class<?>> classes, Class<?> a, Class<?> b) {
        int distance = 0;
        Field[] classAFields = a.getDeclaredFields();
        Field[] classBFields = b.getDeclaredFields();
        Field[] currentClassFields;

        //adds all the superclasses to an arraylist for access
        ArrayList<Class<?>> superclasses = new ArrayList<Class<?>>();
        //for looping through superclass list
        Iterator<Class<?>> it = classes.iterator();
        while (it.hasNext()) {
            superclasses.add(it.next());
        }
        System.out.println("Arraylist: " + superclasses);

        Class<?> currentClass = null; //the current class


        //compares a to b fields
        for (int i = 0; i < classAFields.length; i++) {

            for (int j = 0; j < classBFields.length; j++) {
                if (!(classAFields[i].equals(classBFields[j]))) {
                    System.out.println("--- Fieleds compared ---------------------" + classAFields[i].getName() + i + " === " + classBFields[j].getName() + j);
                    System.out.println(classAFields[i].getName().equals(classBFields[j].getName()) && classAFields[i].getType().equals(classBFields[j].getType()));
                    distance++;
                }

            }
 
            
            //this loop checks the superclasses for the derived fields
            for (int k = 0; k < superclasses.size()-1; k++) {
                currentClass = superclasses.get(k);
                System.out.println("TT: " + currentClass.getClass());
                
                if (currentClass == null) {
                    break;
                }
                
                //need to loop through all of j too!!
                //and after doing that the distance will be correct :D
              

            
                
                currentClassFields = superclasses.get(k).getDeclaredFields();
                if (!(classAFields[i].equals(currentClassFields[k]))) {
                    System.out.println("--- Fieleds compared -------222--------------" + classAFields[i].getName() + i + " === " + currentClassFields[k].getName() + k);
                    System.out.println(classAFields[i].getName().equals(currentClassFields[k].getName()) && classAFields[i].getType().equals(currentClassFields[k].getType()));
                    distance++;
                }
 
            }
            
             
        }

        //this needs to go in the above check
        //loops through all superclasses. need to also check this in the above if statement. the attribute/methods could come from a superclass
        System.out.println("DISTANCE RESULT:" + distance);
        return distance;
    }

    

   

    

    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
