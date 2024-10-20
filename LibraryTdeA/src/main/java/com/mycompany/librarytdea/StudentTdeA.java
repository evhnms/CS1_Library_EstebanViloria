package com.mycompany.librarytdea;

public class StudentTdeA extends PersonTdeA {
    private final int maxLoans = 5;

    public StudentTdeA(int idNumber, String name, String lastName, String rol) {
        super(idNumber, name, lastName, rol);
    }

    public int getMaxLoans() {
        return maxLoans;
    }
}
