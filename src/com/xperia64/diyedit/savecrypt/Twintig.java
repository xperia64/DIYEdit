package com.xperia64.diyedit.savecrypt;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;

import com.xperia64.diyedit.FileByteOperations;

public class Twintig {
	// Copyright 2007-2009  Segher Boessenkool  <segher@kernel.crashing.org>
	// Licensed under the terms of the GNU GPL, version 2
	// http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt

	
	 int verbose = 0;

	 int MAXFILES = 1000;


	 byte[] sd_key=new byte[16];
	 byte[] sd_iv=new byte[16];
	 byte[] md5_blanker=new byte[16];


	 byte[] header=new byte[0xf0c0];

	 public int n_files;
	 int files_size;

	 byte[][] files=new byte[MAXFILES][0x80];
	 String[] src=new String[MAXFILES];

	String titleid="";
	public long title_id;
	byte[] databin;
	 HashMap<String, Integer> permissions;
	 String drip;
	 String out;
	 BufferedOutputStream bos;
	public Twintig(String fold, String outfile)
	{
		
		out=outfile;
		drip=fold;
		title_id=Long.parseLong(fold.substring(fold.lastIndexOf(File.separator)+1),16);
		try {
			bos = new BufferedOutputStream(new FileOutputStream(drip.substring(0,drip.lastIndexOf(File.separator)+1)+outfile.substring(outfile.lastIndexOf(File.separator)+1)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	// This is wrong for some reason. It turns the icon green instead of red for some reason and it isn't animated anymore.
	
	 void read_image(byte[] data, int offset, int w, int h, String name)
	{
		int x, y;
		int ww, hh;
		File f = new File(name);
		if(!f.exists())
		{
			System.out.println("No file");
			return;
		}
		byte[] tmp = FileByteOperations.read(name);
		String header = "";
		/*int o = 0;
		while(tmp[o-1]!=0x0A)
		{
			header+=(char)tmp[o];
			o++;
		}*/
		for(int i = 0; tmp[i]!=0x0A; i++)
		{
			header+=(char)tmp[i];
		}
		header+=(char)0x0A;
		//header=header.trim();
		// Good enough. 
		if(!(header.startsWith("P6 ")&&header.endsWith(" 255\n")))
		{
			return;
		}
		int first=3;
		while(true)
		{
			if(header.charAt(first)==' ')
			{
				break;
			}
			first++;
		}
		ww = Integer.parseInt(header.substring(3,first));
		int second = first+1;
		while(true)
		{
			if(header.charAt(second)==' ')
			{
				break;
			}
			second++;
		}
		hh=Integer.parseInt(header.substring(first+1,second));
		if(hh!=h||ww!=w)
		{
			System.out.println("Bad width/height");
			return;
		}
		int fread_set = header.length();
		for (y = 0; y < h; y++)
			for (x = 0; x < w; x++) {
				byte[] pix=new byte[3];
				int raw;
				int x0, x1, y0, y1, off;

				x0 = x & 3;
				x1 = x >> 2;
				y0 = y & 3;
				y1 = y >> 2;
				off = x0 + 4 * y0 + 16 * x1 + 4 * w * y1;
				for(int i = 0; i<3; i++)
				{
					pix[i]=tmp[i+fread_set];
				}
				fread_set+=3;

				raw = (pix[0] & 0xf8) << 7;
				raw |= (pix[1] & 0xf8) << 2;
				raw |= (pix[2] & 0xf8) >> 3;
				raw |= 0x8000;
				raw &= 0xFFFF;
				Tools.wbe16(data, 2*off+offset, raw);
			}

		tmp=null;
		f=null;
	}

	 byte perm_from_path(String path)
	{
		
		int mode;
		byte perm;
		int i;

		if(permissions==null)
		{
			String permissionsdat = drip+File.separator+"permissions.dat";
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(permissionsdat));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			permissions = new HashMap<String, Integer>();
			String line;
			try {
				while ((line = br.readLine()) != null) {
				   // process the line.
					permissions.put(line.substring(0,line.lastIndexOf(' ')), Integer.parseInt(line.substring(line.lastIndexOf(' ')+1)));
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		perm = 0;
		//System.out.println(drip+File.separator+path);
		mode = permissions.get(path);
		for (i = 0; i < 3; i++) {
			perm <<= 2;
			if ((mode & 0200)!=0)
				perm |= 2;
			if ((mode & 0400)!=0)
				perm |= 1;
			mode <<= 3;
		}

		return perm;
	}

	public  void do_file_header(long title_id, byte[] toc)
	{
		
		for(int i = 0; i<header.length; i++)
		{
			header[i]=0;
		}
		
		Tools.wbe64(header, 0, title_id);
		header[0x0c] = (0x35);
		for(int i = 0; i<16; i++)
		{
			header[0x0e+i]=Tools.MD5_BLANKER[i];
		}
		byte[] wibn={0x57, 0x49, 0x42, 0x4E};
		for(int i = 0; i<4; i++)
		{
			header[0x20+i]=wibn[i];
		}
		// XXX: what about the stuff at 0x24?


		String name;


			name=drip+File.separator+"###title###";

		byte[] title = FileByteOperations.read(name);
		for(int i = 0; i<0x80; i++)
		{
			header[i+0x40]=title[i];
		}

			name=drip+File.separator+"###banner###.ppm";
		
		read_image(header, 0xc0, 192, 64, name);


			name=drip+File.separator+"###icon###.ppm";

		boolean have_anim_icon = true;
		if(new File(name).exists())
			have_anim_icon=false;
		


		if (!have_anim_icon) {
			Tools.wbe32(header, 8, 0x72a0);
			read_image(header, 0x60c0, 48, 48, name);
		} else {
			System.out.println("We have an animation!");
			int i;
			for (i = 0; i < 8; i++) {
				name=drip+File.separator+"###icon"+i+"###.ppm";
				
				if (new File(name).exists()) {
					read_image(header, 0x60c0 + 0x1200*i, 48, 48, name);
				} else{
					break;
				}
					
			}

			Tools.wbe32(header, 8, 0x60a0 + (0x1200*i));
		}


		byte[] md5_calc;
		md5_calc=Tools.md5(header);
		for(int i = 0; i<16; i++)
		{
			header[i+0x0e]=md5_calc[i];
		}
		header=Tools.aes_cbc_enc(Tools.SDKey, Tools.SDIV, header);
		try {
			bos.write(header);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 void find_files_recursive(String path)
	{
		String name;
		// No need
		//int len;
		//int is_dir;
		byte[] p;

		int size;

		File f = new File(path);
		if (!f.exists())
		{	System.out.println("No path");
			return;
		}
		for(File ff:f.listFiles())
		{
			if(!(ff.getName().equals(".")||ff.getName().equals("..")||ff.getName().startsWith("###")||ff.getName().equals("permissions.dat")))
			{
				name=ff.getAbsolutePath();
				src[n_files]=name;

				if(ff.isDirectory())
					size=0;
				else
					size=(int) ff.length();
				
				p = new byte[0x80];
				Tools.wbe32(p, 0, 0x3adf17e);
				Tools.wbe32(p, 4, size);
				p[8] = perm_from_path(name);
				p[0x0a] = (byte) (ff.isDirectory() ? 2 : 1);
				for(int i = 0; i<ff.getName().length(); i++)
				{
					p[i+0x0b]=(byte)ff.getName().charAt(i);
				}
				files[n_files]=p;
				n_files++;
				// maybe fill up with dirt
				size=Math.round(size/0x40)*0x40;
				files_size += 0x80 + size;

				if (ff.isDirectory())
					find_files_recursive(name);
			}
		}
	}

	/* int compar(const void *a, const void *b)
	{
		return strcmp((char *)a + 0x0b, (char *)b + 0x0b);
	}*/

	public void find_files()
	{
		n_files = 0;
		files_size = 0;

		for(int i = 0; i<files.length; i++)
		{
			files[i]=new byte[0x80];
		}
		find_files_recursive(drip);
		//Arrays.sort(files, new Compy());
		//qsort(files, n_files, 0x80, compar);
	}
	
	 int wiggle_name(String name)
	{
		//XXX: encode embedded zeroes, etc.
		return name.length();
	}

	/* void find_files_toc(FILE *toc)
	{
		n_files = 0;
		files_size = 0;

		memset(files, 0, sizeof files);

		int len;
		int is_dir;
		byte *p;
		struct stat sb;
		int size;

		char line[256];

		while (fgets(line, sizeof line, toc)) {
			line[strlen(line) - 1] = 0;	// get rid of linefeed

			char *name;
			for (name = line; *name; name++)
				if (*name == ' ')
					break;
			if (!*name)
				ERROR("no space in TOC line");
			*name = 0;
			name++;

			len = wiggle_name(name);
			if (len >= 53)
				ERROR("path too long");

			if (stat(line, &sb))
				fatal("stat %s", line);

			is_dir = S_ISDIR(sb.st_mode);

			size = (is_dir ? 0 : sb.st_size);

			strcpy(src[n_files], line);

			p = files[n_files++];
			wbe32(p, 0x3adf17e);
			wbe32(p + 4, size);
			p[8] = 0x35;	// rwr-r-
			p[0x0a] = is_dir ? 2 : 1;
			memcpy(p + 0x0b, name, len);
			// maybe fill up with dirt

			size = round_up(size, 0x40);
			files_size += 0x80 + size;

			//if (is_dir)
			//	find_files_recursive(name);
		}

		if (ferror(toc))
			fatal("reading toc");
	}*/

	public  void do_backup_header(long title_id)
	{
		byte[] header = new byte[0x80];

		for(int i = 0; i<0x80; i++)
		{
			header[i] = 0;
		}

		Tools.wbe32(header,0, 0x70);
		Tools.wbe32(header, 4, 0x426b0001);
		Tools.wbe32(header, 8, Tools.NG_id);
		Tools.wbe32(header, 0x0c, n_files);
		Tools.wbe32(header, 0x10, files_size);
		Tools.wbe32(header, 0x1c, files_size + 0x3c0);
		Tools.wbe64(header, 0x60, title_id);
		for(int i = 0; i<6; i++)
		{
			header[i+0x68]=Tools.NG_mac[i];
		}
		try {
			bos.write(header);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*if (fwrite(header, sizeof header, 1, fp) != 1)
			fatal("write Bk header");*/
	}

	 public void do_file(int file_no)
	{
		byte[] header;
		int size;
		int rounded_size;
		byte /*perm, attr, */type;
		//String name=" ";
		byte[] data;
		byte[] in;

		header = files[file_no];
		size = Tools.be32(header, 4);
		//perm = header[8];
		//attr = header[9];
		type = header[10];
		//int loc = 11;
		//while(header[loc]!=0)
		//{
		//	name+=(char)header[loc];
		//	loc++;
		//}
		if (verbose!=0)
		{
			//System.out.println(String.format("file: size=%08x perm=%02x attr=%02x type=%02x name=%s\n",size, perm, attr, type, name));	
		}/*printf(
			    "file: size=%08x perm=%02x attr=%02x type=%02x name=%s\n",
			    size, perm, attr, type, name);*/

		byte[] tmp = new byte[0x80];
		for(int i = 0; i<0x80; i++)
		{
			tmp[i]=header[i];
		}
		try {
			bos.write(tmp);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tmp=null;

		String from = src[file_no];

		if (type == 1) 
		{
			rounded_size = (size/0x40)*0x40;

			data = new byte[rounded_size];
			for(int i = 0; i<rounded_size-size; i++)
			{
				data[i+size]=0;
			}
			in=FileByteOperations.read(from);
			for(int i = 0; i<size; i++)
			{			
				data[i]=in[i];
			}
			tmp = new byte[16];
			for(int i = 0; i<16; i++)
			{
				tmp[i] = header[i+0x50];
			}
			data=Tools.aes_cbc_enc(Tools.SDKey, tmp, data);
			try {
				bos.write(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			data=null;
		}
	}

	 void make_ec_cert(byte[] cert, byte[] sig, String signer, String name, byte[] priv,
	                         int key_id, int certoffset, int sigoffset, int privoffset)
	{
		for(int i = 0; i<0x180; i++)
		{
			cert[i+certoffset]=0;
		}
		Tools.wbe32(cert, certoffset, 0x10002);
		for(int i = 0; i<60; i++)
		{
			cert[i+4+certoffset]=sig[i+sigoffset];
		}
		for(int i = 0; i<signer.length(); i++)
		{
			cert[i+0x80+certoffset]=(byte)signer.charAt(i);
		}
		
		Tools.wbe32(cert, certoffset+0xc0, 2);
		for(int i = 0; i<name.length(); i++)
		{
			cert[i+0xc4+certoffset]=(byte) name.charAt(i);
		}
		Tools.wbe32(cert, certoffset+ 0x104, key_id);
		EC.ec_priv_to_pub(priv, cert, certoffset+0x108, privoffset);
		
	}

	 public void do_sig()
	{
		byte[] sig=new byte[0x40];
		byte[] ng_cert=new byte[0x180];
		byte[] ap_cert=new byte[0x180];
		byte[] hash=new byte[0x14];
		byte[] ap_priv=new byte[30];
		byte[] ap_sig=new byte[60];
		String signer="Root-CA00000001-MS00000002";
		String name=String.format("NG%08x", Tools.NG_id);
		byte[] data;
		int data_size;

		make_ec_cert(ng_cert, Tools.NG_sig, signer, name, Tools.NG_priv, Tools.NG_key_id, 0, 0, 0);
		for(int i = 0; i<ap_priv.length; i++)
		{
			ap_priv[i]=0;
		}
		ap_priv[10] = 1;

		for(int i = 0; i<ap_sig.length; i++)
		{
			ap_sig[i]=81;
		}
		signer=String.format("Root-CA00000001-MS00000002-NG%08x", Tools.NG_id);

		name=String.format("AP%08x%08x", 1, 2);
		
		make_ec_cert(ap_cert, ap_sig, signer, name, ap_priv, 0, 0, 0, 0);

		byte[] tmp = new byte[0x100];
		for(int i = 0; i<0x100; i++)
		{
			tmp[i] = ap_cert[i+0x80];
		}
		hash = Tools.sha(tmp);
		EC.generate_ecdsa(ap_sig, ap_sig, Tools.NG_priv, hash, 0, 30, 0, 0);
		make_ec_cert(ap_cert, ap_sig, signer, name, ap_priv, 0, 0, 0, 0);

		data_size = files_size + 0x80;

		data = new byte[data_size];
		int fread=0xf0c0;
		tmp = FileByteOperations.read(drip.substring(0,drip.lastIndexOf(File.separator)+1)+out.substring(out.lastIndexOf(File.separator)+1));
		for(int i = 0; i<data_size; i++)
		{
			data[i]=tmp[i+fread];
		}
		fread+=data_size;
		hash = Tools.sha(data);
		hash = Tools.sha(hash);
		data= null;

		EC.generate_ecdsa(sig, sig, ap_priv, hash, 0, 30, 0, 0);
		Tools.wbe32(sig, 60, 0x2f536969);
		try {
			bos.write(sig);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bos.write(ng_cert);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bos.write(ap_cert);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			copyFile(new File(drip.substring(0,drip.lastIndexOf(File.separator)+1)+out.substring(out.lastIndexOf(File.separator)+1)), new File(out));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	 public static void copyFile(File sourceFile, File destFile) throws IOException {
		    if(!destFile.exists()) {
		        destFile.createNewFile();
		    }

		    FileChannel source = null;
		    FileChannel destination = null;

		    try {
		        source = new FileInputStream(sourceFile).getChannel();
		        destination = new FileOutputStream(destFile).getChannel();
		        destination.transferFrom(source, 0, source.size());
		    }
		    finally {
		        if(source != null) {
		            source.close();
		        }
		        if(destination != null) {
		            destination.close();
		        }
		    }
		}
	/*int main(int argc, char **argv)
	{
		u64 title_id;
		byte tmp[4];
		int i;

		if (argc != 3 && argc != 4) {
			fprintf(stderr, "Usage: %s <srcdir> <data.bin>\n", argv[0]);
			fprintf(stderr, "or: %s <srcdir> <data.bin> <toc>\n", argv[0]);
			return 1;
		}

		FILE *toc = 0;
		if (argc == 4) {
			toc = fopen(argv[3], "r");
			if (!toc)
				fatal("open %s", argv[3]);
		}

		get_key("sd-key", sd_key, 16);
		get_key("sd-iv", sd_iv, 16);
		get_key("md5-blanker", md5_blanker, 16);

		get_key("default/NG-id", tmp, 4);
		Tools.NG_id = be32(tmp);
		get_key("default/NG-key-id", tmp, 4);
		Tools.NG_key_id = be32(tmp);
		get_key("default/NG-mac", Tools.NG_mac, 6);
		get_key("default/NG-priv", Tools.NG_priv, 30);
		get_key("default/NG-sig", Tools.NG_sig, 60);

		if (sscanf(argv[1], "%016llx", &title_id) != 1)
			ERROR("not a correct title id");

		fp = fopen(argv[2], "wb+");
		if (!fp)
			fatal("open %s", argv[2]);

		if (!toc) {
			if (chdir(argv[1]))
				fatal("chdir %s", argv[1]);
		}

		do_file_header(title_id, toc);

		if (toc!=null&&false)
			find_files_toc(toc);
		else
			find_files();

		do_backup_header(title_id);

		for (i = 0; i < n_files; i++)
			do_file(i);

		// XXX: is this needed?
		if (!toc) {
			if (chdir(".."))
				fatal("chdir ..");
		}

		do_sig();

		fclose(fp);

		return 0;
	}*/
}
