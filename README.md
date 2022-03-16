# Programming_Style
Using Java programming language, implement the term frequency program that is the basis for the exercises in the reference book. 
* Reference book: https://github.com/crista/exercises-in-programming-style 
* Use the list of stop words provided at: https://github.com/crista/exercises-in-programming-style/blob/master/stop_words.txt 
* Run over Pride and Prejudice available at: https://github.com/crista/exercises-in-programming-style/blob/master/pride-and-prejudice.txt
* The successfully output of the program over the book Pride and Prejudice should be this: https://github.com/crista/exercises-in-programming-style/blob/master/test/pride-and-prejudice.txt

## Deployment environment:
* IDEA
* repl.it: You can see all the programs I finished through the following link: https://github.com/crista/exercises-in-programming-style/blob/master/test/pride-and-prejudice.txt

** **Note:** all the exercise were finished in 9 weeks as the following order

## Week1
There is no need to follow any specific style for week1; just make it work as best as you can.
```aidl
/**
 * Steps in terminal:
 * 1. Type "cd Week1" to get into the Week1 folder
 * 2. Type "javac TermFrequency.java" to compile
 * 2. Type "java TermFrequency ../pride-and-prejudice.txt" to run the code
 */
```

## Week2
### Monolith
Constraints:
* No abstractions
* No use of library functions
```aidl
1. Type "cd Week2" to get into the Week2 folder
2. Type "javac Monolith.java" to compile
3. Type "java Monolith.java ../pride-and-prejudice.txt" to run the code
```
Python Reference: https://github.com/crista/exercises-in-programming-style/tree/master/04-monolith

### Cookbook 
Constraints:
* Larger problem decomposed in procedural abstractions
* Larger problem solved as a sequence of commands, each corresponding to a procedure

```aidl
1. Type "cd Week2" to get into the Week2 folder
2. Type "javac Cookbook.java" to compile
3. Type "java Cookbook.java ../pride-and-prejudice.txt" to run the code
```
Python Reference: https://github.com/crista/exercises-in-programming-style/tree/master/05-cookbook 

### Pipeline
Constraints:
* Larger problem decomposed in functional abstractions. Functions, according to Mathematics, are relations from inputs to outputs.
* Larger problem solved as a pipeline of function applications

```aidl
1. Type "cd Week2" to get into the Week2 folder
2. Type "javac Pipeline.java" to compile
3. Type "java Pipeline.java ../pride-and-prejudice.txt" to run the code
```
Python Reference: https://github.com/crista/exercises-in-programming-style/tree/master/06-pipeline

## Week3
### CodeGolf
Constraints:
* As few lines of code as possible

### Stream --> Eight.java
Using any language you want, including Python, implement a recursive solution to the term frequency problem where parsing words from the stream of characters is recursive. Much like the JSON parse function, your parse function should take a character-level file reader, a list of words (initially empty), and the list of stop words, and should return a list of the non-stop words. The function should iterate to get the characters of one single word, and then recurse to get the rest of the words. You can do the rest of the program (counting words, printing them) in non-recursive style.

## Week4
### Kick-forward --> Nine.java
Variation of the candy factory style, with the following additional constraints:
1. Each function takes an additional parameter, usually the last, which is another function
2. That function parameter is applied at the end of the current function
3. That function parameter is given as input what would be the output of the current function
4. Larger problem is solved as a pipeline of functions, but where the next function to be applied is given as parameter to the current function

Ref: https://github.com/crista/exercises-in-programming-style/tree/master/09-kick-forward

### the-one --> Ten.java
Constraints:
1. Existence of an abstraction to which values can be converted.
2. This abstraction provides operations to (1) wrap around values, so that they become the abstraction; (2) bind itself to functions, so to establish sequences of functions; and (3) unwrap the value, so to examine the final result.
3. Larger problem is solved as a pipeline of functions bound together, with unwrapping happening at the end.
4. Particularly for The One style, the bind operation simply calls the given function, giving it the value that it holds, and holds on to the returned value.

