package DataStructures;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.ElementType;
import java. lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java. lang. annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
In annotation type interface we can only use methods with return type as primitives, String, Array of primitives. 
We cannot use Non-primitives or classes or list.

/* Annotation Types basically don’t change our code in real-time but will helpful in the runtime. Annotations can be class level @Path, method level @Override or variable level @SuppressWarnings. And will defining our custom annotations we use some in-built or another custom annotations. This custom annotation’s in-built annotations are 
1) @Target(“ClassMethodVarLevel”) - optional and leave it if you want to use this for all levels. Inside value is like ElementType.METHOD, .TYPE(class), .PACKAGE., .CONSTRUCTOR, .FIELD(variable)
2) @Retention(RetentionPolicy.RUNTIME) to keep this annotation alive up to what extent i.e annotation life cycle. RUNTIME means alive all the way through runtime, so that other code can use this annotation. Other values like RetentionPolicy.SOURCE means make alive even before the code is complied like SuppressWarning annotation, RetentionPolicy.CLASS means only active up to compile and destroys at runtime.
3) @Documented......

* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
@Retention(RetentionPolicy.RUNTIME) // optional
@Target({ElementType.METHOD, ElementType.FIELD}) // optional
public @interface AnnotationTypeExample {
    String author();
    String date();
    int currentRevision() default 1;
    String lastModified() default "N/A";
    String lastModifiedBy() default "N/A";
    String[] reviewers(); // Note use of array
    double PI = 3.14; // static constant variable

/*     String test(){   // Syntax error on token ")", default expected after this tokenJava(1610612967)
        return "test";  // i.e we can only have default value but not the default method in @interface
    } */
}

@interface RunImmediately{
    int times() default 1;
}

/*
which can then annotate a class as follows:
@AnnotationTypeExample (       //The annotation @AnnotationTypeExample is disallowed for this location or same file
   author = "John Doe",
   date = "3/17/2002",
   currentRevision = 6,
   lastModified = "4/12/2004",
   lastModifiedBy = "Jane Doe",
   // Note array notation
   reviewers = {"Alice", "Bob", "Cindy"}
)
*/
class ClassWithAnnotation {
      // class code goes here

      // @RunImmediately
      void classMethodWithAnnotation(){

      }
}



/*
Get 
We can check whether a class is annotated or not by
myClass.getClass().isAnnotationPresent(CustomAnnotation.class);
That is how all the project classes are looped to check for @Configuration annotations classes and again each configClass is looped with all it’s methods to check the @Bean methods then the Spring executes all the beans methods (using Method.invoke()) when our Spring application starts running.
We can pass arguments to custom annotations. We know that interface can only have static and constant variables. So, make them as methods but can be used in @RunImmediately(times=3)
for (Method method : myCat.getclass.getDeclaredMethods()){
    if (method.isAnnotationPresent(RunImmediately.class)){
        RunImmediately annotation = method.getAnnotation(RunImmediately.class)
        for (int i = 0; i ‹ annotation.times); i++){ method. invoke(myCat); }
    }
}
*/
class AnotherClass {

    static void someMethod() throws InvocationTargetException, IllegalAccessException { // or Exception

        // get List of all packages in our project
        String packageNamePattern = "com.mypack.";
        List<String> packagesList = Arrays.stream(Package.getPackages())
                                        .map(Package::getName)
                                        .filter(n -> n.startsWith(packageNamePattern))
                                        .toList();

        // get List of all classes from a single packege
        String packageName = packagesList.get(0);
        InputStream stream = ClassLoader.getSystemClassLoader()
                                        .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        @SuppressWarnings("rawtypes")
        List<Class> setOfClasses = reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toList());
        try {
            setOfClasses.get(0).getClassLoader().loadClass(setOfClasses.get(0).getName());
        } catch (ClassNotFoundException e) {
        }

        // validate with our custom annotaions
        ClassWithAnnotation myClass = new ClassWithAnnotation();
        myClass.getClass().isAnnotationPresent(AnnotationTypeExample.class); // same like @Configuration

        for (Method method : myClass.getClass().getDeclaredMethods()){
            if (method.isAnnotationPresent(AnnotationTypeExample.class)) {
                RunImmediately annotation = method.getAnnotation(RunImmediately.class);  // same like @Bean
                for (int i = 0; i < annotation.times(); i++)
                    method.invoke(new Object());
            }
        }

    }

    @SuppressWarnings("rawtypes")
    static Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
              + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }
}

