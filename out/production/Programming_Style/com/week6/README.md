## Week6
### Instructions to run to code
1. Go to the deploy folder ```cd Week6/deploy```
2. Make changes in config.properties file to choose the plugins you want to use.
   There are 4 combinations as follows:
- extractNormalWords + countNormalWord
- extractNormalWords + countFirstLetter
- extractZWords + countNormalWord
- extractZWords + countFirstLetter
3. Type ```java -jar framework.jar ../../pride-and-prejudice.txt``` in terminal to run the code

-----
### Compile Steps:
1. Go to framework folder: ```cd Week6/framework```
2. Compile: ```javac *.java```
3. Create the jar file: ```jar cfm framework.jar Manifest.mf *.class```

4. Go to plugin1 folder: ```cd ../plugin1```
5. Compile: ```javac -cp ../framework/framework.jar *.java```
6. Create the jar file: ```jar cf extractNormalWords.jar *.class```

7. Go to plugin2 folder: ```cd ../plugin2```
8. Compile: ```javac -cp ../framework/framework.jar *.java```
9. Create the jar file: ```jar cf extractZWords.jar *.class```

10. Go to the plugin3 folder: ```cd ../plugin3```
11. Compile: ```javac -cp ../framework/framework.jar *.java```
12. Create the jar file: ```jar cf countNormalWord.jar *.class```

13. Go to plugin4 folder: ```cd ../plugin4```
14. Compile: ```javac -cp ../framework/framework.jar *.java```
15. Create the jar file: ```jar cf countFirstLetter.jar *.class```

16. run the code: ```java -jar framework.jar ../../pride-and-prejudice.txt```