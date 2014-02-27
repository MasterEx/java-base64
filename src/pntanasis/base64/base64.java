/*
 * Copyright (c) 2014 Periklis Ntanasis <pntanasis@gmail.com>
 * Distributed under the MIT License. See License.txt for more info.
 */
package pntanasis.base64;

import java.io.UnsupportedEncodingException;
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

    private String mapAlphabet(byte[] buffer) {
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

    public String encode(String word) throws UnsupportedEncodingException {
        return encode(word.getBytes("UTF8"));
    }

    public String encode(byte[] word) {
        BitSet bits;
        String retval;
        byte[] intarr = word;
        int bitSetSize;
        // calculate the padding
        if ((intarr.length + 1) % 3 == 0) {
            artificialtailing = 1;
        } else if ((intarr.length + 2) % 3 == 0) {
            artificialtailing = 2;
        }
        bitSetSize = (intarr.length + artificialtailing) * 8;
        bits = new BitSet(bitSetSize);
        // populate the BitSet
        int bitSetPointer = 0;
        for (int i = 0; i < intarr.length; i++) {
            String bit8 = Integer.toBinaryString(intarr[i]);
            int byteSize = getByteSize(bit8.length());
            int tmpBitSetPointer = bitSetPointer + 7;
            for (int j = bit8.length() - 1; j >= bit8.length() - byteSize; j--) {
                if (bit8.charAt(j) == '1') {
                    bits.set(tmpBitSetPointer, true);
                }
                tmpBitSetPointer--;
            }
            bitSetPointer += 8;
        }
        // create and populate the array with the alphabet indexes
        intarr = new byte[bitSetSize / 6];
        bitSetPointer = 0;
        for (int i = 0; i < intarr.length; i++) {
            byte intvalue = 0;
            for (int j = 0; j < 6; j++) {
                if (bits.get(bitSetPointer + j)) {
                    intvalue += power[5 - j];
                }
            }
            bitSetPointer += 6;
            intarr[i] = intvalue;
        }
        retval = mapAlphabet(intarr);
        return retval;
    }

    public byte[] decode(String base64) {
        BitSet bits;
        // ignore padding
        if (base64.charAt(base64.length() - 1) == '=') {
            artificialtailing = 1;
            if (base64.charAt(base64.length() - 2) == '=') {
                artificialtailing = 2;
            }
        }
        // create array of alphabet indexes (values)
        int[] alphabetIndexArray = new int[base64.length() - artificialtailing];
        for (int i = 0; i < base64.length() - artificialtailing; i++) {
            alphabetIndexArray[i] = Char2AlphabetIndex(base64.charAt(i));
        }
        // populate the bit set
        int bitSetSize = alphabetIndexArray.length * 6;
        bits = new BitSet(bitSetSize);
        int bitSetPointer = 0;
        for (int i = 0; i < alphabetIndexArray.length; i++) {
            // an 8-bit with 6 useful bits
            String bit6 = Integer.toBinaryString(alphabetIndexArray[i]);
            for (int j = bit6.length() - 1, k = 8; k > 2 && j >= 0; j--, k--) {
                if (bit6.charAt(j) == '1') {
                    bits.set(bitSetPointer + 8 - bit6.length() + j - 2, true);
                }
            }
            bitSetPointer += 6;
        }
        byte[] bytes = new byte[bitSetSize / 8];
        int bcounter = 0;
        for (int i = 0; i < bytes.length * 8; i += 8) {
            for (int j = 0; j < 8; j++) {
                if (bits.get(i + j)) {
                    bytes[bcounter] += power[7 - j];
                }
            }
            bcounter++;
        }
        // Most times this produces right output (according to test function - apache common base64
        // However some times a zero at the end is needed. The visual outcome is the same
        // Error may happen in case of outcome comparison as happens in the unit test.
        artificialtailing = 0;
        return bytes;
    }

    private int Char2AlphabetIndex(char c) {
        if (c == '+') {
            return alphabet.length - 2;
        } else if (c == '/') {
            return alphabet.length - 1;
        } else if (c >= 'a') {
            return c - 'A' - 6;
        } else if (c <= '9') {
            return c - '0' + Char2AlphabetIndex('z') + 1;
        }
        return c - 'A';
    }

    private int getByteSize(int size) {
        if (size >= 8) {
            return 8;
        } else {
            return size;
        }
    }
}
