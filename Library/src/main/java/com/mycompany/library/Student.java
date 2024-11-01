package com.mycompany.library;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class Student extends Person implements Loanable {
    private final int maxLoans = 5;

    public Student(int idNumber, String name, String lastName, String rol, int currentLoans) {
        super(idNumber, name, lastName, rol, currentLoans);
    }

    @Override
    public int getMaxLoans() {
        return maxLoans;
    }

   @Override
public boolean borrowMaterial(String idMaterial) {
    if (currentLoans < maxLoans) {
        Material.borrow(idMaterial, idNumber);
        currentLoans++;
        saveCurrentLoans();
        System.out.println(name + " ha tomado prestado el material: " + idMaterial);
        return true;
    } else {
        System.out.println(name + " ha alcanzado el límite de préstamos permitidos.");
        return false;
    }
}

    @Override
    public boolean returnMaterial(String idMaterial) {
        if (currentLoans > 0) {
            currentLoans--;
            Material.returnMaterial(idMaterial, idNumber);
            saveCurrentLoans();
            System.out.println(name + " ha devuelto un material.");
            return true;
        } else {
            System.out.println(name + " no tiene materiales prestados.");
            return false;
        }
    }

    @Override
    public boolean renew(String idMaterial) {
        if (currentLoans > 0) {
            Material.renew(idMaterial, idNumber);
            System.out.println(name + " ha renovado el material: " + idMaterial);
            return true;
        } else {
            System.out.println(name + " no tiene materiales prestados para renovar.");
            return false;
        }
    }

    private void saveCurrentLoans() {
        File tempFile = new File("temp_personas.txt");
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(PERSON_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (Integer.parseInt(data[0]) == idNumber) {
                    found = true;
                    writer.write(String.join(",", data[0], data[1], data[2], data[3], String.valueOf(currentLoans++)));
                } else {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los préstamos actuales: " + e.getMessage());
        }

        if (found && new File(PERSON_FILE).delete()) {
            tempFile.renameTo(new File(PERSON_FILE));
        } else {
            System.out.println("No se pudo actualizar el archivo de personas.");
        }
    }
}
