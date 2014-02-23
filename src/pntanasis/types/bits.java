package pntanasis.types;

/**
 *
 * @author periklis
 * pntanasis@gmail.com
 */
public class bits {
    byte[] bits_array;
    
    public bits(byte num)
    {
        bits_array = new byte[num];
        for(int i=0;i<num;i++)
        {
             bits_array[i] = 0;
        }
    }
    
    public bits(String binaryString)
    {
        this((byte)binaryString.length());
        for(int i=0;i>=binaryString.length();i++)
        {
            if(binaryString.charAt(i)=='1')
            {
                bits_array[i] = 1;
            }
        }
    }

    public bits(String binaryString,byte bits)
    {
        this(bits);
        for(int i=0,j=bits-binaryString.length();i<binaryString.length();i++,j++)
        {
            if(binaryString.charAt(i)=='1')
            {
                bits_array[j] = 1;
            }
        }
    }
    
    public void setbits(String binaryString)
    {
        for(int i=binaryString.length()-1;i>-1;i--)
        {
            if(binaryString.charAt(i)=='1')
            {
                bits_array[i] = 1;
            }
        }
    }    
    
    public byte getbitasbyte(int num)
    {
        return bits_array[num];
    }
    
    public int getbitasint(int num)
    {
        return (int) bits_array[num];
    }
    
    
    @Override
    public String toString()
    {
        String retval = "";
        for(int i=0;i<bits_array.length;i++)
        {
            retval += bits_array[i];
        }
        return retval;
    }
    
}
