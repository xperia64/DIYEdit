package com.xperia64.diyedit.editors;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

import com.xperia64.diyedit.Globals;
import com.xperia64.diyedit.metadata.GameMetadata;

public class GameEdit extends GameMetadata {

	public GameIndex index;
	GameMusic music;
	// Note: All subclasses except GameMusic are just the metadata. 
	// The real data can be accessed by getting the offsets from these classes
	
	// 4bpp
	// 0 (Alpha)
	// 1 (Black): 0,0,0
	// 2 (Flesh Colored): 255, 223, 156
	// 3 (Orange): 255, 174, 49
	// 4 (Brown): 255, 73, 0
	// 5 (Red): 255, 0, 0
	// 6 (Purple): 206, 105, 239
	// 7 (Light Blue): 16, 199, 206
	// 8 (Dark Blue): 41, 105, 198
	// 9 (Dark Green): 8, 150, 82
	// 10 (Light Green): 115, 215, 57
	// 11 (Yellow): 255, 255, 90
	// 12 (Dark Grey): 128, 128, 128
	// 13 (Light Grey): 192, 192, 192
	// 14 (White): 255, 255, 255
	// 15 (Hidden Color): 74, 156, 173
	
	// Sprites start at 0x3104
	// Sprites/art/frames are filled in order of creation, all lumped together
	// 2 pixels per byte
	// Small Art Size=0x80 bytes, 16*16
	// Medium Art Size=0x200 bytes, 32*32
	// Large Art Size=0x480 bytes, 48*48
	// X-Large Art Size=0x800 bytes, 64*64
	// 
	// Object Index begins at 0xB104
	// Object Index is strictly linear (hooray)
	// Music @ 0xB95B
	//
	// (y/8)*x where x = number of horizontal pixels * 4;
	public static final int[] palette = 
	{ 		(0 << 24)   | (255 << 16) | (255 << 8) | 255, // Transparent
			(255 << 24) |   (0 << 16) |   (0 << 8) | 0, // Black
			(255 << 24) | (255 << 16) | (223 << 8) | 156, // Flesh Colored
			(255 << 24) | (255 << 16) | (174 << 8) | 49, // Orange
			(255 << 24) | (255 << 16) |  (73 << 8) | 0, // Brown
			(255 << 24) | (255 << 16) |   (0 << 8) | 0, // Red
			(255 << 24) | (206 << 16) | (105 << 8) | 239, // Purple
			(255 << 24) | (16 << 16)  | (199 << 8) | 206, // Light Blue
			(255 << 24) | (41 << 16)  | (105 << 8) | 198, // Dark Blue
			(255 << 24) | (8 << 16)   | (150 << 8) | 82, // Dark Green
			(255 << 24) | (115 << 16) | (215 << 8) | 57, // Light Green
			(255 << 24) | (255 << 16) | (255 << 8) | 90, // Yellow
			(255 << 24) | (128 << 16) | (128 << 8) | 128, // Dark Grey
			(255 << 24) | (192 << 16) | (192 << 8) | 192, // Light Grey
			(255 << 24) | (255 << 16) | (255 << 8) | 255, // White
			(255 << 24) | (74 << 16)  | (156 << 8) | 173, // Weird hidden color, bluish
	};
	public GameEdit(byte[] b) {
		super(b);
		index = new GameIndex(file);
		music = new GameMusic(file);
	}
	public GameEdit(String s)
	{
		super(s);
		index = new GameIndex(file);
		music = new GameMusic(file);
	}
	
