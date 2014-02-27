/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import pntanasis.base64.base64;


/**
 *
 * @author periklis
 */
public class Main {
        
    static boolean decode = false;
    static boolean ignoreGarbage = false;
    static int wrapCols = 76;
    static final base64 coder = new base64();
    static InputStream streamIn = System.in;
    static OutputStream streamOut = System.out;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        int maxArg = args.length;
        try {
            if(!(   args[args.length-1].equals("-d") || 
                    args[args.length-1].equals("-w") || 
                    args[args.length-1].equals("--help") || 
                    args[args.length-1].equals("-i"))) {
                if(!args[args.length-1].equals("-")) {
                    try {
                        streamIn = new FileInputStream(args[args.length-1]);
                        maxArg--;
                    } catch (FileNotFoundException e) {
                        System.out.println("File "+args[args.length-1]+" not found.");
                        System.exit(1);
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // do nothing! :)
        }
            
        for(int i=0;i<maxArg;i++) {
            switch (args[i]) {
                case "-d":
                    decode = true;
                    break;
                case "--help":
                    usage();
                    System.exit(0);
                    break;
                case "-i":
                    ignoreGarbage = true;
                    break;
                case "-w":
                    if(i >= args.length-2) {
                        System.exit(1);
                    }
                    try {
                        wrapCols = Integer.valueOf(args[++i]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid wrap size: "+args[--i]+".");
                        System.exit(1);
                    }
                    break;
                default:
                    System.out.println("Argument parsing error.");
                    System.exit(1);
            }
        }

        if(decode) {
            decode();
        } else {
            encode();
        }
    }
    
    private static void usage() {
        System.out.println("Usage: java -jar base64.jar [OPTION] ... [FILE]");
        System.out.println("A simple base64 client written in java.");
        System.out.println("It encodes or decodes a FILE, or standard input, to standard output.\n");
        System.out.println("OPTION arguments are not mandatory.");
        System.out.println("\t-d\t\tdecode data");
        System.out.println("\t-i\t\twhen decoding, igonores non base64 characters");
        System.out.println("\t-w COLS\t\twhen encoding, wrap the output in COLS characters (default 76).\n\t\t\tUse 0 to disable wrapping");
        System.out.println("\t--help\t\tprints this help and exit");
    }
    
    private static void decode() throws IOException {
        char[] buffer = new char[30000];
        InputStreamReader in = new InputStreamReader(streamIn);
        OutputStreamWriter out = new OutputStreamWriter(streamOut);
        int l = 0;
        while((l = in.read(buffer)) != -1) {
            String decodedString = coder.decode(new String(buffer,0,l));
            char[] retVal = new char[decodedString.length()];
            decodedString.getChars(0, decodedString.length(), retVal, 0);
            out.write(retVal, 0, retVal.length);
        }
        in.close();
        out.close();
    }
    
    private static void encode() throws IOException {
        char[] buffer = new char[30000];
        InputStreamReader in = new InputStreamReader(streamIn);
        OutputStreamWriter out = new OutputStreamWriter(streamOut);
        int l = 0;
        while((l = in.read(buffer)) != -1) {
            String encodedString = coder.encode(new String(buffer,0,l));
            char[] retVal = new char[encodedString.length()];
            encodedString.getChars(0, encodedString.length(), retVal, 0);
            out.write(retVal, 0, retVal.length);
        }
        out.write('\n');
        in.close();
        out.close();
    }

}
