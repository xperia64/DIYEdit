package com.xperia64.diyedit.savecrypt;

import java.util.Random;

public class EC {
	static byte[] ec_b=
		{0x00,0x66,0x64,0x7e,(byte)0xde,0x6c,0x33,0x2c,0x7f,(byte)0x8c,0x09,0x23,(byte)0xbb,0x58,0x21
			,0x3b,0x33,0x3b,0x20,(byte)0xe9,(byte)0xce,0x42,(byte)0x81,(byte)0xfe,0x11,0x5f,0x7d,(byte)0x8f,(byte)0x90,(byte)0xad};

		// order of the addition group of points
		static byte[] ec_N =
			{0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
			,0x13,(byte)0xe9,0x74,(byte)0xe7,0x2f,(byte)0x8a,0x69,0x22,0x03,0x1d,0x26,0x03,(byte)0xcf,(byte)0xe0,(byte)0xd7};

		// base point
		static byte[] ec_G =
			{0x00,(byte)0xfa,(byte)0xc9,(byte)0xdf,(byte)0xcb,(byte)0xac,(byte)0x83,0x13,(byte)0xbb,0x21,(byte)0x39,(byte)0xf1,(byte)0xbb,0x75,0x5f
			,(byte)0xef,0x65,(byte)0xbc,0x39,0x1f,(byte)0x8b,0x36,(byte)0xf8,(byte)0xf8,(byte)0xeb,0x73,0x71,(byte)0xfd,0x55,(byte)0x8b
			,0x01,0x00,0x6a,0x08,(byte)0xa4,0x19,0x03,0x35,0x06,0x78,(byte)0xe5,(byte)0x85,0x28,(byte)0xbe,(byte)0xbf
			,(byte)0x8a,0x0b,(byte)0xef,(byte)0xf8,(byte)0x67,(byte)0xa7,(byte)0xca,0x36,0x71,0x6f,0x7e,0x01,(byte)0xf8,0x10,0x52};

		static void elt_print(String name, byte[] a)
		{
			int i;

			System.out.println(name);
			

			for (i = 0; i < 30; i++)
				System.out.println(a[i]);
		}

		static void elt_copy(byte[] d, byte[] a, int doffset, int aoffset)
		{
			for(int i = 0; i<30; i++)
			{
				d[i+doffset]=a[i+aoffset];
			}
		}

		static void elt_zero(byte[]d, int doffset)
		{
			for(int i = 0; i<30; i++)
			{
				d[i+doffset]=0;
			}
		}

		static int elt_is_zero(byte[] d, int doffset)
		{
			int i;

			for (i = 0; i < 30; i++)
				if (d[i+doffset] != 0)
					return 0;

			return 1;
		}

		static void elt_add(byte[] d, byte[] a, byte[] b, int doffset, int aoffset, int boffset)
		{
			int i;

			for (i = 0; i < 30; i++)
				d[i+doffset] = (byte) ((a[i+aoffset]&0xFF) ^ (b[i+boffset]&0xFF));
		}

		static void elt_mul_x(byte[]d, byte[]a, int doffset, int aoffset)
		{
			byte carry, x, y;
			int i;

			carry = (byte) (a[0+aoffset] & 1);

			x = 0;
			for (i = 0; i < 29; i++) {
				y = a[i + 1+aoffset];
				d[i+doffset] = (byte) ((x&0xFF) ^ ((y&0xFF) >> 7));
				x = (byte) ((y&0xFF) << 1);
			}
			d[29+doffset] = (byte) ((x&0xFF) ^ (carry&0xFF));

			d[20+doffset] = (byte) (((d[20+doffset]&0xFF)^((carry&0xFF)) << 2));
		}

