package com.tr.csvgenerator.model;

/**
 * Created by erez on 11/26/15.
 */
public enum Separator {
    DEFAULT(','),
    TAB('\t'),
    PIPE('|');

    char separator;

    Separator(char separator) {
        this.separator = separator;
    }

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    @Override
    public String toString() {
        return String.valueOf(separator);
    }

    public char getSeparatorAsChar() {
        return separator;
    }
}
