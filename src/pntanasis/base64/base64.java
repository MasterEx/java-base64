package pntanasis.base64;

import java.util.BitSet;
import pntanasis.types.bits;

/**
 *
 * @author periklis
 * pntanasis@gmail.com
 */
public class base64 {

    private int artificialtailing = 0;
    private BitSet bits;

    protected String[] alphabet = {"A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
        "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
        "U" , "V" , "W" , "X" , "Y" , "Z" , "a" , "b" , "c" , "d" , "e" , "f" ,
        "g" , "h" , "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" ,
        "s" , "t" , "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" ,
        "4" , "5" , "6" , "7" , "8" , "9" , "+" , "/"};

    public base64()
    {
        
    }

    private String encode(int[] buffer)
    {
        String retval = "";
        int until = buffer.length;
        if(artificialtailing == 1)
        {
            until--;
        }
        if(artificialtailing == 2)
        {
            until -= 2;
        }
        for(int i=0;i<until;i++)
        { 
//            System.out.println("i "+i+" "+buffer[i]);
            retval += alphabet[buffer[i]];
        }
        if(artificialtailing == 1)
        {
            retval += '=';
        }
        if(artificialtailing == 2)
        {
            retval += "==";
        }
        artificialtailing = 0;
        return retval;
    }

    
    public String encode(String word)
    {
        String retval; //= encode(bitarray2Integerarray(eightbittosixbit(Integerarray28bitarray(String2Intarray(word)))));
        int[] intarr = String2Intarray(word);
        int s =0;
        
        if((word.length()+1)%3==0) {
            artificialtailing = 1;
            s = (1+intarr.length)*8;
            bits = new BitSet((1+intarr.length)*8);
            for(int i=intarr.length;i>intarr.length-8;i--)
                bits.set(bits.length(), false);
        } else if((word.length()+2)%3==0) {
            artificialtailing = 2;
            bits = new BitSet((2+intarr.length)*8);
            for(int i=intarr.length;i>intarr.length-16;i--)
                bits.set(bits.length(), false);
            s = (2+intarr.length)*8;
        } else {
            bits = new BitSet(intarr.length*8);
            s = intarr.length*8;
        }
//        System.out.println("length word:"+word.length()+" intarr:"+intarr.length+" bits:"+bits.length()+" "+bits.size()+" "+s);
        int k = 0;
        for(int i=0;i<intarr.length;i++) {
            String b = Integer.toBinaryString(intarr[i]);
            int st = k;
//            System.out.println("IN HERE 1 "+b);            
            for(int j=b.length()-1;j>=0;j--) {
                if (b.charAt(j) == '1') {
                    bits.set(k+j+(8-b.length()), true);
//                    System.out.println("IN HERE 2 "+k+" "+j);  
                }
            }
             k += 8;
        }
        retval = "";
        int reatarr[] = new int[s/6];
        int j = 0;
        for(int i=0;i<reatarr.length;i++) {           
                int r = 0;
//                System.out.println("r is zero "+r);
                int p = j;
//            for(;j<p+6;j++) {
//                if(bits.get(j)) {
//                    r += Math.pow(2, (j%6)+2);
            for(int bla=0;bla<6;bla++) {
                if(bits.get(j+bla)) {
                    r += Math.pow(2, 5-bla);
//                    System.out.println("IN HERE b = "+bla);
                }
            }
            j = j + 6;
//            System.out.println("IN HERE r = "+r+" "+j);
//            }
            reatarr[i] = r;//System.out.println("IN HERE r = "+r);
        }
        retval = encode(reatarr);
        return retval;
    }

    private int[] String2Intarray(String word)
    {
        byte[] bbuffer = word.getBytes();
        int[] ibuffer = new int[bbuffer.length];
        for(int i=0;i<bbuffer.length;i++)
        {
            ibuffer[i] = (int) bbuffer[i];
        }
        return ibuffer;
    }

    private bits[] Integerarray28bitarray(int[] intarray)
    {
        bits[] bitarray ;
        if((intarray.length+1)%3==0)
        {
            bitarray = new bits[intarray.length+1];
            bitarray[intarray.length] = new bits("00000000");
            artificialtailing = 1;
        }
        else if((intarray.length+2)%3==0)
        {
            bitarray = new bits[intarray.length+2];
            bitarray[intarray.length] = new bits("00000000");
            bitarray[intarray.length+1] = new bits("00000000");
            artificialtailing = 2;
        }
        else
        {
            bitarray = new bits[intarray.length];
        }
        
        for(int i=0;i<intarray.length;i++)
        {                      
            bitarray[i] = new bits(Integer.toBinaryString(intarray[i]),(byte)8);
        }        
        
        return bitarray;
    }

    private int[] bitarray2Integerarray(bits[] bitarray)
    {
        int intarray[] = new int[bitarray.length];
        for(int i=0;i<bitarray.length;i++)
        {
            intarray[i] = bit2int(bitarray[i]);
        }
        return intarray;
    }

    private bits[] eightbittosixbit(bits[] bitarray)
    {
        bits[] sixbit = new bits[4*(bitarray.length/3)];
        String a,b,c,d;
        for(int i=0,j=0;i<bitarray.length&&j<sixbit.length;i+=3,j+=4)
        {
            a=""+bitarray[i].getbitasbyte(0)+bitarray[i].getbitasbyte(1)
                    +bitarray[i].getbitasbyte(2)+bitarray[i].getbitasbyte(3)+bitarray[i].getbitasbyte(4)
                    +bitarray[i].getbitasbyte(5);
            b=""+bitarray[i].getbitasbyte(6)+bitarray[i].getbitasbyte(7)+bitarray[i+1].getbitasbyte(0)+
                    bitarray[i+1].getbitasbyte(1)+bitarray[i+1].getbitasbyte(2)+bitarray[i+1].getbitasbyte(3);
            c=""+bitarray[i+1].getbitasbyte(4)+bitarray[i+1].getbitasbyte(5)+bitarray[i+1].getbitasbyte(6)
                    +bitarray[i+1].getbitasbyte(7)+bitarray[i+2].getbitasbyte(0)+bitarray[i+2].getbitasbyte(1);
            d=""+bitarray[i+2].getbitasbyte(2)+bitarray[i+2].getbitasbyte(3)+bitarray[i+2].getbitasbyte(4)
                    +bitarray[i+2].getbitasbyte(5)+bitarray[i+2].getbitasbyte(6)+bitarray[i+2].getbitasbyte(7);
            sixbit[j] = new bits(a,(byte)6);
            sixbit[j+1] = new bits(b,(byte)6);
            sixbit[j+2] = new bits(c,(byte)6);
            sixbit[j+3] = new bits(d,(byte)6);
        }
        return sixbit;
    }

    private int bit2int(bits bit6)
    {
        int retval = 0;
        if(bit6.getbitasint(5)==1)
        {
            retval += Math.pow(2,0);
        }
        if(bit6.getbitasint(4)==1)
        {
            retval += Math.pow(2,1);
        }
        if(bit6.getbitasint(3)==1)
        {
            retval += Math.pow(2,2);
        }
        if(bit6.getbitasint(2)==1)
        {            
            retval += Math.pow(2,3);
        }
        if(bit6.getbitasint(1)==1)
        {
            retval += Math.pow(2,4);
        }
        if(bit6.getbitasint(0)==1)
        {
            retval += Math.pow(2,5);
        }
        return retval;
    }
}
