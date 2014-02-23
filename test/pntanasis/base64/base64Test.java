/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pntanasis.base64;

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
        // atm tests strings that contain only CAPITAL letters
        for(int i=0;i<10000;i++) {
            for(int j=0;j<100;j++) {
                String word = "";
                for(int w=0;w<i;w++) {
                    word += (char)random(65, 91);
                }        
                String expResult = new String(Base64.encodeBase64(word.getBytes()));
                String result = instance.encode(word);        
                assertTrue("Failed, input: "+word+" result: "+result+" expexted result: "+expResult, expResult.equals(result));                
            }
        }
        // TODO review the generated test code and remove the default call to fail.
        System.out.println("encode was successfull");
    }
    
    // helper method to produce random number between A-B
    public static int random(int A, int B) {
        return (int) (Math.random() * (B - A)) + A;
    }
    
}
