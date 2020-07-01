package com.github.donkeyrit.javaapp.model.enums;

public enum CarStatus {
    OPEN("open", "Free"),
    BUSY("lock", "Busy");

    private String assetName;
    private String uiValue;

    public String getAssetName() {
        return assetName;
    }

    public String getUiValue() {
        return uiValue;
    }

    CarStatus(String assetName, String uiValue) {
        this.assetName = assetName;
        this.uiValue = uiValue;
    }
}
