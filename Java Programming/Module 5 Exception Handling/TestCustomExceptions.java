

public class TestCustomExceptions {

    public static void main(String[] args) throws CustomExceptions {
        try {
            validateAge(-1);
        } catch (CustomExceptions e) {
            // Catching and handling the custom exception
            System.out.println(e.getMessage()); // Print the message from the exception
            e.printStackTrace(); // Optionally, print the stack trace for more details
        }
    }

    private static void validateAge(int age) throws CustomExceptions {
        if (age < 0) {
            // Throwing the custom exception with a message and a cause
            throw new CustomExceptions("Age cannot be negative", new RuntimeException());
        }
    }
}
