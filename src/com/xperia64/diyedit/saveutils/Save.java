package com.xperia64.diyedit.saveutils;

import java.util.ArrayList;

public interface Save {

	ArrayList<byte[]> getMioList(int mode);
	void setMio(int slotNumber, byte[] mio);
	void deleteMio(int mode, int slotNumber);
}
