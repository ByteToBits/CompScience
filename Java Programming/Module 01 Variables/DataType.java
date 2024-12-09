
public class DataType 
{
    // Fields - Data Types in Java
    
    // Primitive Type
    boolean booleanA = true;    // Stores true or false values
    char charA = 'A';           // Stores a single characters or ASCII values

    byte byteA = 10;                // 8-Bit Unsigned Integer     (Whole Numbers from -128 to 127)
    short shortInteger = 32000;     // 16-bit Unsigned Integer    (Whole Numbers -32,768 to 32,767)
    int integer1 = 128000;          // 32-bit Unsigned Integer    (Whole Numbers -2,147,483,648 to 2,147,483,647)
    long longInteger = 1280000000;  // 64-Bit Unsigned Integer    (Whole Number -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807)
    
    float floatA = 6.32175f;    // 32-Bit - Stores fractional numbers uo to 6 to 7 decimal digits | Needs an declare 'f'
    double doubleA = 8.321556;  // 64 Bit - Stores fractional numbers uo to 15 to 16 decimal digits 

    // Non-Primitive Types
    String stringA = "Test String";  //  Variable contains a Collection of Characters
    
    public static void main(String[] args)
    {
        System.out.println("64-Bit Double Max Size: " + Double.MAX_VALUE);
        System.out.println("64-Bit Double Min Size: " + Double.MIN_VALUE);
        System.out.println("32-Bit Float Max Size: " + Float.MAX_VALUE);
        System.out.println("32-Bit Float Min Size: " + Float.MIN_VALUE);
    }

}
