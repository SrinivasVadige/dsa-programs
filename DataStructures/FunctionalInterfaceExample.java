package DataStructures;

import java.util.function.Function;
import java.util.function.Predicate;

/**
Fuctional interfaces in Java is introduced in v8
An Interface that contains exactly one abstract method is known as functional interface.
It can have any number of default, static methods but can contain only one abstract method.
It can also declare methods of object class.
Functional Interface is also known as Single Abstract Method Interfaces or SAM Interfaces.

USE
1) we can define a method with functional interface as a parameter
Eg:
Collections.sort(nums, ()->), reduce(), map(), filter()
List.forEach()
HashMap.merge(), HashMap.compute()
EVERY JAVA LAMBDA METHODS AND METHOD REFERENCES USES THIS FUNCTIONAL INTERFACE PRINCIPLE

* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class FunctionalInterfaceExample {

	// 4 ways to define and use single method interface
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		// 1) IMPLEMENT THE INTERFACE IN OUR CLASS AND OVERRIDE THE METHOD

		// 2) INITIALIZE THE INTERFACE WITH "new keyword" AND @override THE METHOD
		SampleInterface i_instance = new SampleInterface() {
			@Override
			public String display() {
				return "Instance for interface";
			}
		};
		System.out.println(i_instance.display());

		// 3) DEFINING THE SINGLE METHOD INTERFACE WITH LAMBDA
		SampleInterface i_lambda = () -> "lambda"; // or ()->{return "lambda"}
		System.out.println(i_lambda.display());

		// 4) USE OUR CLASS METHOD AS INTERFACE METHOD REFERENCE
		SampleInterface i_methodReference = FunctionalInterfaceExample::show;
		i_methodReference.display(); // if we have parameters then pass here not in show method


		// Function Functional Interface examples
		Function<Integer, Integer> f1 = (i) -> i * i;
		Function<Object, String> f2 = (a) -> a.toString();
	}

	// static class method
	static String show() {
		return "show";
	}
}

@FunctionalInterface   // optional
interface SampleInterface {
	public String display();
}

/*
 * A functional interface can contain implementations in default methods
 * but should have only one un-implemented method
 */
interface MyFunctionalInterface {
	public void execute();

	public default void print(String text) {
		System.out.println(text);
	}
}


/*
A functional interface can extends another interface only when it does not have any abstract method.
*/

interface Sayable{
    void say(String msg);   // abstract method
}
// @FunctionalInterface // ===> cannot declare it as @FunctionalInterface
// cause Invalid '@FunctionalInterface' annotation; Doable is not a functional interface
interface Doable extends Sayable{
    void doIt();
}

interface Sayable2 {
    default void doIt(){  // non-abstract method
        System.out.println("Do it now");
    }
}
@FunctionalInterface
interface Doable2 extends Sayable2{
    void say(String msg);   // abstract method
}




/*
Built-in Functional Interfaces in Java
--------------------------------------
Since Java SE 1.8 onwards, there are many interfaces that are converted into functional interfaces. All these interfaces are annotated with @FunctionalInterface. These interfaces are as follows – 
1) Runnable –> This interface only contains the run() method.
2) Comparable –> This interface only contains the compareTo() method.
3) ActionListener –> This interface only contains the actionPerformed() method.
4) Callable –> This interface only contains the call() method.

Java SE 8 included four main kinds of functional interfaces which can be applied in multiple situations as mentioned bel
1) Function (java.util.function.Function)
2) Predicate (java.util.function.Predicate)
3) UnaryOperator
4) BinaryOperator
5) Supplier
6) Consumer
6) BiConsumer<T,U>
7) BiFunction<T,U,R>
8) DoubleFunction<R>
9) ToLongFunction<T> ..........
 */


/*
Java Functional Composition
---------------------------
Functional composition is a technique to combine multiple functions into a
single function which uses the combined functions internally.
You can compose individual functions (typically one or more Java Lambda Expressions) into a single function yourself,
but Java also comes with built-in support for functional composition to make the job easier for you.
For example we can see "how to compose functions from smaller functions via Java's built-in features"

https://jenkov.com/tutorials/java-functional-programming/functional-composition.html
*/

class FunctionalCompositionExample {

	static void sampleFunctionalComp() {
		Predicate<String> startsWithA = (text) -> text.startsWith("A");
		Predicate<String> endsWithX = (text) -> text.endsWith("x");

		Predicate<String> startsWithAAndEndsWithX = (text) -> startsWithA.test(text) && endsWithX.test(text);
		String input = "A hardworking person must relax";
		boolean result = startsWithAAndEndsWithX.test(input);
		System.out.println(result);

		Predicate<String> composedAnd = startsWithA.and(endsWithX);
		String input2 = "A hardworking person must relax";
		boolean result2 = composedAnd.test(input2);
		System.out.println(result2);

		Predicate<String> composedOr = startsWithA.or(endsWithX);
		String input3 = "A hardworking person must relax sometimes";
		boolean result3 = composedOr.test(input3);
		System.out.println(result3);

		Function<Integer, Integer> multiply = (value) -> value * 2;
		Function<Integer, Integer> add = (value) -> value + 3;

		multiply.apply(3);

		Function<Integer, Integer> addThenMultiply = multiply.compose(add);
		Integer resultFunc = addThenMultiply.apply(3);
		System.out.println(resultFunc);

		Function<Integer, Integer> multiplyThenAdd = multiply.andThen(add);
		Integer resultFunc2 = multiplyThenAdd.apply(3);
		System.out.println(resultFunc2);

	}
}
