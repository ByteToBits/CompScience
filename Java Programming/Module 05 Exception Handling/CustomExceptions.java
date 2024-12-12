
// Custom exception class
public class CustomExceptions extends Exception {

    // No-argument constructor (default constructor)
    public CustomExceptions() {}

    // Constructor that accepts only a message
    public CustomExceptions(String message) {
        super(message); // Pass the message to the parent (Exception) class
    }

    // Constructor that accepts only a cause
    public CustomExceptions(Throwable cause) {
        super(cause); // Pass the cause to the parent (Exception) class
    }

    // Constructor that accepts both a message and a cause
    public CustomExceptions(String message, Throwable cause) {
        super(message, cause); // Pass both message and cause to the parent (Exception) class
    }
}