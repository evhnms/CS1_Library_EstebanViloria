package com.mycompany.librarytdea;

public class TeacherTdeA extends PersonTdeA {
    private final int maxLoans = 3;

    public TeacherTdeA(int idNumber, String name, String lastName, String rol) {
        super(idNumber, name, lastName, rol);
    }

    public int getMaxLoans() {
        return maxLoans;
    }
}
