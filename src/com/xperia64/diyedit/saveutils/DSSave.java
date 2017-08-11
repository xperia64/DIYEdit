package com.xperia64.diyedit.saveutils;

import java.util.ArrayList;

// Seems to work...
public class DSSave implements Save {

	// 0x2C31-0x2CA8 are medals
	// 0x5C31-0x5CA8 are backup medals
	// 06 == medal get
	// 00 = no medal get
	
	// 0x950-0x9A9 = Diamond Games
	// 0x9AF-0xA08 = Diamond Records
	// 0xA0E-0xA67 = Diamond Comics 
	// 0x3950-0x39A9 = Diamond Games Backup
	// 0x39AF-0x3A08 = Diamond Records Backup
	// 0x3A0E-0x3A67 = Diamond Comics Backup
	// 0x00 = not unlocked
	// 0x01 = unlocked & new
	// 0x02 = unlocked & not new

	//0xA60 NO
	//0x3A60 NO
	// Possible forum bytes: ****0xA8B, 0xA8C****, 0x2B2E, ????0x2B30, ????0x2B32, 0x2BB1, 0x2BB2, 0x2C55, 0x2D7E
	//
	// Forum unlock status: 0x2BB1-0x2C30 OR 0x5BB1-0x5C30, 128 entries in forum MAX
	//
	// Backup Bytes: 0x36B6, 0x36C8, 0x36E2, ****0x3A8B, 0x3A8C****, 0x5B2E 0x5C55
	private byte[] saveFile; // WarioWare D.I.Y. 32MByte save file
	private int save_offset=0;
	//private byte[] x2; // Mio file to inject
	//private int m; // Mode ( 0 = game, 1 = record, 2 = manga )
	//private int n; // Slot number on shelf, 0-89
	public DSSave(byte[] read1) {
		saveFile=read1;
		int pos=0, ver1=0, ver2=0;
		while (pos < 4)
	    {
	        ver1 += (saveFile[(int) (20 + pos)]&0xFF) << (8 * pos);
	        
	        pos += 1;
	    }
		//System.out.println(ver1);
		pos = 0;
	    while (pos < 4)
	    {
	        ver2+=(saveFile[(int) (12308 + pos)]&0xFF) << (8 * pos);
	        
	        pos += 1;
	    }
	    //System.out.println(ver2);
	    //int save_offset=0;
	    if (ver1 > ver2){
	        save_offset = 0;}
	    else
	    {
	        save_offset = 12288;
	    }
	}
	
	// Get mio file on shelf slot n
	// I don't understand how the index system works either so don't ask
	
