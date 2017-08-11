package com.xperia64.diyedit.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import com.xperia64.diyedit.FileByteOperations;
import com.xperia64.diyedit.Globals;
import com.xperia64.diyedit.editors.GameEdit;
import com.xperia64.diyedit.metadata.Checksums;
import com.xperia64.diyedit.metadata.GameMetadata;
import com.xperia64.diyedit.metadata.MangaMetadata;
import com.xperia64.diyedit.metadata.Metadata;
import com.xperia64.diyedit.metadata.RecordMetadata;

import org.joda.time.DateTime;
@SuppressWarnings("serial")
public class MetadataFrame extends JFrame implements ActionListener, ItemListener {

	private ResourceBundle bbbbbbb;
	// Common:
	String filz;
	byte[] file;
	private JTextField Name;
	private JLabel NameHeader;
	private JTextField Description;
	private JLabel DescriptionHeader;
	private JTextField Brand;
	private JLabel BrandHeader;
	private JTextField Creator;
	private JLabel CreatorHeader;
	private JLabel SerialHeader;
	private JTextField Serial1;
	private JLabel Dash1;
	private JTextField Serial2;
	private JLabel Dash2;
	private JTextField Serial3;
	private JCheckBox Lock;
	private JComboBox<Integer> ItemColor;
	private JLabel ItemColorHeader;
	private JComboBox<Integer> Logo;
	private JLabel LogoHeader;
	private JComboBox<Integer> LogoColor;
	private JLabel LogoColorHeader;
	private JButton Apply;
	// Game Specific:
	private JTextField Command;
	private JLabel CommandHeader;
	private JComboBox<String> GameLength;
	private JLabel LengthHeader;
	private JComboBox<Integer> CartType;
	private JLabel CartHeader;
	private GamePreview DrawPreview;
	// Record Specific:
	private JComboBox<Integer> RecordType;
	private JLabel RecordHeader;
	private JTextField Timestamp;
	Class<? extends MetadataFrame> cl;
	// Manga Specific:
	// None
	public MetadataFrame(String fname)
	{
		bbbbbbb=ResourceBundle.getBundle("com.xperia64.diyedit.resources.Resources", (Locale.getDefault().getLanguage().equals("zh")||Globals.forceChina)?Locale.CHINA:Locale.US);
		filz=fname;
		file = FileByteOperations.read(filz);
		cl = this.getClass();
	    getContentPane().setLayout(null);
	    setupGUI();
	    WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
            	Globals.MetadataShown=false;
            }
        };
        this.addWindowListener(exitListener);
	    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	String[] setupColors()
	{
		String[] s = new String[8];
		s[0]=(String) bbbbbbb.getObject("yellowMetadataKey");
		s[1]=(String) bbbbbbb.getObject("lightBlueMetadataKey");
		s[2]=(String) bbbbbbb.getObject("greenMetadataKey");
		s[3]=(String) bbbbbbb.getObject("orangeMetadataKey");
		s[4]=(String) bbbbbbb.getObject("darkBlueMetadataKey");
		s[5]=(String) bbbbbbb.getObject("redMetadataKey");
		s[6]=(String) bbbbbbb.getObject("whiteMetadataKey");
		s[7]=(String) bbbbbbb.getObject("blackMetadataKey");
		return s;
	}
	ImageIcon[] setupImages(int t)
	{
		switch(t)
		{
		case 0:
			ImageIcon[] aa = new ImageIcon[8];
			for(int i = 1; i<9; i++)
			{
				aa[i-1]=new ImageIcon(cl.getResource("/spr_"+"color"+Integer.toString(i)+".png"));
			}
			return aa;
		case 1:
			ImageIcon[] bb = new ImageIcon[9];
			for(int i = 1; i<10; i++)
			{
				bb[i-1]=new ImageIcon(cl.getResource("/spr_"+"cart"+Integer.toString(i)+".png"));
			}
			return bb;
		case 2:
			ImageIcon[] cc = new ImageIcon[8];
			for(int i = 1; i<9; i++)
			{
				cc[i-1]=new ImageIcon(cl.getResource("/spr_"+"cart_logo"+Integer.toString(i)+".png"));
			}
			return cc;
		case 3:
			ImageIcon[] dd = new ImageIcon[8];
			for(int i = 1; i<9; i++)
			{
				dd[i-1]=new ImageIcon(cl.getResource("/spr_"+"record"+Integer.toString(i)+".png"));
			}
			return dd;
		case 4:
			ImageIcon[] ee = new ImageIcon[8];
			for(int i = 1; i<9; i++)
			{
				ee[i-1]=new ImageIcon(cl.getResource("/spr_"+"record_logo"+Integer.toString(i)+".png"));
			}
			return ee;
		case 5:
			ImageIcon[] ff = new ImageIcon[8];
			for(int i = 1; i<9; i++)
			{
				ff[i-1]=new ImageIcon(cl.getResource("/spr_"+"manga_logo"+Integer.toString(i)+".png"));
			}
			return ff;
		default:
			return null;
		}
	}
	void setupGUI()
	{
		// Common
		String Names="";
		String Descriptions="";
		String Brands="";
		String Creators="";
		String Serial1s="";
		String Serial2s="";
		String Serial3s="";
		boolean Locked=false;
		int ItemColors=0;
		int Logos=0;
		int LogoColors=0;
		int Timestamps = 0;
		// Game Specific:
		String Commands="";
		int Lengths=0;
		int CartridgeType=0;
		// Record Specific:
		int RecordTypes=0;
		// Manga Specific:
		// None
		switch(Globals.MetadataMode)
		{
		case 0:
			GameMetadata g = new GameMetadata(filz);
			Names=g.getName();
			Descriptions=g.getDescription();
			Brands=g.getBrand();
			Creators = g.getCreator();
			Serial1s = g.getSerial1();
			Serial2s = String.format("%04d", g.getSerial2());
			Serial3s = String.format("%03d", g.getSerial3());
			Locked=g.getLocked();
			ItemColors=g.getCartColor();
			Logos=g.getLogo();
			LogoColors=g.getLogoColor();
			Commands = g.getCommand();
			CartridgeType=g.getCartType();
			Lengths=g.getLength();
			Timestamps = g.getTimestamp();
			break;
		case 1:
			RecordMetadata r = new RecordMetadata(filz);
			Names=r.getName();
			Descriptions=r.getDescription();
			Brands=r.getBrand();
			Creators = r.getCreator();
			Serial1s = r.getSerial1();
			Serial2s = String.format("%04d", r.getSerial2());
			Serial3s = String.format("%03d", r.getSerial3());
			Locked=r.getLocked();
			ItemColors=r.getRecordColor();
			Logos=r.getLogo();
			LogoColors=r.getLogoColor();
			RecordTypes = r.getRecordType();
			Timestamps = r.getTimestamp();
			break;
		case 2:
			MangaMetadata m = new MangaMetadata(filz);
			Names=m.getName();
			Descriptions=m.getDescription();
			Brands=m.getBrand();
			Creators = m.getCreator();
			Serial1s = m.getSerial1();
			Serial2s = String.format("%04d", m.getSerial2());
			Serial3s = String.format("%03d", m.getSerial3());
			Locked=m.getLocked();
			ItemColors=m.getMangaColor();
			Logos=m.getLogo();
			LogoColors=m.getLogoColor();
			Timestamps = m.getTimestamp();
			break;
		default:
			return;
		}
		NameHeader = new JLabel();
		NameHeader.setSize(130,20);
		NameHeader.setLocation(10,0);
		NameHeader.setText(getMode()+(String) bbbbbbb.getObject("nameMetadataKey")+":");
		getContentPane().add(NameHeader);
		Name = new JTextField();
		Name.setSize(76,25);
		Name.setLocation(10,20);
		Name.setText(Names);
		getContentPane().add(Name);
		DescriptionHeader = new JLabel();
		DescriptionHeader.setSize(150,20);
		DescriptionHeader.setLocation(140,0);
		DescriptionHeader.setText(getMode()+(String) bbbbbbb.getObject("descriptionMetadataKey")+":");
		getContentPane().add(DescriptionHeader);
		Description = new JTextField();
		Description.setSize(220,25);
		Description.setLocation(100,20);
		Description.setText(Descriptions);
		getContentPane().add(Description);
		BrandHeader = new JLabel();
		BrandHeader.setSize(140,20);
		BrandHeader.setLocation(350,0);
		BrandHeader.setText((String) bbbbbbb.getObject("brandMetadataKey")+":");
		getContentPane().add(BrandHeader);
		Brand = new JTextField();
		Brand.setSize(75,25);
		Brand.setLocation(350,20);
		Brand.setText(Brands);
		getContentPane().add(Brand);
		CreatorHeader = new JLabel();
		CreatorHeader.setSize(100,20);
		CreatorHeader.setLocation(450,0);
		CreatorHeader.setText((String) bbbbbbb.getObject("creatorMetadataKey")+":");
		getContentPane().add(CreatorHeader);
		Creator = new JTextField();
		Creator.setSize(75,25);
		Creator.setLocation(450,20);
		Creator.setText(Creators);
		getContentPane().add(Creator);
		SerialHeader = new JLabel();
		SerialHeader.setSize(100,20);
		SerialHeader.setLocation(550,0);
		SerialHeader.setText((String) bbbbbbb.getObject("serialMetadataKey")+":");
		getContentPane().add(SerialHeader);
		Serial1 = new JTextField();
		Serial1.setSize(40,25);
		Serial1.setLocation(550,20);
		Serial1.setText(Serial1s);
		getContentPane().add(Serial1);
		Dash1 = new JLabel();
		Dash1.setSize(10,20);
		Dash1.setLocation(590,20);
		Dash1.setText("-");
		getContentPane().add(Dash1);
		Serial2 = new JTextField();
		Serial2.setSize(40,25);
		Serial2.setLocation(595,20);
		Serial2.setText(Serial2s);
		getContentPane().add(Serial2);
		Dash2 = new JLabel();
		Dash2.setSize(10,20);
		Dash2.setLocation(635,20);
		Dash2.setText("-");
		getContentPane().add(Dash2);
		Serial3 = new JTextField();
		Serial3.setSize(30,25);
		Serial3.setLocation(640,20);
		Serial3.setText(Serial3s);
		getContentPane().add(Serial3);
		Lock = new JCheckBox();
		Lock.setSize(100,20);
		Lock.setText((String) bbbbbbb.getObject("lockedMetadataKey"));
		Lock.setLocation(585,130);
		Lock.setSelected(Locked);
		getContentPane().add(Lock);
		ItemColorHeader = new JLabel();
		ItemColorHeader.setSize(150,20);
		ItemColorHeader.setLocation(10,100);
		ItemColorHeader.setText(getMode()+(String) bbbbbbb.getObject("lockedMetadataKey")+":");
		getContentPane().add(ItemColorHeader);
		Integer[] thing = {0,1,2,3,4,5,6,7};
		ItemColor = new JComboBox<Integer>(thing);
		ItemColor.setRenderer(new MyRend(setupImages(0),setupColors()));
		ItemColor.setSize(100,25);
		ItemColor.setLocation(10,120);
		ItemColor.setSelectedIndex(ItemColors);
		getContentPane().add(ItemColor);
		LogoHeader = new JLabel();
		LogoHeader.setSize(100,20);
		LogoHeader.setLocation(120,100);
		LogoHeader.setText(getMode()+(String) bbbbbbb.getObject("logoMetadataKey")+":");
		getContentPane().add(LogoHeader);
		Logo = new JComboBox<Integer>(thing);	
		Logo.setSize(150,25);
		Logo.setLocation(120,120);
		Logo.setSelectedIndex(Logos);
		LogoColorHeader = new JLabel();
		LogoColorHeader.setSize(100,20);
		LogoColorHeader.setLocation(290,100);
		LogoColorHeader.setText((String) bbbbbbb.getObject("logoColorMetadataKey")+":");
		getContentPane().add(LogoColorHeader);
		LogoColor = new JComboBox<Integer>(thing);
		LogoColor.setRenderer(new MyRend(setupImages(0),setupColors()));
		LogoColor.setSize(100,25);
		LogoColor.setLocation(290,120);
		LogoColor.setSelectedIndex(LogoColors);
		getContentPane().add(LogoColor);
		Apply = new JButton();
		Apply.setSize(150,50);
		Apply.setLocation(400,120);
		Apply.setText((String) bbbbbbb.getObject("applyMetadataKey"));
		Apply.addActionListener(this);
		getContentPane().add(Apply);
		Timestamp = new JTextField();
		Timestamp.setSize(220,25);
		Timestamp.setLocation(10,150);
		DateTime date = new DateTime(2000, 1, 1, 0, 0, 0, 0);
		date=date.plusDays(Timestamps);
		Timestamp.setText(date.getMonthOfYear()+"/"+date.getDayOfMonth()+"/"+date.getYear());
		getContentPane().add(Timestamp);
		switch(Globals.MetadataMode)
		{
		case 0:
			CommandHeader = new JLabel();
			CommandHeader.setSize(130,20);
			CommandHeader.setLocation(0,50);
			CommandHeader.setText((String) bbbbbbb.getObject("commandMetadataKey")+":");
			getContentPane().add(CommandHeader);
			Command = new JTextField();
			Command.setSize(200,25);
			Command.setLocation(10,70);
			Command.setText(Commands);
			getContentPane().add(Command);
			LengthHeader = new JLabel();
			LengthHeader.setLocation(220,50);
			LengthHeader.setSize(100,20);
			LengthHeader.setText((String) bbbbbbb.getObject("gameLengthMetadataKey")+":");
			getContentPane().add(LengthHeader);
			GameLength=new JComboBox<String>();
			GameLength.addItem((String) bbbbbbb.getObject("shortMetadataKey"));
			GameLength.addItem((String) bbbbbbb.getObject("longMetadataKey"));
			GameLength.addItem((String) bbbbbbb.getObject("bossMetadataKey"));
			GameLength.setSelectedIndex(Lengths);
			GameLength.setSize(100,25);
			GameLength.setLocation(220,70);
			getContentPane().add(GameLength);
			CartHeader = new JLabel();
			CartHeader.setLocation(350,50);
			CartHeader.setSize(100,20);
			CartHeader.setText((String) bbbbbbb.getObject("cartridgeShapeMetadataKey")+":");
			getContentPane().add(CartHeader);
			Integer[] thing2 = {0,1,2,3,4,5,6,7,8};
			CartType = new JComboBox<Integer>(thing2);
			CartType.setSize(200,25);
			CartType.setLocation(350,70);
			String[] labels_game = {(String) bbbbbbb.getObject("pulseMetadataKey"),(String) bbbbbbb.getObject("rocketMetadataKey"),(String) bbbbbbb.getObject("shieldMetadataKey"),(String) bbbbbbb.getObject("punchMetadataKey"),(String) bbbbbbb.getObject("carMetadataKey"),(String) bbbbbbb.getObject("batMetadataKey"),(String) bbbbbbb.getObject("whatMetadataKey"),(String) bbbbbbb.getObject("otherMetadataKey")};
			Logo.setRenderer(new MyRend(setupImages(2),labels_game));
			getContentPane().add(Logo);
			String[] carts = {(String) bbbbbbb.getObject("roundMetadataKey"),(String) bbbbbbb.getObject("notchMetadataKey"),(String) bbbbbbb.getObject("flatMetadataKey"),(String) bbbbbbb.getObject("ridgesMetadataKey"),(String) bbbbbbb.getObject("zigzagMetadataKey"),(String) bbbbbbb.getObject("prongsMetadataKey"),(String) bbbbbbb.getObject("angledMetadataKey"),(String) bbbbbbb.getObject("gbaMetadataKey"),(String) bbbbbbb.getObject("bigNameMetadataKey")};
			CartType.setRenderer(new MyRend(setupImages(1),carts));
			CartType.setSelectedIndex(CartridgeType);
			getContentPane().add(CartType);
			DrawPreview = new GamePreview(file);
			DrawPreview.setSize(300,72);
			DrawPreview.setLocation(573,50);
			getContentPane().add(DrawPreview);
			break;
		case 1:
			RecordHeader = new JLabel();
			RecordHeader.setLocation(350,50);
			RecordHeader.setSize(100,20);
			RecordHeader.setText((String) bbbbbbb.getObject("recordShapeMetadataKey"));
			getContentPane().add(RecordHeader);
			RecordType = new JComboBox<Integer>(thing);
			String[] records = {(String) bbbbbbb.getObject("circleMetadataKey"),(String) bbbbbbb.getObject("squareMetadataKey"),(String) bbbbbbb.getObject("starMetadataKey"),(String) bbbbbbb.getObject("hexagonMetadataKey"),(String) bbbbbbb.getObject("cloverMetadataKey"),(String) bbbbbbb.getObject("diamondMetadataKey"),(String) bbbbbbb.getObject("plusMetadataKey"),(String) bbbbbbb.getObject("flowerMetadataKey")};
			RecordType.setRenderer(new MyRend(setupImages(3),records));
			RecordType.setSize(200,25);
			RecordType.setLocation(350,70);
			RecordType.setSelectedIndex(RecordTypes);
			getContentPane().add(RecordType);
			String[] labels_record = {(String) bbbbbbb.getObject("heartMetadataKey"),(String) bbbbbbb.getObject("flowerLogoMetadataKey"),(String) bbbbbbb.getObject("stormMetadataKey"),(String) bbbbbbb.getObject("crossMetadataKey"),(String) bbbbbbb.getObject("leafMetadataKey"),(String) bbbbbbb.getObject("dropletMetadataKey"),(String) bbbbbbb.getObject("lightningMetadataKey"),(String) bbbbbbb.getObject("smileMetadataKey")};
			Logo.setRenderer(new MyRend(setupImages(4),labels_record));
			getContentPane().add(Logo);
			break;
		case 2:
			String[] labels_manga = {(String) bbbbbbb.getObject("letterMetadataKey"),(String) bbbbbbb.getObject("houseMetadataKey"),(String) bbbbbbb.getObject("shieldMetadataKey"),(String) bbbbbbb.getObject("skullMetadataKey"),(String) bbbbbbb.getObject("catMetadataKey"),(String) bbbbbbb.getObject("batMetadataKey"),(String) bbbbbbb.getObject("spaceshipMetadataKey"),(String) bbbbbbb.getObject("smileMetadataKey")};
			Logo.setRenderer(new MyRend(setupImages(5),labels_manga));
			getContentPane().add(Logo);
			
		}
		setLocation(100,100);
		setTitle((String) bbbbbbb.getObject("metadataMetadataKey"));
		setSize(685,210);
		setVisible(true);
		setResizable(false);
	}
	private String getMode()
	{
		switch(Globals.MetadataMode)
		{
		case 0:
			return (String) bbbbbbb.getObject("gameKey")+" ";
		case 1:
			return (String) bbbbbbb.getObject("recordKey")+" ";
		case 2:
			return (String) bbbbbbb.getObject("comicKey")+" ";
		default:
			return "Huh?";
		}
	}
	
	public void actionPerformed(ActionEvent e) {  
		if(e.getSource()==Apply)
		{
			FileByteOperations.write(applyChanges(), filz);
		}
	}
    
 
    public void itemStateChanged(ItemEvent e)  {
 
        @SuppressWarnings("unused")
		Object source = e.getItemSelectable();
    }
    private byte[] applyChanges()
    {
    	Metadata m = new Metadata(file);
    	if(Name.getText().length()>12)
    	{
    		JOptionPane.showMessageDialog(this, (String) bbbbbbb.getObject("nameWarningMetadataKey"));
    		return file;
    	}
    	m.setName(Name.getText());
    	if(Description.getText().length()>36)
    	{
    		JOptionPane.showMessageDialog(this, (String) bbbbbbb.getObject("descWarningMetadataKey"));
    		return file;
    	}
    	m.setDescription(Description.getText());
    	if(Brand.getText().length()>9)
    	{
    		JOptionPane.showMessageDialog(this, (String) bbbbbbb.getObject("brandWarningMetadataKey"));
    		return file;
    	}
    	m.setBrand(Brand.getText());
    	if(Creator.getText().length()>9)
    	{
    		JOptionPane.showMessageDialog(this, (String) bbbbbbb.getObject("creatWarningMetadataKey"));
    		return file;
    	}
    	m.setCreator(Creator.getText());
    	if(Serial1.getText().length()>4)
    	{
    		JOptionPane.showMessageDialog(this, (String) bbbbbbb.getObject("serial1WarningMetadataKey"));
    		return file;
    	}
    	if(Serial2.getText().length()>4||!isInteger(Serial2.getText()))
    	{
    		JOptionPane.showMessageDialog(this, (String) bbbbbbb.getObject("serial2WarningMetadataKey"));
    		return file;
    	}
    	if(Serial3.getText().length()>3||!isInteger(Serial3.getText()))
    	{
    		JOptionPane.showMessageDialog(this, (String) bbbbbbb.getObject("serial3WarningMetadataKey"));
    		return file;
    	}
    	m.setSerial(Serial1.getText(), Integer.parseInt(Serial2.getText()), Integer.parseInt(Serial3.getText()));
    	m.setLocked(Lock.isSelected());
    	Checksums.readChecksums(file);
    	switch(Globals.MetadataMode)
    	{
    	case 0:
    		
    		GameMetadata g = new GameMetadata(m.file);
    		g.setCartType((byte) CartType.getSelectedIndex());
    		g.setCartColor((byte)ItemColor.getSelectedIndex());
    		g.setLogo((byte)Logo.getSelectedIndex());
    		g.setLogoColor((byte)LogoColor.getSelectedIndex());
    		g.setLength((byte)GameLength.getSelectedIndex());
    		if(Command.getText().length()>12)
    		{
    			JOptionPane.showMessageDialog(this, (String) bbbbbbb.getObject("commandWarningMetadataKey"));
        		return file;
    		}
    		g.setCommand(Command.getText());
    		file=g.file;
    		break;
    	case 1:
    		RecordMetadata r = new RecordMetadata(m.file);
    		r.setRecordType((byte) RecordType.getSelectedIndex());
    		r.setRecordColor((byte)ItemColor.getSelectedIndex());
    		r.setLogo((byte)Logo.getSelectedIndex());
    		r.setLogoColor((byte)LogoColor.getSelectedIndex());
    		file=r.file;
    		break;
    	case 2:
    		MangaMetadata mm = new MangaMetadata(m.file);
    		mm.setMangaColor((byte)ItemColor.getSelectedIndex());
    		mm.setLogo((byte)Logo.getSelectedIndex());
    		mm.setLogoColor((byte)LogoColor.getSelectedIndex());
    		file=mm.file;
    		break;
    	}
    	return Checksums.writeChecksums(file);
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
class GamePreview extends JComponent {
	private static final long serialVersionUID = 1L;
	byte[] bfilz;
	
	
	GameEdit e2;
	public GamePreview(byte[] bb)
	{
		bfilz=bb;
		e2 = new GameEdit(bfilz);
	}
	public void paint(Graphics g) {
		int x=0;
		int y=0;
		boolean done=false;
		while(!done)
		{
		int c = e2.getPreviewPixel(x,y);
		g.setColor(new Color(r(c),g(c),b(c)));
		g.drawRect(x,y,1,1);
		x++;
		if(x>95)
		{
			y+=1;
			x=0;	
		}
		if(y>63)
		{
			done=true;
			break;
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
class MyRend extends JLabel implements ListCellRenderer<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon[] img;
	private String[] text;
	public MyRend(ImageIcon[] images, String[] labels)
	{
		setOpaque(true);
        setHorizontalAlignment(LEFT);
        setVerticalAlignment(CENTER);
		img=images;
		text=labels;
	}
    public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value,
            int index, boolean isSelected, boolean cellHasFocus) {
    	int selectedIndex = (value).intValue();
    	setIcon(img[selectedIndex]);
    	setText(text[selectedIndex]);
        return this;
    }
	

}
