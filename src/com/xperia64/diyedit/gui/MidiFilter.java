package com.xperia64.diyedit.gui;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.filechooser.*;

import com.xperia64.diyedit.Globals;

	 
/* ImageFilter.java is used by FileChooserDemo2.java. */
public class MidiFilter extends FileFilter {
	 
	private ResourceBundle bbbbbbb;
	public MidiFilter()
	{
		bbbbbbb=ResourceBundle.getBundle("com.xperia64.diyedit.resources.Resources", (Locale.getDefault().getLanguage().equals("zh")||Globals.forceChina)?Locale.CHINA:Locale.US);
	}
		public static String getExtension(File f) {
	        String ext = null;
	        String s = f.getName();
	        int i = s.lastIndexOf('.');

	        if (i > 0 &&  i < s.length() - 1) {
	            ext = s.substring(i+1).toLowerCase(Locale.US);
	        }
	        return ext;
	    }
	    //Accept all directories and all gif, jpg, tiff, or png files.
	    public boolean accept(File f) {
	        if (f.isDirectory()) {
	            return true;
	        }
	 
	        String extension = getExtension(f);
	        if (extension != null) {
	            if (extension.equals("mid")||extension.equals("smf")||extension.equals("midi")) {
	                    return true;
	            } else {
	                return false;
	            }
	        }
	 
	        return false;
	    }
	 
	    //The description of this filter
	    public String getDescription() {
	        return (String) bbbbbbb.getObject("midiFileKey"); //TODO
	    }
	}


