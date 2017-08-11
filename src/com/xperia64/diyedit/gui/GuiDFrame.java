package com.xperia64.diyedit.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.joda.time.DateTime;

import com.xperia64.diyedit.FileByteOperations;
import com.xperia64.diyedit.Globals;
import com.xperia64.diyedit.editors.GameEdit;
import com.xperia64.diyedit.editors.GameEdit.GameArt;
import com.xperia64.diyedit.editors.GameEdit.GameObject;
import com.xperia64.diyedit.editors.MangaEdit;
import com.xperia64.diyedit.metadata.*;
import com.xperia64.diyedit.saveutils.SaveHandler;


	@SuppressWarnings("serial")
	public class GuiDFrame extends JFrame implements ActionListener, ItemListener
	{
		/// Why.
	   private ResourceBundle bbbbbbb;
	   private JButton GameMode;
	   private JButton RecordMode;
	   private JButton MangaMode;
	   private JButton GameMetadata;
	   private JButton RecordMetadata;
	   private JButton PlayExportSong;
	   private JButton MangaMetadata;
	   private JButton ViewManga;
	   private JButton GameTest;
	   private JLabel SaveHeader;
	   private JLabel GameHeader;
	   private JLabel RecordHeader;
	   private JLabel MangaHeader;
	   private JButton OpenFile;
	   private JButton UnlockMedals;
	   private JButton UnlockGames;
	   private JButton UnlockRecords;
	   private JButton UnlockManga;
	   private JButton DumpAll;
	   private JButton TagAll;
	   private JButton DumpAllObjects;
	   private JButton PlayGameBGM;
	   private JFrame window;
	   private JPanel miolist;
	   private JFrame mioframe;
	   private JList<String> j;
	   private JScrollPane mioscroll;
	   private JPopupMenu popup;
	   private JMenuItem menuItem;
	   private int savemode=-1;
	   private int position=-1;
	   private byte[] file;
	   private String name;
	   private SaveHandler s;
	   byte[] saved;
	   private int savetype=0;
	   int flipp=0;
	   public GuiDFrame(String nam, byte[] fil)
	   {
		   bbbbbbb=ResourceBundle.getBundle("com.xperia64.diyedit.resources.Resources", (Locale.getDefault().getLanguage().equals("zh")||Globals.forceChina)?Locale.CHINA:Locale.US);
		   name=nam;
		   file=fil;
		   s = new SaveHandler(nam);
	     getContentPane().setLayout(null);
	     setupGUI();
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   }
	   void setupGUI()
	   {
		Font f = new Font(Font.SANS_SERIF, 3, 10);
		SaveHeader = new JLabel();
		SaveHeader.setLocation(45,20);
		SaveHeader.setSize(150,20);
		SaveHeader.setText((String) bbbbbbb.getObject("saveToolsKey")); 
		getContentPane().add(SaveHeader);
		OpenFile = new JButton();
		OpenFile.setSize(100,20);
		OpenFile.setLocation(0, 0);
		OpenFile.setFont(f);
		OpenFile.setText((String) bbbbbbb.getObject("openFileKey"));
		OpenFile.addActionListener(this);
		getContentPane().add(OpenFile);
		GameMode = new JButton();
		GameMode.setLocation(0,50);
		GameMode.setSize(150,20);
		GameMode.setText((String) bbbbbbb.getObject("modifyGamesKey")); 
		GameMode.setFont(f);
		GameMode.addActionListener(this);
		GameMode.setEnabled(false);
		getContentPane().add(GameMode);
		RecordMode = new JButton();
		RecordMode.setLocation(0,80);
		RecordMode.setSize(150,20);
		RecordMode.setText((String) bbbbbbb.getObject("modifyRecordsKey")); 
		RecordMode.setFont(f);
		RecordMode.addActionListener(this);
		RecordMode.setEnabled(false);
		getContentPane().add(RecordMode);
		MangaMode = new JButton();
		MangaMode.setLocation(0,110);
		MangaMode.setSize(150,20);
		MangaMode.setText((String) bbbbbbb.getObject("modifyMangaKey")); 
		MangaMode.setFont(f);
		MangaMode.addActionListener(this);
		MangaMode.setEnabled(false);
		getContentPane().add(MangaMode);
		UnlockMedals=new JButton();
		UnlockMedals.setSize(150, 20);
		UnlockMedals.setLocation(0,140);
		UnlockMedals.setText((String) bbbbbbb.getObject("unlockDSMedalsKey")); 
		UnlockMedals.setFont(f);
		UnlockMedals.addActionListener(this);
		UnlockMedals.setEnabled(false);
		getContentPane().add(UnlockMedals);
		UnlockGames=new JButton();
		UnlockGames.setSize(150, 20);
		UnlockGames.setLocation(0, 170);
		UnlockGames.setText((String) bbbbbbb.getObject("unlockDSGamesKey")); 
		UnlockGames.setFont(f);
		UnlockGames.addActionListener(this);
		UnlockGames.setEnabled(false);
		getContentPane().add(UnlockGames);
		UnlockRecords=new JButton();
		UnlockRecords.setSize(150, 20);
		UnlockRecords.setLocation(0,200);
		UnlockRecords.setText((String) bbbbbbb.getObject("unlockDSRecordsKey")); 
		UnlockRecords.setFont(f);
		UnlockRecords.addActionListener(this);
		UnlockRecords.setEnabled(false);
		getContentPane().add(UnlockRecords);
		UnlockManga=new JButton();
		UnlockManga.setSize(150, 20);
		UnlockManga.setLocation(0,230);
		UnlockManga.setText((String) bbbbbbb.getObject("unlockDSMangaKey")); 
		UnlockManga.setFont(f);
		UnlockManga.addActionListener(this);
		UnlockManga.setEnabled(false);
		getContentPane().add(UnlockManga);
		DumpAll = new JButton();
		DumpAll.setLocation(100,0);
		DumpAll.setSize(100,20);
		DumpAll.setText("Dump All...");
		DumpAll.setFont(f);
		DumpAll.addActionListener(this);
		DumpAll.setEnabled(false);
		getContentPane().add(DumpAll);
		TagAll = new JButton();
		TagAll.setLocation(200,0);
		TagAll.setSize(100,20);
		TagAll.setText("Tag All...");
		TagAll.setFont(f);
		TagAll.addActionListener(this);
		TagAll.setEnabled(true);
		getContentPane().add(TagAll);
		DumpAllObjects = new JButton();
		DumpAllObjects.setLocation(160,140);
		DumpAllObjects.setSize(150,20);
		DumpAllObjects.setText("Dump All Objects...");
		DumpAllObjects.setFont(f);
		DumpAllObjects.addActionListener(this);
		DumpAllObjects.setEnabled(false);
		getContentPane().add(DumpAllObjects);
		if(file.length==33554432||file.length==33566720||file.length==33554554||file.length==4719808||file.length==6438592)
		{
			savetype|=1;
			GameMode.setEnabled(true);
			DumpAll.setEnabled(true);
		}
		if(file.length==33554432||file.length==33566720||file.length==33554554||file.length==591040||file.length==6438592)
		{
			savetype|=2;
			RecordMode.setEnabled(true);
			DumpAll.setEnabled(true);
		}
		if(file.length==33554432||file.length==33566720||file.length==33554554||file.length==1033408||file.length==6438592)
		{
			savetype|=4;
			MangaMode.setEnabled(true);
			DumpAll.setEnabled(true);
		}
		if(file.length==33554432||file.length==33554554||file.length==33566720)
		{
			UnlockMedals.setEnabled(true);
			UnlockGames.setEnabled(true);
			UnlockRecords.setEnabled(true);
			UnlockManga.setEnabled(true);
		}
		GameHeader = new JLabel();
		GameHeader.setLocation(200,20);
		GameHeader.setSize(150,20);
		GameHeader.setText((String) bbbbbbb.getObject("gameToolsKey")); 
		getContentPane().add(GameHeader);
		GameMetadata = new JButton();
		GameMetadata.setLocation(160,50);
		GameMetadata.setSize(150,20);
		GameMetadata.setText((String) bbbbbbb.getObject("editGameMetadataKey")); 
		GameMetadata.setFont(f);
		GameMetadata.addActionListener(this);
		if(file.length!=65536)
		{
			GameMetadata.setEnabled(false);
		}
		getContentPane().add(GameMetadata);
		RecordHeader = new JLabel();
		RecordHeader.setLocation(355,20);
		RecordHeader.setSize(150,20);
		RecordHeader.setText((String) bbbbbbb.getObject("recordToolsKey")); 
		getContentPane().add(RecordHeader);
		RecordMetadata = new JButton();
		RecordMetadata.setLocation(320,50);
		RecordMetadata.setSize(150,20);
		RecordMetadata.setText((String) bbbbbbb.getObject("editRecordMetadataKey")); 
		RecordMetadata.setFont(f);
		RecordMetadata.addActionListener(this);
		RecordMetadata.setEnabled(false);
		getContentPane().add(RecordMetadata);
		PlayExportSong = new JButton();
		PlayExportSong.setLocation(320,80);
		PlayExportSong.setSize(150,20);
		PlayExportSong.setText((String) bbbbbbb.getObject("midiKey")); 
		PlayExportSong.setFont(f);
		PlayExportSong.addActionListener(this);
		PlayExportSong.setEnabled(false);
		getContentPane().add(PlayExportSong);

		if(file.length==8192)
		{
			RecordMetadata.setEnabled(true);
			PlayExportSong.setEnabled(true);
		}
		MangaHeader = new JLabel();
		MangaHeader.setLocation(510,20);
		MangaHeader.setSize(150,20);
		MangaHeader.setText((String) bbbbbbb.getObject("mangaToolsKey")); 
		getContentPane().add(MangaHeader);
		MangaMetadata = new JButton();
		MangaMetadata.setLocation(480,50);
		MangaMetadata.setSize(150,20);
		MangaMetadata.setText((String) bbbbbbb.getObject("editMangaMetadataKey")); 
		MangaMetadata.setFont(f);
		MangaMetadata.addActionListener(this);
		MangaMetadata.setEnabled(false);
		getContentPane().add(MangaMetadata);
		ViewManga = new JButton();
		ViewManga.setLocation(480,80);
		ViewManga.setSize(150,20);
		ViewManga.setText((String) bbbbbbb.getObject("viewMangaKey")); 
		ViewManga.setFont(f);
		ViewManga.addActionListener(this);
		ViewManga.setEnabled(false);
		getContentPane().add(ViewManga);
		GameTest = new JButton();
		GameTest.setLocation(160,80);
		GameTest.setSize(150,20);
		GameTest.setText("View Game BG"); 
		GameTest.setFont(f);
		GameTest.addActionListener(this);
		GameTest.setEnabled(false);
		getContentPane().add(GameTest);
		PlayGameBGM = new JButton();
		PlayGameBGM.setLocation(160,110);
		PlayGameBGM.setSize(150,20);
		PlayGameBGM.setText("Play Game BGM"); 
		PlayGameBGM.setFont(f);
		PlayGameBGM.addActionListener(this);
		PlayGameBGM.setEnabled(false);
		getContentPane().add(PlayGameBGM);
		if(file.length==14336)
		{
			MangaMetadata.setEnabled(true);
			ViewManga.setEnabled(true);
		}
		if(file.length==65536)
		{
			GameTest.setEnabled(true);
			PlayGameBGM.setEnabled(true);
			DumpAllObjects.setEnabled(true);
		}
		setLocation(50,50);
		setTitle(name.substring(name.lastIndexOf(File.separator)+1));
		setSize(650,300);
		setVisible(true);
		setResizable(false);
		
	   }
	   public void actionPerformed(ActionEvent e) {
		   
	        if (e.getSource() == GameMetadata&&!Globals.MetadataShown) {
	        	Globals.MetadataShown=true;
	        	Globals.MetadataMode=0;
	        	new MetadataFrame(name);
	        } else if (e.getSource() == RecordMetadata&&!Globals.MetadataShown) {
	        	Globals.MetadataShown=true;
	        	Globals.MetadataMode=1;
	            new MetadataFrame(name);
	        }else if (e.getSource() == MangaMetadata&&!Globals.MetadataShown)
	        {
	        	Globals.MetadataShown=true;
	        	Globals.MetadataMode=2;
	        	new MetadataFrame(name);
	        }else if(e.getSource() == PlayExportSong&&!Globals.playing){
	        	new MusicPlayer(file, false);
	        	
	        }else if(e.getSource()==ViewManga){
	        drawManga();
	        }else if(e.getSource()==OpenFile)
	        {
	        	this.setVisible(false);
	        	if(window!=null)
	        	{
	        	window.setVisible(false);
	        	window.dispose();
	        	}
	        	if(mioframe!=null)
	        	{
	        		mioframe.setVisible(false);
	        		mioframe.dispose();
	        	}
	        	s.release();
	        	saved=null;
	        	file=null;
	        	s=null;
	        	name=null;
	        	System.gc();
	        	this.dispose();
	        	FileChooser f = new FileChooser(true, true, false, false);
	        	if(f.getFile()!=null)
	        	new GuiDFrame(f.getFile().getAbsolutePath(), FileByteOperations.read(f.getFile().getAbsolutePath()));
	        	else{
	        		new Warning((String) bbbbbbb.getObject("fileWarningKey")); 
	        		System.exit(0);}
	        }else if(e.getSource()==GameMode)
	        {
	        	savemode=0;
	        	ArrayList<byte[]> b = s.getMios(0);
	        	GameMetadata g;
	        	if(b==null)
	        	{
	        		System.exit(0);
	        	}
	        	saved = new byte[b.size()];
	        	String[] titles = new String[b.size()];
	        	for(int i = 0; i<b.size(); i++)
	        	{
	        		saved[i]=1;
	        		if(b.get(i)!=null)
	        		{
	        			g = new GameMetadata(b.get(i));
		        		titles[i]=(Integer.toString(i+1)+". "+g.getName()+" - "+g.getCreator());
	        		}else{
	        			titles[i]=(Integer.toString(i+1)+(String) bbbbbbb.getObject("dotEmptyKey")); 
	        		}
	        		
	        	}
	        	
	        	j = new JList<String>(titles);
	        	popup = new JPopupMenu();
	            menuItem = new JMenuItem((String) bbbbbbb.getObject("extractKey")); 
	            menuItem.addActionListener(this);
	            popup.add(menuItem);
	            menuItem = new JMenuItem((String) bbbbbbb.getObject("injectKey")); 
	            menuItem.addActionListener(this);
	            popup.add(menuItem);
	            menuItem = new JMenuItem((String) bbbbbbb.getObject("deleteKey")); 
	            popup.add(menuItem);
	            menuItem.addActionListener(this);
	            menuItem = new JMenuItem((String) bbbbbbb.getObject("saveChangesKey")); 
	            menuItem.addActionListener(this);
	            popup.add(menuItem);
	        	mioframe = new JFrame();
	        	miolist = new JPanel();
	        	mioscroll = new JScrollPane(miolist);
	        	mioframe.add(mioscroll);
	        	miolist.add(j);
	        	j.addMouseListener(new MouseAdapter()
	            {
	                public void mousePressed(MouseEvent e)
	                {
	                    if ( SwingUtilities.isRightMouseButton(e) )
	                    {
	                    	position = j.locationToIndex(e.getPoint());
	                        j.setSelectedIndex(j.locationToIndex(e.getPoint()));
	                        popup.show(e.getComponent(),
	                                e.getX(), e.getY());
	                    }
	                }
	             });
			    mioframe.setTitle((String) bbbbbbb.getObject("gamesKey")); 
			    mioframe.setSize(300,500);
			    mioframe.setResizable(true);
			    mioframe.setVisible(true);
			    mioframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        	
	        }else if(e.getSource()==GameTest)
	        {
	        	drawBG();
	   		}else if(e.getSource()==RecordMode)
	        {
	        	savemode=1;
	        	ArrayList<byte[]> b = s.getMios(1);
	        	GameMetadata g;
	        	if(b==null)
	        	{
	        		System.exit(0);
	        	}
	        	saved=new byte[b.size()];
	        	String[] titles = new String[b.size()];
	        	for(int i = 0; i<b.size(); i++)
	        	{
	        		saved[i]=1;
	        		if(b.get(i)!=null)
	        		{
	        			g = new GameMetadata(b.get(i));
		        		titles[i]=(Integer.toString(i+1)+". "+g.getName()+" - "+g.getCreator());
	        		}else{
	        			titles[i]=(Integer.toString(i+1)+(String) bbbbbbb.getObject("dotEmptyKey")); 
	        		}
	        		
	        	}
	        	
	        	j = new JList<String>(titles);
	        	popup = new JPopupMenu();
	            menuItem = new JMenuItem((String) bbbbbbb.getObject("extractKey")); 
	            menuItem.addActionListener(this);
	            popup.add(menuItem);
	            menuItem = new JMenuItem((String) bbbbbbb.getObject("injectKey")); 
	            menuItem.addActionListener(this);
	            popup.add(menuItem);
	            menuItem = new JMenuItem((String) bbbbbbb.getObject("deleteKey")); 
	            popup.add(menuItem);
	            menuItem.addActionListener(this);
	            menuItem = new JMenuItem((String) bbbbbbb.getObject("saveChangesKey")); 
	            menuItem.addActionListener(this);
	            popup.add(menuItem);
	        	mioframe = new JFrame();
	        	miolist = new JPanel();
	        	mioscroll = new JScrollPane(miolist);
	        	mioframe.add(mioscroll);
	        	miolist.add(j);
	        	j.addMouseListener(new MouseAdapter()
	            {
	                public void mousePressed(MouseEvent e)
	                {
	                	
	                    //System.out.println(e);
	                    if ( SwingUtilities.isRightMouseButton(e) )
	                    {
	                    	position = j.locationToIndex(e.getPoint());
	                        j.setSelectedIndex(j.locationToIndex(e.getPoint()));
	                        popup.show(e.getComponent(),
	                                e.getX(), e.getY());
	                    }
	                }
	             });
			    mioframe.setTitle((String) bbbbbbb.getObject("recordsKey")); 
			    mioframe.setSize(300,500);
			    mioframe.setResizable(true);
			    mioframe.setVisible(true);
			    mioframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        	
	        }else if(e.getSource()==MangaMode)
	        {
	        	
	        	savemode=2;
	        	ArrayList<byte[]> b = s.getMios(2);
	        	GameMetadata g;
	        	if(b==null)
	        	{
	        		System.exit(0);
	        	}
	        	saved=new byte[b.size()];
	        	String[] titles = new String[b.size()];
	        	for(int i = 0; i<b.size(); i++)
	        	{
	        		saved[i]=1;
	        		if(b.get(i)!=null)
	        		{
	        			g = new GameMetadata(b.get(i));
		        		titles[i]=(Integer.toString(i+1)+". "+g.getName()+" - "+g.getCreator());
	        		}else{
	        			titles[i]=(Integer.toString(i+1)+(String) bbbbbbb.getObject("dotEmptyKey")); 
	        		}
	        		
	        	}
	        	
	        	j = new JList<String>(titles);
	        	popup = new JPopupMenu();
	            menuItem = new JMenuItem((String) bbbbbbb.getObject("extractKey")); 
	            menuItem.addActionListener(this);
	            popup.add(menuItem);
	            menuItem = new JMenuItem((String) bbbbbbb.getObject("injectKey")); 
	            menuItem.addActionListener(this);
	            popup.add(menuItem);
	            menuItem = new JMenuItem((String) bbbbbbb.getObject("deleteKey")); 
	            popup.add(menuItem);
	            menuItem.addActionListener(this);
	            menuItem = new JMenuItem((String) bbbbbbb.getObject("saveChangesKey")); 
	            menuItem.addActionListener(this);
	            popup.add(menuItem);
	        	mioframe = new JFrame();
	        	miolist = new JPanel();
	        	mioscroll = new JScrollPane(miolist);
	        	mioframe.add(mioscroll);
	        	miolist.add(j);
	        	j.addMouseListener(new MouseAdapter()
	            {
	                public void mousePressed(MouseEvent e)
	                {
	                	
	                    //System.out.println(e);
	                    if ( SwingUtilities.isRightMouseButton(e) )
	                    {
	                    	position = j.locationToIndex(e.getPoint());
	                        j.setSelectedIndex(j.locationToIndex(e.getPoint()));
	                        popup.show(e.getComponent(),
	                                e.getX(), e.getY());
	                    }
	                }
	             });
			    mioframe.setTitle((String) bbbbbbb.getObject("mangaKey")); 
			    mioframe.setSize(300,500);
			    mioframe.setResizable(true);
			    mioframe.setVisible(true);
			    mioframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        	
	        }else if(e.getSource()==UnlockMedals)
	        {
	        	FileByteOperations.write(s.unlockMedals(),name);
	        }else if(e.getSource()==UnlockGames)
	        {
	        	FileByteOperations.write(s.unlockGames(),name);
	        }else if(e.getSource()==UnlockRecords)
	        {
	        	FileByteOperations.write(s.unlockRecords(),name);
	        }else if(e.getSource()==UnlockManga)
	        {
	        	FileByteOperations.write(s.unlockManga(),name);
	        	
	        }else if(e.getSource()==PlayGameBGM)
	        {
	        	new MusicPlayer(file, true);
	        }else if(e.getSource()==DumpAllObjects)
	        {
	        	FileChooser ffff = new FileChooser(false, true, false, true);
	        	if(ffff.getFile()!=null)
	        	{
	        		if(ffff.getFile().exists()&&ffff.getFile().isDirectory())
	        		{
	        			System.out.println(ffff.getFile().getAbsolutePath()+File.separator);
	        	GameEdit ge = new GameEdit(file);
	        	File fout = new File(ffff.getFile().getAbsolutePath()+File.separator+"names.txt");
	        	FileOutputStream fos = null;
				try
				{
					fos = new FileOutputStream(fout);
				} catch (FileNotFoundException e3)
				{
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
	         
	        	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	        	BufferedImage e8 = new BufferedImage(192,128, BufferedImage.TYPE_INT_RGB);
	        	int x1=0, y1=0;
	        	while(x1*y1<192*128)
	        	{
	        		e8.setRGB(x1, y1, GameEdit.palette[ge.getBackgroundPixel(x1, y1)]);
	        		if(++x1>=192)
					{
						x1=0;
						y1++;
					}
					if(y1>=128)
						break;
	        	}
	        	try
				{
					ImageIO.write(e8, "png", new File(ffff.getFile().getAbsolutePath()+File.separator+"bg.png"));
				} catch (IOException e2)
				{
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
	        	for(int i = 0; i<15; i++)
	        	{
	        		GameObject oo = ge.index.getGameObject(i);
	        		String nerm = oo.getObjectTitle();
	        		if(oo.isReal())
	        		{
	        			int objectSize = 1 + oo.getObjectSize();
	        			objectSize*=16;
	        			BufferedImage[][] arts = new BufferedImage[4][4];
	        			String[] artsNames = new String[4];
	        			for(int o = 0; o<4; o++)
	        			{
	        				GameArt artz = oo.getGameArt(o);
	        				if(artz.isReal())
	        				{
	        					artsNames[o] = artz.getArtTitle();
	        					System.out.println(artsNames[o]+" "+artz.getArtFrames());
	        					for(int q = 0; q<artz.getArtFrames(); q++)
	        					{
	        						if(artsNames[o]!=null)
	        						{
	        						BufferedImage tmp = new BufferedImage(objectSize, objectSize, BufferedImage.TYPE_INT_ARGB);
	        						int x = 0, y = 0;
	        						while(x*y<objectSize*objectSize)
	        						{
	        							int cooc = ge.getArtPixel(i, o, q, x, y);
	        							int color = GameEdit.palette[cooc];
	        							tmp.setRGB(x, y, color);
	        							if(++x>=objectSize)
	        							{
	        								x=0;
	        								y++;
	        							}
	        							if(y>=objectSize)
	        								break;
	        						}
	        						arts[o][q] = tmp;
	        						}
	        					}
	        				}
	        			}
	        			for(int o = 0; o<4; o++)
	        			{
	        				if(artsNames[o]!=null)
	        				{
	        					String line = String.format("(%d-%d-X): %s - %s",i,o,nerm,artsNames[o]);
    						try
							{
								bw.write(line);
								bw.newLine();
							} catch (IOException e2)
							{
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
	        				}
	        				for(int q = 0; q<4; q++)
	        				{
	        					//arts[o][q].
	        					if(arts[o][q]!=null)
	        					{
	        					try
								{
	        						
	        						String name = String.format(ffff.getFile().getAbsolutePath()+File.separator+"%d-%d-%d.png",i,o,q);
	        						//name = name.replaceAll("[^a-zA-Z0-9.-]", "_");
	        						
									ImageIO.write(arts[o][q], "png", new File(name));
								} catch (IOException e1)
								{
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	        					}
	        				}
	        			}
	        		}
	        	}
	        	try
				{
					bw.close();
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        		}
	        	}
	        }
	        else if(e.getSource() instanceof JMenuItem)
	        {
	        	
	        	String txt = ((JMenuItem)(e.getSource())).getText();
	        	int mod=-1;
	        	if(txt.equals((String) bbbbbbb.getObject("extractKey")))
	        	{
	        		mod=0;
	        	}else if(txt.equals((String) bbbbbbb.getObject("injectKey")))
	        	{
	        		mod=1;
	        	}else if(txt.equals((String) bbbbbbb.getObject("deleteKey")))
	        	{
	        		mod=2;
	        	}else if(txt.equals((String) bbbbbbb.getObject("saveChangesKey")))
	        	{
	        		mod=3;
	        	}
	        	switch(mod)
	        	{
	        	case 0:  
	        		
	        	    FileChooser f = new FileChooser(false, false, false, false);
	        	    if(f.getFile()!=null)
	        	    {
	        	    String namez=f.getFile().getAbsolutePath();
	        	    if(!namez.toLowerCase(Locale.US).endsWith(".mio"))
	        	    {
	        	    	namez+=".mio";
	        	    }
	        		FileByteOperations.write(s.getMio(savemode, position), namez);
	        	    }else{
	        	    	new Warning((String) bbbbbbb.getObject("mioFileWarningKey"));
	        	    	miolist.add(j);
		        		miolist.revalidate();
			        	miolist.repaint();
	        	    }
	        		break;
	        	case 1:  
	        		FileChooser f2 = new FileChooser(false, true, false, false);
	        		if(f2.getFile()!=null)
	        		{
	        		s.setMio(f2.getFile().getAbsolutePath(), position);
	        		miolist.remove(j);
	        		ArrayList<byte[]> b = s.getMios(savemode);
	        		int size=-1;
	        		switch(savemode)
	        		{
	        		case 0:
	        			size=65536;
	        			break;
	        		case 1:
	        			size=8192;
	        			break;
	        		case 2:
	        			size=14336;
	        			break;
	        		}
		        	if(b==null)
		        	{
		        		new Warning((String) bbbbbbb.getObject("mioInjectWarningKey"));
		        		miolist.add(j);
		        		miolist.revalidate();
			        	miolist.repaint();
		        		break;
		        	}else if(f2.getFile().length()==0){
		        		new Warning((String) bbbbbbb.getObject("mioInjectWarningKey"));
		        		miolist.add(j);
		        		miolist.revalidate();
			        	miolist.repaint();
		        		break;
		        	}else if(f2.getFile().length()!=size)
		        	{
		        		new Warning((String) bbbbbbb.getObject("mioFileTypeWarningKey"));
		        		miolist.add(j);
		        		miolist.revalidate();
			        	miolist.repaint();
		        		break;
		        	}
		        	Metadata m;
		        	String[] titles = new String[b.size()];
		        	saved[position]=0;
		        	for(int i = 0; i<b.size(); i++)
		        	{
		        		if(b.get(i)!=null)
		        		{
		        			if(b.get(i)[0]==0)
		        			{
		        				titles[i]=(Integer.toString(i+1)+(String) bbbbbbb.getObject("dotEmptyKey")+((saved[i]==0)?" *"+(String) bbbbbbb.getObject("unsavedKey"):""));
		        			}else{
		        				m = new Metadata(b.get(i));
				        		titles[i]=(Integer.toString(i+1)+". "+m.getName()+" - "+m.getCreator()+ ((saved[i]==0)?" *"+(String) bbbbbbb.getObject("unsavedKey"):""));
		        			}
		        		}else{
		        			titles[i]=(Integer.toString(i+1)+(String) bbbbbbb.getObject("dotEmptyKey")+((saved[i]==0)?" *"+(String) bbbbbbb.getObject("unsavedKey"):""));
		        		}
		        		
		        	}
		        	j=new JList<String>(titles);
		        	miolist.add(j);
		        	j.addMouseListener(new MouseAdapter()
		            {
		                public void mousePressed(MouseEvent e)
		                {
		                	
		                    //System.out.println(e);
		                    if ( SwingUtilities.isRightMouseButton(e) )
		                    {
		                    	position = j.locationToIndex(e.getPoint());
		                        j.setSelectedIndex(j.locationToIndex(e.getPoint()));
		                        popup.show(e.getComponent(),
		                                e.getX(), e.getY());
		                    }
		                }
		             });
		        	miolist.revalidate();
		        	miolist.repaint();
	        		}else{
	        			new Warning((String) bbbbbbb.getObject("mioInjectWarningKey"));
	        			miolist.add(j);
		        		miolist.revalidate();
			        	miolist.repaint();
	        		}
	        		break;
	        	case 2: 
	        		s.deleteMio(savemode, position);
	        		miolist.remove(j);
	        		ArrayList<byte[]> b2 = s.getMios(savemode);
		        	if(b2==null)
		        	{
		        		System.exit(0);
		        	}
		        	String[] titles2 = new String[b2.size()];
		        	Metadata m2;
		        	saved[position]=0;
		        	for(int i = 0; i<b2.size(); i++)
		        	{
		        		if(b2.get(i)!=null)
		        		{
		        			if(b2.get(i)[0]==0)
		        			{
		        				titles2[i]=(Integer.toString(i+1)+(String) bbbbbbb.getObject("dotEmptyKey")+ ((saved[i]==0)?" *"+(String) bbbbbbb.getObject("unsavedKey"):"")); 
		        			}else{
		        				m2 = new Metadata(b2.get(i));
				        		titles2[i]=(Integer.toString(i+1)+". "+m2.getName()+" - "+m2.getCreator()+((saved[i]==0)?" *"+(String) bbbbbbb.getObject("unsavedKey"):""));
		        			}
		        		}else{
		        			titles2[i]=(Integer.toString(i+1)+(String) bbbbbbb.getObject("dotEmptyKey")+ ((saved[i]==0)?" *"+(String) bbbbbbb.getObject("unsavedKey"):"")); 
		        		}
		        		
		        	}
		        	j=new JList<String>(titles2);
		        	miolist.add(j);
		        	j.addMouseListener(new MouseAdapter()
		            {
		                public void mousePressed(MouseEvent e)
		                {
		                    if ( SwingUtilities.isRightMouseButton(e) )
		                    {
		                    	position = j.locationToIndex(e.getPoint());
		                        j.setSelectedIndex(j.locationToIndex(e.getPoint()));
		                        popup.show(e.getComponent(),
		                                e.getX(), e.getY());
		                    }
		                }
		             });
		        	miolist.revalidate();
		        	miolist.repaint();
	        		break;
	        	case 3: 
	        		s.saveChanges();
	        		miolist.remove(j);
	        		ArrayList<byte[]> b3 = s.getMios(savemode);
		        	if(b3==null)
		        	{
		        		System.exit(0);
		        	}
		        	String[] titles3 = new String[b3.size()];
		        	Metadata m3;
		        	for(int i = 0; i<b3.size(); i++)
		        	{
		        		saved[i]=1;
		        		if(b3.get(i)!=null)
		        		{
		        			if(b3.get(i)[0]==0)
		        			{
		        				titles3[i]=(Integer.toString(i+1)+(String) bbbbbbb.getObject("dotEmptyKey")); 
		        			}else{
		        				m3 = new Metadata(b3.get(i));
				        		titles3[i]=(Integer.toString(i+1)+". "+m3.getName()+" - "+m3.getCreator());
		        			}
		        		}else{
		        			titles3[i]=(Integer.toString(i+1)+(String) bbbbbbb.getObject("dotEmptyKey")); 
		        		}
		        		
		        	}
		        	j=new JList<String>(titles3);
		        	miolist.add(j);
		        	j.addMouseListener(new MouseAdapter()
		            {
		                public void mousePressed(MouseEvent e)
		                {
		                    if ( SwingUtilities.isRightMouseButton(e) )
		                    {
		                    	position = j.locationToIndex(e.getPoint());
		                        j.setSelectedIndex(j.locationToIndex(e.getPoint()));
		                        popup.show(e.getComponent(),
		                                e.getX(), e.getY());
		                    }
		                }
		             });
		        	miolist.revalidate();
		        	miolist.repaint();
	        		break;
	        	}
	        }else if(e.getSource()==DumpAll)
	        {
	        	ArrayList<Object> things= new ArrayList<Object>();
	        	if((savetype&1)!=0)
	        		things.add("Games");
	        	if((savetype&2)!=0)
	        		things.add("Records");
	        	if((savetype&4)!=0)
	        		things.add("Comics/Manga");
	        	
	        	things.add("Cancel");
	        	Object[] options = things.toArray();
	int n = JOptionPane.showOptionDialog(this,
	    "Dump which MIO",
	    "Dump MIO Files",
	    JOptionPane.YES_NO_CANCEL_OPTION,
	    JOptionPane.QUESTION_MESSAGE,
	    null,
	    options,
	    options[options.length-1]);
		Metadata m;
    	switch((String)options[n])
    	{
    	case "Games":
    		FileChooser f1 = new FileChooser(true, false, false, true);
    		if(f1.getFile()==null)
    		break;
    		String prefix1 = f1.getFile().getAbsolutePath();
    		ArrayList<byte[]> mios1 = s.getMios(0);
    		for(int i = 0; i<mios1.size(); i++)
    		{
    			if(mios1.get(i)!=null)
    			{
    				//ContentLetter+"-"+Person+"("+Brand+")- "+"("+Region+") ("+Date+") "+Title+".mio";
    				m = new Metadata(mios1.get(i));
    				DateTime date = new DateTime(2000, 1, 1, 0, 0, 0, 0);
    				date=date.plusDays(m.getTimestamp());
    				String name = String.format("%s-%s(%s)-%04d (%s) (%04d-%02d-%02d) %s.mio", "G", m.getCreator(), m.getBrand(), m.getSerial2(), (m.getRegion())?"J":"UE", date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), (m.getLocked()?"(#) ":"")+m.getName());
    				name=Globals.stripBadChars(name);
    				name=prefix1+File.separator+name;
    				//System.out.println(name);
    				FileByteOperations.write(mios1.get(i), name);
    			}
    		}
    		break;
    	case "Records":
    		FileChooser f2 = new FileChooser(true, false, false, true);
    		if(f2.getFile()==null)
    		break;
    		String prefix2 = f2.getFile().getAbsolutePath();
    		ArrayList<byte[]> mios2 = s.getMios(1);
    		for(int i = 0; i<mios2.size(); i++)
    		{
    			if(mios2.get(i)!=null)
    			{
    				//ContentLetter+"-"+Person+"("+Brand+")- "+"("+Region+") ("+Date+") "+Title+".mio";
    				m = new Metadata(mios2.get(i));
    				DateTime date = new DateTime(2000, 1, 1, 0, 0, 0, 0);
    				date=date.plusDays(m.getTimestamp());
    				String name = String.format("%s-%s(%s)-%04d (%s) (%04d-%02d-%02d) %s.mio", "R", m.getCreator(), m.getBrand(), m.getSerial2(), (m.getRegion())?"J":"UE", date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), (m.getLocked()?"(#) ":"")+m.getName());
    				name=name.replace('?', '¿').replace('/', '〳').replace('\\','〳').replace(':','：').replace('*', '★').replace('\"','\'').replace('＜','a').replace('＞','b').replace('|','l').replace('!','¡');;
    				name=prefix2+File.separator+name;
    				//System.out.println(name);
    				FileByteOperations.write(mios2.get(i), name);
    			}
    		}
    		break;
    	case "Comics/Manga":
    		FileChooser f3 = new FileChooser(true, false, false, true);
    		if(f3.getFile()==null)
    		break;
    		String prefix3 = f3.getFile().getAbsolutePath();
    		ArrayList<byte[]> mios3 = s.getMios(2);
    		for(int i = 0; i<mios3.size(); i++)
    		{
    			if(mios3.get(i)!=null)
    			{
    				//ContentLetter+"-"+Person+"("+Brand+")- "+"("+Region+") ("+Date+") "+Title+".mio";
    				m = new Metadata(mios3.get(i));
    				DateTime date = new DateTime(2000, 1, 1, 0, 0, 0, 0);
    				date=date.plusDays(m.getTimestamp());
    				String name = String.format("%s-%s(%s)-%04d (%s) (%04d-%02d-%02d) %s.mio", "G", m.getCreator(), m.getBrand(), m.getSerial2(), (m.getRegion())?"J":"UE", date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), (m.getLocked()?"(#) ":"")+m.getName());
    				name=name.replace('?', '¿').replace('/', '〳').replace('\\','〳').replace(':','：').replace('*', '★').replace('\"','\'').replace('<','＜').replace('>','＞').replace('|','l').replace('!','¡');;
    				name=prefix3+File.separator+name;
    				//System.out.println(name);
    				FileByteOperations.write(mios3.get(i), name);
    			}
    		}
    		break;
    	}
	        }else if(e.getSource()==TagAll)
	        {
	        	FileChooser ffff = new FileChooser(false, true, false, true);
	        	if(ffff.getFile()!=null)
	        	{
	        		if(ffff.getFile().exists()&&ffff.getFile().isDirectory())
	        		{
	        	ArrayList<String> mios1 = new ArrayList<String>();
	    		for(File ff : ffff.getFile().listFiles())
	    		{
	    			if((!ff.getName().startsWith("G")&&!ff.getName().startsWith("R")&&!ff.getName().startsWith("M"))&&ff.isFile()&&ff.getName().toLowerCase(Locale.US).endsWith(".mio"))
	    				mios1.add(ff.getAbsolutePath());
	    		}
	    		Metadata m;
	        				//ContentLetter+"-"+Person+"("+Brand+")- "+"("+Region+") ("+Date+") "+Title+".mio";
	    		for(String ss : mios1)
	    		{
	    			File fff = new File(ss);
	    			String key="";
	    			switch((int)fff.length())
	    			{
	    			case 65536:
	    				key="G";
	    				break;
	    			case 8192:
	    				key="R";
	    				break;
	    			case 14336:
	    				key="M";
	    				break;
	    			}
	    			
	    			m = new Metadata(ss);
	    			DateTime date = new DateTime(2000, 1, 1, 0, 0, 0, 0);
	    			date=date.plusDays(m.getTimestamp());
	    			String name = String.format("%s-%s(%s)-%04d (%s) (%04d-%02d-%02d) %s.mio", key, m.getCreator(), m.getBrand(), m.getSerial2(), (m.getRegion())?"J":"UE", date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), (m.getLocked()?"(#) ":"")+m.getName());
	    			name=name.replace('?', '¿').replace('/', '〳').replace('\\','〳').replace(':','：').replace('*', '★').replace('\"','\'').replace('<','＜').replace('>','＞').replace('|','l').replace('!','¡');
	    			File out = new File(ss.substring(0,ss.lastIndexOf(File.separator)+1)+name);
	    			
	    			fff.renameTo(out);	
	    		}
	    		}
	        	}
	        }
	        else{
	        	System.out.println("Hax "+e.getSource().toString());
	        }
	   }
	    
	 
	    public void itemStateChanged(ItemEvent e)  {
	 
	        @SuppressWarnings("unused")
			Object source = e.getItemSelectable();

	    }
	    private void drawBG()
	    {
	    	
	    	final BackgroundPreviewer tt = new BackgroundPreviewer(file);
	    	window = new JFrame();
			 window.setLayout(new GridLayout(1,3));
			    window.setBounds(30, 30, 1000, 1000);
			    Container container = window.getContentPane();
		        ArrayList < JComponent > components = new ArrayList < JComponent >();
		        components.add( tt );
		        container.add(tt);
		        window.pack();
		        //window.add(b);
		        window.setTitle(new MangaMetadata(file).getName());
		        window.setSize(400,300);
		        window.setResizable(false);
			    window.setVisible(true);
			    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			    int delay = 250; //milliseconds
			    if(tt.numFrames>1)
			    {
			    	ActionListener taskPerformer = new ActionListener() {
				        public void actionPerformed(ActionEvent evt) {
				            //...Perform a task...
				        	SwingUtilities.invokeLater(new Runnable()
				        	{
				        	    public void run()
				        	    {
				        	    	tt.flipflop=flipp;
				        	        window.repaint();
				        	    	flipp+=1;
				        	    	flipp=(flipp>=tt.numFrames)?0:flipp;			        	    	
				        	    }
				        	}); 
				        }
				    };
				    new Timer(delay, taskPerformer).start();
			    }	    
	    }
	    private void drawManga()
	    {
	    	MangaPreviewer t = new MangaPreviewer(0,file);
	    	MangaPreviewer t1 = new MangaPreviewer(1,file);
	    	MangaPreviewer t2 = new MangaPreviewer(2,file);
	    	MangaPreviewer t3 = new MangaPreviewer(3,file);
	    	window = new JFrame();
			 window.setLayout(new GridLayout(1,3));
			    window.setBounds(30, 30, 1000, 1000);
			    Container container = window.getContentPane();
		        ArrayList < JComponent > components = new ArrayList < JComponent >();
		        components.add( t );
		        container.add(t);
		        components.add( t1 );
	            container.add(t1);
	            components.add( t2 );
	            container.add(t2);
	            components.add( t3 );
	            container.add(t3);
		        window.pack();
		        //window.add(b);
		        window.setTitle(new MangaMetadata(file).getName());
		        window.setSize(781,157);
		        window.setResizable(false);
			    window.setVisible(true);
			    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    }
	    public static boolean isInteger(String s) {
		    try { 
		        Integer.parseInt(s); 
		    } catch(NumberFormatException e) { 
		        return false; 
		    }
		    // only got here if we didn't return false
		    return true;
		}
	}
	@SuppressWarnings("serial")
	class MangaPreviewer extends JComponent
	{
		int i;
		byte[] bb;
		public MangaPreviewer(int p, byte[] b)
		{
			i=p;
			bb=b;
		}
		//192x128
		public void paint(Graphics g) {
				int x=0;
				int y=0;
				boolean done=false;
				MangaEdit e2 = new MangaEdit(bb);
				while(!done)
				{
					if(e2.getPixel((byte) i, x, y))
					{
						g.setColor(new Color(0,0,0));
					}else{
						g.setColor(new Color(255,255,255));
					}
					g.drawRect(x,y,1,1);
					
					x++;
					if(x>191)
					{
						y+=1;
						x=0;	
					}
					if(y>127)
					{
						done=true;
						break;
					}
				}		
		  }		
	}
	@SuppressWarnings("serial")
	class BackgroundPreviewer extends JComponent
	{
		boolean forceBG=true;
		byte[] bb;
		public int flipflop=0;
		public int numFrames;
		GameEdit e2;
		int objnum=0;
		public BackgroundPreviewer(byte[] b)
		{
			bb=b;
			e2 = new GameEdit(bb);
			
			numFrames=1;//e2.getGameIndex().getGameObject(objnum).getGameArt(0).getArtFrames();
		}
		//192x128
		public void paint(Graphics g) {


				int x=0;
				int y=0;
				boolean done=false;
				
				//GameObject obj = e2.objectAtIndex(0);
				//System.out.println(obj.getObjectTitle());
				//System.out.println(e2.getGameIndex().getFreeSlots());
				while(!done)
				{
					if(!forceBG)
					{
						int c = e2.getArtPixel(objnum, 0, flipflop, x, y);
						GameObject go = e2.index.getGameObject(objnum);
						int size = (go.getObjectSize()+1)*16;
						if(c!=0)
						{
						g.setColor(new Color(r(c),g(c),b(c)));
						}else{
							g.setColor(new Color(0,0,0,0));
						}
						g.drawRect(2*x,2*y,1,1);
						x++;
						if(x>size-1)
						{
							y+=1;
							x=0;	
						}
						if(y>size-1)
						{
							done=true;
							break;
						}
					}else{
						int c = e2.getBackgroundPixel(x,y);
						if(c!=0)
						{
						g.setColor(new Color(r(c),g(c),b(c)));
						}else{
							g.setColor(new Color(0,0,0,0));
						}
						g.drawRect(2*x,2*y,1,1);
						x++;
						if(x>191)
						{
							y+=1;
							x=0;	
						}
						if(y>127)
						{
							done=true;
							break;	
						}
					}
				}
		  }
		public int r(int b)
		  {
			 switch(b)
			 {
			 case 1: return 0;
			 case 2: return 255;
			 case 3: return 255;
			 case 4: return 198;
			 case 5: return 255;
			 case 6: return 206;
			 case 7: return 16;
			 case 8: return 41;
			 case 9: return 8;
			 case 10: return 115;
			 case 11: return 255;
			 case 12: return 128;
			 case 13: return 192;
			 case 14: return 255;
			 default: return 0;
			 }
		  }
		  public int g(int b)
		  {
			  switch(b)
				 {
				 case 1: return 0;
				 case 2: return 223;
				 case 3: return 174;
				 case 4: return 73;
				 case 5: return 0;
				 case 6: return 105;
				 case 7: return 199;
				 case 8: return 105;
				 case 9: return 150;
				 case 10: return 215;
				 case 11: return 255;
				 case 12: return 128;
				 case 13: return 192;
				 case 14: return 255;
				 default: return 0;
				 }
		  }
		  public int b(int b)
		  {
			  switch(b)
				 {
				 case 1: return 0;
				 case 2: return 156;
				 case 3: return 49;
				 case 4: return 0;
				 case 5: return 0;
				 case 6: return 239;
				 case 7: return 206;
				 case 8: return 198;
				 case 9: return 82;
				 case 10: return 57;
				 case 11: return 90;
				 case 12: return 128;
				 case 13: return 192;
				 case 14: return 255;
				 default: return 0;
				 }
		  }
	}
	