		static void elt_mul(byte[]d, byte[]a, byte[]b, int doffset, int aoffset, int boffset)
		{
			int i, n;
			int mask;

			elt_zero(d,doffset);

			i = 0;
			mask = 1;
			for (n = 0; n < 233; n++) {
				elt_mul_x(d, d, doffset, doffset);

				if (((a[i+aoffset]&0xFF) & mask) != 0)
					elt_add(d, d, b, doffset, doffset, boffset);

				mask >>= 1;
				if (mask == 0) {
					mask = 0x80;
					i++;
				}
			}
		}

		static byte[] square =
			{0x00,0x01,0x04,0x05,0x10,0x11,0x14,0x15,0x40,0x41,0x44,0x45,0x50,0x51,0x54,0x55};

		static void elt_square_to_wide(byte[]d, byte[]a, int doffset, int aoffset)
		{
			int i;

			for (i = 0; i < 30; i++) {
				d[2*i+doffset] = square[(a[i+aoffset]&0xFF) >> 4];
				d[2*i + 1+doffset] = square[(a[i+aoffset]&0xFF) & 15];
			}
		}

		static void wide_reduce(byte[]d, int doffset)
		{
			int i;
			byte x;

			for (i = 0; i < 30; i++) {
				x = d[i+doffset];

				d[i + 19+doffset] ^= (x&0xFF) >> 7;
				d[i + 20+doffset] ^= (x&0xFF) << 1;
				d[i + 29+doffset] ^= (x&0xFF) >> 1;
				d[i + 30+doffset] ^= (x&0xFF) << 7;
			}

			x = (byte) ((d[30+doffset]&0xFF) & ~1);

			d[49+doffset] ^= (x&0xFF) >> 7;
			d[50+doffset] ^= (x&0xFF) << 1;

			d[59+doffset] ^= (x&0xFF) >> 1;

			d[30+doffset] &= 1;
		}

		static void elt_square(byte[]d, byte[]a, int doffset, int aoffset)
		{
			byte[] wide = new byte[60];

			elt_square_to_wide(wide, a, 0, aoffset);
			wide_reduce(wide, 0);

			elt_copy(d, wide, doffset, 30);
		}

		static void itoh_tsujii(byte[]d, byte[]a, byte[]b, int j, int doffset, int aoffset, int boffset)
		{
			byte[] t = new byte[30];

			elt_copy(t, a, 0, aoffset);
			while (j-->0) {
				elt_square(d, t, doffset, 0);
				elt_copy(t, d, 0, doffset);
			}

			elt_mul(d, t, b, doffset, 0, boffset);
		}

		static void elt_inv(byte[]d, byte[]a, int doffset, int aoffset)
		{
			byte[] t=new byte[30];
			byte[] s=new byte[30];
			itoh_tsujii(t, a, a, 1, 0, aoffset, aoffset);
			itoh_tsujii(s, t, a, 1, 0, 0, aoffset);
			itoh_tsujii(t, s, s, 3, 0, 0, 0);
			itoh_tsujii(s, t, a, 1, 0, 0, aoffset);
			itoh_tsujii(t, s, s, 7, 0, 0, 0);
			itoh_tsujii(s, t, t, 14, 0, 0, 0);
			itoh_tsujii(t, s, a, 1, 0, 0, aoffset);
			itoh_tsujii(s, t, t, 29, 0, 0, 0);
			itoh_tsujii(t, s, s, 58, 0, 0, 0);
			itoh_tsujii(s, t, t, 116, 0, 0, 0);
			elt_square(d, s, doffset, 0);
		}

		
		static boolean point_is_zero(byte[]p, int poffset)
		{
			return elt_is_zero(p, poffset)==1 && elt_is_zero(p, poffset+30)==1;
		}

