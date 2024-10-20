package com.mycompany.librarytdea;

import java.util.HashMap;
import java.util.HashSet;

public class PersonTdeA {
    private int idNumber;
    private String name;
    private String lastName;
    private String rol;

    private static HashSet<Integer> idNumbers = new HashSet<>();
    private static HashMap<Integer, Integer> borrowedMaterialsCount = new HashMap<>();
    private static HashMap<Integer, String> roles = new HashMap<>();
    private static HashMap<Integer, Integer> renewalCounts = new HashMap<>(); // Contar renovaciones

    public PersonTdeA(int idNumber, String name, String lastName, String rol) {
        this.idNumber = idNumber;
        this.name = name;
        this.lastName = lastName;
        this.rol = rol;
        roles.put(idNumber, rol);
        renewalCounts.put(idNumber, 0);
    }

    public int getIdNumber() {
        return idNumber;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRol() {
        return rol;
    }

    public static boolean createPerson(int idNumber, String name, String lastName, String rol) {
        if (idNumbers.contains(idNumber)) {
            System.out.println("Ya existe una persona con esa cédula.");
            return false;
        } else if (name.length() >= 30) {
            System.out.println("El nombre ha superado el número de caracteres permitidos. Intenta de nuevo.");
            return false;
        } else if (lastName.length() >= 30) {
            System.out.println("El apellido ha superado el número de caracteres permitidos. Intenta de nuevo.");
            return false;
        }

        idNumbers.add(idNumber);
        borrowedMaterialsCount.put(idNumber, 0);
        roles.put(idNumber, rol);
        System.out.println("Usuario registrado correctamente.");
        return true;
    }

    public static void removePerson(HashSet<PersonTdeA> people, int idNumber) {
        for (PersonTdeA person : people) {
            if (person.idNumber == idNumber) {
                if (borrowedMaterialsCount.get(idNumber) > 0) {
                    System.out.println("No se puede eliminar a " + person.getName() + " " + person.getLastName() + " porque tiene materiales prestados.");
                } else {
                    people.remove(person);
                    idNumbers.remove(idNumber);
                    borrowedMaterialsCount.remove(idNumber);
                    roles.remove(idNumber);
                    System.out.println("Se ha eliminado a: " + person.getName() + " " + person.getLastName());
                }
                return;
            }
        }
        System.out.println("No existe una persona con la cédula ingresada.");
    }

    public static void incrementBorrowedMaterials(int idNumber) {
        borrowedMaterialsCount.put(idNumber, borrowedMaterialsCount.getOrDefault(idNumber, 0) + 1);
    }

    public static void decrementBorrowedMaterials(int idNumber) {
        if (borrowedMaterialsCount.containsKey(idNumber) && borrowedMaterialsCount.get(idNumber) > 0) {
            borrowedMaterialsCount.put(idNumber, borrowedMaterialsCount.get(idNumber) - 1);
        } else {
            System.out.println("No se puede devolver, la persona no tiene materiales prestados.");
        }
    }

    public static int getBorrowedMaterialsCount(int idNumber) {
        return borrowedMaterialsCount.getOrDefault(idNumber, 0);
    }

    public static String getRol(int idNumber) {
        return roles.get(idNumber);
    }

    public static void incrementRenewalCount(int idNumber) {
        int currentCount = renewalCounts.getOrDefault(idNumber, 0);
        renewalCounts.put(idNumber, currentCount + 1);
    }

    public static int getRenewalCount(int idNumber) {
        return renewalCounts.getOrDefault(idNumber, 0);
    }
}
