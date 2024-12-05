
import static com.sun.corba.se.impl.naming.cosnaming.NamingContextImpl.debug;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import static jdk.nashorn.internal.codegen.ObjectClassGenerator.pack;
import sun.net.www.protocol.file.FileURLConnection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author raymalk
 */
public class AutoTest {
    
    int filedDistTest = 0;
    int TypeDistTest = 0;
    String jarName;

    
    /*
        This function take a string for the JAR file bath *It has to be the full bath*
        The function will open the JAR file and loop through all the files in the JAR
        with every file the function will call the process function and pass the java file name
    */
    
    public static void openAndRestractJAR(String fileBath) throws ClassNotFoundException, IOException, Exception{
        System.out.println("========== Extracting classes name =============");
//        //File directory = new File("/Users/raymalk/Downloads/");
//        ArrayList<Class<?>> classes = getClassesForPackage("test.jar");
//        //checkDirectory(directory, "Test", classes );
//        if(classes.isEmpty())
//            System.out.println("Classes is empty");
//        for(Class cla : classes){
//            System.out.println(cla.getName());
//        }

        JarFile jarFile = new JarFile(fileBath);
        List<String> classes= new ArrayList<String>();
        final Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            final JarEntry entry = entries.nextElement();
            if (entry.getName().contains(".")) {
                System.out.println("File : " + entry.getName());
                JarEntry fileEntry = jarFile.getJarEntry(entry.getName());
                InputStream input = jarFile.getInputStream(fileEntry);
                classes.addAll(extractClassesFromJavaFile(input));
            }
        }
        
        for(int i = 0; i < classes.size(); i++){
            System.out.println(classes.get(i));
        }
        
        //Just trying one of the tests in the FieldDisntance
        if(classes.size() > 1){
        int dd = FieldDistance.compareObject(classes.get(0).trim(), classes.get(1).trim());
        System.out.println("FieldDis == " + dd);
        }
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        assert classLoader != null;
//        List<Class> dirs = getClasses(classLoader, "/Users/raymalk/Downloads/Calculator");
//        System.out.println(dirs.size());
//        for(int i = 0; i < dirs.size(); i++){
//            System.out.println(dirs.get(i).getTypeName());
//        }
    }
    
    /*
    This function will real java files line by line and find class name
    The function will return a list of String will all classes names in the java file
    */
    private static List<String> extractClassesFromJavaFile(InputStream input) throws IOException, ClassNotFoundException {
        InputStreamReader isr = new InputStreamReader(input);
        BufferedReader reader = new BufferedReader(isr);
        String line;
       
        List<String> classes = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
            //System.out.println(line);
            if(line.startsWith("class")){
                String c = line.replace("class", "").replace("{", "");
                c.trim();
                classes.add(c);
                //System.out.println(c);
            }
        }
        reader.close();
        
        return classes;
    }
    
    /*
    This function
    */
   
    
