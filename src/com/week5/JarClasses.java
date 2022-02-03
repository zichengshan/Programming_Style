package com.week5;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Reference:

// json-20211205
// org/json/CDL.class
public class JarClasses {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String jarFile = args[0];
        JarFile jar = new JarFile(jarFile);

        List<String> classNames =  findAllClassesInJar2(jar);
        for(String name : classNames)
            System.out.println(name);
    }

    private static List<String> findAllClassesInJar2(JarFile jar) throws ClassNotFoundException {
        Stream<JarEntry> stream = jar.stream();

        return stream
                .filter(entry -> entry.getName().endsWith(".class"))
                .map(entry -> getFQN(entry.getName()))
                .sorted()
                .collect(Collectors.toList());

    }

    private static String getFQN(String resourceName) {
        return resourceName.replaceAll("/", ".").substring(0, resourceName.lastIndexOf('.'));
    }

    private static void methodName(){
        Class cls = null;
        Method[] methods = cls.getDeclaredMethods();
        for (Method m: methods)
            System.out.println("Found method: " + m.getName());
    }

}
