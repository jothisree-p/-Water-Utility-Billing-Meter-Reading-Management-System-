package com.water.util;

public class ValidationException extends Exception {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "VALIDATION FAILED";
    }
}
