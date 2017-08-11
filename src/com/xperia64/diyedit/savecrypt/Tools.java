package com.xperia64.diyedit.savecrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Tools {
	
	// Please don't sue me. Dolphin already has all of these. Granted they're scattered, but they are all there.
	
	
	// ---------------------------------------
	// These are from https://github.com/dolphin-emu/dolphin/blob/master/Source/Core/Core/HW/WiiSaveCrypted.cpp
	final static byte[] SDKey = {
			(byte) 0xAB, 0x01, (byte) 0xB9, (byte) 0xD8, (byte) 0xE1, 0x62, 0x2B, 0x08,
			(byte) 0xAF, (byte) 0xBA, (byte) 0xD8, 0x4D, (byte) 0xBF, (byte) 0xC2, (byte) 0xA5, 0x5D
			};
	final static byte[] MD5_BLANKER = {
			0x0E, 0x65, 0x37, (byte) 0x81, (byte) 0x99, (byte) 0xBE, 0x45, 0x17,
			(byte) 0xAB, 0x06, (byte) 0xEC, 0x22, 0x45, 0x1A, 0x57, (byte) 0x93
			};
	final static byte[] SDIV = {
		0x21, 0x67, 0x12, (byte) 0xE6, (byte) 0xAA, 0x1F, 0x68, (byte) 0x9F,
		(byte) 0x95, (byte) 0xC5, (byte) 0xA2, 0x23, 0x24, (byte) 0xDC, 0x6A, (byte) 0x98
	};
	// ---------------------------------------
	
	// ---------------------------------------
	// These are from https://github.com/dolphin-emu/dolphin/blob/master/Source/Core/Core/ec_wii.cpp
	final static int NG_id = 0x0403AC68;
	final static int NG_key_id = 0x6AAB8C59;
	final static byte[] NG_priv = {
		0, (byte)0xAB, (byte)0xEE, (byte)0xC1, (byte)0xDD, (byte)0xB4, (byte)0xA6, (byte)0x16, (byte)0x6B, 
		(byte)0x70, (byte)0xFD, (byte)0x7E, (byte)0x56, (byte)0x67, (byte)0x70,
		0x57, (byte)0x55, (byte)0x27, (byte)0x38, (byte)0xA3, (byte)0x26, (byte)0xC5, (byte)0x46, (byte)0x16, 
		(byte)0xF7, (byte)0x62, (byte)0xC9, (byte)0xED, (byte)0x73, (byte)0xF2
		};
	final static byte[] NG_sig = {
		0, (byte)0xD8, (byte)0x81, (byte)0x63, (byte)0xB2, (byte)0x00, (byte)0x6B, (byte)0x0B, (byte)0x54, (byte)0x82, (byte)0x88, 
		(byte)0x63, (byte)0x81, (byte)0x1C, (byte)0x00,
		0x71, (byte)0x12, (byte)0xED, (byte)0xB7, (byte)0xFD, (byte)0x21, (byte)0xAB, 
		(byte)0x0E, (byte)0x50, (byte)0x0E, (byte)0x1F, (byte)0xBF, (byte)0x78, (byte)0xAD, (byte)0x37,
		0x00, (byte)0x71, (byte)0x8D, (byte)0x82, (byte)0x41, (byte)0xEE, (byte)0x45, 
		(byte)0x11, (byte)0xC7, (byte)0x3B, (byte)0xAC, (byte)0x08, (byte)0xB6, (byte)0x83, (byte)0xDC,
		0x05, (byte)0xB8, (byte)0xA8, (byte)0x90, (byte)0x1F, (byte)0xA8, (byte)0x2A, 
		(byte)0x0E, (byte)0x4E, (byte)0x76, (byte)0xEF, (byte)0x44, (byte)0x72, (byte)0x99, (byte)0xF8
		};
	// ---------------------------------------
	
	
	
	// Put Wii MAC Adress here
	final static byte[] NG_mac = {
		0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00
	};
	
	
	public static int be16(byte[] p, int offset)
	{
		return ((p[offset]&0xFF) << 8) | (p[offset+1]&0xFF);
	}
	public static int be32(byte[] p, int offset )
	{
		return ((p[offset]&0xFF) << 24) | ((p[offset+1]&0xFF) << 16) | ((p[offset+2]&0xFF) << 8) | (p[offset+3]&0xFF);
	}
	static long be64(byte[] p, int offset)
	{
		return ((long)be32(p, offset) << 32) | be32(p, offset+4);
	}
	public static void wbe16(byte[] p, int off, int x)
	{
		p[0+off] = (byte) (x >> 8);
		p[1+off] = (byte) x;
	}
	public static void wbe32(byte[] p, int off, int x)
	{
		wbe16(p, off, x >> 16);
		wbe16(p, off+2, x);
	}
	public static void wbe64(byte[] p, int off, long x)
	{
		wbe32(p, off, (int) (x >> 32));
		wbe32(p, off + 4, (int)x);
	}
	public static byte[] sha(byte[] data)
	{
		MessageDigest md = null;
		try {
	        md = MessageDigest.getInstance("SHA-1");
	    }
	    catch(NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
		return md.digest(data);
	}
	public static byte[] md5(byte[] data)
	{
		MessageDigest md = null;
		try {
	        md = MessageDigest.getInstance("MD5");
	    }
	    catch(NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
		return md.digest(data);
	}
	public static byte[] aes_cbc_dec(byte[] key, byte[] iv, byte[] in)
	{
		SecretKey skeyKey = new SecretKeySpec(key, "AES");
		AlgorithmParameterSpec ivspec = new IvParameterSpec(iv);
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/NoPadding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cipher.init(Cipher.DECRYPT_MODE, skeyKey, ivspec);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return cipher.doFinal(in);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new byte[0];
	}
	public static byte[] aes_cbc_enc(byte[] key, byte[] iv, byte[] in)
	{
		SecretKey skeyKey = new SecretKeySpec(key, "AES");
		AlgorithmParameterSpec ivspec = new IvParameterSpec(iv);
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/NoPadding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cipher.init(Cipher.ENCRYPT_MODE, skeyKey, ivspec);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return cipher.doFinal(in);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new byte[0];
	}
	public static boolean check_ec(byte[] ng, byte[] ap, byte[] sig, byte[] sig_hash)
	{
		byte[] ap_hash = new byte[20];
		byte[] ng_Q= new byte[ng.length-0x108], ap_R= new byte[ap.length-0x04], ap_S= new byte[ap.length-0x22];
		byte[] ap_Q= new byte[ap.length-0x108], sig_R= new byte[sig.length], sig_S= new byte[sig.length-30];
		for(int i = 0; i<ng.length-0x108; i++)
		{
			ng_Q[i]=ng[i+0x108];
		}
		for(int i = 0; i<ap.length-0x4; i++)
		{
			ap_R[i]=ap[i+0x4];
		}
		for(int i = 0; i<ap.length-0x22; i++)
		{
			ap_S[i]=ap[i+0x22];
		}
		
		byte[] tmp = new byte[0x100];
		for(int i = 0; i<0x100; i++)
		{
			tmp[i]=ap[i+0x80];
		}
		ap_hash=sha(tmp);
		
		for(int i = 0; i<ap.length-0x108; i++)
		{
			ap_Q[i]=ap[i+0x108];
		}
		sig_R=sig;
		for(int i = 0; i<sig.length-30; i++)
		{
			sig_S[i]=sig[i+30];
		}
		return EC.check_ecdsa(ng_Q, ap_R, ap_S, ap_hash, 0, 0, 0, 0)
		       && EC.check_ecdsa(ap_Q, sig_R, sig_S, sig_hash, 0, 0, 0, 0);
	}
}