	// Background: 0x100-0x30FF
		// 0x20 bytes arranged into an 8x8 square. Horiztonal, then vertical
		// Pixel order is "backwards". One black and one white pixel = 0xE1, not 0x1E
		// Colors in RGB: Same as preview pic.
	public int getBackgroundPixel(int x, int y)
	{
		
		int offset=0x100;
		offset+=(y/8)*0x300;
		offset+=(x/8)*0x20;
		offset+=(y%8)*4;
		offset+=(x%8)/2;
		byte mybyte = file[offset];
		int realb;
		if(x%2!=0)
		{
			realb = (mybyte& 0xF0) >> 4;
		}else{
			realb = (mybyte&0x0F);
		}
		return realb;
	}
	public void setBackgroundPixel(int x, int y, int color)
	{
		int offset=0x100;
		offset+=(y/8)*0x300;
		offset+=(x/8)*0x20;
		offset+=(y%8)*4;
		offset+=(x%8)/2;
		byte mybyte = file[offset];
		byte realb;
		if(x%2!=0)
		{
			realb = (byte) ((color<<4)|(mybyte&0x0F));
		}else{
			realb = (byte) (color|(mybyte&0xF0));
		}
		file[offset]=realb;
		
	}
	public int getPreviewPixel(int x, int y)
	{
		// So much to do so much to see So much to do so much to see So much to do so much to see So much to do so much to see So much to do so much to see So much to do so much to see So much to do so much to see So much to do so much to see So much to do so much to see So much to do so much to see So much to do so much to see So much to do so much to see So much to do so much to see 
		int offset=0xEA80;
		offset+=(y/8)*0x180;
		offset+=(x/8)*0x20;
		offset+=(y%8)*4;
		offset+=(x%8)/2;
		byte mybyte = file[offset];
		int realb;
		if(x%2!=0)
		{
			realb = ((mybyte&0xFF) & 0xF0) >> 4;
		}else{
			realb = ((mybyte&0xFF) & 0x0F);
		}
		return realb;
	}
	public void setPreviewPixel(int x, int y, int color)
	{
		int offset=0xEA80;
		offset+=(y/8)*0x180;
		offset+=(x/8)*0x20;
		offset+=(y%8)*4;
		offset+=(x%8)/2;
		byte mybyte = file[offset];
		byte realb;
		if(x%2!=0)
		{
			realb = (byte) ((color<<4)|(mybyte&0x0F));
		}else{
			realb = (byte) (color|(mybyte&0xF0));
		}
		file[offset]=realb;
	}
	public void setGameIndex(GameIndex ind)
	{
		for(int i = 0xB104; i<0xB904; i++)
		{
			file[i]=index.index[i-0xB104];
		}
	}
	public int getArtPixel(int object, int art, int frame, int x, int y)
	{
		GameObject obj = index.getGameObject(object);
		GameArt a = obj.getGameArt(art);
		int offset = a.getArtOffset(frame)&0xFF;
		offset*=0x80;
		offset+=0x3104;
		offset+=(y/8)*0x40*(obj.getObjectSize()+1);
		offset+=(x/8)*0x20;
		offset+=(y%8)*4;
		offset+=(x%8)/2;
		byte mybyte = file[offset];
		int realb;
		if(x%2!=0)
		{
			realb = ((mybyte&0xFF) & 0xF0) >> 4;
		}else{
			realb = ((mybyte&0xFF) & 0x0F);
		}
		return realb;
	}
	public void setArtPixel(int object, int art, int frame, int x, int y, int color)
	{
		GameObject obj = index.getGameObject(object);
		GameArt a = obj.getGameArt(art);
		int offset = a.getArtOffset(frame)&0xFF;
		offset*=0x80;
		offset+=0x3104;
		offset+=(y/8)*0x40*(obj.getObjectSize()+1);
		offset+=(x/8)*0x20;
		offset+=(y%8)*4;
		offset+=(x%8)/2;
		byte mybyte = file[offset];
		byte realb;
		if(x%2!=0)
		{
			realb = (byte) ((color<<4)|(mybyte&0x0F));
		}else{
			realb = (byte) (color|(mybyte&0xF0));
		}
		file[offset]=realb;
	}
	// objNum: 0-14;
	// size: 0-3;
	// 
	public void addObject(int objNum, int size, String objectname, String art1name)
	{
		int freeSlots = index.getFreeSlots();
		if(freeSlots>=Globals.objectSize[size])
		{
			defragment(); // Defragment twice because paranoia. Ok not as paranoid now
			GameArt a = new GameArt(art1name, index.getFreeSlotList().get(0));
			GameObject o = new GameObject(size, objectname, a);
			index.setGameObject(o, objNum);
			this.setGameIndex(index);
			//defragment(); // TECHNICALLY not necessary. 
		}else{
			return;
		}
	}
	public void removeObject(int which)
	{
		index.removeGameObject(which);
		defragment();
	}
	public GameMusic getGameMusic()
	{
		return music;
	}
	public void setGameMusic(GameMusic gm)
	{
		for(int i = 0xB961; i<0xBA74; i++)
		{
			file[i]=music.music[i-0xB961];
		}
	}
	// The fustercluck of AI.
	// objNum: this is obvious
	// section: 0-5
	// 0 = Start
	// 1-5 = AI
	// Object 1 starts at 0xBBB9
	
