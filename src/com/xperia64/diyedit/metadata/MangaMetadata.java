package com.xperia64.diyedit.metadata;

// Read and write manga metadata
public class MangaMetadata extends Metadata {

	
	public MangaMetadata(String f)
	{
		super(f);
	}
	
	public MangaMetadata(byte[] b)
	{
		super(b);
	}
	// Reads "Book" color of manga, 8 colors with values 0-7
	public byte getMangaColor()
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
	// Writes "Book" color of manga, 8 colors with values 0-7
	public void setMangaColor(byte c)
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
	// Reads "Book" logo of manga, 8 logos with values 0-7
	public byte getLogo()
	{
		//0 Letter
		//1 House
		//2 Sword
		//3 Skull
		//4 Cat
		//5 Bat
		//6 Alien
		//7 Smile
		return file[167];
	}
	// Writes "Book" logo of manga, 8 logos with values 0-7
	public void setLogo(byte c)
	{
		//0 Letter
		//1 House
		//2 Sword
		//3 Skull
		//4 Cat
		//5 Bat
		//6 Alien
		//7 Smile
		file[167]=c;
	}
	// Reads "Book" logo color of manga, 8 colors with values 0-7
	public byte getLogoColor()
	{
		//0 Yellow
		//1 Light Blue
		//2 Green
		//3 Orange
		//4 Dark Blue
		//5 Red
		//6 White
		//7 Black
		return file[169];
	}
	// Writes "Book" color of manga, 8 colors with values 0-7
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
