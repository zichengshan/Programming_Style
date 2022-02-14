package com.week6.framework;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class framework {

    // define two interfaces
    public static IExtract iExtract;
    public static IFreq iFreq;

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // properties is used to read the config files
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        // read the config file and get the target jar name
        String extractType = properties.getProperty("words");
        String freqType = properties.getProperty("frequencies");

        Class cls1 = null;
        Class cls2 = null;

        // get the url pointer
        URL cls1Url = new File(extractType + ".jar").toURI().toURL();
        URL cls2Url = new File(freqType + ".jar").toURI().toURL();

        //use classLoader to load the classes in the jarFile
        URL[] urls = {cls1Url, cls2Url};
        URLClassLoader urlClassLoader = new URLClassLoader(urls);

        cls1 = urlClassLoader.loadClass(extractType);
        cls2 = urlClassLoader.loadClass(freqType);
        iExtract = (IExtract) cls1.getDeclaredConstructor().newInstance();
        iFreq = (IFreq) cls2.getDeclaredConstructor().newInstance();

        Map<String, Integer> map = iFreq.top25Freq(iExtract.extractWords(args[0]));

        //Sort the hashmap
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, (o1, o2) -> { return -o1.getValue().compareTo(o2.getValue());});

        //print the top 25 most frequent words
        int k = 0;
        for (Map.Entry<String, Integer> m : list) {
            if (k++ == 25) {break;}
            System.out.println(m.getKey() + " : " + m.getValue());
        }
    }
}