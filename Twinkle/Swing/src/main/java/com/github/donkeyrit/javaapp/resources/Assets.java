package com.github.donkeyrit.javaapp.resources;

public enum Assets {
    AVATAR("avatar"),
    MINI_AVATAR("avatar/mini_avatar"),
    BACKGROUND("background"),
    BUTTONS("buttons"),
    CARS("cars"),
    MINI_CARS("cars/min"),
    FLAGS("flags"),
    LOGO("logo"),
    STATUS("status");

    private final String path;

    Assets(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
