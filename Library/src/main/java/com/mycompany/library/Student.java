package com.mycompany.library;

public class Student extends Person {
    private final int maxLoans = 5;

    public Student(int idNumber, String name, String lastName, String rol) {
        super(idNumber, name, lastName, rol);
    }

    public int getMaxLoans() {
        return maxLoans;
    }
}