	public ArrayList<Byte> getAI(int objNum, int section)
	{
		ArrayList<Byte> ai = new ArrayList<Byte>();
		if(section==0)
		{
			// 0x0 = ?, seems to be 4
			// 0x1 = Art state left shifted 4
			// 0x2 = Art command (hold/play once/loop) left shifted 4
			// 0x3 = Art speed left shifted 4
			
		}else{
			
		}
		
		
		return ai;
	}
	public void setAI(int objNum, int section, ArrayList<Byte> ai)
	{
		
	}
	// Not neccessary??????!?!??
	// Actually yes it is. It works better than ninty's. 
	// But I suppose defragmenting on flash isn't a good idea.
	private void defragment()
	{

		ArrayList<Integer> slots = index.getFreeSlotList();
		int firstAnomaly=-1;
		boolean needsDefragmenting = false;
		for(int i = 0; i<slots.size()-1; i++)
		{
			if(slots.get(i)+1!=slots.get(i+1))
			{
				needsDefragmenting=true;
				firstAnomaly=slots.get(i)+1;
				System.out.println(slots);
				break;
			}
		}
		if(needsDefragmenting&&firstAnomaly!=-1)
		{
			int[] bad = index.findGameArtFromOffset(firstAnomaly);
			if(bad[0]==-1)
			{
				System.out.println("Halp");
			}else{
				GameObject obj = index.getGameObject(bad[0]);
				GameArt art = obj.getGameArt(bad[1]);
				int multiplier = (obj.getObjectSize()+1)*(obj.getObjectSize()+1);
				int offset = 0x3104+(art.getArtOffset(bad[2])&0xFF)*0x80;
				byte[] tmp = new byte[multiplier*0x80];
				for(int i = 0; i<tmp.length; i++)
				{
					tmp[i]=file[offset+i];
					file[offset+i]=0;
				}
				offset=0x3104+(index.getFreeSlotList().get(0))*0x80;
				for(int i = 0; i<tmp.length; i++)
				{
					file[offset+i]=tmp[i];
				}
				art.setArtOffset(bad[2], (byte)((int)index.getFreeSlotList().get(0)));
				obj.setGameArt(art, bad[1]);
				index.setGameObject(obj, bad[0]);
				this.setGameIndex(index);
				defragment();
			}
			
		}
	}
	public class GameMusic
	{
		public byte[] music;
		public GameMusic(byte[] b)
		{
			music = new byte[0x114];
			for(int i = 0xB961; i<0xBA74; i++)
			{
				music[i-0xB961]=b[i];
			}
		}
		public byte getVolume(int track)
		{
			return music[0x100+track];
		}
		// Up to 1 blocks of 32 notes, 5 tracks 0-4, volume 0-4
		public void setVolume(int track, byte volume)
		{
			music[0x100+track]=volume;
		}
		// Up to 1 blocks of 32 notes, 5 tracks 0-4
		public byte getPanning(int track)
		{
			return music[0x105+track];
		}
		// Up to 1 blocks of 32 notes, 5 tracks 0-4, panning 0-4
		public void setPanning(int track, byte panning)
		{
			music[0x105+track]=panning;
		}
		// Up to 1 blocks of 32 notes, 4 tracks 0-3
		public byte getInstrument(int track)
		{
			return music[0x10A+track];
		}
		// Up to 1 blocks of 32 notes, 4 tracks 0-3, 48 instruments 0-2F
		public void setInstrument(int track, byte instrument)
		{
			music[0x10A+track]=instrument;
		}
		// Up to 1 blocks of 32 notes
		public byte getDrumset(int block)
		{
			return music[0x10E];
		}
		// Up to 1 blocks of 32 notes, 8 drumsets 0-7
		public void setDrumset(byte drumset)
		{
			music[0x10E]=drumset;
		}
		// Up to 1 blocks of 32 notes, 4 tracks 0-3, 32 notes 0-1F
		public int getNote(int track, int position)
		{
			return music[track*0x20+position];
		}
		// Up to 1 blocks of 32 notes, 4 tracks 0-3, 32 notes 0-1F, 25 pitches 0-18
		public void setNote(int track, int position, byte note)
		{
			music[track*0x20+position]=note;
		}
		// Up to 1 blocks of 32 notes, Up to 4 drums 0-3, 32 notes 0-1F
		public byte getDrum( int drumNumber, int position)
		{
			return music[0x80+0x20*drumNumber+position];
		}
		// Up to 1 blocks of 32 notes, Up to 4 drums 0-3, 32 notes 0-1F, 14 drums 0-D
		public void setDrum( int drumNumber, int position, byte drum)
		{
			music[0x80+0x20*drumNumber+position]=drum;
		}
		// Finds note number from given string (e.g. "Low-Sol")
		public byte findNote(String n)
		{
			for(int i = 0; i<Globals.noteCodes.length; i++)
			{
				if(n==Globals.noteCodes[i])
				{
					return (byte)i;
				}
			}
			return 0;
		}
		// Finds note number from given string, real note names (e.g. "Low-G")
		public byte findPianoNote(String n)
		{
			for(int i = 0; i<Globals.noteCodesPiano.length; i++)
			{
				if(n==Globals.noteCodesPiano[i])
				{
					return (byte)i;
				}
			}
			return 0;
		}
	}
	public class GameIndex
	{
		public byte[] index;
		public GameIndex(byte[] b)
		{
			index = new byte[2048];
			for(int i = 0xB104; i<0xB904; i++)
			{
				index[i-0xB104]=b[i];
			}
		}
		public int getFreeSlots()
		{
			//defragment();
			return getFreeSlotList().size();
		}
		
