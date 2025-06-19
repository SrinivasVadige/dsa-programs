package DataStructures;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.lang.Enum.EnumDesc;
import java.lang.constant.ClassDesc;;

/**
 * <pre>
 * public enum must be in a own file, and so we can have n number of default enums in a file
 *
 * public abstract class Enum<E extends Enum<E>> implements Constable, Comparable<E>, Serializable {
 * public class EnumMap<K extends Enum<K>, V> extends AbstractMap<K, V> implements java.io.Serializable, Cloneable
 * public abstract sealed class EnumSet<E extends Enum<E>> extends AbstractSet<E> implements Cloneable, java.io.Serializable permits JumboEnumSet, RegularEnumSet
 *
 * Two types of Enums: 1. Standard Enum, 2. Constructor Enum
 * Use standard enums when you need a simple enumeration of constants.
 * Use constructor enums when you need to associate additional data or behavior with each constant.
 *
 * * So, enum can be converted to other types like below:
 * * to Arrays using EnumClass.values() or Arrays.asList(EnumClass.class.getEnumConstants()),
 * * to List using Arrays.asList(EnumClass.values()) or Arrays.asList(EnumClass.class.getEnumConstants()) or new ArrayList<>(EnumClass.values()) or Stream.of(WeekDay.values()).collect(Collectors.toList()),
 * * to EnumSet using EnumSet.allOf(EnumClass.class),
 * * to EnumMap using new EnumMap<>(EnumClass.class),
 * * to Stream using Arrays.stream(EnumClass.values()) or Stream.of(EnumClass.values()) or Arrays.stream(EnumClass.class.getEnumConstants())
 * * and later on we can convert them to traditional Map, List, Set and so on
 * *
 * *
 * </pre>
 *
 * @see {@linkplain java.lang.Enum java.lang.Enum} - since JDK 1.5 2004
 * @see {@linkplain java.util.EnumMap java.util.EnumMap} - since JDK 1.5 2004
 * @see {@linkplain java.util.EnumSet java.util.EnumSet} - since JDK 1.5 2004
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 17 Oct 2024
 *
 */

public class EnumExample {

