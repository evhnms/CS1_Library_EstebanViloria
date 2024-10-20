package com.mycompany.cs1_library_estebanviloria;

public class Student extends Person {
    private final int maxLoans = 5;

    public Student(int idNumber, String name, String lastName, String rol) {
        super(idNumber, name, lastName, rol);
    }

    public int getMaxLoans() {
        return maxLoans;
    }
}
