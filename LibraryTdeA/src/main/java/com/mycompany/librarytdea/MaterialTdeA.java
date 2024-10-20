package com.mycompany.librarytdea;

import java.util.Date;
import java.util.HashSet;

public class MaterialTdeA {
    private String idMaterial;
    private String title;
    private Date dateOfRegistration;
    private int quantityRegistered;
    private int quantityAvailable;

    private static HashSet<String> idMaterials = new HashSet<>();

    public MaterialTdeA(String idMaterial, String title, Date dateOfRegistration, int quantityRegistered) {
        this.idMaterial = idMaterial;
        this.title = title;
        this.dateOfRegistration = dateOfRegistration;
        this.quantityRegistered = quantityRegistered;
        this.quantityAvailable = quantityRegistered;
    }

    public String getIdMaterial() {
        return idMaterial;
    }

    public String getTitle() {
        return title;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public int getQuantityRegistered() {
        return quantityRegistered;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public static boolean createMaterial(String idMaterial, String title, Date dateOfRegistration, int quantityRegistered) {
        if (idMaterials.contains(idMaterial)) {
            System.out.println("Ya existe el material en la base de datos");
            return false;
        } else if (!idMaterial.matches("^[A-Z]{2}\\d{5}$")) {
            System.out.println("El código no cumple con los parámetros requeridos");
            return false;
        }

        idMaterials.add(idMaterial.toUpperCase());
        System.out.println("Se ha creado exitosamente el material:" + title);
        return true;
    }

    public static void borrow(String idMaterial, int idNumber, HashSet<MaterialTdeA> materials) {
        for (MaterialTdeA material : materials) {
            if (material.getIdMaterial().equals(idMaterial)); {
                if (material.quantityAvailable > 0) {
                    material.quantityAvailable--;
                    PersonTdeA.incrementBorrowedMaterials(idNumber);
                    System.out.println("Material prestado a la cédula: " + idNumber);
                } else {
                    System.out.println("No hay materiales disponibles para prestar.");
                }
                return;
            }
        }
        System.out.println("El material con ID: " + idMaterial + " no existe.");
    }

    public static void returnMaterial(String idMaterial, int idNumber, HashSet<MaterialTdeA> materials) {
        for (MaterialTdeA material : materials) {
            if (material.getIdMaterial().equals(idMaterial)) {
                if (PersonTdeA.getBorrowedMaterialsCount(idNumber) > 0) {
                    material.quantityAvailable++;
                    PersonTdeA.decrementBorrowedMaterials(idNumber);
                    System.out.println("Material devuelto por la cédula: " + idNumber);
                } else {
                    System.out.println("No se puede devolver, la cédula " + idNumber + " no tiene materiales prestados.");
                }
                return;
            }
        }
        System.out.println("El material con ID: " + idMaterial + " no existe.");
    }

    public static void renew(String idMaterial, int idNumber, HashSet<MaterialTdeA> materials) {
        for (MaterialTdeA material : materials) {
            if (material.getIdMaterial().equals(idMaterial)) {
                if (PersonTdeA.getBorrowedMaterialsCount(idNumber) > 0) {
                    PersonTdeA.incrementRenewalCount(idNumber);
                    System.out.println("Material renovado por la cédula: " + idNumber);
                } else {
                    System.out.println("No se puede renovar, la cédula " + idNumber + " no tiene materiales prestados.");
                }
                return;
            }
        }
        System.out.println("El material con ID: " + idMaterial + " no existe.");
    }

    public static void addMaterial(String idMaterial, int addQuantityRegistered, HashSet<MaterialTdeA> materials) {
        for (MaterialTdeA material : materials) {
            if (material.getIdMaterial().equals(idMaterial)) {
                material.quantityRegistered += addQuantityRegistered;
                material.quantityAvailable += addQuantityRegistered;
                System.out.println("Se ha aumentado la cantidad registrada de: " + material.getTitle());
                System.out.println("Cantidad registrada actual: " + material.quantityRegistered);
                return;
            }
        }
        System.out.println("El material con ID: " + idMaterial + " no existe.");
    }
}
