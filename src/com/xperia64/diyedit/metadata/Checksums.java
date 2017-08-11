package com.xperia64.diyedit.metadata;

public class Checksums {
	
	//Check if the checksums are correct
	//For mio files only
	//Originally from crygortool
	public static int readChecksums(byte[] b)
	{
		
		int checkSumOne=0;
		int checkSumTwo=0;
		int fileSumOne=((b[19]&0xFF)<<24)|((b[18]&0xFF)<<16)|((b[17]&0xFF)<<8)|(b[16]&0xFF);
		int fileSumTwo=((b[23]&0xFF)<<24)|((b[22]&0xFF)<<16)|((b[21]&0xFF)<<8)|(b[20]&0xFF);
		for(int i = 0; i<256; i++)
		{
			if(!(i>=16&&i<23))
			{
				checkSumOne+=(b[i]&0xFF);
			}
		}
		for(int i = 256; i<b.length; i++)
		{
			checkSumTwo+=(b[i]&0xFF);
		}
		if(checkSumOne==fileSumOne&&checkSumTwo==fileSumTwo)
		{
			return 0;
		}else if(checkSumOne!=fileSumOne&&checkSumTwo==fileSumTwo){
			return 1;
		}else if(checkSumOne==fileSumOne&&checkSumTwo!=fileSumTwo){
			return 2;
		}else{
			return 3;
		}
	}
	// Write checksums to the byte array and optionally to the file
	public static byte[] writeChecksums(byte[] b)
	{
		int checkSumOne=0;
		int checkSumTwo=0;
		for(int i = 16; i<23; i++)
		{
			b[i]=0;
		}
		for(int i = 0; i<256; i++)
		{
			checkSumOne+=(b[i]&0xFF);
		}
		for(int i = 256; i<b.length; i++)
		{
			checkSumTwo+=(b[i]&0xFF);
		}
		b[19]=(byte) ((checkSumOne>>24)&0xFF);
		b[18]=(byte) ((checkSumOne>>16)&0xFF);
		b[17]=(byte) ((checkSumOne>>8)&0xFF);
		b[16]=(byte) ((checkSumOne)&0xFF);
		b[23]=(byte) ((checkSumTwo>>24)&0xFF);
		b[22]=(byte) ((checkSumTwo>>16)&0xFF);
		b[21]=(byte) ((checkSumTwo>>8)&0xFF);
		b[20]=(byte) ((checkSumTwo)&0xFF);
		return b;
	}

	
} 

