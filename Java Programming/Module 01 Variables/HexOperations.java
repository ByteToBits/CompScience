/**
 * Description: Hexidecimal Class demonstrates data manipulation for Bits to Hex Values
 * Each Hex Value is (4-Bits | 1 Nibble)
 * @author Tristan Sim
 * @version 1.00
 */

public class HexOperations {

    // Each Hex Value is (4-Bits | 1 Nibble) '0101'
    // Which equates to half a byte
    public HexOperations() {}

    public static void main(String[] args)
    {   
        HexOperations hexOperations =  new HexOperations();  
        // Practice converting between binary, decimal, and hexadecimal.

        // 1) Convert Binary to Hex
        Byte[] bits = {0, 1, 0, 0, 0, 0, 0, 1}; // Binary Value of '65'

        System.out.print("Binary Value: ");
        for (Byte bit : bits)
        {
            System.out.print(bit + " ");
        }
        
        String hexValue = hexOperations.binaryToHex(bits);
        
        int integerValue = hexOperations.hexToDecimal(hexValue); 
    }
    
    /**
     * Custom Methods: Parse Bits to a 2-Digit Hex Value/2-Nibble
     * @param bits Array of Bit values
     * @return Hex Value
     */
    public String binaryToHex(Byte[] bits)
    {   
        // Step 1: Combine Binary Bits to a Singular Integer (Byte)
        int byteValue = 0; 
        for (int i = 0; i < bits.length; i++)
        {   
            // '<<' Bitwise Operator | Left shift and add each bit
            byteValue = (byteValue << 1) | bits[i]; 
        }
        
        // Step 2: Convert Byte to Hexidecimal
        String hexValue = Integer.toHexString(byteValue).toUpperCase(); 

        System.out.println("\nHex (2-Hex Digit/Nibble) from Byte: " + hexValue);

        return hexValue; 
    }

    public int hexToDecimal (String Hex)
    {   
        // Radix: The (Base) for the Number System being used to Intepret the String
        // Default: 10 (Decimal) | 16 (Hexidecimal)  |  2 (Binary)
        int integerValue = Integer.parseInt(Hex, 16); // Parse Hex String as a Base of 16
        System.out.println("Decimal from Hex: " + integerValue);
        return integerValue; 
    }

    public String[] byteToNibble(Byte[] bits) 
    {   
        if (bits == null || bits.length != 1) {
            throw new IllegalArgumentException("Input must be a single byte.");
        }
        
        String[] nibbles = new String[2]; 

        // Convert the byte to binary string and pad to 8 bits
        String binaryString = String.format("%8s", Integer.toBinaryString(bits[0] & 0xFF)).replace(' ', '0');

        // Extract the two nibbles (4 bits each)
        nibbles[0] = binaryString.substring(0, 4); // First nibble
        nibbles[1] = binaryString.substring(4);   // Second nibble

        return nibbles; 
    }



}
