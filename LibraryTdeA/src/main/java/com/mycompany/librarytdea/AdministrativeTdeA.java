package com.mycompany.librarytdea;

public class AdministrativeTdeA extends PersonTdeA {
    private final int maxLoans = 2;

    public AdministrativeTdeA(int idNumber, String name, String lastName, String rol) {
        super(idNumber, name, lastName, rol);
    }

    public int getMaxLoans() {
        return maxLoans;
    }
}
