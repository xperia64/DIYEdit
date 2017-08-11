package com.xperia64.diyedit.saveutils;

import java.util.ArrayList;

public class WiiSave implements Save {

	private byte[] wiiSave; // Wii Savefile
	public byte[] sys0; // SYS_0
	public byte[] sys1; // SYS_1
	//private byte[] x2; // Miofile
	//private int n; // Shelf position 0-77
	//0xF and 0x6B are incremented on each save in GDATA when games are modified in any way, including high scores.
	//High scores are stored somewhere around 0x360 in GDATA
	//0x460-0x4A7 look like the state of the game, perhaps whether its been played or not.
	//0x0-0x3 look like the checksum in GDATA and SYS_0 and SYS_1
	//0xE-0xF, 0x22-0x23, 0x462-0x463, 0x8A2-0x8A3 are incremented by 0x2 each save in SYS_0 and SYS_1.
	//Every save switches between SYS_0 and SYS_1, hence the incrementing by 2. The one with the lower number is saved to next, probably for backup purposes. This looks the same as the DS save's duplicate indexes.
	//Checksums do not look unencrypted, but they make no sense (e.g. there aren't enough bytes in the file to add up to the values they give). They also look like they are read left to right unlike the in the DS version's save.
	//Maybe XOR padding. Why is it neccessary?
	// Index: 0x20-0x67 in GDATA, RDATA, and MDATA.
	
	// MAGIC WII SAVE CHECKSUM NUMBERS (add them):
	
	// SYS_*: 0x3CBC
	// GDATA: 0x4804BC
	// MDATA: 0xFC4BC
	// RDATA: 0x904BC
	// The checksums are not implemented for now and WWDIY showcase will threaten you by saying everything is corrupt
	// and needs to be deleted. It doesn't really delete anything, it just fixes the checksums and the save icon.

	// NOTE: Checksums are actually implemented. Simple 32-bit addition. Comments kept for historical reasons.
	public WiiSave(byte[] read1, byte[] read2, byte[] read3)
	{
		wiiSave=read1;
		sys0=read2;
		sys1=read3;
	}
	@Override
	public ArrayList<byte[]> getMioList(int mode) {
		double data_size=0;
		switch(mode)
		{
		case 0:
            data_size = 65536;
            break;
		case 1:
			data_size = 8192;
			break;
		case 2:
            data_size = 14336;
            break;
		default:
			return null;
		}
		
	    
	    ArrayList<byte[]> mios = new ArrayList<byte[]>();
	    for(int i = 0; i<72; i++ )
	    {
	    	
	    	int gamepos = 0, slot=-1;
	        slot = wiiSave[(int) (i+0x20)];
	        //System.out.println(slot);
	            gamepos = (int) (0x4A8+data_size*slot);
	            if(wiiSave[gamepos]==0)
	            {
	            	mios.add(null);
	            }else{
			        byte[] item = new byte[(int) data_size];
			        for(int o = gamepos; o<(item.length+gamepos); o++)
			        {
			        	item[o-gamepos]=wiiSave[o];
			        }	
			        mios.add(item);
	            }	        
	    }
		return mios;
	}
	@Override
	public void setMio(int slotNumber, byte[] mio) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		setMio(slotNumber, mode, null, true);
	}

	private void setMio(int slotNumber, int mode, byte[] mio, boolean delete)
	{
		int sys_offset=0;
		double data_size=0;
		switch(mode)
		{
		case 0:
            data_size = 65536;
            sys_offset = 1048;
			System.out.println("Game");
            break;
		case 1:
			data_size = 8192;
			sys_offset = 2136;
			System.out.println("Record");
			break;
		case 2:
            data_size = 14336;
            sys_offset = 3224;
			System.out.println("Manga");
            break;
		default:
			System.out.println("Default");
			//return;
		}
		if(delete)
		{
			int gamepos, slot;
	        slot = wiiSave[(int) (slotNumber+0x20)];
			gamepos = (int) (0x4A8+data_size*slot);
	        for(int o = gamepos; o<(data_size+gamepos); o++)
	        {
	        	wiiSave[o]=0;
	        }
	        wiiSave[slot+0x460]=0x00;
	        sys0[slot+sys_offset]=0x00;
	        sys1[slot+sys_offset]=0x00;
    	    return;
		}else{
			int gamepos, slot;
	        slot = wiiSave[(int) (slotNumber+0x20)];
			gamepos = (int) (0x4A8+data_size*slot);
	        for(int o = gamepos; o<(data_size+gamepos); o++)
	        {
	        	wiiSave[o]=mio[o-gamepos];
	        }
	        wiiSave[slot+0x460]=0x01;
	        sys0[slot+sys_offset]=0x01;
	        sys1[slot+sys_offset]=0x01;
		}
	}
	public void release()
	{
		wiiSave=null;
		sys0=null;
		sys1=null;
	}
	public ArrayList<byte[]> saveSetChanges() {
		// TODO Auto-generated method stub
		int wiiSaveChecksum = 0;
		switch(wiiSave.length)
		{
			case 4719808:
				wiiSaveChecksum = 0x4804BC;
				break;
			case 591040:
				wiiSaveChecksum = 0x904BC;
				break;
			case 1033408:
				wiiSaveChecksum = 0xFC4BC;
				break;
		}
		for(int i = 4; i<wiiSave.length; i+=4)
		{
			wiiSaveChecksum+=((wiiSave[i]&0xFF)<<24|(wiiSave[i+1]&0xFF)<<16|(wiiSave[i+2]&0xFF)<<8|(wiiSave[i+3]&0xFF));
		}
		int sys0Checksum = 0x3CBC;
		int sys1Checksum = 0x3CBC;
		for(int i = 4; i<sys0.length; i+=4)
		{
			sys0Checksum+=((sys0[i]&0xFF)<<24|(sys0[i+1]&0xFF)<<16|(sys0[i+2]&0xFF)<<8|(sys0[i+3]&0xFF));
		}
		for(int i = 4; i<sys1.length; i+=4)
		{
			sys1Checksum+=((sys1[i]&0xFF)<<24|(sys1[i+1]&0xFF)<<16|(sys1[i+2]&0xFF)<<8|(sys1[i+3]&0xFF));
		}
		wiiSave[0]=(byte) ((wiiSaveChecksum>>24)&0xFF);
		wiiSave[1]=(byte) ((wiiSaveChecksum>>16)&0xFF);
		wiiSave[2]=(byte) ((wiiSaveChecksum>>8)&0xFF);
		wiiSave[3]=(byte) ((wiiSaveChecksum)&0xFF);
		
		sys0[0]=(byte) ((sys0Checksum>>24)&0xFF);
		sys0[1]=(byte) ((sys0Checksum>>16)&0xFF);
		sys0[2]=(byte) ((sys0Checksum>>8)&0xFF);
		sys0[3]=(byte) ((sys0Checksum)&0xFF);
		
		sys1[0]=(byte) ((sys1Checksum>>24)&0xFF);
		sys1[1]=(byte) ((sys1Checksum>>16)&0xFF);
		sys1[2]=(byte) ((sys1Checksum>>8)&0xFF);
		sys1[3]=(byte) ((sys1Checksum)&0xFF);
		ArrayList<byte[]>a = new ArrayList<byte[]>();
		a.add(wiiSave);
		a.add(sys0);
		a.add(sys1);
		return a;
	}

	
	
}
