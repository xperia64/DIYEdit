package com.xperia64.diyedit;

// Need to make a more general class


import java.io.File;
import java.io.IOException;

//import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
//import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

import org.jfugue.*;

import com.xperia64.diyedit.editors.GameEdit;




public class ExportGameMidi {
	// Switches to force or unforce swing mode
	private boolean forceSwing = false;
	private boolean forceUnSwing = false;
	private Player p = new Player();
	private boolean paused=false;
	private int offset=43;
	byte[] bb;
	public ExportGameMidi(byte[] b)
	{
		bb=b;
	}
	// Exports or plays mio file as a midi using jfugue
	public void export(String filename, boolean play)
	{	
		String pl = calculatePlaystring();
		if(play)
		{
					
					 Synthesizer synth = null;
					try {
						synth = MidiSystem.getSynthesizer();
						
					} catch (MidiUnavailableException e) {
						System.out.println("Midi unavailable");
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						
						synth.open();
						//javax.sound.midi.Instrument[] x = synth.getLoadedInstruments();
						//for(int i = 0; i<x.length; i++)
						//{
						//	synth.unloadInstrument(x[i]);
						//}
						//synth.loadAllInstruments(soundbank);
					} catch (MidiUnavailableException e) {
						System.out.println("Midi unavailable");
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 try {
						p=new Player(synth);
					} catch (MidiUnavailableException e) {
						System.out.println("Midi unavailable");
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		p.play(pl);
		}else{
			File f = new File(filename);
			try {
				p.saveMidi(pl, f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	// Calculates extremely long string of notes, instruments etc.
	// Games don't swing and have only one tempo. 2fast.
	private String calculatePlaystring()
	{
		GameEdit ge = new GameEdit(bb);
		GameEdit.GameMusic ed = ge.getGameMusic();
		int note;
		//int t = 50*ed.getTempo()/13; // Random, but sounds right
		/*if((ed.getSwing()||forceSwing)&&!forceUnSwing)
		{
		t=(int) (50*ed.getTempo()/17.3); // Again, random
		}*/
		int t=480;
		String s = "T"+(t)+" ";
		// flipflop determines whether to shorten a beat or not based on the swing switches.
		boolean flipflop=false;
		forceUnSwing=true;
		// Tracks 1-4, 1 note per beat
		for(int x = 0; x<=3; x++)
		{
			
				s+="V"+x+" I"+Globals.instrumentConversion[ed.getInstrument(x)]+" X[Pan_Position]="+3200*ed.getPanning(x)+" X[Volume]="+3200*ed.getVolume( x)+" ";
				int correctPitch=0;
				switch(ed.getInstrument(x)) // Pitch correction
				{
				case 40:
					correctPitch=12;
					break;
				case 41:
					correctPitch=12;
					break;
				case 42:
					correctPitch=24;
					break;
				case 43:
					correctPitch=-12;
					break;
				case 45:
					correctPitch=36;
					break;
				case 46:
					correctPitch=36;
					break;
				case 47:
					correctPitch=-12;
					break;
				}
				@SuppressWarnings("unused")
				String correctLength="";
				switch(ed.getInstrument(x))
				{
				
				}
				for(int o = 0; o<32; o++)
				{

					if((forceSwing)&&!forceUnSwing) // Simulates swing feature of DIY
					{
						if(flipflop)
						{
							flipflop=false;
							note=ed.getNote(x, o);
							note+=offset;
							if(note!=(offset-1))
							{
								s+="["+(note+correctPitch)+"]i ";
							}else{
							s+="Ri ";
							}
						}else{
							flipflop=true;
							note=ed.getNote(x, o);
							note+=offset;
							if(note!=(offset-1))
							{
								s+="["+(note+correctPitch)+"] ";
							}else{
							s+="Rq ";
							}
							}
					}else{
						note=ed.getNote(x, o);
						note+=offset;
						if(note!=(offset-1))
						{
							s+="["+(note+correctPitch)+"] ";
						}else{
						s+="Rq ";
						}
						
					}
					
					
				}
			}
		// Drum track, up to 4 notes per beat
		// the + determines whether to play the drums together or not
		/*for(int i = 0; i<ed.getFlag()+1;i++)
		{*/
			
			for(int o = 0; o<32; o++)
			{
				int drum1=ed.getDrum( 0, o);
				int drum2=ed.getDrum( 1, o);
				int drum3=ed.getDrum( 2, o);
				int drum4=ed.getDrum( 3, o);
				s+="V9 X[Pan_Position]="+1200*ed.getPanning( 4)+" X[Volume]="+2000*ed.getVolume( 4)+" ";
				if((forceSwing)&&!forceUnSwing)
				{
					if(flipflop)
					{
						flipflop=false;
						if(drum1==-1&&drum2==-1&&drum3==-1&&drum4==-1)
						{
							s+="Rq ";
						}else{
							if(drum1!=-1)
							{
								s+=Globals.drumConversion[drum1];
								if(drum2==-1)
								{
									s+="q ";
								}else{
									s+="q+";
								}
							}
							if(drum2!=-1)
							{
								s+=Globals.drumConversion[drum2];
								if(drum3==-1)
								{
									s+="q ";
								}else{
									s+="q+";
								}
							}
							if(drum3!=-1)
							{
								s+=Globals.drumConversion[drum3];
								if(drum4==-1)
								{
									s+="q ";
								}else{
									s+="q+";
								}
							}
							if(drum4!=-1)
							{
								s+=Globals.drumConversion[drum2]+"q ";
							}
						}
					}else{
						flipflop=true;
						if(drum1==-1&&drum2==-1&&drum3==-1&&drum4==-1)
						{
							s+="Ri ";
						}else{
							if(drum1!=-1)
							{
								s+=Globals.drumConversion[drum1];
								if(drum2==-1)
								{
									s+="i ";
								}else{
									s+="i+";
								}
							}
							if(drum2!=-1)
							{
								s+=Globals.drumConversion[drum2];
								if(drum3==-1)
								{
									s+="i ";
								}else{
									s+="i+";
								}
							}
							if(drum3!=-1)
							{
								s+=Globals.drumConversion[drum3];
								if(drum4==-1)
								{
									s+="i ";
								}else{
									s+="i+";
								}
							}
							if(drum4!=-1)
							{
								s+=Globals.drumConversion[drum2]+"i ";
							}
						}
					}
				}else{
					if(drum1==-1&&drum2==-1&&drum3==-1&&drum4==-1)
					{
						s+="Rq ";
					}else{
						if(drum1!=-1)
						{
							s+=Globals.drumConversion[drum1];
							if(drum2==-1)
							{
								s+="q ";
							}else{
								s+="q+";
							}
						}
						if(drum2!=-1)
						{
							s+=Globals.drumConversion[drum2];
							if(drum3==-1)
							{
								s+="q ";
							}else{
								s+="q+";
							}
						}
						if(drum3!=-1)
						{
							s+=Globals.drumConversion[drum3];
							if(drum4==-1)
							{
								s+="q ";
							}else{
								s+="q+";
							}
						}
						if(drum4!=-1)
						{
							s+=Globals.drumConversion[drum2]+"q ";
						}
					}
				}
				
				
				
			}
		//}
		return s;
	}
	public void PlayPause()
	{
		if(!paused)
		{
			paused=true;
			p.pause();
		}else{
			paused=false;
			p.resume();
		}
		
	}
	public void Stop()
	{
		p.stop();
	}
	public boolean playing()
	{
		return p.isPlaying();
	}
	public boolean paused()
	{
		return p.isPaused();
	}
	
}
