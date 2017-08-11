package com.xperia64.diyedit.metadata;

// Read and write record metadata
public class RecordMetadata extends Metadata {
	//0x100: jazz/swing byte
	//0x101: tempo from 0x00-0x12
	//0x102: ending flag position. 0x01-0x018, CANNOT be 0x00
	//Song blocks start at 0x107 and are 0x114 bytes long each
	//Notes go from 0x00 to 0x100 of each song block
	//0x101 to 0x105 are volume indicators from 0x00 to 0x04
	//0x106 to 0x10A are panning indicators
	//0x10B to 0x10E are instruments
	//0x10F is drums
	//0x110-0x114 other track options?
	//0x00-0x2F instruments
	//0x00-0x07 drumsets
	//0x00-0x0D
	//0x05 bytes of 0x00 between blocks
	//Blocks appear to be either 0x10F or 0x114 in length
	//0=60
	//1=70 etc
	//notes go from 0x00 to 0x18, 0xFF = null notes
	
	public RecordMetadata(String f)
	{
		super(f);
	}
	
	public RecordMetadata(byte[] b)
	{
		super(b);
	}
	public int getFlag()
	{
		return file[258];
	}
	public void setFlag(byte i)
	{
		file[258]=i;
	}

	// Reads "Record" shape of song, 8 records with values 0-7
	public byte getRecordType()
	{
		//0 Circle
		//1 Square
		//2 Star
		//3 Hexagon
		//4 Clover
		//5 Diamond
		//6 Fat cross
		//7 Flower
		return file[166];
	}
	// Writes "Record" shape of song, 8 records with values 0-7
	public void setRecordType(byte c)
	{
		//0 Circle
		//1 Square
		//2 Star
		//3 Hexagon
		//4 Clover
		//5 Diamond
		//6 Fat cross
		//7 Flower
		file[166]=c;
	}
	// Reads "Record" color of song, 8 colors with values 0-7
	public byte getRecordColor()
	{
		//0 Yellow
		//1 Light Blue
		//2 Green
		//3 Orange
		//4 Dark Blue
		//5 Red
		//6 White
		//7 Black
		return file[168];
	}
	// Writes "Record" color of song, 8 colors with values 0-7
	public void setRecordColor(byte c)
	{
		//0 Yellow
		//1 Light Blue
		//2 Green
		//3 Orange
		//4 Dark Blue
		//5 Red
		//6 White
		//7 Black
		file[168]=c;
	}
	// Reads "Record" logo of song, 8 logos with values 0-7
	public byte getLogo()
	{
		//0 Heart
		//1 Flower
		//2 Hurricane
		//3 X
		//4 Leaf
		//5 Droplet
		//6 Lightning
		//7 Smile
		return file[167];
	}
	// Writes "Record" logo of song, 8 logos with values 0-7
	public void setLogo(byte c)
	{
		//0 Heart
		//1 Flower
		//2 Hurricane
		//3 X
		//4 Leaf
		//5 Droplet
		//6 Lightning
		//7 Smile
		file[167]=c;
	}
	// Reads "Record" logo color of song, 8 colors with values 0-7
	public byte getLogoColor()
	{
		//0 Heart
		//1 Flower
		//2 Hurricane
		//3 X
		//4 Leaf
		//5 Droplet
		//6 Lightning
		//7 Smile
		return file[169];
	}
	// Writes "Record" color of song, 8 colors with values 0-7
	public void setLogoColor(byte c)
	{
		//0 Yellow
		//1 Light Blue
		//2 Green
		//3 Orange
		//4 Dark Blue
		//5 Red
		//6 White
		//7 Black
		file[169]=c;
	}
	
}
