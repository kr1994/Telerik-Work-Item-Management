package com.telerikacademy.oop.WIM.models.common.enums;

public enum WIType {
    BUG,
    FEEDBACK,
    STORY;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