		static void point_double(byte[]r, byte[]p, int roffset, int poffset)
		{
			byte[] s = new byte[30], t = new byte[30];
			byte[]px = new byte[30], py= new byte[30], rx= new byte[30], ry= new byte[30];
			for(int i = 0; i<30; i++)
			{
				px[i]=p[i+poffset];
			}
			for(int i = 0; i<30; i++)
			{
				py[i]=p[i+poffset+30];
			}
			for(int i = 0; i<30; i++)
			{
				rx[i]=r[i+roffset];
			}
			for(int i = 0; i<30; i++)
			{
				ry[i]=r[i+roffset+30];
			}
			
			if (elt_is_zero(px, 0)==1) {
				elt_zero(rx, 0);
				elt_zero(ry, 0);
				for(int i = 0; i<30; i++)
				{
					r[i+roffset]=rx[i];
				}
				for(int i = 0; i<30; i++)
				{
					r[i+roffset+30]=ry[i];
				}
				return;
			}

			elt_inv(t, px, 0, 0);
			elt_mul(s, py, t, 0, 0, 0);
			elt_add(s, s, px, 0, 0, 0);

			elt_square(t, px, 0, 0);

			elt_square(rx, s, 0, 0);
			elt_add(rx, rx, s, 0, 0, 0);
			rx[29] ^= 1;

			elt_mul(ry, s, rx, 0, 0, 0);
			elt_add(ry, ry, rx, 0, 0, 0);
			elt_add(ry, ry, t, 0, 0, 0);
			for(int i = 0; i<30; i++)
			{
				r[i+roffset]=rx[i];
			}
			for(int i = 0; i<30; i++)
			{
				r[i+roffset+30]=ry[i];
			}
		}

		static void point_add(byte[]r, byte[]p, byte[]q, int roffset, int poffset, int qoffset)
		{
			byte[] s=new byte[30], t=new byte[30], u=new byte[30];
			byte[]px=new byte[30], py=new byte[30], qx=new byte[30], qy=new byte[30], rx=new byte[30], ry=new byte[30];

			for(int i = 0; i<30; i++)
			{
				px[i]=p[i+poffset];
			}
			for(int i = 0; i<30; i++)
			{
				py[i]=p[i+poffset+30];
			}
			for(int i = 0; i<30; i++)
			{
				rx[i]=r[i+roffset];
			}
			for(int i = 0; i<30; i++)
			{
				ry[i]=r[i+roffset+30];
			}
			for(int i = 0; i<30; i++)
			{
				qx[i]=q[i+qoffset];
			}
			for(int i = 0; i<30; i++)
			{
				qy[i]=q[i+qoffset+30];
			}

			if (point_is_zero(p, poffset)) {
				elt_copy(rx, qx, 0, 0);
				elt_copy(ry, qy, 0, 0);
				for(int i = 0; i<30; i++)
				{
					r[i+roffset]=rx[i];
				}
				for(int i = 0; i<30; i++)
				{
					r[i+roffset+30]=ry[i];
				}
				return;
			}
			if (point_is_zero(q, qoffset)) {
				elt_copy(rx, px, 0, 0);
				elt_copy(ry, py, 0, 0);
				for(int i = 0; i<30; i++)
				{
					r[i+roffset]=rx[i];
				}
				for(int i = 0; i<30; i++)
				{
					r[i+roffset+30]=ry[i];
				}
				return;
			}
			elt_add(u, px, qx, 0, 0, 0);

			if (elt_is_zero(u, 0)!=0) {
				elt_add(u, py, qy, 0, 0, 0);
				if (elt_is_zero(u, 0)!=0)
					point_double(r, p, roffset, poffset);
				else {
					elt_zero(rx, 0);
					elt_zero(ry, 0);
					for(int i = 0; i<30; i++)
					{
						r[i+roffset]=rx[i];
					}
					for(int i = 0; i<30; i++)
					{
						r[i+roffset+30]=ry[i];
					}
				}

				return;
			}
			elt_inv(t, u, 0, 0);
			elt_add(u, py, qy, 0, 0, 0);
			elt_mul(s, t, u, 0, 0, 0);

			elt_square(t, s, 0, 0);
			elt_add(t, t, s, 0, 0, 0);
			elt_add(t, t, qx, 0, 0, 0);
			t[29] ^= 1;

			elt_mul(u, s, t, 0, 0, 0);
			elt_add(s, u, py, 0, 0, 0);
			elt_add(rx, t, px, 0, 0, 0);
			elt_add(ry, s, rx, 0, 0, 0);
			for(int i = 0; i<30; i++)
			{
				r[i+roffset]=rx[i];
			}
			for(int i = 0; i<30; i++)
			{
				r[i+roffset+30]=ry[i];
			}
		}

