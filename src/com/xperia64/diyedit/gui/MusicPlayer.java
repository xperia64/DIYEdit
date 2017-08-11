package com.xperia64.diyedit.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.xperia64.diyedit.ExportGameMidi;
import com.xperia64.diyedit.ExportMidi;
import com.xperia64.diyedit.Globals;
import com.xperia64.diyedit.metadata.RecordMetadata;

@SuppressWarnings("serial")
public class MusicPlayer extends JFrame implements ActionListener, ItemListener {
	
	private JButton PlayPause;
	private JButton Stop;
	private JButton Export;
	private boolean playing=false;
	private boolean started=false;
	static ExportMidi e2;
	static ExportGameMidi e3;
	private ResourceBundle bbbbbbb;
	static boolean gam;
	byte[] bb;
	public MusicPlayer(byte[] b, boolean isGame)
	{
		bbbbbbb = ResourceBundle.getBundle("com.xperia64.diyedit.resources.Resources", (Locale.getDefault().getLanguage().equals("zh")||Globals.forceChina)?Locale.CHINA:Locale.US);
		bb=b;
		if(gam = isGame)
		{
			e3 = new ExportGameMidi(b);
		}else{
			e2 = new ExportMidi(b);
		}
	     getContentPane().setLayout(null);
	     setupGUI();
	     WindowListener exitListener = new WindowAdapter() {

	            @Override
	            public void windowClosing(WindowEvent e) {
	            	if(gam)
	            	{
	            		if(!e3.paused()&&!e3.playing()&&started)
		    			{
		    				started=false;
		    				playing=false;
		    			}
		    			if(started)
		    			{
		    			e3.Stop();
		    			}
	            	}else{
	            	if(!e2.paused()&&!e2.playing()&&started)
	    			{
	    				started=false;
	    				playing=false;
	    			}
	    			if(started)
	    			{
	    			e2.Stop();
	    			}
	            	}
	            }
	        };
	        this.addWindowListener(exitListener);
	     setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	void setupGUI()
	{
		PlayPause = new JButton();
		PlayPause.setSize(50,50);
		PlayPause.setLocation(20,20);
		PlayPause.setText("|>");
		PlayPause.addActionListener(this);
		getContentPane().add(PlayPause);
		Stop = new JButton();
		Stop.setSize(50,50);
		Stop.setLocation(80,20);
		Stop.setText("[ ]");
		Stop.addActionListener(this);
		getContentPane().add(Stop);
		Export = new JButton();
		Export.setSize(100,50);
		Export.setLocation(140,20);
		Export.setText((String) bbbbbbb.getObject("saveMidiKey"));
		Export.addActionListener(this);
		getContentPane().add(Export);
		setLocation(50,50);
		setTitle((String)bbbbbbb.getObject("musicPlayerKey")+": "+new RecordMetadata(bb).getName());
		setSize(300,120);
		setVisible(true);
		setResizable(false);
}
	public void actionPerformed(ActionEvent e) {  
		if(e.getSource()==PlayPause)
		   {
			if(gam)
			{
				if(!e3.paused()&&!e3.playing()&&started)
				{
					started=false;
					playing=false;
				}
				if(!playing&&!started)
				{
					playing=true;
					started=true;
					PlayPause.setText("||");
					new PlayThread().start();
					
				}else if(!playing&&started)
				{
					playing = true;
					PlayPause.setText("||");
					e3.PlayPause();
			   	}else if(playing){
					playing=false;
					PlayPause.setText("|>");
					e3.PlayPause();
				}
			}else{
			if(!e2.paused()&&!e2.playing()&&started)
			{
				started=false;
				playing=false;
			}
			if(!playing&&!started)
			{
				playing=true;
				started=true;
				PlayPause.setText("||");
				new PlayThread().start();
				
			}else if(!playing&&started)
			{
				playing = true;
				PlayPause.setText("||");
				e2.PlayPause();
		   	}else if(playing){
				playing=false;
				PlayPause.setText("|>");
				e2.PlayPause();
			}
			}
			  
		   }
		if(e.getSource()==Stop)
		{
			if(gam)
			{
				if(!e3.paused()&&!e3.playing()&&started)
				{
					started=false;
					playing=false;
				}
				if(started)
				{
				e3.Stop();
				}	
			}else{
			if(!e2.paused()&&!e2.playing()&&started)
			{
				started=false;
				playing=false;
			}
			if(started)
			{
			e2.Stop();
			}
			}
			PlayPause.setText("|>");
		}
		if(e.getSource()==Export)
		{
			FileChooser fc = new FileChooser(false, false, true, false);
			
			if(fc.getFile()!=null)
			{
				if(gam)
				{
					if(!e3.paused()&&!e3.playing()&&started)
					{
						started=false;
						playing=false;
					}
					if(started)
					{
					e3.Stop();
					}
					e3.export(fc.getFile().getAbsolutePath()+(!fc.getFile().getAbsolutePath().toLowerCase(Locale.US).endsWith(".mid")?".mid":""), false);
				}else{
				if(!e2.paused()&&!e2.playing()&&started)
				{
					started=false;
					playing=false;
				}
				if(started)
				{
				e2.Stop();
				}
				e2.export(fc.getFile().getAbsolutePath()+(!fc.getFile().getAbsolutePath().toLowerCase(Locale.US).endsWith(".mid")?".mid":""), false);
				}
				this.setVisible(false);
				this.dispose();
			}else{
				new Warning((String) bbbbbbb.getObject("midiWarningKey"));
			}
		}
	}
    
 
    public void itemStateChanged(ItemEvent e)  {
        @SuppressWarnings("unused")
		Object source = e.getItemSelectable();
    }
    
}

	class PlayThread extends Thread
	{
		public void run()
		{
			if(MusicPlayer.gam)
				MusicPlayer.e3.export("", true);
			else
			MusicPlayer.e2.export("", true);
		}
	}
