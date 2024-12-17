
/*
 * Unchecked Exception: Is an Exception that occurs during Runtime; Handled by Sub-Class -> Runtime Exception
 * It won't be identified by Java during compile time. 
 */

public class UncheckedException {
    
    public static void main(String[] args)
    {
        String name = null; 
        getLength(name);
    }

    private static void getLength(String stringInput) {
        try {
            System.out.println(stringInput.length()); 
        } catch (NullPointerException npe) {
            System.out.println("Null Poiner Exception: String cannot be null");
        }
    }
}