//    private static Class[] getClasses(String packageName)
//        throws ClassNotFoundException, IOException {
//    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//    assert classLoader != null;
//    String path = packageName;
//    System.out.println("Path ======================================== " + path);
//    Enumeration<URL> resources = classLoader.getResources(path);
//    List<File> dirs = new ArrayList<File>();
//    while (resources.hasMoreElements()) {
//        URL resource = resources.nextElement();
//        dirs.add(new File(resource.getFile()));
//    }
//    ArrayList<Class> classes = new ArrayList<Class>();
//    for (File directory : dirs) {
//        classes.addAll(findClasses(directory, packageName));
//    }
//    return classes.toArray(new Class[classes.size()]);
//}
//    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
//    List<Class> classes = new ArrayList<Class>();
//    if (!directory.exists()) {
//        return classes;
//    }
//    File[] files = directory.listFiles();
//    for (File file : files) {
//        if (file.isDirectory()) {
//            assert !file.getName().contains(".");
//            classes.addAll(findClasses(file, packageName + "." + file.getName()));
//        } else if (file.getName().endsWith(".class")) {
//            classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
//        }
//    }
//    return classes;
//}
    
    
    
    /**************************************************************************
    *
    *
    *
    * was trying mutible was to read JAR but the above functions didn't work for me
    *
    * Source of the code: https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
    *
    *
    *
    *
    ***************************************************************************/
    public static List<Class> getClasses(ClassLoader cl,String pack) throws Exception{

        String dottedPackage = pack.replaceAll("[/]", ".");
        List<Class> classes = new ArrayList<Class>();
        URL upackage = cl.getResource(pack);

        DataInputStream dis = new DataInputStream((InputStream) upackage.getContent());
        String line = null;
        while ((line = dis.readLine()) != null) {
            if(line.endsWith(".class")) {
               classes.add(Class.forName(dottedPackage+"."+line.substring(0,line.lastIndexOf('.'))));
            }
        }
        return classes;
    }
    
        public List getClasseNames() {
            System.out.println("GetClassNames called");
        ArrayList classes = new ArrayList();

        try {
            JarInputStream jarFile = new JarInputStream(new FileInputStream(
                    jarName));
            JarEntry jarEntry;

            while (true) {
                jarEntry = jarFile.getNextJarEntry();
                if (jarEntry == null) {

                    System.out.println("Didn't find any class");
                    break;
                }
                if (jarEntry.getName().endsWith(".class")) {
                    if (debug)
                        System.out.println("Found "
                                + jarEntry.getName());
                    classes.add(jarEntry.getName().replaceAll("/", "\\."));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return classes;
    }
    
    
//    public static List<Class> getClasses(ClassLoader cl,String pack) throws Exception{
//
//        String dottedPackage = pack.replaceAll("[/]", ".");
//        List<Class> classes = new ArrayList<Class>();
//        URL upackage = cl.getResource(pack);
//
//        DataInputStream dis = new DataInputStream((InputStream) upackage.getContent());
//        String line = null;
//        while ((line = dis.readLine()) != null) {
//            if(line.endsWith(".class")) {
//               classes.add(Class.forName(dottedPackage+"."+line.substring(0,line.lastIndexOf('.'))));
//            }
//        }
//        return classes;
//    }
        
        private static void checkDirectory(File directory, String pckgname,
        ArrayList<Class<?>> classes) throws ClassNotFoundException {
    File tmpDirectory;

    if (directory.exists() && directory.isDirectory()) {
        System.out.println("Directory is correct");
        final String[] files = directory.list();

        for (final String file : files) {
            if (file.endsWith(".class")) {
                try {
                    classes.add(Class.forName(pckgname + '.'
                            + file.substring(0, file.length() - 6)));
                } catch (final NoClassDefFoundError e) {
                    // do nothing. this class hasn't been found by the
                    // loader, and we don't care.
                }
            } else if ((tmpDirectory = new File(directory, file))
                    .isDirectory()) {
                checkDirectory(tmpDirectory, pckgname + "." + file, classes);
            }
        }
    }
}


private static void checkJarFile(JarURLConnection connection,
        String pckgname, ArrayList<Class<?>> classes)
        throws ClassNotFoundException, IOException {
    final JarFile jarFile = connection.getJarFile();
    final Enumeration<JarEntry> entries = jarFile.entries();
    String name;

    for (JarEntry jarEntry = null; entries.hasMoreElements()
            && ((jarEntry = entries.nextElement()) != null);) {
        name = jarEntry.getName();

        if (name.contains(".class")) {
            name = name.substring(0, name.length() - 6).replace('/', '.');

            if (name.contains(pckgname)) {
                classes.add(Class.forName(name));
            }
        }
    }
}

/**
 * Attempts to list all the classes in the specified package as determined
 * by the context class loader
 * 
 * @param pckgname
 *            the package name to search
 * @return a list of classes that exist within that package
 * @throws ClassNotFoundException
 *             if something went wrong
 */
public static ArrayList<Class<?>> getClassesForPackage(String pckgname)
        throws ClassNotFoundException {
    final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

    try {
        final ClassLoader cld = Thread.currentThread()
                .getContextClassLoader();

        if (cld == null)
            throw new ClassNotFoundException("Can't get class loader.");

        final Enumeration<URL> resources = cld.getResources(pckgname
                .replace('.', '/'));
        URLConnection connection;

        for (URL url = null; resources.hasMoreElements()
                && ((url = resources.nextElement()) != null);) {
            try {
                connection = url.openConnection();

                if (connection instanceof JarURLConnection) {
                    checkJarFile((JarURLConnection) connection, pckgname,
                            classes);
                } else if (connection instanceof FileURLConnection) {
                    try {
                        checkDirectory(
                                new File(URLDecoder.decode(url.getPath(),
                                        "UTF-8")), pckgname, classes);
                    } catch (final UnsupportedEncodingException ex) {
                        throw new ClassNotFoundException(
                                pckgname
                                        + " does not appear to be a valid package (Unsupported encoding)",
                                ex);
                    }
                } else
                    throw new ClassNotFoundException(pckgname + " ("
                            + url.getPath()
                            + ") does not appear to be a valid package");
            } catch (final IOException ioex) {
                throw new ClassNotFoundException(
                        "IOException was thrown when trying to get all resources for "
                                + pckgname, ioex);
            }
        }
    } catch (final NullPointerException ex) {
        throw new ClassNotFoundException(
                pckgname
                        + " does not appear to be a valid package (Null pointer exception)",
                ex);
    } catch (final IOException ioex) {
        throw new ClassNotFoundException(
                "IOException was thrown when trying to get all resources for "
                        + pckgname, ioex);
    }

    return classes;
}
}
