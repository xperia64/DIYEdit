package com.xperia64.diyedit.savecrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import com.xperia64.diyedit.FileByteOperations;

public class Tachtig {

	private byte[] databin;
	private byte[] md5file = new byte[16];
	private byte[] md5calc = new byte[16];
	public Path mPath;
	public int n_files;
	int fread_offset;
	int files_size;
	int total_size;
	public String titleid="";
	HashMap<String, Integer> permissions = new HashMap<String, Integer>();
	public Tachtig(byte[] data)
	{
		databin=data;
		//System.out.println(System.getProperty("java.io.tmpdir"));
		try {
			mPath = Files.createTempDirectory("showcase_");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new File(mPath.toString()).deleteOnExit();
	}
	static int perm_to_mode(int perm)
	{
		int mode;
		int i;

		mode = 0;
		for (i = 0; i < 3; i++) {
			mode <<= 3;
			if ((perm & 0x20)!=0)
				mode |= 3;
			if ((perm & 0x10)!=0)
				mode |= 5;
			perm <<= 2;
		}

		return mode;
	}
	static void output_image(byte[] data, int start, int w, int h, String name)
	{
		
		int x, y;
		ArrayList<Byte> bb = new ArrayList<Byte>();
		for (byte b : ("P6 "+w+" "+h+" 255\n").getBytes()) {
		    bb.add(b);
		}
		for (y = 0; y < h; y++)
			for (x = 0; x < w; x++) {
				
				int raw;
				int x0, x1, y0, y1, off;
				
				x0 = x & 3;
				x1 = x >> 2;
				y0 = y & 3;
				y1 = y >> 2;
				off = x0 + 4 * y0 + 16 * x1 + 4 * w * y1;

				raw = Tools.be16(data, 2*off+start);
				// RGB5A3
				if ((raw & 0x8000)!=0) {
					bb.add( (byte) ((raw >> 7) & 0xf8));
					bb.add( (byte) ((raw >> 2) & 0xf8));
					bb.add( (byte) ((raw << 3) & 0xf8));
				} else {
					bb.add( (byte) ((raw >> 4) & 0xf0));
					bb.add( (byte) (raw       & 0xf0));
					bb.add( (byte) ((raw << 4) & 0xf0));
				}
			}
		Byte[] bbb = bb.toArray(new Byte[bb.size()]);
		byte[] bytes = new byte[bbb.length];
		int j=0;
		// Unboxing byte values. (Byte[] to byte[])
		for(Byte b : bbb)
		    bytes[j++] = b.byteValue();
		
		FileByteOperations.write(bytes, name);
	}
	public void do_file_header()
	{
		byte[] header=new byte[61632];
		for(int i = 0; i<header.length; i++)
		{
			header[i]=databin[i+fread_offset];
		}
		fread_offset+=header.length;
		header=Tools.aes_cbc_dec(Tools.SDKey, Tools.SDIV, header);
		for(int i = 0x0e; i<0x1E; i++)
		{
			md5file[i-0x0e]=header[i];
		}
		for(int i = 0x0e; i<0x1E; i++)
		{
			header[i]=Tools.MD5_BLANKER[i-0x0e];
		}
		md5calc=Tools.md5(header);
		for(int i = 0; i<0x10; i++)
		{	
			if(md5calc[i]!=md5file[i])
			{
				System.out.println("Borked at md5");
				return;
			}
		}
		int header_size=Tools.be32(header,8);
		if(header_size<0x72a0||header_size>0xf0a0||(header_size - 0x60a0) % 0x1200 != 0)
		{
			System.out.println("borked at header size");
			return;
		}
		long l = Tools.be64(header,0);
		titleid = String.format("%016x", l);
		/*snprintf(dir, sizeof dir, "%016llx", );
		if (mkdir(dir, 0777))
			fatal("mkdir %s", dir);
		if (chdir(dir))
			fatal("chdir %s", dir);*/
		File curr = new File(mPath.toString()+File.separator+titleid);
		curr.mkdir();
		
		byte[] tmp=new byte[128];
		for(int i = 0x40; i<0xC0; i++)
		{
			tmp[i-0x40]=header[i];
		}
		
		FileByteOperations.write(tmp, mPath.toString()+File.separator+titleid+File.separator+"###title###");
		output_image(header, 0xc0, 192, 64, mPath.toString()+File.separator+titleid+File.separator+"###banner###.ppm");
		if (header_size == 0x72a0)
			output_image(header, 0x60c0, 48, 48, mPath.toString()+File.separator+titleid+File.separator+"###icon###.ppm");
		else
			for (int i = 0; 0x1200*i + 0x60c0 < header_size; i++) {
				output_image(header, 0x60c0 + 0x1200*i, 48, 48, mPath.toString()+File.separator+titleid+File.separator+"###icon"+i+"###.ppm");
			}
	}
	public void do_backup_header()
	{
		byte header[] = new byte[0x80];

		for(int i = 0; i<header.length; i++)
		{
			header[i]=databin[i+fread_offset];
		}
		fread_offset+=header.length;
		
		if (Tools.be32(header, 4) != 0x426b0001)
			System.out.println("no Bk header");
		if (Tools.be32(header, 0) != 0x70)
			System.out.println("wrong Bk header size");

		

		n_files = Tools.be32(header, 0x0c);
		files_size = Tools.be32(header, 0x10);
		total_size = Tools.be32(header, 0x1c);
	}
	public void do_file()
	{
		byte[] header = new byte[0x80];
		int size;
		int rounded_size;
		int perm, /*attr,*/ type;
		String name="";
		byte[] data;
		int mode;
		for(int i = 0; i<header.length; i++)
		{
			header[i]=databin[i+fread_offset];
		}
		fread_offset+=0x80;

		if (Tools.be32(header,0) != 0x03adf17e)
			System.out.println("bad file header");

		size = Tools.be32(header, 4);
		perm = header[8];
		//attr = header[9];
		type = header[10];
		int loc = 11;
		while(header[loc]!=0)
		{
			name+=(char)header[loc];
			loc++;
		}
		mode = perm_to_mode(perm);
		switch (type) {
		case 1:
			rounded_size = (size + 63) & ~63;
			data = new byte[rounded_size];
			for(int i = 0; i<data.length; i++)
			{
				data[i]=databin[i+fread_offset];
			}
			fread_offset+=rounded_size;
			byte[] localiv = new byte[16];
			for(int i = 0; i<16; i++)
			{
				localiv[i]=header[i+0x50];
			}
			data = Tools.aes_cbc_dec(Tools.SDKey, localiv, data);
			FileByteOperations.write(data, mPath.toString()+File.separator+titleid+File.separator+name);
			mode &= ~0111;
			header = null;
			break;

		case 2:
			if(!new File(mPath.toString()+File.separator+titleid+File.separator+name).exists())
				new File(mPath.toString()+File.separator+titleid+File.separator+name).mkdir();
			break;

		default:
			System.out.println("unhandled file type");
		}
		permissions.put(mPath.toString()+File.separator+titleid+File.separator+name, mode);
	}
	public void write_permissions()
	{
		PrintWriter f0 = null;
		try {
			f0 = new PrintWriter(new FileWriter(mPath.toString()+File.separator+titleid+File.separator+"permissions.dat"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(String s:permissions.keySet())
		{
		    f0.write(s+" "+permissions.get(s)+"\n");
		}
		f0.close();
	}
	public void do_sig()
	{
		byte[] sig = new byte[0x40];
		byte[] ng_cert=new byte[0x180];
		byte[] ap_cert=new byte[0x180];
		byte[] hash=new byte[0x14];
		byte[] data;
		int data_size;
		
		for(int i = 0; i<sig.length; i++)
		{
			sig[i]=databin[i+fread_offset];
		}
		fread_offset+=sig.length;
		for(int i = 0; i<ng_cert.length; i++)
		{
			ng_cert[i]=databin[i+fread_offset];
		}
		fread_offset+=ng_cert.length;
		for(int i = 0; i<ap_cert.length; i++)
		{
			ap_cert[i]=databin[i+fread_offset];
		}

		fread_offset+=ap_cert.length;
		data_size = total_size - 0x340;

		data = new byte[data_size];
		fread_offset=0xf0c0;
		for(int i = 0; i<data.length; i++)
		{
			data[i]=databin[i+fread_offset];
		}
		
		fread_offset+=data.length;
		hash=Tools.sha(data);
		hash=Tools.sha(hash);
		Tools.check_ec(ng_cert, ap_cert, sig, hash);
	}
	
}
