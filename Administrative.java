package com.mycompany.library;

public class Administrative extends Person {
    private final int maxLoans = 2;

    public Administrative(int idNumber, String name, String lastName, String rol) {
        super(idNumber, name, lastName, rol);
    }

    public int getMaxLoans() {
        return maxLoans;
    }
}