    @SuppressWarnings("unlikely-arg-type")
    public static void main(String[] args) {

        // WeekDay - standard enum operations
        System.out.println("WeekDay enum ----------------");
        WeekDay dayEnum = WeekDay.MONDAY;
        String day1 = WeekDay.MONDAY.name(); // built-in final static method, same as toSting()
        WeekDay day1Enum = WeekDay.valueOf(day1); // throws IllegalArgumentException if the value is not in the enum
        day1Enum = WeekDay.valueOf("MONDAY"); // built-in final static method & same as above
        String day2 = WeekDay.MONDAY.toString(); // built-in non-final static & can be @Override as shown inside the WeekDay enum
        int dayIndex = WeekDay.MONDAY.ordinal(); // built-in final static method for index
        WeekDay[] days = WeekDay.values(); // built-in final static method
        Class<? extends Enum<?>> decClass = day1Enum.getDeclaringClass(); // prints class DataStructures.WeekDay
/**/    System.out.println(WeekDay.SUNDAY); /* prints Sunday --> @Override toString() custom method in WeekDay enum */
/**/    System.out.println(WeekDay.SUNDAY.name()); /* prints SUNDAY i.e specific Enum constant as String */
/**/    System.out.println(WeekDay.SUNDAY.toString()); /* prints Sunday --> @Override toString() custom method in WeekDay enum */
        System.out.println(WeekDay.MONDAY.equals(day1)); // false because day1 is a string but .equals(Object param)
        System.out.println(WeekDay.MONDAY.equals(day1Enum)); // true always compare enum to enum or string to string
        System.out.println(WeekDay.MONDAY.name().equals(day1)); // true as we compare string to string
        System.out.println(dayEnum);
        System.out.println(day1);
        System.out.println(day1Enum);
        System.out.println(day2);
        System.out.println(dayIndex);
        System.out.println(days);
        System.out.println(decClass);

        // Enum Descriptor --- with private constructor and one .of() static method
        System.out.println("Enum Descriptor -----------------"); // TODO
        Optional<EnumDesc<WeekDay>> enumDesc1 = WeekDay.MONDAY.describeConstable();
        System.out.println(enumDesc1.get().constantName());
        EnumDesc<WeekDay> enumDesc2 = EnumDesc.of(ClassDesc.of("DataStructures", "EnumExample$WeekDay"), "MONDAY");
        System.out.println(enumDesc2);
        System.out.println(enumDesc2.constantName());


        // to Array
        System.out.println("Array -----------------");
        WeekDay[] daysArr = WeekDay.values();
        daysArr = WeekDay.class.getEnumConstants(); // same as above
        System.out.println(Arrays.toString(daysArr));

        // to List
        System.out.println("List -----------------");
        List<WeekDay> daysList = Arrays.asList(WeekDay.values());
        daysList = new ArrayList<>(Arrays.asList(WeekDay.values())); // same as above
        daysList = Arrays.asList(WeekDay.class.getEnumConstants()); // same as above
        daysList = Stream.of(WeekDay.values()).collect(Collectors.toList()); // same as above
        System.out.println(daysList);

        // to EnumMap
        System.out.println("EnumMap -----------------");
        EnumMap<WeekDay, String> scheduleEnumMap = new EnumMap<>(WeekDay.class);
        scheduleEnumMap.put(WeekDay.MONDAY, "Work"); // Adding elements or custom constructor parameter to the EnumMap
        scheduleEnumMap.put(WeekDay.TUESDAY, "Work");
        scheduleEnumMap.put(WeekDay.WEDNESDAY, "Study");
        scheduleEnumMap.put(WeekDay.THURSDAY, "Study");
        scheduleEnumMap.put(WeekDay.FRIDAY, "Relax");
        System.out.println(scheduleEnumMap); // Output: Work
        System.out.println(scheduleEnumMap.get(WeekDay.MONDAY)); // Output: Work
        System.out.println(scheduleEnumMap.get(WeekDay.FRIDAY)); // Output: Relax

        // to EnumSet
        System.out.println("EnumSet -----------------");
        EnumSet<WeekDay> weekDaysSet = EnumSet.allOf(WeekDay.class);
        int mondayOrdinal = EnumSet.allOf(WeekDay.class).stream().filter(d->d.name().equals("MONDAY")).map(d->d.ordinal()).findFirst().orElse(0);
        EnumSet<WeekDay> weekDaysRangeSet = EnumSet.range(WeekDay.MONDAY, WeekDay.FRIDAY); // from MONDAY to FRIDAY
        EnumSet<WeekDay> weekDaysEnumSet = EnumSet.of(WeekDay.MONDAY, WeekDay.TUESDAY); //  only mentioned enums
        EnumSet.of(WeekDay.MONDAY, WeekDay.TUESDAY).contains(WeekDay.MONDAY); // same like Arrays.asList().contains()
        weekDaysEnumSet.add(WeekDay.WEDNESDAY);
        weekDaysEnumSet.remove(WeekDay.MONDAY);
        EnumSet<WeekDay> weekDaysCopy = EnumSet.copyOf(weekDaysEnumSet);
        EnumSet<WeekDay> weekDaysClear = EnumSet.noneOf(WeekDay.class);
        weekDaysClear.addAll(weekDaysEnumSet);
        EnumSet.complementOf(weekDaysEnumSet).forEach(System.out::println);
        System.out.println(mondayOrdinal);
        System.out.println(weekDaysSet);
        System.out.println(weekDaysRangeSet);
        System.out.println(weekDaysEnumSet);
        System.out.println(weekDaysCopy);
        System.out.println(weekDaysClear);

        // to Stream and other types like Map, List, Set
        System.out.println("Enum with Stream -----------------");
        Stream<WeekDay> enumStream = Stream.of(WeekDay.values());
        enumStream = Stream.of(WeekDay.class.getEnumConstants()); // same as above
        enumStream = Arrays.stream(WeekDay.values()); // same as above
        enumStream = Arrays.stream(WeekDay.class.getEnumConstants()); // same as above
        enumStream = Arrays.asList(WeekDay.values()).stream(); // same as above
        enumStream = Arrays.asList(WeekDay.class.getEnumConstants()).stream(); // same as above
        WeekDay day = Stream.of(WeekDay.values()).filter(d->d.name().equals("MONDAY")).findFirst().get();
        Map<String, WeekDay> scheduleMap = Stream.of(WeekDay.values()).collect(Collectors.toMap(WeekDay::name, Function.identity()));
        Map<WeekDay, String> scheduleMap2 = Stream.of(WeekDay.values()).collect(Collectors.toMap(Function.identity(), WeekDay::name));
        enumStream.forEach(System.out::println);
        System.out.println(day);
        System.out.println(scheduleMap);
        System.out.println(scheduleMap2);



        // Roman enum (Constructor with 1 parameter)
        System.out.println("Roman enum ----------------");
        Roman romanEnum = Roman.I;
        romanEnum = Roman.valueOf("I");
        System.out.println(romanEnum);
        // System.out.println(romanEnum.value); --- if value class variable is not private
        System.out.println(romanEnum.getValue());
        System.out.println(Roman.valueOf("L"));
        System.out.println(Roman.valueOf("D").getValue()); // java.lang.IllegalArgumentException if we use small "d", i.e enum is case sensitive
        System.out.println(Arrays.toString(Roman.values())); // .values() is a built-in final static method



        // Frequency enum (Constructor with 2 parameters)
        System.out.println("Frequency enum ----------------");
        String frequency = "Every Week";
        Frequency freqEnum = Frequency.WEEKLY; // by default a constructor initializes the EVERY WEEK enum and all the static methods can be used
        System.out.println(Frequency.isFrequency(frequency));
        System.out.println(Frequency.toCode(frequency));
        System.out.println(Frequency.getValueByDisplayName(frequency));
        System.out.println(freqEnum.getDisplayName());
        System.out.println(freqEnum.getCode());
        System.out.println(freqEnum.name());
        System.out.println(freqEnum.ordinal());
    }
}

