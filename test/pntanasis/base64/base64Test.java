/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pntanasis.base64;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author periklis
 */
public class base64Test {
    
    public base64Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of encode method, of class base64.
//     */
//    @Test
//    public void testEncode_intArr() {
//        System.out.println("encode");
//        int[] buffer = null;
//        base64 instance = new base64();
//        String expResult = "";
//        String result = instance.encode(buffer);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of encode method, of class base64.
     */
    @Test
    public void testEncode_String() {
        System.out.println("encode");
        base64 instance = new base64();
        // tests up to 1000 char string of random characters (32-126 ASCII chars)
        for(int i=0;i<1000;i++) {
            for(int j=0;j<3;j++) {
                String word = "";
                for(int w=0;w<i;w++) {
                    word += (char)random(32, 127);
                }        
                String expResult = new String(Base64.encodeBase64(word.getBytes()));
                String result = instance.encode(word);        
                assertTrue("Failed, input: "+word+" result: "+result+" expexted result: "+expResult, expResult.equals(result));                
            }
        }
        System.out.println("encode was successfull");
    }
    
    @Test
    public void testEncode_File() throws FileNotFoundException, IOException {
        System.out.println("encode (file)");
        base64 instance = new base64();
        FileReader file = new FileReader("test.file"); //test file in project root
        String f = "";
        int c;
        while((c = file.read()) != -1) {
            f += ""+(char)c;
        }      
        String expResult = new String(Base64.encodeBase64(f.getBytes()));
        String result = instance.encode(f);        
        assertTrue("Failed, result: "+result+" expexted result: "+expResult, expResult.equals(result));
        System.out.println("encode (file) was successfull");
    }
    
    @Test
    public void testEncode_Performance() throws FileNotFoundException, IOException {
        System.out.println("encode (file)");
        long start, end;
        base64 instance = new base64();
        FileReader file = new FileReader("test.file"); //test file in project root
        String f = "";
        int c;
        while((c = file.read()) != -1) {
            f += ""+(char)c;
        }
        
        start = System.currentTimeMillis();
        new String(Base64.encodeBase64(f.getBytes())); 
        end = System.currentTimeMillis();
        System.out.println("Process APACHE base64 encoding toke " + (end - start) + " MilliSeconds");
        start = System.currentTimeMillis();
        new String(Base64.encodeBase64(f.getBytes())); 
        end = System.currentTimeMillis();
        System.out.println("Process APACHE base64 encoding toke " + (end - start) + " MilliSeconds");start = System.currentTimeMillis();
        new String(Base64.encodeBase64(f.getBytes())); 
        end = System.currentTimeMillis();
        System.out.println("Process APACHE base64 encoding toke " + (end - start) + " MilliSeconds");
        
        start = System.currentTimeMillis();
        instance.encode(f);
        end = System.currentTimeMillis();
        System.out.println("Process base64 encoding toke " + (end - start) + " MilliSeconds");
        start = System.currentTimeMillis();
        instance.encode(f);
        end = System.currentTimeMillis();
        System.out.println("Process base64 encoding toke " + (end - start) + " MilliSeconds");start = System.currentTimeMillis();
        instance.encode(f);
        end = System.currentTimeMillis();
        System.out.println("Process base64 encoding toke " + (end - start) + " MilliSeconds");
        
        System.out.println("base64 encoding performance test was successfull");
    }
    
    @Test
    public void testDecode() throws FileNotFoundException, IOException {
        System.out.println("decode random strings");
        base64 instance = new base64();
        for(int i=0;i<100;i++) {
            String word = "";
            int r = random(1,100);
            for(int j=0;j<r;j++) {
                word += (char) random(32,127);
            }
            String base64 = new String(Base64.encodeBase64(word.getBytes()));
            String result = instance.decode(base64);
            assertTrue("Failed, result: "+result+" expexted result: "+word+" base64:"+base64, word.equals(result));
        }
    }
    
    @Test
    public void testDecode_Performance() throws FileNotFoundException, IOException {
        System.out.println("decode file Performance");
        long start, end;
        base64 instance = new base64();
        FileReader file = new FileReader("test.file"); //test file in project root
        String f = "";
        int c;
        while((c = file.read()) != -1) {
            f += ""+(char)c;
        }
        String base64 = new String(Base64.encodeBase64(f.getBytes()));
        start = System.currentTimeMillis();
        new String(Base64.decodeBase64(base64.getBytes())); 
        end = System.currentTimeMillis();
        System.out.println("Process APACHE base64 decode toke " + (end - start) + " MilliSeconds");
        new String(Base64.decodeBase64(base64.getBytes())); 
        end = System.currentTimeMillis();
        System.out.println("Process APACHE base64 decode toke " + (end - start) + " MilliSeconds");
        new String(Base64.decodeBase64(base64.getBytes())); 
        end = System.currentTimeMillis();
        System.out.println("Process APACHE base64 decode toke " + (end - start) + " MilliSeconds");
        
        instance.decode(base64);
        end = System.currentTimeMillis();
        System.out.println("Process base64 decode toke " + (end - start) + " MilliSeconds");
        instance.decode(base64);
        end = System.currentTimeMillis();
        System.out.println("Process base64 decode toke " + (end - start) + " MilliSeconds");
        instance.decode(base64); 
        end = System.currentTimeMillis();
        System.out.println("Process base64 decode toke " + (end - start) + " MilliSeconds");
    }
    
    // helper method to produce random number between A-B
    public static int random(int A, int B) {
        return (int) (Math.random() * (B - A)) + A;
    }
    
}
