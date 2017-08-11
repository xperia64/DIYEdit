package com.xperia64.diyedit.resources;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Resources_zh_CN extends Resources {
	public Object handleGetObject(String key) {
        if (key.equals("fileWarningKey")) return "没有被选中的文件，退出中……";
        // GuiDFrame
        if (key.equals("openFileKey")) return "打开文件……";
        if (key.equals("saveToolsKey")) return "存档编辑";
        if (key.equals("modifyGamesKey")) return "编辑游戏";
        if (key.equals("modifyRecordsKey")) return "编辑唱片";
        if (key.equals("modifyMangaKey")) return "编辑漫画";
        if (key.equals("unlockDSMedalsKey")) return "解锁奖牌(仅限DS版)";
        if (key.equals("unlockDSGamesKey")) return "解锁游戏(仅限DS版)";
        if (key.equals("unlockDSRecordsKey")) return "解锁唱片(仅限DS版)";
        if (key.equals("unlockDSMangaKey")) return "解锁漫画(仅限DS版)";
        if (key.equals("gameToolsKey")) return "游戏编辑";
        if (key.equals("editGameMetadataKey")) return "编辑游戏元数据";
        if (key.equals("recordToolsKey")) return "唱片编辑";
        if (key.equals("editRecordMetadataKey")) return "编辑唱片元数据";
        if (key.equals("midiKey")) return "播放/导出MIDI";
        if (key.equals("mangaToolsKey")) return "漫画编辑";
        if (key.equals("editMangaMetadataKey")) return "编辑漫画元数据";
        if (key.equals("viewMangaKey")) return "查看漫画";
        if (key.equals("dotEmptyKey")) return ". 空格";
        if (key.equals("extractKey")) return "导出";
        if (key.equals("injectKey")) return "导入";
        if (key.equals("deleteKey")) return "删除";
        if (key.equals("saveChangesKey")) return "保存更改";
        if (key.equals("gamesKey")) return "游戏";
        if (key.equals("recordsKey")) return "唱片";
        if (key.equals("mangaKey")) return "漫画";
        if (key.equals("gameKey")) return "游戏";
        if (key.equals("recordKey")) return "唱片";
        if (key.equals("comicKey")) return "漫画";
        if (key.equals("mioFileWarningKey")) return "没有被选中的MIO文件";
        if (key.equals("unsavedKey")) return "没有被保存";
        if (key.equals("mioInjectWarningKey")) return "没有可以导入的MIO文件";
        if (key.equals("mioFileTypeWarningKey")) return "文件种类不正确!";
        // End GuiDFrame
        // MetadataFrame
        if (key.equals("nameMetadataKey")) return "名称";
        if (key.equals("descriptionMetadataKey")) return "介绍";
        if (key.equals("brandMetadataKey")) return "制作商";
        if (key.equals("creatorMetadataKey")) return "制作人";
        if (key.equals("serialMetadataKey")) return "序列号";
        if (key.equals("colorMetadataKey")) return "颜色";
        if (key.equals("logoMetadataKey")) return "图标";
        if (key.equals("logoColorMetadataKey")) return "图标的颜色";
        if (key.equals("applyMetadataKey")) return "应用改变";
        if (key.equals("commandMetadataKey")) return "游戏指令";
        if (key.equals("gameLengthMetadataKey")) return "游戏时间长度";
        // Game Lengths
        if (key.equals("shortMetadataKey")) return "短";
        if (key.equals("longMetadataKey")) return "长";
        if (key.equals("bossMetadataKey")) return "BOSS游戏"; // As in Boss game length
        // End Game Lengths
        if (key.equals("cartridgeShapeMetadataKey")) return "卡带形状";
        // MIO metadata stuff
        // Descriptions of game logos
        if (key.equals("pulseMetadataKey")) return "FC游戏";
        if (key.equals("rocketMetadataKey")) return "火箭";
        if (key.equals("shieldMetadataKey")) return "盾牌";
        if (key.equals("punchMetadataKey")) return "拳头";
        if (key.equals("carMetadataKey")) return "汽车";
        if (key.equals("batMetadataKey")) return "棒球"; // Baseball bat
        if (key.equals("whatMetadataKey")) return "??"; // Probably doesn't need translating
        if (key.equals("otherMetadataKey")) return "其他";
        // End descriptions of game logos
        // Descriptions of game cartridge shapes
        if (key.equals("roundMetadataKey")) return "圆形";
        if (key.equals("notchMetadataKey")) return "有缺口";
        if (key.equals("flatMetadataKey")) return "扁平";
        if (key.equals("ridgesMetadataKey")) return "有突起";
        if (key.equals("zigzagMetadataKey")) return "锯齿边";
        if (key.equals("prongsMetadataKey")) return "牛角";
        if (key.equals("angledMetadataKey")) return "斜角";
        if (key.equals("gbaMetadataKey")) return "GBA"; // Probably doesn't need translating
        if (key.equals("bigNameMetadataKey")) return "著名人士作品";
        // End descriptions of game cartridge shapes
        if (key.equals("recordShapeMetadataKey")) return "唱片";
        // Descriptions of record shapes
        if (key.equals("circleMetadataKey")) return "圆形";
        if (key.equals("squareMetadataKey")) return "方形";
        if (key.equals("starMetadataKey")) return "星形";
        if (key.equals("hexagonMetadataKey")) return "六边形";
        if (key.equals("cloverMetadataKey")) return "四叶草";
        if (key.equals("diamondMetadataKey")) return "菱形";
        if (key.equals("plusMetadataKey")) return "加号"; // Looks like a fat +
        if (key.equals("flowerMetadataKey")) return "八瓣花朵形";
        // End descriptions of record shapes
        // Descriptions of record logos
        if (key.equals("heartMetadataKey")) return "星形";
        if (key.equals("flowerLogoMetadataKey")) return "花朵";
        if (key.equals("stormMetadataKey")) return "旋风/忍者刀片";
        if (key.equals("crossMetadataKey")) return "大叉"; // Looks like a fat X
        if (key.equals("leafMetadataKey")) return "树叶";
        if (key.equals("dropletMetadataKey")) return "水滴";
        if (key.equals("lightningMetadataKey")) return "闪电";
        if (key.equals("smileMetadataKey")) return "笑脸";
        // End descriptions of record logos
        // Descriptions of manga logos
        if (key.equals("letterMetadataKey")) return "信封";
        if (key.equals("houseMetadataKey")) return "房子";
        // see game logos for shield
        if (key.equals("skullMetadataKey")) return "骷髅头";
        if (key.equals("catMetadataKey")) return "小猫";
        // see game logos for bat
        if (key.equals("spaceshipMetadataKey")) return "UFO";
        // see record logos for smiley face
        // End descriptions of manga logos
        if (key.equals("metadataMetadataKey")) return "元数据编辑器";
        if (key.equals("lockedMetadataKey")) return "锁定";
        // Metadata warnings
        if (key.equals("nameWarningMetadataKey")) return "名字长度不能大于12个字符！";
        if (key.equals("descWarningMetadataKey")) return "描述内容不能大于36个字符！";
        if (key.equals("brandWarningMetadataKey")) return "制作商名字不能大于9个字符！";
        if (key.equals("creatWarningMetadataKey")) return "制作人名字不能大于9个字符！";
        if (key.equals("serial1WarningMetadataKey")) return "序列号第一部分不能大于4个字符！";
        if (key.equals("serial2WarningMetadataKey")) return "序列号第二部分不能大于4个字符且必须为数字！";
        if (key.equals("serial3WarningMetadataKey")) return "序列号第三部分不能大于3个字符且必须为数字！";
        if (key.equals("commandWarningMetadataKey")) return "游戏指令不能大于12个字符！";
        // End metadata warnings
        // Colors
        if (key.equals("yellowMetadataKey")) return "黄色";
        if (key.equals("lightBlueMetadataKey")) return "天蓝色";
        if (key.equals("greenMetadataKey")) return "绿色";
        if (key.equals("orangeMetadataKey")) return "橘色";
        if (key.equals("darkBlueMetadataKey")) return "深蓝色";
        if (key.equals("redMetadataKey")) return "红色";
        if (key.equals("whiteMetadataKey")) return "白色";
        if (key.equals("blackMetadataKey")) return "黑色";
        // End Colors
        // End MetadataFrame
        // Other strings
        if (key.equals("midiFileKey")) return "MIDI文件";
        if (key.equals("mioFileKey")) return "MIO文件（包括瓦里奥制造DIY存档）";
        if (key.equals("saveMidiKey")) return "保存 MIDI...";
        if (key.equals("midiWarningKey")) return "没有可以保存的MIDI文件";
        if (key.equals("musicPlayerKey")) return "音乐播放器";
        return null;
    }

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
