package com.github.donkeyrit.javaapp.model.enums;

public enum CarStatus {
    OPEN("open"),
    BUSY("lock");

    private String assetName;

    public String getAssetName() {
        return assetName;
    }

    CarStatus(String assetName) {
        this.assetName = assetName;
    }
}