// Standard Enum
enum WeekDay {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

    // print all enum values
    public static void printAll() {
        Arrays.stream(values()).forEach(System.out::println); // 🔥 we can use WeekDay.values() or just use values() directly
    }
}

// Constructor Enum with 1 non-final parameter
enum Roman {
    I(1), V(5), X(10), L(50), C(100), D(500), M(1000);

    private int value; // Must be non-static and private is optional & final is optional and if private then param can only be accessed by getter i.e getValue()

    Roman(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

// Constructor Enum with 2 final parameters and public static methods
enum Frequency {

    ONCE("Once", "1"), // code can be String or int but we still have the ordinal() built-in static method
    WEEKLY("Every Week", "2"),
    EVERY_TWO_WEEKS("Every Two Weeks", "3"),
    EVERY_FIRST_AND_FIFTEENTH("Every 1st and 15th of the Month", "4"),
    MONTHLY("Monthly", "5"),
    EVERY_TWO_MONTHS("Every Two Months", "6"),
    QUARTERLY("Quarterly", "7"),
    SEMI_ANNUALLY("Semi-Annually", "8"),
    ANNUALLY("Annually", "9");

    final String displayName; // final because it is initialized in the constructor and cannot be changed
    final String code;

    public String getDisplayName() {
        return displayName;
    }

    public String getCode() {
        return code;

    }

    // enum constructor is always a private by default because -> Frequency foo = new Frequency("new day","44"); will not work like class
    Frequency(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

    /*
    * @TimeComplexity O(1)
        is item present in the enum -----
     */
    public static boolean isFrequency(String frequency) {
        try {
            Frequency.valueOf(frequency); // or Frequency.valueOf(frequency.toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }


    /*
    * @TimeComplexity O(1)
    *
     */
    public static boolean isFrequency2(String frequency) {
        try {
            Enum.valueOf(Frequency.class, frequency); // or Enum.valueOf(Frequency.class, frequency.toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }



    /*
    * @TimeComplexity O(n)
    *
    * WeekDay.values() is same as values()
    */
    public static boolean isFrequency3(String frequency) {
         return Stream.of(values()).anyMatch(lifeFrequency -> lifeFrequency.name().equalsIgnoreCase(frequency));
//        return Stream.of(WeekDay.values()).anyMatch(lifeFrequency -> lifeFrequency.name().equalsIgnoreCase(frequency));
    }




    /*
        NOTE:
        -----
        If you know for sure, your enum constants are uppercase (like MONDAY, ACTIVE), then:
        Frequency.valueOf(frequency.toUpperCase()); ---------
        is clean, fast, and preferred.

        If you aren’t sure whether enum constants use uppercase, camel case (Monday), or mixed case, then:
        Arrays.stream(Frequency.values())
              .anyMatch(f -> f.name().equalsIgnoreCase(frequency)); ---------
        or a similar .equalsIgnoreCase() approach
        is safer and more flexible, though slower (O(n)).
     */






    // EnumDescriptor ---- used to provide metadata about an enum class
    // public static boolean isFrequency3(String frequency) {
    //     return  EnumDesc
    // }

    // get item if present -----
    public static String toCode(String frequency) {
        try {
            return Frequency.valueOf(frequency).code; // or EnumSet.allOf(Frequency.class).stream().filter(d->d.name().equals("WEEKLY")).map(d->d.getCode()).findFirst().orElse("N/A");
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to map requested frequency " + frequency + " to field value");
        }
        return "N/A";
        // throw new FrequencyMappingException("Unable to map requested frequency " + frequency + to field value.") ");
    }

    // get item if present - alternate method ------
    // If we compare with only getDisplayName() then it will fail for "Semi-Annually" because of "-" in the display name. So, use both(getDisplayName() & name()) or replace getDisplayName() with name() or use above toCode() method instead
    public static String getValueByDisplayName(String frequency) {
        return Arrays.stream(Frequency.values())
                .filter(lifeFrequency -> lifeFrequency.getDisplayName().equalsIgnoreCase(frequency) || lifeFrequency.name().equalsIgnoreCase(frequency))
                .findFirst()
                .map(Frequency::getCode)
                .orElse("N/A");
    }
}

class FrequencyMappingException extends Exception {
    public FrequencyMappingException(String message) {
        super(message);
    }
}