		public int[] findGameArtFromOffset(int offset)
		{
			int[] rets = {-1,-1,-1};
			boolean broken = false;
			for(int i = 0; i<15; i++)
			{
					GameObject obj = getGameObject(i);
					if(obj.isReal())
					{
						for(int o = 0; o<4; o++)
						{
							GameArt art = obj.getGameArt(o);
							if(art.isReal())
							{
								for(int q = 0; q<art.getArtFrames(); q++)
								{
									int test=art.getArtOffset(q)&0xFF;
									if(test==offset)
									{
										rets[0]=i; // Object
										rets[1]=o; // Art
										rets[2]=q; // Frame
										broken=true;
										break;
									}
								}
							}
							if(broken)
								break;
						}
					}
				if(broken) // Badness
					break;
			}
			return rets;
		}
		public GameObject getGameObject(int which)
		{
			return new GameObject(index, which);
		}
		public void setGameObject(GameObject obj, int which)
		{
			int offset=which*0x88;
			for(int i = offset; i<offset+0x88; i++)
			{
				index[i]=obj.object[i-offset];
			}
		}
		public void removeGameObject(int which)
		{
			int offset=which*0x88;
			for(int i = offset; i<offset+0x88; i++)
			{
				index[i]=0;
			}
		}
		private ArrayList<Integer> getFreeSlotList()
		{
			ArrayList<Integer> free = new ArrayList<Integer>();
			for(int i = 0; i<256; i++)
			{
				free.add(i,i);
			}
			for(int i = 0; i<15; i++)
			{
				GameObject obj = getGameObject(i);
				int trueSize=0;
				trueSize=(obj.getObjectSize()+1)*(obj.getObjectSize()+1);
				if(obj.isReal())
				{
					for(int o = 0; o<4; o++)
					{
						GameArt art = obj.getGameArt(o);
						if(art.isReal())
						{
							for(int q = 0; q<art.getArtFrames(); q++)
							{
								int offset=art.getArtOffset(q)&0xFF;
								for(int z = offset; z<trueSize+offset; z++)
								{
									free.set(z, -1);
								}
							}
						}
					}
				}
			}
			free.removeAll(Collections.singleton(-1));
			return free;
		}
		
	}
	public class GameObject
	{
		private byte[] object;
		// Index info size = 0x88
		// 0x00 = 0x00-0x03. Indicates the size of the object
		// 0x01 looks to be always 1, possibly denoting section 2
		// 0x02-0x14 reserved for title/padding of whole object. Some characters have 2 bytes per char hence the length of 18 bytes for 9 characters
		// 0x15 is padding
		// 0x16th byte is the animation speed (0-4)
		// 0x17th byte is the number of frames in the first art state
		// 0x18-0x1B are the indicate the offset for each animation state
		// 0x1C-0x2D is the title of animation state 1
		// 0x2E-0x31 is padding
		// Repeat 0x16-0x31 three times for art states 2-4
		
