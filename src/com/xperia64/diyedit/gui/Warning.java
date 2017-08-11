package com.xperia64.diyedit.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Warning extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Warning(String w)
	{
		 this.setUndecorated( true );
	        this.setVisible( true );
	        this.setLocationRelativeTo( null );
	        JOptionPane.showMessageDialog(this, w);
	        this.setVisible(false);
	        this.dispose();
	}
}
