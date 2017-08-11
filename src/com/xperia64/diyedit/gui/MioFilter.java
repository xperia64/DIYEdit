package com.xperia64.diyedit.gui;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.filechooser.*;

import com.xperia64.diyedit.Globals;

	 
/* ImageFilter.java is used by FileChooserDemo2.java. */
public class MioFilter extends FileFilter {
	 
		private boolean saves;
		private ResourceBundle bbbbbbb;
		public MioFilter(boolean s)
		{
			saves=s;
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
	            if (extension.equals("mio")||((extension.equals("dsv")||extension.equals("sav")||extension.equals("bin"))&&saves)) {
	                    return true;
	            } else {
	                return false;
	            }
	        }else if(saves&&((f.length()==4719808&&f.getName().equals("GDATA"))||(f.length()==1033408&&f.getName().equals("MDATA"))||(f.length()==591040&&f.getName().equals("RDATA")))){
	        	return true;
	        }
	 
	        return false;
	    }
	 
	    //The description of this filter
	    public String getDescription() {
	        return (String) bbbbbbb.getObject("mioFileKey"); //TODO
	    }
	}