		// i=0-14 please.
		public GameObject(byte[] index, int which)
		{
			int offset=which*0x88;
			object = new byte[0x88];
			for(int i = offset; i<offset+0x88; i++)
			{
				object[i-offset]=index[i];
			}
		}
		// or Create one new game art before stuff;
		public GameObject(int size, String title, GameArt newArt)
		{
			object = new byte[0x88];
			object[0]=(byte) size;
			object[1]=1;
			for(int i = 2; i<0x14; i++)
			{
				if((i-2)>=title.length())
					break;
				object[i]=(byte) title.charAt(i-2);
			}
			for(int i = 0x15; i<0x31; i++)
			{
				object[i] = newArt.art[i-0x15];
			}
		}
		public boolean isReal()
		{
			return object[1]!=0;
		}
		public String getObjectTitle()
		{
			StringBuilder objTitle=new StringBuilder();
			for(int i = 2; i<0x14; i++)
			{
				if(object[i]==0)
				{
					break;
				}
				if((object[i]&0xFF)>=0x80) // Unicode-type thing
				{
					if((object[i]&0xFF)>=0xE0) // Japan, why u have 3 byte letters
					{
						int key=(object[i]&0xFF)<<16|(object[i+1]&0xFF)<<8|(object[i+2]&0xFF);
						if(Globals.translation.containsKey(key))
							objTitle.append(Globals.translation.get(key));
						else if(key>=0xe38080&&key<=0xe3f02f){
							byte[] bbb = new byte[3];
							bbb[0]=object[i];
							bbb[1]=object[i+1];
							bbb[2]=object[i+2];
							try {
								objTitle.append(new String(bbb,"UTF-8"));
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						else
							objTitle.append("?");
						i++;
						i++;
					}else{
						int key=(object[i]&0xFF)<<8|(object[i+1]&0xFF);
						if(Globals.translation.containsKey(key))
							objTitle.append(Globals.translation.get(key));
						else
							objTitle.append("?");
						i++; //two.
					}
				}else{
					objTitle.append((char)object[i]);
				}
			}
			return objTitle.toString();
		}
		public void setObjectTitle(String title)
		{
			char[] c = title.toCharArray();
			for(int i = 2; i<0x14; i++)
			{
				object[i]=(byte) c[i-2];
			}
		}
		public GameArt getGameArt(int state)
		{
			return new GameArt(object, state);
		}
		public void setGameArt(GameArt art, int which)
		{
			int offset=0x15;
			offset+=0x1C*which;
			for(int i = offset; i<offset+0x1C; i++)
			{
				object[i]=art.art[i-offset];
			}
		}
		// 0 = small
		// 1 = medium
		// 2 = large
		// 3 = x-large
		public int getObjectSize()
		{
			return object[0];
		}
		public void setObjectSize(byte s)
		{
			object[0]=s;
		}
		
	}
	public class GameArt
	{
		private byte[] art;
		int offset=0x15;
		// state 0-3
		public GameArt(byte[] ob, int state)
		{
			offset+=0x1C*state;
			art = new byte[0x1C];
			for(int i = offset; i<offset+0x1C; i++)
			{
				art[i-offset]=ob[i];
			}
		}
		public GameArt(String title, int offset)
		{
			// Organization is terrible;
			art = new byte[0x1C];
			art[0]=2; // 2 or 4;
			art[1]=1;
			art[2]=(byte) offset;
			System.out.println(title);
			for(int i = 6; i<0x18; i++)
			{
				if((i-6)>=title.length())
					break;
				art[i]=(byte) title.charAt(i-6);
			}
		}
		public boolean isReal()
		{
			// Should always be greater than 0
			return art[1]!=0;
		}
		public String getArtTitle()
		{
			StringBuilder artTitle=new StringBuilder();
			for(int i = 6; i<0x18; i++)
			{
				
				if(art[i]==0)
				{
					break;
				}
				if((art[i]&0xFF)>=0x80) // Unicode-type thing
				{
					if((art[i]&0xFF)>=0xE0) // Japan, why u have 3 byte letters
					{
						int key=(art[i]&0xFF)<<16|(art[i+1]&0xFF)<<8|(art[i+2]&0xFF);
						if(Globals.translation.containsKey(key))
							artTitle.append(Globals.translation.get(key));
						else if(key>=0xe38080&&key<=0xe3f02f){
							byte[] bbb = new byte[3];
							bbb[0]=art[i];
							bbb[1]=art[i+1];
							bbb[2]=art[i+2];
							try {
								artTitle.append(new String(bbb,"UTF-8"));
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else
							artTitle.append("?");
						i++;
						i++;
					}else{
						int key=(art[i]&0xFF)<<8|(art[i+1]&0xFF);
						if(Globals.translation.containsKey(key))
							artTitle.append(Globals.translation.get(key));
						else
							artTitle.append("?");
						i++; //two.
					}
				}else{
					artTitle.append((char)art[i]);
				}
			}
			//System.out.println(objTitle);
			return artTitle.toString();
		}
		// Art State name
		public void setArtTitle(String title)
		{
			char[] c = title.toCharArray();
			for(int i = 6; i<0x18; i++)
			{
				art[i]=(byte) c[i-2];
			}
		}
		public byte getArtFrames() // Number of frames
		{
			return art[1];
		}
		public void setArtFrames(byte num)
		{
			art[1]=num;
		}
		public byte getArtOffset(int whichFrame)
		{
			return art[2+whichFrame];
		}
		public void setArtOffset(int whichFrame, byte offset)
		{
			art[2+whichFrame]=offset;
		}
		public byte getArtSpeed()
		{
			// Speed 0-4
			return art[0];
		}
		public void setArtSpeed(byte speed)
		{
			art[0]=speed;
		}
	}
}
