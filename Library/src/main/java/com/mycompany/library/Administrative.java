package com.mycompany.library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Administrative extends Person implements Loanable {
    private final int maxLoans = 5;  

    public Administrative(int idNumber, String name, String lastName, String rol, int currentLoans) {
        super(idNumber, name, lastName, rol, currentLoans);
    }

    @Override
    public int getMaxLoans() {
        return maxLoans;
    }

    @Override
    public boolean borrowMaterial(String idMaterial) {
        if (currentLoans < maxLoans) {
            currentLoans++;
            Material.borrow(idMaterial, idNumber);
            System.out.println(name + " ha tomado prestado el material: " + idMaterial);
            return true;
        } else {
            System.out.println(name + " ha alcanzado el límite de préstamos permitidos.");
            return false;
        }
    }

    @Override
    public boolean returnMaterial(String idMaterial) {
        if (hasActiveLoans()) {
            currentLoans--;
            Material.returnMaterial(idMaterial, idNumber);
            System.out.println(name + " ha devuelto un material");
            return true;
        } else {
            System.out.println(name + " no tiene materiales prestados.");
            return false;
        }
    }

    private boolean hasActiveLoans() {
        String loansFile = "prestamos.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(loansFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (Integer.parseInt(data[0]) == idNumber) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de préstamos: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean renew(String idMaterial) {
        if (currentLoans > 0) {
            Material.renew(idMaterial, idNumber);
            currentLoans++;
            System.out.println(name + " ha renovado el material: " + idMaterial);
            return true;
        } else {
            System.out.println(name + " no tiene materiales prestados para renovar.");
            return false;
        }
    }
}
