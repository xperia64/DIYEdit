package com.xperia64.diyedit;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// Read and write from and to byte arrays
public class FileByteOperations {
	/** Read the given binary file, and return its contents as a byte array.*/ 
	   public static byte[] read(String aInputFileName){
	    log("Reading in binary file named : " + aInputFileName);
	    File file = new File(aInputFileName);
	    log("File size: " + file.length());
	    byte[] result = new byte[(int)file.length()];
	    try {
	      InputStream input = null;
	      try {
	        int totalBytesRead = 0;
	        input = new BufferedInputStream(new FileInputStream(file));
	        while(totalBytesRead < result.length){
	          
	          int bytesRemaining = result.length - totalBytesRead;
	          //input.read() returns -1, 0, or more :
	          int bytesRead = input.read(result, totalBytesRead, bytesRemaining);         
	          if (bytesRead > 0){
	            totalBytesRead = totalBytesRead + bytesRead;
	          }
	        }
	        /*
	         the above style is a bit tricky: it places bytes into the 'result' array; 
	         'result' is an output parameter;
	         the while loop usually has a single iteration only.
	        */
	        log("Num bytes read: " + totalBytesRead);
	      }
	      finally {
	        log("Closing input stream.");
	        input.close();
	      }
	    }
	    catch (FileNotFoundException ex) {
	      log("File not found.");
	    }
	    catch (IOException ex) {
	      log(ex);
	    }
	    return result;
	  }
	  
	  /**
	   Write a byte array to the given file. 
	   Writing binary data is significantly simpler than reading it. 
	  */
	  public static void write(byte[] aInput, String aOutputFileName){
	    log("Writing binary file...");
	    try {
	      OutputStream output = null;
	      try {
	        output = new BufferedOutputStream(new FileOutputStream(aOutputFileName));
	      
	    	   output.write(aInput);
	       
	       
	      }
	      finally {
	        output.close();
	      }
	    }
	    catch(FileNotFoundException ex){
	      log("File not found.");
	    }
	    catch(IOException ex){
	      log(ex);
	    }
	  }
	  
	  private static void log(Object aThing){
	    //System.out.println(String.valueOf(aThing));
	  }
}
