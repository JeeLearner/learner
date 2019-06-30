package com.jee.web.aspect;

public enum SortKeyEnum {

    ASC("ASC"),
    DESC("DESC");

    public final String value;

    SortKeyEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
