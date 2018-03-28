package com.example.stpl.loyality_ui.aappii;

/**
 * Created by stpl on 5/3/18.
 */

public class Current_Tier {


    private int maintanancePoints,tierBonusPercentage;
    private String displayName;

    public int getMaintanancePoints() {
        return maintanancePoints;
    }

    public void setMaintanancePoints(int maintanancePoints) {
        this.maintanancePoints = maintanancePoints;
    }

    public int getTierBonusPercentage() {
        return tierBonusPercentage;
    }

    public void setTierBonusPercentage(int tierBonusPercentage) {
        this.tierBonusPercentage = tierBonusPercentage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