Ref: https://github.com/crista/exercises-in-programming-style/tree/master/10-the-one

### bulletin-board --> Sixteen.java
Constraints:
1. Larger problem is decomposed into entities using some form of abstraction (objects, modules or similar)
2. The entities are never called on directly for actions
3. Existence of an infrastructure for publishing and subscribing to events, aka the bulletin board
4. Entities post event subscriptions (aka 'wanted') to the bulletin board and publish events (aka 'offered') to the bulletin board. the bulletin board does all the event management and distribution

Ref: https://github.com/crista/exercises-in-programming-style/tree/master/16-bulletin-board

## Week5
### Jar
Upload a couple of jar files to your Repl, one of them must be json-org.java, the jar file of the course project. Note that you should only upload the jars, not source files.
Write a program called JarClasses.java (inside Week5 folder) that takes a jar file as command line argument and prints out a listing of all the classes inside, as well as the number of declared public, private, protected, and static methods for each, and the number of declared fields for each. The listing should be sorted alphabetically on the name of the classes. Your program should use reflection to get the required information about the methods and fields of each class.

For example, the command line and output for json-java.jar is:
$ java JarClasses json-java.jar
```aidl
----------org.json.CDL----------
  Public methods: 9
  Private methods: 1
  Protected methods: 0
  Static methods: 10
  Fields: 0
----------org.json.Cookie----------
  Public methods: 4
  Private methods: 0
  Protected methods: 0
  Static methods: 4
  Fields: 0
----------org.json.CookieList----------
  Public methods: 2
  Private methods: 0
  Protected methods: 0
  Static methods: 2
  Fields: 0
----------org.json.HTTP----------
  Public methods: 2
  Private methods: 0
  Protected methods: 0
  Static methods: 2
  Fields: 1
----------org.json.HTTPTokener----------
  Public methods: 1
  Private methods: 0
  Protected methods: 0
  Static methods: 0
  Fields: 0
----------org.json.JSONArray----------
  Public methods: 72
  Private methods: 5
  Protected methods: 0
  Static methods: 2
  Fields: 1
----------org.json.JSONException----------
  Public methods: 0
  Private methods: 0
  Protected methods: 0
  Static methods: 0
  Fields: 1
----------org.json.JSONML----------
  Public methods: 10
  Private methods: 1
  Protected methods: 0
  Static methods: 11
  Fields: 0
----------org.json.JSONObject----------
  Public methods: 78
  Private methods: 11
  Protected methods: 3
  Static methods: 27
  Fields: 3
----------org.json.JSONObject$1----------
  Public methods: 0
  Private methods: 0
  Protected methods: 0
  Static methods: 0
  Fields: 0
----------org.json.JSONObject$Null----------
  Public methods: 3
  Private methods: 0
  Protected methods: 1
  Static methods: 0
  Fields: 0
----------org.json.JSONPointer----------
  Public methods: 4
  Private methods: 3
  Protected methods: 0
  Static methods: 4
  Fields: 2
----------org.json.JSONPointer$Builder----------
  Public methods: 3
  Private methods: 0
  Protected methods: 0
  Static methods: 0
  Fields: 1
----------org.json.JSONPointerException----------
  Public methods: 0
  Private methods: 0
  Protected methods: 0
  Static methods: 0
  Fields: 1
----------org.json.JSONPropertyIgnore----------
  Public methods: 0
  Private methods: 0
  Protected methods: 0
  Static methods: 0
  Fields: 0
----------org.json.JSONPropertyName----------
  Public methods: 1
  Private methods: 0
  Protected methods: 0
  Static methods: 0
  Fields: 0
----------org.json.JSONString----------
  Public methods: 1
  Private methods: 0
  Protected methods: 0
  Static methods: 0
  Fields: 0
----------org.json.JSONStringer----------
  Public methods: 1
  Private methods: 0
  Protected methods: 0
  Static methods: 0
  Fields: 0
----------org.json.JSONTokener----------
  Public methods: 16
  Private methods: 2
  Protected methods: 0
  Static methods: 1
  Fields: 8
----------org.json.JSONWriter----------
  Public methods: 10
  Private methods: 4
  Protected methods: 0
  Static methods: 1
  Fields: 6
----------org.json.Property----------
  Public methods: 2
  Private methods: 0
  Protected methods: 0
  Static methods: 2
  Fields: 0
----------org.json.XML----------
  Public methods: 14
  Private methods: 5
  Protected methods: 0
  Static methods: 19
  Fields: 11
----------org.json.XML$1----------
  Public methods: 1
  Private methods: 0
  Protected methods: 0
  Static methods: 0
  Fields: 1
----------org.json.XML$1$1----------
  Public methods: 4
  Private methods: 0
  Protected methods: 0
  Static methods: 0
  Fields: 3
----------org.json.XMLParserConfiguration----------
  Public methods: 10
  Private methods: 0
  Protected methods: 2
  Static methods: 0
  Fields: 7
----------org.json.XMLTokener----------
  Public methods: 6
  Private methods: 0
  Protected methods: 0
  Static methods: 1
  Fields: 1
----------org.json.XMLXsiTypeConverter----------
  Public methods: 1
  Private methods: 0
  Protected methods: 0
  Static methods: 0
  Fields: 0
```

