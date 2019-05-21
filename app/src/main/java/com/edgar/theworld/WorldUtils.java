package com.edgar.theworld;

public class WorldUtils {

    public static final String[] DATA_FILE_NAMES = {"items_names/weapons_names.txt",
            "items_names/helmets_names.txt", "items_names/clothes_names.txt",
            "items_names/accessories_names.txt", "items_names/wings_names.txt",
            "items_names/boss_icons_names.txt", "items_names/miscs_names.txt",
            "items_names/item_names_raw.txt"};
    private static final String ASSET_ROOT_PATH = "file:///android_asset/";
    private static final String ASSET_DATA_PATH = ASSET_ROOT_PATH + "data/";

    public static final int MAX_ITEM_PAGE = 5;
    public static final String[] PAGE_TITLES = {"WEAPONS", "HELMETS", "CLOTHES", "ACCESSORIES",
            "WINGS", "BOSS_ICONS", "MISCS"};
    private static final String ASSET_ICONS_PATH = ASSET_ROOT_PATH + "icons/";
    private static final String TAG = WorldUtils.class.getName();

    public static String removeColorCodes(String oldString) {
        //Description=|c0040e0d0[药水]|r |n|CFFFFDEAD一种神奇的混合草药和治疗药水制作的|r |n|c00adff2f∴|r|c0080ff0020秒持续时间 生命值恢复 +800|r
        oldString = oldString.replaceAll("\\|[cC][a-zA-Z0-9]{8}", "");
        String newString = oldString.replaceAll("\\|[cC][a-zA-Z0-9]{6}", "");
        return newString;
    }

    public static String removeNewLinesAndQuotes(String oldString) {
        oldString = oldString.replaceAll("\\|r|\\|n", "");
        oldString = oldString.replaceAll("\\\"", "");
        return oldString;
    }

    public static String getIconFilePath(String artString) {
        String[] splitStrings = artString.split("\\\\");
        String iconFilePath = ASSET_ICONS_PATH.concat("default.bmp");
        if (splitStrings.length >= 2) {
            iconFilePath = splitStrings[splitStrings.length - 1].toLowerCase();
            iconFilePath = iconFilePath.replace(".blp", ".bmp");
            iconFilePath = ASSET_ICONS_PATH.concat(iconFilePath);
        }
        return iconFilePath;
    }

    public static String getItemArtPath(String nameChs) {
        nameChs = nameChs.replace("“", "");
        nameChs = nameChs.replace("”", "");
        nameChs = nameChs.replace("·", " ");
        String result = nameChs.concat(".jpg");
        result = ASSET_ICONS_PATH.concat(result);
        return result;
    }

    public static String getItemNameChs(String nameString) {
        nameString = removeColorCodes(nameString);
        nameString = removeNewLinesAndQuotes(nameString);
        nameString = nameString.replace("Name=", "");
        nameString = nameString.replace(" ", "");
        nameString = nameString.replace("+", " +");
        return nameString;
    }

    public static String getItemNameEng(String nameString) {
        nameString = removeColorCodes(nameString);
        nameString = removeNewLinesAndQuotes(nameString);
        nameString = nameString.replace("EngName=", "");
        return nameString;
    }

    public static String getDescriptionString(String descriptionString) {
        descriptionString = descriptionString.replace("Description=", "");
        descriptionString = removeColorCodes(descriptionString);
        descriptionString = descriptionString.replaceAll("\\\"", "");
        descriptionString = descriptionString.replaceAll("\\|r[ ]*\\|n", "\n");
        return descriptionString;
    }

    public static String getLevelString(String descriptionString) {
        descriptionString = removeColorCodes(descriptionString);
        descriptionString = removeNewLinesAndQuotes(descriptionString);
        String levelString = "Lv.0";
        if (!descriptionString.contains("等级.") && !descriptionString.contains("Lv."))
            return levelString;
        String[] splitStrings = descriptionString.split("\\.");
        if (splitStrings.length >= 2) {
            levelString = "Lv.".concat(splitStrings[splitStrings.length - 1]);
        }
        return levelString;
    }

    public static String getItemQuality(String descriptionString) {
        descriptionString = removeColorCodes(descriptionString);
        descriptionString = removeNewLinesAndQuotes(descriptionString);
        String qualityString = "Default";

        if (descriptionString.contains("Description=[史诗]")) {
            String[] splitStrings = descriptionString.split("- | -");
            if (splitStrings.length >= 3) {
                qualityString = splitStrings[1];
                qualityString = qualityString.replace(" 品质", "");
            }
        } else {
            String[] splitStrings = descriptionString.split("[\\[\\]]");
            if (splitStrings.length >= 3) {
                qualityString = splitStrings[1];
            }
        }
        return qualityString;
    }

    public static String getIndexString(String lineString) {
        String[] splitStrings = lineString.split("[\\[\\]]");
        String result = "I000";
        if (splitStrings.length >= 3) {
            result = splitStrings[1];
        }
        return result;
    }

}
