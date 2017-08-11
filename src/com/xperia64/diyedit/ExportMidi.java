package com.xperia64.diyedit;

import java.io.File;
import java.io.IOException;

//import javax.sound.midi.InvalidMidiDataException;
//import javax.sound.midi.MidiSystem;
//import javax.sound.midi.MidiUnavailableException;
//import javax.sound.midi.Soundbank;
//import javax.sound.midi.Synthesizer;

import org.jfugue.*;

import com.xperia64.diyedit.editors.RecordEdit;



public class ExportMidi {
	// Switches to force or unforce swing mode
	private boolean forceSwing = false;
	private boolean forceUnSwing = false;
	private Player p = new Player();
	private boolean paused=false;
	private int offset=43;
	byte[] bb;
	public ExportMidi(byte[] b)
	{
		bb=b;
	}
	// Exports or plays mio file as a midi using jfugue
	public void export(String filename, boolean play)
	{	
		String pl = calculatePlaystring();
		System.out.println(pl);
		if(play)
		{
			//Soundbank soundbank = null;
			/*try {
				soundbank = MidiSystem.getSoundbank(new File("merlin_symphony.sf2"));
			} catch (InvalidMidiDataException e) {
				System.out.println("Bad midi data");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("IO Exception");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
					
					 /*Synthesizer synth = null;
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
					}*/
					
						p=new Player();
					
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
	private String calculatePlaystring()
	{
		RecordEdit ed = new RecordEdit(bb);
		int note;
		int t = ed.getTempo(); 
		if((ed.getSwing()||forceSwing)&&!forceUnSwing)
			t*=3; // I cheat because jfugue is bad with chained tuplets. 2 becomes 1.5
		else
			t*=4;
		
		StringBuilder s = new StringBuilder("T");
		s.append(t);
		// flipflop determines whether to shorten a beat or not based on the swing switches.
		boolean flipflop=false;
		// Tracks 1-4, 1 note per beat
		for(int x = 0; x<4; x++)
		{
			
			float dur=-1;
			int lastNote=-1;
			for(int i = 0; i<ed.getFlag();i++)
			{
				dur=0;
				// V = Channel, I = Instrument, (self explanatory), X72 = release time
				int instrument = ed.getInstrument(i, x);
				float correctLength = Globals.instrumentLengths[instrument];
				int correctDecay = Globals.instrumentDecay[instrument];
				s.append(String.format(" V%d I%s X[Pan_Position]=%d X[Volume]=%d X72=%d", x, Globals.instrumentConversion[instrument], 3200*ed.getPanning(i, x), 3200*ed.getVolume(i, x), correctDecay));
				int correctPitch=Globals.instrumentOctave[instrument];
				
				// TODO:
				// Predict the length of notes based on the notes or die off
				for(int o = 0; o<32; o++)
				{

					if((ed.getSwing()||forceSwing)&&!forceUnSwing) // Simulates swing feature of DIY
					{
						if(flipflop)
						{
							flipflop=false;
							note=ed.getNote(i, x, o);
							
							if(note!=-1)
							{
								note+=offset;
								dur=0;
								s.append(" [");
								s.append((note+correctPitch));
								s.append("]i");
								lastNote=s.length();
							}else{
								if(dur<correctLength&&lastNote>0)
								{
									s=new StringBuilder(s.substring(0, lastNote) + "i" + s.substring(lastNote, s.length()));
									//System.out.println(s);
									dur+=0.5f;
									lastNote++;
								}else{
									s.append(" Ri");
								}
							}
						}else{
							flipflop=true;
							note=ed.getNote(i, x, o);
							if(note!=-1)
							{
								note+=offset;
								dur=0;
								s.append(" [");
								s.append((note+correctPitch));
								s.append("]q");
								lastNote=s.length();
							}else{
								if(dur<correctLength&&lastNote>0)
								{
									s=new StringBuilder(s.substring(0, lastNote) + "q" + s.substring(lastNote, s.length()));
									//System.out.println(s);
									dur+=1;
									lastNote++;
								}else{
									s.append(" Rq");
								}
							}
							}
					}else{
						note=ed.getNote(i, x, o);
						
						if(note!=-1)
						{
							note+=offset;
							dur=0;
							s.append(" [");
							s.append((note+correctPitch));
							s.append("]q");
							lastNote=s.length();
						}else{
							if(dur<correctLength&&lastNote>0)
							{
								dur+=1;
								s=new StringBuilder(s.substring(0, lastNote) + "q"+ s.substring(lastNote, s.length()));
								lastNote++;
							}else{
								s.append(" Rq");
							}
						}
					}
				}
			}
		}
		// Drum track, up to 4 notes per beat
		// the + determines whether to play the drums together or not
		flipflop=false;
		for(int i = 0; i<ed.getFlag();i++)
		{
			
			for(int o = 0; o<32; o++)
			{
				int drum1=ed.getDrum(i, 0, o);
				int drum2=ed.getDrum(i, 1, o);
				int drum3=ed.getDrum(i, 2, o);
				int drum4=ed.getDrum(i, 3, o);
				// Pan position and volume. Yeah.
				s.append(String.format(" V9 X[Pan_Position]=%d X[Volume]=%d ", 1200*ed.getPanning(i, 4), 2000*ed.getVolume(i, 4)));
				if((ed.getSwing()||forceSwing)&&!forceUnSwing)
				{
					if(flipflop)
					{
						flipflop=false;
						if(drum1==-1&&drum2==-1&&drum3==-1&&drum4==-1)
						{
							s.append("Ri ");
						}else{
							if(drum1!=-1)
							{
								s.append(Globals.drumConversion[drum1]);
								if(drum2==-1)
								{
									s.append("i ");
								}else{
									s.append("i+");
								}
							}
							if(drum2!=-1)
							{
								s.append(Globals.drumConversion[drum2]);
								if(drum3==-1)
								{
									s.append("i ");
								}else{
									s.append("i+");
								}
							}
							if(drum3!=-1)
							{
								s.append(Globals.drumConversion[drum3]);
								if(drum4==-1)
								{
									s.append("i ");
								}else{
									s.append("i+");
								}
							}
							if(drum4!=-1)
							{
								s.append(Globals.drumConversion[drum2]+"i ");
							}
						}
					}else{
						flipflop=true;
						if(drum1==-1&&drum2==-1&&drum3==-1&&drum4==-1)
						{
							s.append("Rq ");
						}else{
							if(drum1!=-1)
							{
								s.append(Globals.drumConversion[drum1]);
								if(drum2==-1)
								{
									s.append("q ");
								}else{
									s.append("q+");
								}
							}
							if(drum2!=-1)
							{
								s.append(Globals.drumConversion[drum2]);
								if(drum3==-1)
								{
									s.append("q ");
								}else{
									s.append("q+");
								}
							}
							if(drum3!=-1)
							{
								s.append(Globals.drumConversion[drum3]);
								if(drum4==-1)
								{
									s.append("q ");
								}else{
									s.append("q+");
								}
							}
							if(drum4!=-1)
							{
								s.append(Globals.drumConversion[drum4]+"q ");
							}
						}
					}
				}else{
					if(drum1==-1&&drum2==-1&&drum3==-1&&drum4==-1)
					{
						s.append("Rq ");
					}else{
						if(drum1!=-1)
						{
							s.append(Globals.drumConversion[drum1]);
							if(drum2==-1)
							{
								s.append("q ");
							}else{
								s.append("q+");
							}
						}
						if(drum2!=-1)
						{
							s.append(Globals.drumConversion[drum2]);
							if(drum3==-1)
							{
								s.append("q ");
							}else{
								s.append("q+");
							}
						}
						if(drum3!=-1)
						{
							s.append(Globals.drumConversion[drum3]);
							if(drum4==-1)
							{
								s.append("q ");
							}else{
								s.append("q+");
							}
						}
						if(drum4!=-1)
						{
							s.append(Globals.drumConversion[drum4]+"q ");
						}
					}
				}
			}
		}
		return s.toString();
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
