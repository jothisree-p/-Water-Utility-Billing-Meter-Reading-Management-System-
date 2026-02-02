package com.water.util;

public class ActiveBillException extends Exception {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "ACTIVE BILL EXISTS – CANNOT PROCEED";
    }
}
