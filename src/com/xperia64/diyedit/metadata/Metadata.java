package com.xperia64.diyedit.metadata;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;

import com.xperia64.diyedit.FileByteOperations;
import com.xperia64.diyedit.Globals;

// Generic metadata class, works for all filetypes
public class Metadata {
	
	public byte[] file;
	public Metadata(String f)
	{
		file = FileByteOperations.read(f);
	}
	public Metadata(byte[] b)
	{
		file=b;
	}
	// Returns the name in the mio file header
	public String getName()
	{
		StringBuilder name = new StringBuilder();
		for(int i = 0x1C; i<0x32; i++)
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
						name.append(Globals.translation.get(key));
					else if(key>=0xe38080&&key<=0xe3f02f){
						byte[] bbb = new byte[3];
						bbb[0]=file[i];
						bbb[1]=file[i+1];
						bbb[2]=file[i+2];
						try {
							name.append(new String(bbb,"UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else
						name.append("?");
					i++;
					i++;
				}else{
					int key=(file[i]&0xFF)<<8|(file[i+1]&0xFF);
					if(Globals.translation.containsKey(key))
						name.append(Globals.translation.get(key));
					else
						name.append("?");
					i++; //two.
				}
			}else{
				name.append((char)file[i]);
			}
		}
		return name.toString();
	}
	// Sets the name in the mio file header
	public void setName(String name)
	{
		String[] characters = name.split("");
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
		
		for(int i = 0x1C; i<0x32; i++)
		{
			if(i-0x1C<values.size())
			{
				file[i]=(values.get(i-28));
			}else{
				file[i]=0x00;
			}
			
		}
	}
	// Returns the description from the mio file header
	public String getDescription()
	{
		StringBuilder desc = new StringBuilder();
		for(int i = 0x5B; i<0xA2; i++)
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
						desc.append(Globals.translation.get(key));
					else if(key>=0xe38080&&key<=0xe3f02f){
						byte[] bbb = new byte[3];
						bbb[0]=file[i];
						bbb[1]=file[i+1];
						bbb[2]=file[i+2];
						try {
							desc.append(new String(bbb,"UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
						desc.append("?");
					i++;
					i++;
				}else{
					int key=(file[i]&0xFF)<<8|(file[i+1]&0xFF);
					if(Globals.translation.containsKey(key))
						desc.append(Globals.translation.get(key));
					else
						desc.append("?");
					i++; //two.
				}
			}else{
				desc.append((char)file[i]);
			}
		}
		return desc.toString();
	}
	// Sets the description in the mio file header
	public void setDescription(String desc)
	{
		String[] characters = desc.split("");
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
		
		for(int i = 0x5B; i<0xA2; i++)
		{
			if(i-0x5B<values.size())
			{
				file[i]=values.get(i-91);
			}else{
				file[i]=0x00;
			}
		}
		
	}
	// Returns the brand from the mio file header
	public String getBrand()
	{
		StringBuilder brand = new StringBuilder();
		for(int i = 0x35; i<0x47; i++)
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
						brand.append(Globals.translation.get(key));
					else if(key>=0xe38080&&key<=0xe3f02f){
						byte[] bbb = new byte[3];
						bbb[0]=file[i];
						bbb[1]=file[i+1];
						bbb[2]=file[i+2];
						try {
							brand.append(new String(bbb,"UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
						brand.append("?");
					i++;
					i++;
				}else{
					int key=(file[i]&0xFF)<<8|(file[i+1]&0xFF);
					if(Globals.translation.containsKey(key))
						brand.append(Globals.translation.get(key));
					else
						brand.append("?");
					i++; //two.
				}
			}else{
				brand.append((char)file[i]);
			}
			
		}
		return brand.toString();
	}
	// Sets the brand in the mio file header
	public void setBrand(String brand)
	{
		String[] characters = brand.split("");
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
		
		for(int i = 0x35; i<0x47; i++)
		{
			if(i-0x35<values.size())
			{
				file[i]=values.get(i-0x35);
			}else{
				file[i]=0x00;
			}
		}
		
	}
	// Returns the creator from the mio file header
	public String getCreator()
	{
		StringBuilder cr=new StringBuilder();
		for(int i = 0x48; i<0x5A; i++)
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
						cr.append(Globals.translation.get(key));
					else if(key>=0xe38080&&key<=0xe3f02f){
						byte[] bbb = new byte[3];
						bbb[0]=file[i];
						bbb[1]=file[i+1];
						bbb[2]=file[i+2];
						try {
							cr.append(new String(bbb,"UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
						cr.append("?");
					i++;
					i++;
				}else{
					int key=(file[i]&0xFF)<<8|(file[i+1]&0xFF);
					if(Globals.translation.containsKey(key))
						cr.append(Globals.translation.get(key));
					else
						cr.append("?");
					i++; //two.
				}
			}else{
				cr.append((char)file[i]);
			}
			
		}
		return cr.toString();
	}
	// Sets the creator in the mio file header
	public void setCreator(String cre)
	{
		String[] characters = cre.split("");
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
		for(int i = 0x48; i<0x5A; i++)
		{
			if(i-72<values.size())
			{
				file[i]=values.get(i-72);
			}else{
				file[i]=0x00;
			}
		}
	}
	// Returns the first 4-character serial from the mio file header
	public String getSerial1()
	{
		StringBuilder s1 = new StringBuilder();
		for(int i = 207; i<211; i++)
		{
			s1.append((char)file[i]);
		}
		return s1.toString();
	}
	// Returns the second 4-digit serial from the mio file header
	public int getSerial2()
	{
		int s2=0;
		String tmp=Integer.toHexString((file[215]&0xFF));
		while(tmp.length()<2)
		{
			tmp="0"+tmp;
		}
		if(tmp.length()>2)
		{
			tmp=tmp.substring(tmp.length()-2,tmp.length());
		}
		String tmp2=Integer.toHexString((file[214]&0xFF));
		while(tmp2.length()<2)
		{
			tmp2="0"+tmp2;
		}
		String tmp3=Integer.toHexString((file[213]&0xFF));
		if(tmp2.length()>2)
		{
			tmp2=tmp2.substring(tmp2.length()-2,tmp2.length());
		}
		while(tmp3.length()<2)
		{
			tmp3="0"+tmp3;
		}
		if(tmp3.length()>2)
		{
			tmp3=tmp3.substring(tmp3.length()-2,tmp3.length());
		}
		String tmp4=Integer.toHexString((file[212]&0xFF));
		while(tmp3.length()<2)
		{
			tmp3="0"+tmp3;
		}
		s2=Integer.parseInt((tmp+tmp2+tmp3+tmp4),16)+1;
		return s2;
	}
	// Returns the third 3-digit serial from the mio file header
	public int getSerial3()
	{
		int s3=0;
		String tmp=Integer.toHexString((file[217]&0xFF));
		tmp+=Integer.toHexString((file[216]&0xFF));
		s3=Integer.parseInt(tmp,16);
		return s3;
	}
	// Sets the entire mio file serial
	public void setSerial(String s1, int s2, int s3)
	{
		s1=s1.toLowerCase(Locale.US);
		char[] c = s1.toCharArray();
		s2=s2-1;
		for(int i = 207; i<211; i++)
		{
			file[i]=(byte) c[i-207];
		}
		String hex11=Integer.toHexString(s2);
		int l = hex11.length();
		for(int i = 0; i<8-l; i++)
		{
			hex11="0"+hex11;
		}
		String hex21=Integer.toHexString(s3);
		l=hex21.length();
		for(int i = 0; i<4-l; i++)
		{
			hex21="0"+hex21;
		}
		file[212]=(byte) Integer.parseInt(hex11.substring(6,8),16);
		file[213]=(byte) Integer.parseInt(hex11.substring(4,6),16);
		file[214]=(byte) Integer.parseInt(hex11.substring(2,4),16);
		file[215]=(byte) Integer.parseInt(hex11.substring(0,2),16);
		file[216]=(byte) Integer.parseInt(hex21.substring(2,4),16);
		file[217]=(byte) Integer.parseInt(hex21.substring(0,2),16);
	}
	// Returns if the game is locked or not
	public boolean getLocked()
	{
		return(file[232]!=0?true:false);
	}
	// Sets whether the game is locked or not
	public void setLocked(boolean lock)
	{
		file[232]=(byte) (lock?5:0); // Arbitrary non-zero value locks
	}
	public int getTimestamp()
	{
		return ((file[0xEF]&0xFF)<<8)|(file[0xEE]&0xFF);
	}
	public void setTimestamp(int day)
	{
		file[0xEE]=(byte) (day&0xFF);
		file[0xEF]=(byte)((day&0xFF00)>>8);
	}
	public boolean getRegion()
	{
		// true = japan, false = UE
		return ((file[0x1C]&0xFF)>=0xE0||(file[0x1C]&0xFF)==0xC3);
	}
}
