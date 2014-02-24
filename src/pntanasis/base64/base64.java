package pntanasis.base64;

import java.util.BitSet;

/**
 *
 * @author periklis pntanasis@gmail.com
 */
public class base64 {

    private int artificialtailing = 0;
    private BitSet bits;

    protected String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H",
        "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
        "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f",
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
        "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3",
        "4", "5", "6", "7", "8", "9", "+", "/"};

    private static final int[] power = {1, 2, 4, 8, 16, 32};

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
                    bits.set(bitSetPointer + j + (8 - bit8.length()), true);
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

    private int[] String2Intarray(String word) {
        byte[] bbuffer = word.getBytes();
        int[] ibuffer = new int[bbuffer.length];
        for (int i = 0; i < bbuffer.length; i++) {
            ibuffer[i] = (int) bbuffer[i];
        }
        return ibuffer;
    }
}
