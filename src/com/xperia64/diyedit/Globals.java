package com.xperia64.diyedit;

import java.util.HashMap;
import java.util.Map;


// Class with global variables
public class Globals {

	// Checksum error codes
	public static final String[] errorCodes = {"Reading checksums OK!","First checksum is invalid!","Second checksum is invalid!","Both checksums are invalid!"};
	// Instrument names
	public static final String[] instrumentCodes = {"Piano","Organ","Harpsichord","Melodica","Flute","Trumpet","Saxophone","Wood Flute",
											  "Acoustic Guitar","Electric Guitar","Banjo","Bass","Violin","Marimba","Vibraphone","Timpani",
											  "Star Drop","UFO","Alien","Robot","Rocket","Moon","Green Dude","Phone Dial",
											  "Cat","Dog","Pig","Insects","Frog","Yoshi","Birds","Monkeys",
											  "DoReMi Voice","Wah Dude","Opera Man","Soul Girl","Baby","Laughing Men","KungFu Men","Humming",
											  "DingDing","PongPong","FahFah","BongBong","BingBing","TingTing","BlingBling","BoonBoon"};
	
	// In semitones
	public static final int[] instrumentOctave = {0,0,0,0,24,0,0,12,
											0,0,0,0,0,0,0,0,
											0,0,0,0,0,0,0,0,
											0,0,0,0,0,0,0,0,
											0,0,0,0,0,0,0,0,
											12,12,24,-12,0,36,24,-12};
	
	// DO NOT EDIT
	/*public static final float[] realLengths = {6f,10f,4f,9f,8f,2f,4f,8f,
										 4f,16f,4f,4f,8f,2f,10f,3f,
										 20f,4.5f,7.5f,2.5f,4f,1f,7f,1.5f,
										 2f,1.5f,2f,2.5f,1f,3.5f,2f,2f,
										 3f,8f,8.5f,8.5f,3f,2.5f,2f,12f,
										 8f,8.5f,12f,8f,12f,5f,4f,2f};*/
	
	// Edit this one
	public static final float[] instrumentLengths = {6f,10f,4f,9f,8f,2f,4f,8f,
											   4f,16f,4f,4f,8f,2f,10f,3f,
											   20f,4.5f,7.5f,2.5f,4f,1f,7f,1.5f,
											   2f,1.5f,2f,2.5f,1f,3.5f,2f,2f,
											   3f,8f,8.5f,8.5f,3f,2.5f,2f,12f,
											   8f,8.5f,12f,8f,12f,5f,4f,2f};
	
	public static final int[] instrumentDecay = {72,72,72,72,72,72,72,72,
										   100,127,72,72,72,72,72,72,
										   72,72,72,72,72,72,72,72,
										   72,72,72,72,72,72,72,72,
										   72,72,72,72,72,72,72,72,
										   70,80,100,100,127,110,90,40};
	
	public static final String[] instrumentConversion = {"0","19","6","21","73","56","66","75",
												   "24","29","105","32","40","12","11","47",
												   "111","78","36","87","53","38","80","13",
												   "82","127","122","108","55","123","53","54",
												   "30","53","85","121","57","126","91","80",
												   "80","80","80","80","80","80","80","80"};
	// Drumset names
	public static final String[] drumCodes = {"Normal Drums","Electric Drums","Samba Drums","Asian Drums","Kitchen Drums","Toy Drums","BeatBox Drums","8bit Drums"};
	public static final String[] drumConversion = {"[ACOUSTIC_BASS_DRUM]","[ACOUSTIC_SNARE]","[CLOSED_HI_HAT]","[OPEN_HI_HAT]","[CRASH_CYMBAL_1]","[LOW_FLOOR_TOM]","[HIGH_FLOOR_TOM]","[LOW_MID_TOM]","[SIDE_STICK]","[HAND_CLAP]","[TAMBOURINE]","[SHORT_GUIRO]","[MUTE_TRIANGLE]","[OPEN_TRIANGLE]"};
	// Note names in original format
	public static final String[] noteCodes = {"LowSol", "LowSolSharp","LowLa","LowLaSharp","LowTi","LowDo","LowDoSharp","LowRe","LowReSharp","LowMi","MiddleFa","MiddleFaSharp","MiddleSol","MiddleSolSharp","MiddleLa","MiddleLaSharp","MiddleTi","HighDo","HighDoSharp","HighRe","HighReSharp","HighMi","HighFa","HighFaSharp","HighSol"};
	// Note names in real format
	public static final String[] noteCodesPiano = {"LowG", "LowGSharp","LowA","LowASharp","LowB","LowC","LowCSharp","LowD","LowDSharp","LowE","MiddleF","MiddleFSharp","MiddleG","MiddleGSharp","MiddleA","MiddleASharp","MiddleB","HighC","HighCSharp","HighD","HighDSharp","HighE","HighF","HighFSharp","HighG"};
	
