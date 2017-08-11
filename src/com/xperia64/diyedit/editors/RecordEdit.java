package com.xperia64.diyedit.editors;

import com.xperia64.diyedit.Globals;
import com.xperia64.diyedit.metadata.RecordMetadata;

// Class for editing records
public class RecordEdit extends RecordMetadata{
	// Up to 24 blocks of 32 notes, 5 tracks 0-4
	public RecordEdit(byte[] b)
	{
		super(b);
	}
	public RecordEdit(String f)
	{
		super(f);
	}
	public byte getVolume(int block, int track)
	{
		/**@param block: 0-24*/
		//track=0-4;
		int offset;
		//get block
		offset=0x107+0x114*block+0x100+track;
		//for(offset=0x107; offset<block*0x114; offset+=0x114);
		//offset+=(0x100+track);
		return file[offset];
		
	}
	// Up to 24 blocks of 32 notes, 5 tracks 0-4, volume 0-4
	public void setVolume(int block, int track, byte volume)
	{
		int offset;
		//get block
		offset=0x107+0x114*block+0x100+track;
		//for(offset=0x107; offset<block*0x114; offset+=0x114);
		//offset+=(0x100+track);
		file[offset]=volume;
	}
	// Up to 24 blocks of 32 notes, 5 tracks 0-4
	public byte getPanning(int block, int track)
	{
		int offset;
		//get block
		offset=0x107+0x114*block+0x105+track;
		//for(offset=263; offset<block*276; offset+=276);
		//offset+=(261+track);
		return file[offset];
	}
	// Up to 24 blocks of 32 notes, 5 tracks 0-4, panning 0-4
	public void setPanning(int block, int track, byte panning)
	{
		int offset;
		//get block
		offset=0x107+0x114*block+0x105+track;
		//for(offset=263; offset<block*276; offset+=276);
		//offset+=(261+track);
		file[offset]=panning;
	}
	// Up to 24 blocks of 32 notes, 4 tracks 0-3
	public byte getInstrument(int block, int track)
	{
		int offset;
		//get block
		offset=0x107+0x114*block+0x10A+track;
		return file[offset];
	}
	// Up to 24 blocks of 32 notes, 4 tracks 0-3, 48 instruments 0-2F
	public void setInstrument(int block, int track, byte instrument)
	{
		int offset=0x107+0x114*block+0x10A+track;;
		file[offset]=instrument;
	}
	// Up to 24 blocks of 32 notes
	public byte getDrumset(int block)
	{
		int offset;
		//get block
		offset=0x107+0x114*block+0x10A+4;
		return file[offset];
	}
	// Up to 24 blocks of 32 notes, 8 drumsets 0-7
	public void setDrumset(int block, byte drumset)
	{
		int offset;
		//get block
		offset=0x107+0x114*block+0x10A+4;
		file[offset]=drumset;
	}
	// Up to 24 blocks of 32 notes, 4 tracks 0-3, 32 notes 0-1F
	public int getNote(int block, int track, int position)
	{
		int offset;
		//get block
		offset=0x107+0x114*block+track*0x20+position;
		return file[offset];
	}
	// Up to 24 blocks of 32 notes, 4 tracks 0-3, 32 notes 0-1F, 25 pitches 0-18
	public void setNote(int block, int track, int position, byte note)
	{
		int offset;
		//get block
		offset=0x107+0x114*block+track*0x20+position;
		file[offset]=note;
	}
	// Up to 24 blocks of 32 notes, Up to 4 drums 0-3, 32 notes 0-1F
	public byte getDrum(int block, int drumNumber, int position)
	{
		int offset;
		//get block
		offset=0x107+0x114*block+0x80+drumNumber*0x20+position;
		return file[offset];
	}
	// Up to 24 blocks of 32 notes, Up to 4 drums 0-3, 32 notes 0-1F, 14 drums 0-D
	public void setDrum(int block, int drumNumber, int position, byte drum)
	{
		int offset;
		//get block
		offset=0x107+0x114*block+0x80+drumNumber*0x20+position;
		file[offset]=drum;
	}
	// Finds note number from given string (e.g. "Low-Sol")
	public byte findNote(String n)
	{
		for(int i = 0; i<Globals.noteCodes.length; i++)
		{
			if(n.equals(Globals.noteCodes[i]))
			{
				return (byte)i;
			}
		}
		return (byte) 0;
	}
	// Finds note number from given string, real note names (e.g. "Low-G")
	public byte findPianoNote(String n)
	{
		for(int i = 0; i<Globals.noteCodesPiano.length; i++)
		{
			if(n.equals(Globals.noteCodesPiano[i]))
			{
				return (byte)i;
			}
		}
		return (byte) 0;
	}
	public boolean getSwing()
	{
		return file[256]!=0;
	}
	public void setSwing(boolean b)
	{
		file[256]=(byte) (b?1:0);
	}
	public int getTempo()
	{
		return (file[257]*10)+60;
	}
	public void setTempo(int t)
	{
		file[257]=(byte) ((t-60)/10);
	}
	
}
