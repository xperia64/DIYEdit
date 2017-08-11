package com.xperia64.diyedit.savecrypt;

public class BN {
	static void bn_print(String name, byte[] a, int n)
	{
		int i;

		System.out.println(name);

		for (i = 0; i < n; i++)
			System.out.println(a[i]);
	}

	static void bn_zero(byte[] d, int n, int doffset)
	{
		for(int i = 0; i<n; i++)
		{
			d[i+doffset]=0;
		}
	}

	static void bn_copy(byte[] d, byte[] a, int n, int doffset, int aoffset)
	{
		for(int i = 0; i<n; i++)
		{
			d[i+doffset]=a[i+aoffset];
		}
	}

	static int bn_compare(byte[] a, byte[] b, int n, int aoffset, int boffset)
	{
		int i;

		for (i = 0; i < n; i++) {
			if ((a[i+aoffset]&0xFF) < (b[i+boffset]&0xFF))
				return -1;
			if (((a[i+aoffset])&0xFF) > (b[i+boffset]&0xFF))
				return 1;
		}

		return 0;
	}

	static void bn_sub_modulus(byte[] a, byte[] N, int n, int aoffset, int Noffset)
	{
		int i;
		int dig;
		int c;

		c = 0;
		for (i = n - 1; i>=0; i--) {
			dig = (N[i+Noffset]&0xFF) + c;
			c = ((a[i+aoffset]&0xFF) < dig)?1:0;
			a[i+aoffset]=(byte) ((a[i+aoffset]&0xFF) - dig);
		}
	}
	static void bn_add(byte[] d, byte[] a, byte[] b, byte[] N, int n, int doffset, int aoffset, int boffset, int Noffset)
	{
		int i;
		int dig;
		int c;

		c = 0;
		for (i = n - 1; i>=0; i--) {
			dig = (a[i+aoffset]&0xFF) +(b[i+boffset]&0xFF) + c;
			c = ((dig >= 0x100)?1:0);
			d[i+doffset] = (byte) dig;
		}
		if (c!=0)
			bn_sub_modulus(d, N, n, doffset, Noffset);

		if (bn_compare(d, N, n, doffset, Noffset) >= 0)
			bn_sub_modulus(d, N, n, doffset, Noffset);
		
	}
	
	static void bn_mul(byte[] d, byte[] a, byte[] b, byte[] N, int n, int doffset, int aoffset, int boffset, int Noffset)
	{
		int i;
		int mask;
		bn_zero(d, n, doffset);
		
		for (i = 0; i < n; i++)
		{
			for (mask = 0x80; mask != 0; mask >>= 1) {
				bn_add(d, d, d, N, n, doffset, doffset, doffset, Noffset);
				if (((a[i+aoffset]&0xFF) & mask) != 0)
					bn_add(d, d, b, N, n, doffset, doffset, boffset, Noffset);
			}
		}
		
		
	}

	static void bn_exp(byte[] d, byte[] a, byte[] N, int n, byte[] e, int en, int doffset, int aoffset, int Noffset, int eoffset)
	{
		byte[] t = new byte[512];
		int i;
		int mask;

		bn_zero(d, n, doffset);
		d[n-1+doffset] = 1;
		
		for (i = 0; i < en; i++)
			for (mask = 0x80; mask != 0; mask >>= 1) {
				bn_mul(t, d, d, N, n, 0, doffset, doffset, Noffset);
				if (((e[i+eoffset]&0xFF) & mask) != 0)
				{
					bn_mul(d, t, a, N, n, doffset, 0, aoffset, Noffset);
				}
				else{
					bn_copy(d, t, n, doffset, 0);
				}
			}
	}

	// only for prime N -- stupid but lazy, see if I care
	static void bn_inv(byte[] d, byte[] a, byte[] N, int n, int doffset, int aoffset, int Noffset)
	{
		byte[] t=new byte[512], s=new byte[512];
		bn_copy(t, N, n, 0, Noffset);
		bn_zero(s, n, 0);
		s[n-1] = 2;
		bn_sub_modulus(t, s, n, 0, 0);
		bn_exp(d, a, N, n, t, n, doffset, aoffset, Noffset, 0);
		
	}

}
