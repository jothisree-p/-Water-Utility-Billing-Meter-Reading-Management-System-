package com.water.util;

public class ReadingAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "METER READING ALREADY EXISTS FOR THIS PERIOD";
    }
}
