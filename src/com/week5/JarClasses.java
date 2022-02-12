package com.week5;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Reference:https://docs.oracle.com/javase/7/docs/api/java/net/URLClassLoader.html
 */

/**
 * 1. Type "cd Week5" to get into the Week5 folder
 * 2. Type "javac JarClasses.java" to compile
 * 3. Type "java JarClasses.java json-java.jar" to run the code
 */

public class JarClasses {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String jarFile = args[0];
        JarFile jar = new JarFile(jarFile);
        // Get all the class names in target jar file
        List<String> classNames = findAllClassesInJar2(jar);

        File file = new File(jarFile);
        URL[] url = new URL[]{file.toURL()};
        //use classLoader to load the classes in the jarFile
        URLClassLoader urlClassLoader = new URLClassLoader(url);

        Class cls = null;

        for (String name : classNames){
            int pub_methods_count = 0;
            int pri_methods_count = 0;
            int pro_methods_count = 0;
            int static_methods_count = 0;
            int fields_count = 0;
            System.out.println("---------" + name + "---------");

            // Load the target class
            cls = urlClassLoader.loadClass(name);
            // Return fields declared by the class or interface represented by this Class object.
            Field[] fields = cls.getDeclaredFields();
            fields_count = fields.length;
            // getDeclaredMethods() method returns metadata of the all the methods from the specified class only.
            Method[] methods = cls.getDeclaredMethods();

            // check each method in the array
            for (Method method: methods)
            {
                // get the modifier of the target method
                int index = method.getModifiers();

                //Increase counters depending on the value of the index
                if(Modifier.isPublic(index))
                    pub_methods_count++;
                if(Modifier.isPrivate((index)))
                    pri_methods_count++;
                if(Modifier.isStatic(index))
                    static_methods_count++;
                if(Modifier.isProtected(index))
                    pro_methods_count++;
            }
            System.out.println("Public methods: " + pub_methods_count);
            System.out.println("Private methods: " + pri_methods_count);
            System.out.println("Protected methods: " + pro_methods_count);
            System.out.println("Static methods: " + static_methods_count);
            System.out.println("Fields: " + fields_count);
        }
    }

    /**
     * Find all class in jar file, and return them in a list
     * @param jar
     * @return
     * @throws ClassNotFoundException
     */
    private static List<String> findAllClassesInJar2(JarFile jar) throws ClassNotFoundException {
        Stream<JarEntry> stream = jar.stream();
        return stream
                .filter(entry -> entry.getName().endsWith(".class"))
                .map(entry -> getFQN(entry.getName()))
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Replace "/" with "."
     * @param resourceName
     * @return
     */
    private static String getFQN(String resourceName) {
        return resourceName.replaceAll("/", ".").substring(0, resourceName.lastIndexOf('.'));
    }
}
