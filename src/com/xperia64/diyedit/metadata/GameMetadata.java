package com.xperia64.diyedit.metadata;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.xperia64.diyedit.Globals;


// Read and write game metadata
public class GameMetadata extends Metadata {
	
	public GameMetadata(String f)
	{
		super(f);
	}
	
	public GameMetadata(byte[] b)
	{
		super(b);
	}
	// Returns the command the game tells the player
	public String getCommand()
	{
		StringBuilder cmd=new StringBuilder();
		for(int i = 0xE5DD; i<0xE5F5; i++)
		{
			if(file[i]==0)
			{
				break;
			}
			if((file[i]&0xFF)>=0x80) // Unicode-type thing
			{
				if((file[i]&0xFF)>=0xE0) // Japan, why u have 3 byte letters
				{
					int key=(file[i]&0xFF)<<16|(file[i+1]&0xFF)<<8|(file[i+2]&0xFF);
					if(Globals.translation.containsKey(key))
						cmd.append(Globals.translation.get(key));
					else if(key>=0xe38080&&key<=0xe3f02f){
						byte[] bbb = new byte[3];
						bbb[0]=file[i];
						bbb[1]=file[i+1];
						bbb[2]=file[i+2];
						try {
							cmd.append(new String(bbb,"UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
						cmd.append("?");
					i++;
					i++;
				}else{
					int key=(file[i]&0xFF)<<8|(file[i+1]&0xFF);
					if(Globals.translation.containsKey(key))
						cmd.append(Globals.translation.get(key));
					else
						cmd.append("?");
					i++; //two.
				}
			}else{
				cmd.append((char)file[i]);
			}
		}
		return cmd.toString();
	}
	// Sets the command the game tells the player
	public void setCommand(String cmd)
	{
		String[] characters = cmd.split("");
		ArrayList<Byte> values = new ArrayList<Byte>();
		for(int i = 1; i<characters.length; i++)
		{
			if((characters[i].getBytes()[0]&0xFF)>=0x80)
			{
				if(Globals.reverseTranslation.containsKey(characters[i]))
				{
					values.add((byte) ((Globals.reverseTranslation.get(characters[i])>>8)&0xFF));
					values.add((byte) ((Globals.reverseTranslation.get(characters[i])>>8)&0xFF));
				}else if(((characters[i].getBytes()[0]&0xFF)<<16|((characters[i].getBytes()[1]&0xFF)<<8)|(characters[i].getBytes()[2]&0xFF))>=0xe38080&&((characters[i].getBytes()[0]&0xFF)<<16|((characters[i].getBytes()[1]&0xFF)<<8)|(characters[i].getBytes()[2]&0xFF))<=0xe3f02f)
				{
					values.add((byte) (characters[i].getBytes()[0]&0xFF));
					values.add((byte) (characters[i].getBytes()[1]&0xFF));
					values.add((byte) (characters[i].getBytes()[2]&0xFF));
				}
			}else{
				values.add((byte) (characters[i].getBytes()[0]&0xFF));
			}
			
		}
		for(int i = 0xE5DD; i<0xE5F5; i++)
		{
			if(i-0xE5DD<values.size())
			{
				file[i]=values.get(i-58845);
			}else{
				file[i]=0x00;
			}
		}
	}
	// Reads length of game, 0 is short, 1 is long, 2 is boss
	public byte getLength()
	{
		return file[58885];	
	}
	// Writes length of game, 0 is short, 1 is long, 2 is boss
	public void setLength(byte l)
	{
		file[58885]= l;
	}
	// Reads "cartridge" shape of game, 9 cartridges with values 0-8
	public byte getCartType()
	{
		//0 Round top
		//1 Top with hole
		//2 Square top with indents
		//3 Round top with vents at bottom
		//4 Zig-zag sides
		//5 Flat top with notches
		//6 Angled top with small indents on sides
		//7 GBA style with protruding top
		//8 Star
		return file[166];
	}
	// Writes "cartridge" shape of game, 9 cartridges with values 0-8
	public void setCartType(byte c)
	{
		//0 Round top
		//1 Top with hole
		//2 Square top with indents
		//3 Round top with vents at bottom
		//4 Zig-zag sides
		//5 Flat top with notches
		//6 Angled top with small indents on sides
		//7 GBA style with protruding top
		//8 Star
		file[166]=c;
	}
	// Reads "cartridge" color of game, 8 colors with values 0-7
	public byte getCartColor()
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
	// Writes "cartridge" color of game, 8 colors with values 0-7
	public void setCartColor(byte c)
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
	// Reads "cartridge" logo of game, 8 logos with values 0-7
	public byte getLogo()
	{
		//0 Pulse
		//1 Rocket
		//2 Sword
		//3 Fist
		//4 Car
		//5 Bat
		//6 Blocks
		//7 Blob
		return file[167];
	}
	// Writes "cartridge" logo of game, 8 logos with values 0-7
	public void setLogo(byte c)
	{
		//0 Pulse
		//1 Rocket
		//2 Sword
		//3 Fist
		//4 Car
		//5 Bat
		//6 Blocks
		//7 Blob
		file[167]=c;
	}
	// Reads "cartridge" logo color of game, 8 colors with values 0-7
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
	// Writes "cartridge" color of game, 8 colors with values 0-7
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
