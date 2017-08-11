package com.xperia64.diyedit.gui;

import java.io.File;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.xperia64.diyedit.Globals;




public class FileChooser extends JFrame {
	private static final long serialVersionUID = 1L;
	private File myFile;
	public FileChooser(final boolean saves, final boolean open, final boolean midi, final boolean fold) {
		this.setUndecorated( true );
		this.setVisible( true );
		this.setLocationRelativeTo( null );
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		JFileChooser fc;
		if(Globals.lastFolder.isEmpty())
			fc = new JFileChooser();
		else
			fc = new JFileChooser(Globals.lastFolder);
		if(Globals.forceChina)
		{
			fc.setLocale(Locale.CHINA);
		}
		fc.setAcceptAllFileFilterUsed(false);
		if(fold)
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		else
			if(midi)
				fc.setFileFilter(new MidiFilter());
			else
				fc.setFileFilter(new MioFilter(saves));
		int ret;
		if(open)
			ret = fc.showOpenDialog(this);
		else
			ret = fc.showSaveDialog(this);
		if(ret==JFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			if(f==null)
			{
				System.exit(0);
			}else if(f.getAbsolutePath().isEmpty()){
				System.exit(0);
			}
			Globals.lastFolder=f.getAbsolutePath().substring(0,f.getAbsolutePath().lastIndexOf(File.separator));
			myFile=f;
		}else{
			myFile=null;
		}
		this.setVisible(false);
		this.dispose();

	}
	public File getFile() {
		return myFile;
	}
}
