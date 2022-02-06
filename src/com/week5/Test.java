package com.week5;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

public class Test {
    public static void main(String[] args) throws IOException {
        String jarFile = args[0];
        JarFile jar = new JarFile(jarFile);
        int pub_methods_count = 0, pri_methods_count = 0, pro_methods_count = 0, static_methods_count = 0, fields_count = 0;

        //use a classLoader object to load the classes in the jarFile jar
        File classFile = new File(jarFile);
        URL url1 = classFile.toURL();
//        URL[] url = new URL[]{classFile.toURL()};

    }
}
