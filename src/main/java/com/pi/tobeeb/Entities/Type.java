package com.pi.tobeeb.Entities;

public enum Type {
    PRESENCIEL("PRESENCIEL"),
    ENLIGNE("ENLIGNE");

    private final String value;

    Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
