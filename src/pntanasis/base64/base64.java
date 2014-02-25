package pntanasis.base64;

import java.util.BitSet;

/**
 *
 * @author periklis pntanasis@gmail.com
 */
public class base64 {

    private int artificialtailing = 0;

    private static final String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H",
        "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
        "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f",
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
        "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3",
        "4", "5", "6", "7", "8", "9", "+", "/"};

    private static final int[] power = {1, 2, 4, 8, 16, 32, 64, 128};

    public base64() {

    }

    private String encode(int[] buffer) {
        String retval = "";
        int until = buffer.length;
        if (artificialtailing == 1) {
            until--;
        }
        if (artificialtailing == 2) {
            until -= 2;
        }
        for (int i = 0; i < until; i++) {
            retval += alphabet[buffer[i]];
        }
        if (artificialtailing == 1) {
            retval += '=';
        }
        if (artificialtailing == 2) {
            retval += "==";
        }
        artificialtailing = 0;
        return retval;
    }

    public String encode(String word) {
        BitSet bits;
        String retval;
        int[] intarr = String2Intarray(word);
        int bitSetSize;
        // calculate the padding
        if ((word.length() + 1) % 3 == 0) {
            artificialtailing = 1;
            bitSetSize = (1 + intarr.length) * 8;
        } else if ((word.length() + 2) % 3 == 0) {
            artificialtailing = 2;
            bitSetSize = (2 + intarr.length) * 8;
        } else {
            bitSetSize = intarr.length * 8;
        }
        bits = new BitSet(bitSetSize);
        // populate the BitSet
        int bitSetPointer = 0;
        for (int i = 0; i < intarr.length; i++) {
            String bit8 = Integer.toBinaryString(intarr[i]);
            for (int j = bit8.length() - 1; j >= 0; j--) {
                if (bit8.charAt(j) == '1') {
                    bits.set(bitSetPointer + j + 8 - bit8.length(), true);
                }
            }
            bitSetPointer += 8;
        }
        // create and populate the array with the alphabet indexes
        intarr = new int[bitSetSize / 6];
        bitSetPointer = 0;
        for (int i = 0; i < intarr.length; i++) {
            int intvalue = 0;
            for (int j = 0; j < 6; j++) {
                if (bits.get(bitSetPointer + j)) {
                    intvalue += power[5 - j];
                }
            }
            bitSetPointer += 6;
            intarr[i] = intvalue;
        }
        retval = encode(intarr);
        return retval;
    }

    public String decode(String base64) {
        BitSet bits;
        String retval = "";
        // ignore padding
        if (base64.charAt(base64.length()-1) == '=') {
            artificialtailing = 1;
            if(base64.charAt(base64.length()-2) == '=') {
                artificialtailing = 2;
            }
        }
        // create array of alphabet indexes (values)
        int[] alphabetIndexArray = new int[base64.length()-artificialtailing];
        for(int i=0;i<base64.length()-artificialtailing;i++) {
            alphabetIndexArray[i] = Char2AlphabetIndex(base64.charAt(i));
        }
        // populate the bit set
        bits = new BitSet(alphabetIndexArray.length*6);
        int bitSetPointer = 0;
        for (int i = 0; i < alphabetIndexArray.length; i++) {
            // an 8-bit with 6 useful bits
            String bit6 = Integer.toBinaryString(alphabetIndexArray[i]);
            for (int j = bit6.length() - 1, k = 8 ; k>2 && j >= 0 ; j--, k--) {
                if (bit6.charAt(j) == '1') {
                    bits.set(bitSetPointer + 8 - bit6.length() + j - 2, true);
                }
            }
            bitSetPointer += 6;
        }
        // transform bit set to characters
        for(int i=0;i<bits.length();i+=8) {
            char c = 0;
            for(int j=0;j<8;j++) {
                if (bits.get(i + j)) {
                    c += power[7 - j];
                }
            }
            retval += ""+c;
        }
        return retval;
    }

    private int[] String2Intarray(String word) {
        byte[] bbuffer = word.getBytes();
        int[] ibuffer = new int[bbuffer.length];
        for (int i = 0; i < bbuffer.length; i++) {
            ibuffer[i] = (int) bbuffer[i];
        }
        return ibuffer;
    }

    private int Char2AlphabetIndex(char c) {
        if (c == 43) {
            return alphabet.length - 1;
        } else if (c == 47) {
            return alphabet.length - 2;
        } else if (c >= 97) {
            return c-71;
        }
        return c-65;
    }
}