	// OK I kinda do now
	public ArrayList<byte[]> getMioList(int mode)
	{
		double data_start=0;
		double data_index=0;
		double data_size=0;
		switch(mode)
		{
		case 0:
            data_start = 18;
            data_index = 1760; //0x6E0
            data_size = 65536;
            break;
		case 1:
			data_start = 146;
			data_index = 1942; //0x796
			data_size = 8192;
			break;
		case 2:
			data_start = 162;
            data_index = 2124; //0x84C
            data_size = 14336;
            break;
		default:
			return null;
		}
		int pos=0;
	    
	    ArrayList<byte[]> mios = new ArrayList<byte[]>();
	    for(int i = 0; i<90; i++ )
	    {
	    	pos=2*i;
	    	int gamepos = 0, slot;
	        slot = saveFile[(int) (pos+save_offset+data_index)];
	        //System.out.println(slot);
	        if (slot == 0)
	        {
	            mios.add(null);
	        }
	        else
	        {
	            gamepos = (int) ((data_start * 65536) + ((slot - 1) * data_size));
	            pos += 2;
		        byte[] item = new byte[(int) data_size];
		        for(int o = gamepos; o<(item.length+gamepos); o++)
		        {
		        	item[o-gamepos]=saveFile[o];
		        }
		        mios.add(item);
	        }
	        
	    }
	    //System.out.println("pos: "+pos);
	        
	        return mios;
	}
	@Override
	public void setMio(int slotNumber, byte[] mio) {
		int mode=0;
		switch(mio.length)
		{
		case 8192:
			mode=1;
			break;
		case 14336:
			mode=2;
			break;
		case 65536:
			mode=0;
			break;
		}
		setMio(slotNumber, mode, mio, false);
	}
	@Override
	public void deleteMio(int mode, int slotNumber) {
		setMio(slotNumber, mode, null, true);
	}
	public byte[] saveChanges() {
		double sum;
		int pos;
		 	saveFile[save_offset+16]=0;
		    saveFile[save_offset+17]=0;
		    saveFile[save_offset+18]=0;
		    saveFile[save_offset+19]=0;
		    sum = 0;
		    for(int i = 0; i<0x3000; i++)
		    {
		    	sum+=(saveFile[save_offset+i]&0xFF);
		    }
		    System.out.println(sum);
		    pos = 0;
		    while (pos < 4)
		    {
		        saveFile[save_offset + 16 + pos]=(byte) (((int)sum >> (8 * pos)));
		        pos += 1;
		    }
		return saveFile;
	}
	// Boolean used to specify whether to set
	// or delete the mio file at slot n
	private void setMio(int slotNumber, int mode, byte[] mio, boolean delete) {
		int pos=0;
		double data_start=0;
		double data_index=0;
		double data_size=0;
		switch(mode)
		{
		case 0:
            data_start = 18;
            data_index = 1760;
            data_size = 65536;
			System.out.println("Game");
            break;
		case 1:
			data_start = 146;
			data_index = 1942;
			data_size = 8192;
			System.out.println("Record");
			break;
		case 2:
			data_start = 162;
            data_index = 2124;
            data_size = 14336;
			System.out.println("Manga");
            break;
		default:
			System.out.println("Default");
			//return;
		}
		
	    
	    if (delete)
	    {
	            int slot;
	            slot = slotNumber* 2;
	            saveFile[(int) (save_offset + data_index + slot)]=0;
	    	    return;
	        }
	        else
	        {
	            int game_offset, off;
	            ArrayList<Integer> free_offsets = new ArrayList<Integer>();
	            game_offset = 0;
	            pos = 0;
	            while (pos < 90)
	            {
	                free_offsets.add(pos + 1);
	                pos += 1;
	            }
	            pos = 0;
	            int newoffset=0;
	            while (pos < 184)
	            {
	                newoffset= (int) (save_offset + data_index + pos);
	                if (pos == 180)
	                {
	                    if (mio.length == 65536)
	                        newoffset=(save_offset + 1748);
	                    if (mio.length == 8192)
	                    	newoffset=(save_offset + 1752);
	                    if (mio.length == 14336)
	                    	newoffset=(save_offset + 1756);
	                }
	                if (pos == 182)
	                {
	                    if (mio.length == 65536)
	                    	 newoffset=(save_offset + 1750);
	                    if (mio.length == 8192)
	                    	 newoffset=(save_offset + 1754);
	                    if (mio.length == 14336)
	                    	 newoffset=(save_offset + 1758);
	                }
	                off = saveFile[newoffset];
	                int p;
	                p = 0;
	                while (p < free_offsets.size())
	                {
	                    if (free_offsets.get(p) == off)
	                        free_offsets.remove(p);
	                    p += 1;
	                }
	                pos += 2;
	            }
	            if (mio.length == 65536)
	            {
	                pos = 0;
	                while (pos < 32)
	                {
	                    off = saveFile[save_offset + 2306 + pos];
	                    int p;
	                    p = 0;
	                    while (p < (free_offsets).size())
	                    {
	                        if (free_offsets.get(p) == off)
	                            free_offsets.remove(p);
	                        p += 1;
	                    }
	                    pos += 2;
	                }
	            }
	            //System.out.println(free_offsets.size());
	            if(free_offsets.size()<=0)
	            {
	            	deleteMio(mode,slotNumber);
	            	setMio(slotNumber, mode, mio, delete);
	            }else
	            if (mio.length == data_size && free_offsets.size() > 0)
	            {
	                game_offset = free_offsets.get(0);
	                saveFile[(int) (save_offset + data_index + (slotNumber * 2))]=(byte) game_offset;
	                for(int i = 0; i<mio.length; i++)
	                {
	                	saveFile[(int) ((data_start * 65536) + ((game_offset - 1) * data_size))+i]=mio[i];
	                }
	            }
	            else
	            	System.out.println("Bad.");
	            
	    }
	   
	}
	public void unlockMedals()
	{
		for(int i = 11313; i<11433; i++ )
		{
			saveFile[i]=6;
		}
		for(int i = 23601; i<23721; i++ )
		{
			saveFile[i]=6;
		}
	}
	public void unlockGames()
	{
		for(int i = 2384; i<2474; i++ )
		{
			saveFile[i]=1;
		}
		for(int i = 14672; i<14762; i++ )
		{
			saveFile[i]=1;
		}
	}
	public void unlockRecords()
	{
		for(int i = 2479; i<2569; i++ )
		{
			saveFile[i]=1;
		}
		for(int i = 14767; i<14857; i++ )
		{
			saveFile[i]=1;
		}		
	}
	public void unlockManga()
	{
		for(int i = 2574; i<2664; i++ )
		{
			saveFile[i]=1;
		}
		for(int i = 14862; i<14952; i++ )
		{
			saveFile[i]=1;
		}
	}
	public void unlockForum()
	{
		// Doesn't work
		// Uses some weird index system again.
		// I managed to glitch it once and have the same 'post' appear over and over
		return;
	}
	public void release()
	{
		saveFile=null;
	}
	// Fix bytes because -128 is needed for some reason in java
	// Perhaps chars could be use instead
	// Java y u no have unsigned numbers other than char
	/*private ArrayList<Integer> delete(ArrayList<Integer> a, int index)
	{
		ArrayList<Integer> n= new ArrayList<Integer>();
		for(int i = 0; i<a.size(); i++)
		{
			if(i!=index)
			{
				n.add(a.get(i));
			}
		}
		return n;
	}*/

}
