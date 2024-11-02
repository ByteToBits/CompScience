
import java.util.*;
import java.util.Stack; 

public class ReverseSequence {

    // Problem : Single Line Input | Whitespaces in between text  
    // Take Text Sequence and print it in revserse order 
    // Hint: String str = "abc"; is equivalent to: char data[] = {'a', 'b', 'c'}; String str = new String(data);

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); 

        System.out.println("Please Input Sequence:"); 
        String inputSequence = scanner.nextLine();  
        
        // Split Input by whitespace and store in an array
        String [] words = inputSequence.split(" "); 
        
        // Stack: Last-In, First-Out (LIFO) | Last Element In will also be the first element out
        Stack<String> stack = new Stack<String>(); 
        
        // Push Each Word Into the Stack
        for (String word : words) {
            stack.push(word); 
        }

        // Print the Reverse Word Order
        System.out.println("Output Sequence: "); 
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " "); 
        }

        scanner.close(); 

    }
 
}
