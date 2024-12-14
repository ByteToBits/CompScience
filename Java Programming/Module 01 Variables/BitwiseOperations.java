import java.util.ArrayList;

public class BitwiseOperations
{
    public static void main(String[] args)
    {
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
                System.out.println("Iteration 0 (Before Bit Shfit): " + parseBits(byteValue, 8)); 
            }
        } 
        
        System.out.println("Value of Byte = " + byteValue + " (8-Bit Form: " + parseBits(byteValue, 8) + ")");
        
    }

    public static ArrayList<String> parseBits(Byte byteValue, int bitLength)
    {  
       ArrayList<String> bitData = new ArrayList<String>(); 

       // Convert into Byte into a Binary String
       // & 0xFF makes sure you're only working with the actual 8 bits of the byte, ignoring negative signs or extra bits.
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
}