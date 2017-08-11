package com.xperia64.diyedit.editors;

import com.xperia64.diyedit.metadata.MangaMetadata;

// Class for editing mangas
public class MangaEdit extends MangaMetadata {
	// 0x100 = start of panels
	// 0x100-0xCFF
	// 0xD00-0x18FF
	// 0x1900-0x24FF
	// 0x2500-0x30FF
	// 0x3100-0x3103 = New/created switches (0=new, 1 = created)
	// Panel length = 0xC00
	// Separation between line pixels = 0x06 bytes
	// 0x0F blocks of 0xC0 in length
	// y values of 0x00-0x07 are in the first block,  0x08-0x0F in the 2nd etc
	
	/* Editing stuff: Lets say our image starts at 0x00 with a 0x0F pattern. 
	 * 0x01 is not the next horizontal segment.
	 * Instead, 0x08 is. 
	 * 0x01 is placed below 0x00 by one pixel.
	 * All pattern bytes are 8 pixels long.
	 * 
	 * Codes for pixels!!!!
	 * Pixels are defined as the binary code for each hex number,
	 * plus enough spaces in front to make 8 pixels
	 * 0E = 1110 = ____***_
	 * 0xBF = x width max
	 * 0x7F = y height max
	 * I'm measuring from the top left to bottom right.
	 */
	// Check if panel is created
	public MangaEdit(byte[] b)
	{
		super(b);
	}
	public MangaEdit(String f)
	{
		super(f);
	}
	public boolean isCreated(byte panel)
	{
		if (file[12544+panel]==1)
		{
			return true;
		}
		return false;
	}
	// Set if panel is created
	public void setCreated(byte panel, boolean c)
	{
		if(c)
		{
			file[12544+panel]=1;
		}else{
			file[12544+panel]=0;
		}
	}
	// Test if pixel is filled
	public boolean getPixel(byte panel, int x, int y)
	{
		int offset=256+panel*3072; // panel offset
		int boffset = (y/8)*192; // block offset, determined by y
		int xoffset = (x/8)*8; // How many bytes
		int yoffset = (y-((y/8)*8));
		int indexer = (x-((x/8)*8));
		offset+=(boffset+xoffset+yoffset);
		int b = file[(int)offset]&0xFF;
		String b1 = Integer.toBinaryString(b);
		while(b1.length()<8)
		{
			b1="0"+b1;
		}
		if(b1.charAt(indexer)=='1')
		{
			return true;
		}
		return false;
	}
	// Sets pixel, not tested thoroughly
	public void setPixel(byte panel, int x, int y, boolean on)
	{
		int offset=256+panel*3072; // panel offset
		int boffset = (y/8)*192; // block offset, determined by y
		int xoffset = (x/8)*8; // Fun with rounding
		int yoffset = (y-((y/8)*8));
		int indexer = (x-((x/8)*8));
		offset+=(boffset+xoffset+yoffset);
		int b = file[(int)offset]&0xFF;
		String b1 = Integer.toBinaryString(b);
		while(b1.length()<8)
		{
			b1="0"+b1;
		}
		char[] b2 = b1.toCharArray();
		if(on)
		{
			b2[indexer]='1';
		}else{
			b2[indexer]='0';
		}
		b1="";
		for(int i = 0; i<b2.length; i++)
		{
			b1+=b2[i];
		}
		int newNum=Integer.parseInt(b1,2);
		file[(int)offset]=(byte)newNum;
		
	}
}
