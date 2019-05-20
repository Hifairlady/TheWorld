package com.edgar.theworld;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "equips_table")
public class EquipItem {

    @PrimaryKey
    private int itemId;

    private String itemIndex, nameChs, nameEng, itemArtPath, itemType, itemLevel, itemQuality, itemDescription;

    public EquipItem(int itemId, String itemIndex, String nameChs, String nameEng, String itemArtPath, String itemType, String itemLevel, String itemQuality, String itemDescription) {
        this.itemId = itemId;
        this.itemIndex = itemIndex;
        this.nameChs = nameChs;
        this.nameEng = nameEng;
        this.itemArtPath = itemArtPath;
        this.itemType = itemType;
        this.itemLevel = itemLevel;
        this.itemQuality = itemQuality;
        this.itemDescription = itemDescription;
    }

    public String getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(String itemLevel) {
        this.itemLevel = itemLevel;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getNameChs() {
        return nameChs;
    }

    public void setNameChs(String nameChs) {
        this.nameChs = nameChs;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(String itemIndex) {
        this.itemIndex = itemIndex;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemArtPath() {
        return itemArtPath;
    }

    public void setItemArtPath(String itemArtPath) {
        this.itemArtPath = itemArtPath;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemQuality() {
        return itemQuality;
    }

    public void setItemQuality(String itemQuality) {
        this.itemQuality = itemQuality;
    }
}
