/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import org.apache.commons.codec.binary.Base64;
import pntanasis.base64.base64;


/**
 *
 * @author periklis
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
            // TODO code application logic here
            base64 coder = new base64();
            //System.out.println(coder.encode("Man is distinguished, not only by his reason, but by this singular passion from other animals, which is a lust of the mind, that by a perseverance of delight in the continued and indefatigable generation of knowledge, exceeds the short vehemence of any carnal pleasure."));
            //System.out.println(coder.encode("The quick brown fox jumps over the lazy dog"));
            System.out.println(coder.encode("abc"));
            System.out.println(coder.decode("YWJj"));
            System.out.println(coder.encode("???"));
            System.out.println(coder.decode("Pz8/"));
            System.out.println(coder.decode("aGVsbG8="));
            System.out.println(coder.encode("PXLEQTEKAVKGDBOOWAREYHNEVMEYYNTGSCOTSVMJNGJUOWORYDSGMSOPOWBNNIOSD@"));
            System.out.println("PXLEQTEKAVKGDBOOWAREYHNEVMEYYNTGSCOTSVMJNGJUOWORYDSGMSOPOWBNNIOSD@");
            System.out.println(coder.decode("UFhMRVFURUtBVktHREJPT1dBUkVZSE5FVk1FWVlOVEdTQ09UU1ZNSk5HSlVPV09SWURTR01TT1BPV0JOTklPU0R="));
            System.out.println(coder.encode("abc"));
            System.out.println(new String(Base64.encodeBase64("abc".getBytes())));
            // TODO: MAKE THIS WORK!
            //System.out.println("Περικλής");
            //System.out.println(coder.encode("Περικλής"));
            //System.out.println(coder.decode("zqDOtc+BzrnOus67zq7Pgg=="));
            //la8os sto *10
            //pws na swsw noumera se 8 bit???            

    }

}
