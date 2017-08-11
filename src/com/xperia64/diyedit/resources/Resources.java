package com.xperia64.diyedit.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class Resources extends ResourceBundle {
	 public Object handleGetObject(String key) {
         if (key.equals("fileWarningKey")) return "No file selected, exiting";
         // GuiDFrame
         if (key.equals("openFileKey")) return "Open file...";
         if (key.equals("saveToolsKey")) return "Save Tools";
         if (key.equals("modifyGamesKey")) return "Modify Games";
         if (key.equals("modifyRecordsKey")) return "Modify Records";
         if (key.equals("modifyMangaKey")) return "Modify Manga/Comics";
         if (key.equals("unlockDSMedalsKey")) return "Unlock Medals (DS)";
         if (key.equals("unlockDSGamesKey")) return "Unlock Games (DS)";
         if (key.equals("unlockDSRecordsKey")) return "Unlock Records (DS)";
         if (key.equals("unlockDSMangaKey")) return "Unlock Comics(DS)";
         if (key.equals("gameToolsKey")) return "Game Tools";
         if (key.equals("editGameMetadataKey")) return "Edit Game Metadata";
         if (key.equals("recordToolsKey")) return "Record Tools";
         if (key.equals("editRecordMetadataKey")) return "Edit Record Metadata";
         if (key.equals("midiKey")) return "Play/Export MIDI";
         if (key.equals("mangaToolsKey")) return "Manga/Comic Tools";
         if (key.equals("editMangaMetadataKey")) return "Edit Manga Metadata";
         if (key.equals("viewMangaKey")) return "View Manga/Comic";
         if (key.equals("dotEmptyKey")) return ". Empty";
         if (key.equals("extractKey")) return "Extract";
         if (key.equals("injectKey")) return "Inject";
         if (key.equals("deleteKey")) return "Delete";
         if (key.equals("saveChangesKey")) return "Save Changes";
         if (key.equals("gamesKey")) return "Games";
         if (key.equals("recordsKey")) return "Records";
         if (key.equals("mangaKey")) return "Comics/Manga";
         if (key.equals("gameKey")) return "Game";
         if (key.equals("recordKey")) return "Record";
         if (key.equals("comicKey")) return "Comic/Manga";
         if (key.equals("mioFileWarningKey")) return "No MIO file selected";
         if (key.equals("unsavedKey")) return "Unsaved";
         if (key.equals("mioInjectWarningKey")) return "No mio file to inject";
         if (key.equals("mioFileTypeWarningKey")) return "Wrong type or invalid MIO file";
         // End GuiDFrame
         // MetadataFrame
         if (key.equals("nameMetadataKey")) return "Name";
         if (key.equals("descriptionMetadataKey")) return "Description";
         if (key.equals("brandMetadataKey")) return "Brand";
         if (key.equals("creatorMetadataKey")) return "Creator";
         if (key.equals("serialMetadataKey")) return "Serial Number";
         if (key.equals("colorMetadataKey")) return "Color";
         if (key.equals("logoMetadataKey")) return "Logo";
         if (key.equals("logoColorMetadataKey")) return "Logo Color";
         if (key.equals("applyMetadataKey")) return "Apply Changes";
         if (key.equals("commandMetadataKey")) return "Command";
         if (key.equals("gameLengthMetadataKey")) return "Game Length";
         // Game Lengths
         if (key.equals("shortMetadataKey")) return "Short";
         if (key.equals("longMetadataKey")) return "Long";
         if (key.equals("bossMetadataKey")) return "Boss"; // As in Boss game length
         // End Game Lengths
         if (key.equals("cartridgeShapeMetadataKey")) return "Cartridge Shape";
         // MIO metadata stuff
         // Descriptions of game logos
         if (key.equals("pulseMetadataKey")) return "Pulse";
         if (key.equals("rocketMetadataKey")) return "Rocket";
         if (key.equals("shieldMetadataKey")) return "Shield";
         if (key.equals("punchMetadataKey")) return "Punch";
         if (key.equals("carMetadataKey")) return "Car";
         if (key.equals("batMetadataKey")) return "Bat"; // Baseball bat
         if (key.equals("whatMetadataKey")) return "??"; // Probably doesn't need translating
         if (key.equals("otherMetadataKey")) return "Other";
         // End descriptions of game logos
         // Descriptions of game cartridge shapes
         if (key.equals("roundMetadataKey")) return "Round";
         if (key.equals("notchMetadataKey")) return "Notch";
         if (key.equals("flatMetadataKey")) return "Flat";
         if (key.equals("ridgesMetadataKey")) return "Ridges";
         if (key.equals("zigzagMetadataKey")) return "Zigzag";
         if (key.equals("prongsMetadataKey")) return "Prongs";
         if (key.equals("angledMetadataKey")) return "Angled";
         if (key.equals("gbaMetadataKey")) return "GBA"; // Probably doesn't need translating
         if (key.equals("bigNameMetadataKey")) return "Big Name Star";
         // End descriptions of game cartridge shapes
         if (key.equals("recordShapeMetadataKey")) return "Record Shape";
         // Descriptions of record shapes
         if (key.equals("circleMetadataKey")) return "Circle";
         if (key.equals("squareMetadataKey")) return "Square";
         if (key.equals("starMetadataKey")) return "Star";
         if (key.equals("hexagonMetadataKey")) return "Hexagon";
         if (key.equals("cloverMetadataKey")) return "Clover";
         if (key.equals("diamondMetadataKey")) return "Diamond";
         if (key.equals("plusMetadataKey")) return "Plus"; // Looks like a fat +
         if (key.equals("flowerMetadataKey")) return "Flower";
         // End descriptions of record shapes
         // Descriptions of record logos
         if (key.equals("heartMetadataKey")) return "Heart";
         if (key.equals("flowerLogoMetadataKey")) return "Flower";
         if (key.equals("stormMetadataKey")) return "Storm";
         if (key.equals("crossMetadataKey")) return "Cross"; // Looks like a fat X
         if (key.equals("leafMetadataKey")) return "Leaf";
         if (key.equals("dropletMetadataKey")) return "Droplet";
         if (key.equals("lightningMetadataKey")) return "Lightning";
         if (key.equals("smileMetadataKey")) return "Smiley Face";
         // End descriptions of record logos
         // Descriptions of manga logos
         if (key.equals("letterMetadataKey")) return "Letter";
         if (key.equals("houseMetadataKey")) return "House";
         // see game logos for shield
         if (key.equals("skullMetadataKey")) return "Skull";
         if (key.equals("catMetadataKey")) return "Cat";
         // see game logos for bat
         if (key.equals("spaceshipMetadataKey")) return "Spaceship";
         // see record logos for smiley face
         // End descriptions of manga logos
         if (key.equals("metadataMetadataKey")) return "Metadata Editor";
         if (key.equals("lockedMetadataKey")) return "Locked";
         // Metadata warnings
         if (key.equals("nameWarningMetadataKey")) return "Name cannot be longer than 12 characters!";
         if (key.equals("descWarningMetadataKey")) return "Description cannot be longer than 36 characters!";
         if (key.equals("brandWarningMetadataKey")) return "Brand name cannot be named longer than 9 characters!";
         if (key.equals("creatWarningMetadataKey")) return "Creator name cannot be longer than 9 characters!";
         if (key.equals("serial1WarningMetadataKey")) return "First serial cannot be more than 4 characters!";
         if (key.equals("serial2WarningMetadataKey")) return "Second serial cannot be more than 4 characters and must be numeric!";
         if (key.equals("serial3WarningMetadataKey")) return "Third serial cannot be more than 3 characters and must be numeric!";
         if (key.equals("commandWarningMetadataKey")) return "Command cannot be longer than 12 characters!";
         // End metadata warnings
         // Colors
         if (key.equals("yellowMetadataKey")) return "Yellow";
         if (key.equals("lightBlueMetadataKey")) return "Light Blue";
         if (key.equals("greenMetadataKey")) return "Green";
         if (key.equals("orangeMetadataKey")) return "Orange";
         if (key.equals("darkBlueMetadataKey")) return "Dark Blue";
         if (key.equals("redMetadataKey")) return "Red";
         if (key.equals("whiteMetadataKey")) return "White";
         if (key.equals("blackMetadataKey")) return "Black";
         // End Colors
         // End MetadataFrame
         // Other strings
         if (key.equals("midiFileKey")) return "MIDI Files";
         if (key.equals("mioFileKey")) return "MIO Files";
         if (key.equals("saveMidiKey")) return "Save MIDI...";
         if (key.equals("midiWarningKey")) return "No MIDI file to save";
         if (key.equals("musicPlayerKey")) return "Music Player";
         return null;
     }

     public Enumeration<String> getKeys() {
         return Collections.enumeration(keySet());
     }

     // Overrides handleKeySet() so that the getKeys() implementation
     // can rely on the keySet() value.
	 protected Set<String> handleKeySet() {
         return new HashSet<String>(Arrays.asList("fileWarningKey","openFileKey", "saveToolsKey", "modifyGamesKey", "modifyRecordsKey", "modifyMangaKey",
        		 "unlockDSMedalsKey", "unlockDSGamesKey", "unlockDSRecordsKey", "unlockDSMangaKey", "gameToolsKey", "editGameMetadataKey",
        		 "recordToolsKey", "editRecordMetadataKey", "midiKey", "mangaToolsKey", "editMangaMetadataKey", "viewMangaKey", "dotEmptyKey",
        		 "extractKey", "injectKey","deleteKey", "saveChangesKey", "gamesKey", "recordsKey", "mangaKey", "gameKey", "recordKey", "comicKey", "mioFileWarningKey", "unsavedKey",
        		 "mioInjectWarningKey", "mioFileTypeWarningKey", "nameMetadataKey", "descriptionKey", "brandMetadataKey", "creatorMetadataKey", "serialMetadataKey",
        		 "colorMetadataKey", "logoMetadataKey", "logoColorMetadataKey", "applyMetadataKey", "commandMetadataKey", "shortMetadataKey",
        		 "longMetadataKey", "bossMetadataKey", "cartridgeShapeMetadataKey", "pulseMetadataKey", "rocketMetadataKey", "shieldMetadataKey",
        		 "punchMetadataKey", "carMetadataKey", "batMetadataKey", "whatMetadataKey", "otherMetadataKey", "roundMetadataKey", "notchMetadataKey",
        		 "flatMetadataKey", "ridgesMetadataKey", "zigzagMetadataKey", "prongsMetadataKey", "angledMetadataKey", "gbaMetadataKey", "bigNameMetadataKey",
        		 "recordShapeMetadataKey", "circleMetadataKey", "squareMetadataKey", "starMetadataKey", "hexagonMetadataKey", "cloverMetadataKey","diamondMetadataKey", "plusMetadataKey",
        		 "flowerMetadataKey", "heartMetadataKey", "flowerLogoMetadataKey", "crossMetadataKey", "leafMetadataKey", "dropletMetadataKey", "lightningMetadataKey",
        		 "smileMetadataKey", "letterMetadataKey", "houseMetadataKey", "skullMetadataKey", "catMetadataKey", "spaceshipMetadataKey", "metadataMetadataKey", "lockedMetadataKey",
        		 "nameWarningMetadataKey", "descWarningMetadataKey", "brandWarningMetadataKey", "creatWarningMetadataKey", "serial1WarningMetadataKey",
        		 "serial2WarningMetadataKey", "serial3WarningMetadataKey", "commandWarningMetadataKey", "midiFileKey", "mioFileKey", "saveMidiKey", "midiWarningKey"));
     }
}

