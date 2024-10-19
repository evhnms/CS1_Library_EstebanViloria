package com.mycompany.library;

public class Teacher extends Person {
    private final int maxLoans = 3;

    public Teacher(int idNumber, String name, String lastName, String rol) {
        super(idNumber, name, lastName, rol);
    }

    public int getMaxLoans() {
        return maxLoans;
    }
}