## Week6
### plugins
Constraints:
1. The problem is decomposed using some form of abstraction (procedures, functions, objects, etc.)
2. All or some of those abstractions are physically encapsulated into their own, usually pre-compiled, packages. Main program and each of the packages are compiled independently. These packages are loaded dynamically by the main program, usually in the beginning (but not necessarily).
3. Main program uses functions/objects from the dynamically-loaded packages, without knowing which exact implementations will be used. New implementations can be used without having to adapt or recompile the main program.
4. External specification of which packages to load. This can be done by a configuration file, path conventions, user input or other mechanisms for external specification of code to be linked at run time.

Ref: https://github.com/crista/exercises-in-programming-style/tree/master/20-plugins

### persistent-tables
Constraints:
1. The input data of the problem is modeled as entities with relations between them
2. The data is placed in tables, with columns potentially cross-referencing data in other tables
3. Existence of a relational query engine
4. The problem is solved by issuing queries over the tabular data

Ref: https://github.com/crista/exercises-in-programming-style/tree/master/26-persistent-tables

## Week7
### lazy-rivers
Constraints:
1. Data comes to functions in streams, rather than as a complete whole all at at once
2. Functions are filters / transformers from one kind of data stream to another

Ref: https://github.com/crista/exercises-in-programming-style/tree/master/28-lazy-rivers

### Stream API --> Streams.java

## Week8
### actor --> TwentyNine.java
Constraints:
1. The larger problem is decomposed into 'things' that make sense for the problem domain
2. Each 'thing' has a queue meant for other \textit{things} to place messages in it
3. Each 'thing' is a capsule of data that exposes only its ability to receive messages via the queue
4. Each 'thing' has its own thread of execution independent of the others.

Ref: https://github.com/crista/exercises-in-programming-style/tree/master/29-actors

### Thread --> Thirty.java

### producer-and-consumer --> ThirtyTwo.java

## Wee9
### array programming language
Using Python + NumPy, or any other array programming language, implement a program that takes as input an array of characters (the characters from Pride and Prejudice, for example), normalizes to UPPERCASE, ignores words smaller than 2 characters, and replaces the vowels with their [Leet](https://simple.wikipedia.org/wiki/Leet)  counterparts when there is a one-to-one mapping. Then it prints out the 5 most frequently occurring 2-grams. (2-grams are all 2 consecutive words in a sequence) Note that you should stick to the array programming style as much as possible. This means: avoid explicit iteration over the elements of the array. If you find yourself writing an iteration, think of how you could do it with one or more array operations. (Sometimes, it's not possible; but most often it is)

Ref: https://github.com/crista/exercises-in-programming-style/tree/master/03-arrays
































