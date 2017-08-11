package com.xperia64.diyedit;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import com.xperia64.diyedit.gui.FileChooser;
import com.xperia64.diyedit.gui.GuiDFrame;
import com.xperia64.diyedit.gui.Warning;
import com.xperia64.diyedit.savecrypt.Tachtig;
import com.xperia64.diyedit.savecrypt.Twintig;

// Test main class
public class Main {
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		if(true)
		{
		FileChooser f = new FileChooser(true, true, false, false);
		Globals.populateTranslation();
		if(true) // The BEST way to test things
		{
		if(f.getFile()!=null)
			new GuiDFrame(f.getFile().getAbsolutePath(), FileByteOperations.read(f.getFile().getAbsolutePath()));
		else
			new Warning((String) ResourceBundle.getBundle("com.xperia64.diyedit.resources.Resources", (Locale.getDefault().getLanguage().equals("zh")||Globals.forceChina)?Locale.CHINA:Locale.US).getObject("fileWarningKey"));
		}else{
		Tachtig t = new Tachtig(FileByteOperations.read(f.getFile().getAbsolutePath()));
		t.do_file_header();
		t.do_backup_header();
		for (int i = 0; i < t.n_files; i++)
			t.do_file();
		t.write_permissions();
		t.do_sig();
		Twintig tt = new Twintig(t.mPath.toString()+File.separator+"0001000157413445","yoloswag.bin");
		tt.do_file_header(tt.title_id, null);
		tt.find_files();
		tt.do_backup_header(tt.title_id);
		for (int i = 0; i < tt.n_files; i++)
			tt.do_file(i);
		tt.do_sig();
		}
		}else{
			
		}
		
	}
	//60032
	//63104
	//96x64
}