	//
	//public static String[] colors = {"Yellow", "Light Blue", "Green", "Orange", "Dark Blue", "Red", "White", "Black"};
	// Storage array for file
	//public static byte[] read;
	// Name of file
	//public static String name;
	// File R/W for mio file
	//public static FileByteOperations MyfileIO = new FileByteOperations();
	// Switch for MetadataFrame
	public static int MetadataMode=0;
	// Switch for GuiDFrame
	public static boolean MetadataShown=false;
	public static boolean playing=false;
	public static String lastFolder="";
	public static final boolean forceChina=false; // Temp thing to control language. Chinese and English are the only languages. 
	public static final int[] objectSize = {1,4,9,16};
	public static HashMap<Integer, String> translation = new HashMap<Integer, String>();
	public static HashMap<String, Integer> reverseTranslation = new HashMap<String, Integer>();
	// Is there a better way to do this? Probably.
	public static void populateTranslation()
	{
		translation.put(0xC2A3, "£");
		// Japan
		translation.put(0xC397, "×");
		translation.put(0xC3B7, "÷");
		// End Japan
		translation.put(0xDC80,"̲1");
		translation.put(0xDC81,"̲2");
		translation.put(0xDC82,"̲3");
		translation.put(0xDC83,"̲4");
		translation.put(0xDC84,"̲5");
		translation.put(0xDC85,"̲6");
		translation.put(0xDC86,"̲7");
		translation.put(0xDC87,"̲8");
		translation.put(0xDC88,"̲9");
		translation.put(0xDC89,"̲1̲0");
		translation.put(0xDC8A,"̲1̲1");
		translation.put(0xDC8B,"̲1̲2");
		translation.put(0xDC8C,"̲1̲3");
		translation.put(0xDC8D,"̲1̲4");
		translation.put(0xDC8E,"̲1̲5");
		translation.put(0xDC8F,"̲A");
		translation.put(0xDC90,"̲B");
		translation.put(0xDC91,"̲C");
		translation.put(0xDC92,"̲D");
		translation.put(0xDC93, "★");
		translation.put(0xDC94, "→");
		translation.put(0xDC95, "←");
		translation.put(0xDC96, "↑");
		translation.put(0xDC97, "↓");
		translation.put(0xDCA0, "€");
		
		// Japan
		translation.put(0xE2809C, "“");
		translation.put(0xE2809D, "”");
		translation.put(0xE280A6, "…"); // Close enough. Should be centered vertically tho
		translation.put(0xE280BB, "※");
		translation.put(0xE28690, "←");
		translation.put(0xE28691, "↑");
		translation.put(0xE28692, "→");
		translation.put(0xE28693, "↓");
		translation.put(0xE291A0, "①"); //1
		translation.put(0xE291A1, "②"); //2
		translation.put(0xE291A2, "③"); //3
		translation.put(0xE291A3, "④"); //4
		translation.put(0xE291A4, "⑤"); //5
		translation.put(0xE291A5, "⑥"); //6
		translation.put(0xE291A6, "⑦"); //7
		translation.put(0xE291A7, "⑧"); //8
		translation.put(0xE291A8, "⑨"); //9
		translation.put(0xE291A9, "⑩"); //10
		translation.put(0xE291AA, "⑪"); //11
		translation.put(0xE291AB, "⑫"); //12
		translation.put(0xE291AC, "⑬"); //13
		translation.put(0xE291AD, "⑭"); //14
		translation.put(0xE291AE, "⑮"); //15
		translation.put(0xE291AF, "Ⓐ");
		translation.put(0xE291B0, "Ⓑ");
		translation.put(0xE291B1, "Ⓒ");
		translation.put(0xE291B2, "Ⓓ");
		translation.put(0xE296A1, "□");
		translation.put(0xE296B3, "△");
		translation.put(0xE2978B, "◯");
		translation.put(0xE2978E, "◎");
		translation.put(0xE29885, "★");
		translation.put(0xE29886, "☆");
		translation.put(0xE299AA, "♪");
		translation.put(0xE299AD, "♭");
		translation.put(0xE299AF, "♯");
		translation.put(0xE38080, " ");
		translation.put(0xE3808C, "「");
		translation.put(0xE3808D, "」");
		translation.put(0xE3808E, "『");
		translation.put(0xE3808F, "』");
		translation.put(0xE383BB, "・");
		translation.put(0xEE8086, "◔ ");
		translation.put(0xEE8095, "❤");
		translation.put(0xEFBC81, "!");
		translation.put(0xEFBC84, "$");
		translation.put(0xEFBC85, "%");
		translation.put(0xEFBC86, "&");
		translation.put(0xEFBC88, "(");
		translation.put(0xEFBC89, ")");
		translation.put(0xEFBC8B, "+");
		translation.put(0xEFBC8C, ",");
		translation.put(0xEFBC8D, "-");
		translation.put(0xEFBC8E, ".");
		translation.put(0xEFBC8F, "〳");
		// Numerals
		for(int i = 0; i<10; i++)
		{
			translation.put(0xEFBC90+i, Character.toString((char)(i+0x30)));
		}
		translation.put(0xEFBC9A, ":"); // Use japan-y characters to get around filename restrictions
		translation.put(0xEFBC9B, ";"); // On second thought, leave them and just replace them when extracting
		translation.put(0xEFBC9C, "<");
		translation.put(0xEFBC9D, "=");
		translation.put(0xEFBC9E, ">");
		translation.put(0xEFBC9F, "?");
		translation.put(0xEFBCA0, "@");
		// Capital Letters
		for(int i = 0; i<26; i++)
		{
			translation.put(0xEFBCA1+i, Character.toString((char)(i+0x41)));
		}
		// Lowercase Letters
		for(int i = 0; i<26; i++)
		{
			translation.put(0xEFBD81+i, Character.toString((char)(i+0x61)));
		}
		translation.put(0xEFBD9E, "〜");
		translation.put(0xEFBFA5, "¥");
		
		// Invert stuff.
		for(Map.Entry<Integer, String> entry : translation.entrySet()){
		    reverseTranslation.put(entry.getValue(), entry.getKey());
		}
	}
	public static String stripBadChars(String s)
	{
		// Better alternative characters?
		return s.replace('?', '¿').replace('/', '〳').replace('\\','〳').replace(':','：').replace('*', '★').replace('\"','\'').replace('<','＜').replace('>','＞').replace('|','l').replace('!','¡');
	}
	//ContentLetter+"-"+Person+"("+Brand+")- "+"("+Region+") ("+Date+") "+Title+".mio";
}
