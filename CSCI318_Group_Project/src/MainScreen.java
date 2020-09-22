
import java.lang.reflect.Field;
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
                
                int nonShared = distanceBetweenNonSharedFields(b1.getClass(), dog2.getClass());
                System.out.println("Check if two classes have different Fields " + nonShared);

                Class<?> clazz = bird1.getClass();
                Class<?> clazz2 = dog2.getClass();
                Class<?> clazz3 = puppy1.getClass();

                Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
                classes.add(clazz);
                classes.add(clazz2);
                classes.add(clazz3);
                //System.out.println(classes);

                System.out.println("Common classes of " + classes + " :");
                 Set<Class<?>> classes22 = commonSuperclasses(classes);
                System.out.println(classes22);
                System.out.println("---");
                System.out.println(lowestCommonSuperclasses(classes));

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
    
    
    static int distanceBetweenNonSharedFields(Class<?> a, Class<?> b){
        int distence = 0;
        
        System.out.println("--- Class A Gen superclass---------------------" + a.getGenericSuperclass());
        
        //First check if both classes have the same superclass
        if(haveSameSuperClass(a, b)){
            Field[] classAFields = a.getFields();
            Field[] classBFields = b.getFields();
            
            
            for (Field classAField : classAFields) {
                for (Field classBField : classBFields) {
                    if(classAField.getName().equals(classBField.getName())){
                        System.out.println("--- Class A Field---------------------" + classAField.getName());
                        System.out.println("--- Class B Field---------------------" + classBField.getName());
                        distence++;
                    }
//if(!(classAFields[i].getGenericType() == classBFields[j].getGenericType())){
//    distence++;
//}
                }
            }
        }
        
        return distence;
    }
    
    static boolean haveSameSuperClass(Class<?> a, Class<?> b){
        if(a.getGenericSuperclass().equals(b.getGenericSuperclass()))
            return true;
        return false;
    }

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


    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
