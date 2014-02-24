/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

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
            //la8os sto *10
            //pws na swsw noumera se 8 bit???            

    }

}
