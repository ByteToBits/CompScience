import java.util.ArrayList;

public class BitwiseOperations
{   
    public BitwiseOperations()
    {

    }
    public static void main(String[] args)
    {   
        BitwiseOperations bitOperation = new BitwiseOperations(); 
        byte[] bits = {0, 1, 0, 0, 0, 0, 0, 1}; // Binary Value of '65'
        byte byteValue = 0; // 8-bit Unsigned Value
        
        // "<<"" - Bitwise Left Shift Operator; vice versa, ">>" shift bits to the right
        // effectively multiply the number by 2^n, where n is the number of positions shifted
        for (int i = 0; i < bits.length; i++) // Since bits is 8 bits, shifting 8 bits makes 2^8 equating to 1 Byte
        {   
            byteValue = (byte) ((byteValue << 1) | bits[i]);
            // 1) byteValue << 1 -> Moves all existing bits of byteValue to the left, discards the left-most bit, and fills the right-most bit with 0.
            // 2) | bits[i]; "|" is the bitwise OR operator. If bits[i] == 1/true, it sets the right-most bit of byteValue to 1. Otherwise, it remains unchanged.

            if (i == 0){
                System.out.println("\nIteration 0 (Before Bit Shift Operation)"); 
                System.out.println("Value of Byte = " + byteValue + " (8-Bit Form: " + bitOperation.parseBits(byteValue, 8) + ")");
            }
            else if (i == 2) {
                System.out.println("\nIteration 2 (Bit Shift Right to Left Twice)"); 
                System.out.println("Value of Byte = " + byteValue + " (8-Bit Form: " + bitOperation.parseBits(byteValue, 8) + ")");
            }
        } 
        
        System.out.println("\nAfter Complete 8-bit Shift Operation:"); 
        System.out.println("Value of Byte = " + byteValue + " (8-Bit Form: " + bitOperation.parseBits(byteValue, 8) + ")");

        System.out.println("\n8-Bit Manipulation by Flipping Bits:");
        System.out.println("Flip Byte = " + byteValue + " to a Value of '88'");
        
        byteValue = bitOperation.flipBit(byteValue, 8, 3); // Flip Bit 3
        byteValue = bitOperation.flipBit(byteValue, 8, 4); // Flip Bit 4 
        byteValue = bitOperation.flipBit(byteValue, 8, 7); // Flip Bit 7 
        
        System.out.println("Value of Byte = " + byteValue + " (8-Bit Form: " + bitOperation.parseBits(byteValue, 8) + ")");     

    }

    public ArrayList<String> parseBits(Byte byteValue, int bitLength)
    {  
       ArrayList<String> bitData = new ArrayList<String>(); 

       // Convert into Byte into a Binary String
       // & 0xFF makes sure you're only working with the actual 8 bits of the byte, ignoring negative signs or extra bits.
       // 0xFF is a hexadecimal literal representing the binary value 11111111 | Masking = Used to isolate the lower 8 bits of a number in operations 
       String binaryString = Integer.toBinaryString(byteValue & 0xFF);  
       
       // Ensures the Data is 8-Bits; else Pad Data with "0"
       while (binaryString.length() < bitLength)
       {
           binaryString = "0" + binaryString; 
       }

       // Add Each Bit to the ArrayList
       for (int i = 0; i < binaryString.length(); i++)
       {
          bitData.add(String.valueOf(binaryString.charAt(i)));
       }

       return bitData; 
    }

    public Byte flipBit(Byte byteValue, int bitLength, int targetBit)
    {   
        // Convert Byte to Binary String
        String binaryString = Integer.toBinaryString(byteValue & 0xFF); 
        while (binaryString.length() < bitLength) // Padding to form a Complete 8-Bit
        {
            binaryString = "0" + binaryString;
        }

        StringBuffer bitData = new StringBuffer(binaryString); 

        if (targetBit >= 0 && targetBit < bitLength)
        {
            char flippedBit = binaryString.charAt(targetBit) == '1' ? '0' : '1'; 
            bitData.setCharAt(targetBit, flippedBit);
        }
        
        // Radix: The (Base) for the Number System being used to Intepret the String
        // Default: 10 (Decimal) | 16 (Hexidecimal)  |  2 (Binary)
        int results = Integer.parseInt(bitData.toString(), 2);

        return (byte) results; 
    }
}