		static void point_mul(byte[] d, byte[] a, byte[] b, int doffset, int aoffset, int boffset)	// a is bignum
		{
			int i;
			int mask;
			elt_zero(d, doffset);
			elt_zero(d, doffset+30);

			for (i = 0; i < 30; i++)
				for (mask = 0x80; mask != 0; mask >>= 1) {
					point_double(d, d, doffset, doffset);
					if (((a[i+aoffset]&0xFF) & mask) != 0)
						point_add(d, d, b, doffset, doffset, boffset);
				}
		}

		static void generate_ecdsa(byte[] R, byte[] S, byte[] k, byte[] hash, int Roffset, int Soffset, int koffset, int hashoffset)
		{
			byte[] e=new byte[30];
			byte[] kk=new byte[30];
			byte[] m=new byte[30];
			byte[] minv=new byte[30];
			byte[] mG=new byte[60];

			elt_zero(e, 0);
			for(int i = 0; i<20; i++)
			{
				e[i+10]=hash[i+hashoffset];
			}

			Random r = new Random();
			r.nextBytes(m);
			m[0] = 0;

			//	R = (mG).x

			point_mul(mG, m, ec_G, 0, 0, 0);
			elt_copy(R, mG, Roffset, 0);
			if (BN.bn_compare(R, ec_N, 30, Roffset, 0) >= 0)
				BN.bn_sub_modulus(R, ec_N, 30, Roffset, 0);

			//	S = m**-1*(e + Rk) (mod N)

			elt_copy(kk, k, 0, 0);
			if (BN.bn_compare(kk, ec_N, 30, 0, 0) >= 0)
				BN.bn_sub_modulus(kk, ec_N, 30, 0, 0);
			BN.bn_mul(S, R, kk, ec_N, 30, Soffset, Roffset, 0, 0);
			BN.bn_add(kk, S, e, ec_N, 30, 0, Soffset, 0, 0);
			BN.bn_inv(minv, m, ec_N, 30, 0, 0, 0);
			BN.bn_mul(S, minv, kk, ec_N, 30, Soffset, 0, 0, 0);
		}
		static boolean flipped=false;
		static int written=0;
		static boolean check_ecdsa(byte[] Q, byte[] R, byte[] S, byte[] hash, int Qoffset, int Roffset, int Soffset, int hashoffset)
		{
			byte[] Sinv = new byte[30];
			byte[] e = new byte[30];
			byte[] w1=new byte[30], w2=new byte[30];
			byte[] r1= new byte[60], r2=new byte[60];
			
			BN.bn_inv(Sinv, S, ec_N, 30, 0, Soffset, 0);
			
			elt_zero(e, 0);
			for(int i = 0; i<20; i++)
			{
				e[i+10]=hash[i+hashoffset];
			}
			BN.bn_mul(w1, e, Sinv, ec_N, 30, 0, 0, 0, 0);
			BN.bn_mul(w2, R, Sinv, ec_N, 30, 0, Roffset, 0, 0);
			point_mul(r1, w1, ec_G, 0, 0, 0);
			point_mul(r2, w2, Q, 0, 0, Qoffset);
			point_add(r1, r1, r2, 0, 0, 0);
			if (BN.bn_compare(r1, ec_N, 30, 0, 0) >= 0)
			{
				BN.bn_sub_modulus(r1, ec_N, 30, 0, 0);
			}

			return (BN.bn_compare(r1, R, 30, 0, Roffset) == 0);
		}

		public static void ec_priv_to_pub(byte[] k, byte[] Q, int Qoffset, int koffset)
		{
			point_mul(Q, k, ec_G, Qoffset, koffset, 0);
		}

}
