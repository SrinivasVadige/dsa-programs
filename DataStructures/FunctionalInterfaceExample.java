package DataStructures;

import java.util.function.Function;
import java.util.function.Predicate;

/**
	Functional interfaces in Java is introduced in v8
	An Interface that contains exactly one abstract method is known as functional interface. ---> @FunctionalInterface annotation is optional
	It can have any number of default, static methods but can contain only one abstract method.
	It can also declare methods of object class.
	Functional Interface is also known as Single Abstract Method Interfaces or SAM Interfaces.

	USE CASES
 	we can define a method with functional interface as a parameter
	Examples:
	Collections.sort(nums, ()->), reduce(), map(), filter()
	List.forEach()
	HashMap.merge(), HashMap.compute()

 	3 Ways to implement functional interface
 	1) Anonymous inner class (new Interface() {}) Java 1+ --> creates a new inner class and allocates memory in heap -- not recommended
 	2) Lambda expression
 	3) Method reference

 	NOTE:
	1) All java lambda methods and method references use this functional interface principle
    2) We can override multiple methods in Anonymous inner class but only one method in Lambda expression and method reference
    3) We can also create inner interfaces just like inner classes
 	4) If built-in Functional interfaces abstract methods don't have throws Exception, then we can't throw Exception in lambda expression and method reference
		Function<String, String> func = (input) -> {
			throw new IOException("fail"); // ❌ Compiler error: unhandled exception
		};
       So, wrap it in try-catch block
 		Function<String, String> func = (input) -> {
            try {
                throw new Exception("fail"); // ✅ works
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };



* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class FunctionalInterfaceExample {

	// 4 ways to define and use single method interface
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		/*
			1) Anonymous inner class (new Interface() {}) -------------------

		   defining the single method interface declaration using anonymous class,
		   implement the interface in our class and override the method
		   initialize the interface with "new keyword" and @override the method
		 */
		SampleInterface i_instance = new SampleInterface() {
			@Override
			public String display() {
				return "Instance for interface";
			}
		};
		System.out.println(i_instance.display());




		/*
			2) Lambda expression -------------------

			defining the single method interface with lambda

			Lambda expressions cannot throw checked exceptions unless declared
		 */
		SampleInterface i_lambda = () -> "lambda"; // or ()->{return "lambda"}
		System.out.println(i_lambda.display());



		/*
			3) Method reference -------------------

			use our class method as interface method reference

			👉 FunctionalInterfaceExample::show ---> if show method is static, we can implement this statement in anywhere -- static class variable, non-static class variable, static method, non-static method
			👉 new FunctionalInterfaceExample()::showNonStatic ---> if showNonStatic method is non-static and current statement in static method like psvm main()
			👉 this::showNonStatic ---> if showNonStatic method is non-static and current statement in non-static method
		 */
		SampleInterface i_methodReference = FunctionalInterfaceExample::show;
		i_methodReference.display(); // if we have parameters, then pass here not in show method

		SampleInterface i_methodReferenceNonStatic = new FunctionalInterfaceExample()::showNonStatic; // or this::showNonStatic 🔥
		i_methodReferenceNonStatic.display();




		// Function Functional Interface examples
		Function<Integer, Integer> f1 = (i) -> i * i;
		Function<Object, String> f2 = Object::toString;


//		Function<String, String> func = (input) -> {
//			throw new Exception("fail"); // ❌ Compiler error: unhandled exception
//		};
		Function<String, String> func = (input) -> {
            try {
                throw new Exception("fail"); // ✅ works
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
	}




	// static class method
	static String show() {
		return "show using static method";
	}


	// non-static class method
	public String showNonStatic() {
		return "show using non-static method";
	}










	@FunctionalInterface   // optional
	interface SampleInterface {
		public String display();
	}








	/*
	   A functional interface can contain implementations in default methods
	   but should have only one un-implemented method
	 */
	interface MyFunctionalInterface {
		public void execute();

		public default void print(String text) {
			System.out.println(text);
		}
	}






	/*
		A functional interface can extend another interface only when it does not have any abstract method.
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
	Since Java SE 1.8 onwards, there've been many interfaces that are converted into functional interfaces. All these interfaces are annotated with @FunctionalInterface. These interfaces are as follows –
	1) Runnable –> This interface only contains the run() method.
	2) Comparable –> This interface only contains the compareTo() method.
	3) ActionListener –> This interface only contains the actionPerformed() method.
	4) Callable –> This interface only contains the call() method.

	Java SE 8 included four main kinds of functional interfaces which can be applied in multiple situations as mentioned below
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
	For example, we can see "how to compose functions from smaller functions via Java's built-in features"

	https://jenkov.com/tutorials/java-functional-programming/functional-composition.html
	*/

	static class FunctionalCompositionExample {

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
}
