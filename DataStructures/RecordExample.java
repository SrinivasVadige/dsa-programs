package DataStructures;

/**
 * Records in java are introduced in 14
* Record extends from class and record must be in it's own file
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public record RecordExample(int foo, String bar) {

    public RecordExample setFoo(int newFoo){ // optional but it has to be public for setting as it record IMMUTABLE
        return new RecordExample(newFoo,  bar);
    }
    RecordExample setBar(String newBar){ // optional
        return new RecordExample(foo,  newBar);
    }
    public RecordExample{} // optional

}

/*
In many cases, this data is immutable, since immutability ensures the validity of the data without synchronization.

To accomplish this, we create data classes with the following:

private, final field for each piece of data
getter for each field
public constructor with a corresponding argument for each field
equals method that returns true for objects of the same class when all fields match
hashCode method that returns the same value when all fields match
toString method that includes the name of the class and the name of each field and its corresponding value

*/



