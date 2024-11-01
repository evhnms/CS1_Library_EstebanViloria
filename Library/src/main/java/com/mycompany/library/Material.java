package com.mycompany.library;

import java.io.*;
import java.util.Date;
import java.util.HashMap;

public class Material {
    private String idMaterial;
    private String title;
    private Date dateOfRegistration;
    private int quantityRegistered;
    private int quantityAvailable;

    private static final String MATERIAL_FILE = "materiales.txt";
    private static final String LOANS_FILE = "prestamos.txt";
    private static final int MAX_RENEWALS = 3;
    private static final HashMap<Integer, HashMap<String, Integer>> renewalRecords = new HashMap<>();

    public Material(String idMaterial, String title, Date dateOfRegistration, int quantityRegistered) {
        this.idMaterial = idMaterial.toUpperCase();
        this.title = title;
        this.dateOfRegistration = dateOfRegistration;
        this.quantityRegistered = quantityRegistered;
        this.quantityAvailable = quantityRegistered;
    }

    public static boolean exists(String idMaterial) {
        return searchInFile(MATERIAL_FILE, idMaterial) != null;
    }

    public static boolean createMaterial(String idMaterial, String title, Date dateOfRegistration, int quantityRegistered) {
        if (!idMaterial.matches("^[A-Z]{2}\\d{5}$")) {
            System.out.println("Código no válido");
            return false;
        }

        if (exists(idMaterial)) {
            System.out.println("Material ya registrado");
            return false;
        }

        return writeToFile(MATERIAL_FILE, String.join(",", idMaterial.toUpperCase(), title, String.valueOf(dateOfRegistration.getTime()), 
                String.valueOf(quantityRegistered), String.valueOf(quantityRegistered)), "Material creado: " + title);
    }

    public static void borrow(String idMaterial, int idNumber) {
        String[] materialData = searchInFile(MATERIAL_FILE, idMaterial);
        if (materialData != null && Integer.parseInt(materialData[4]) > 0) {
            updateMaterialFile(idMaterial, -1);
            writeToFile(LOANS_FILE, idNumber + "," + idMaterial, "Material prestado a: " + idNumber);
        } else {
            System.out.println(materialData == null ? "Material no encontrado." : "No disponible.");
        }
    }

    public static void returnMaterial(String idMaterial, int idNumber) {
        if (searchInFile(MATERIAL_FILE, idMaterial) != null) {
            updateMaterialFile(idMaterial, 1);
            removeLoanEntry(idMaterial, idNumber);
        } else {
            System.out.println("Material no encontrado.");
        }
    }

    public static void renew(String idMaterial, int idNumber) {
        renewalRecords.putIfAbsent(idNumber, new HashMap<>());
        int currentRenewals = renewalRecords.get(idNumber).getOrDefault(idMaterial, 0);

        if (currentRenewals < MAX_RENEWALS) {
            renewalRecords.get(idNumber).put(idMaterial, currentRenewals + 1);
            System.out.println("Material renovado para: " + idNumber);
        } else {
            System.out.println("Límite de renovaciones alcanzado.");
        }
    }

    public static void addMaterial(String idMaterial, int addQuantity) {
        if (searchInFile(MATERIAL_FILE, idMaterial) != null) {
            updateMaterialFile(idMaterial, addQuantity);
            System.out.println("Cantidad aumentada para: " + idMaterial);
        } else {
            System.out.println("Material no encontrado.");
        }
    }

    private static String[] searchInFile(String fileName, String idMaterial) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.lines()
                    .map(line -> line.split(","))
                    .filter(data -> data[0].equalsIgnoreCase(idMaterial))
                    .findFirst().orElse(null);
        } catch (IOException e) {
            System.out.println("Error de lectura: " + e.getMessage());
            return null;
        }
    }

    private static void updateMaterialFile(String idMaterial, int quantityChange) {
        try (BufferedReader reader = new BufferedReader(new FileReader(MATERIAL_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter("temp_materiales.txt"))) {

            reader.lines().forEach(line -> {
                String[] data = line.split(",");
                if (data[0].equalsIgnoreCase(idMaterial)) {
                    data[4] = String.valueOf(Integer.parseInt(data[4]) + quantityChange);
                }
                try {
                    writer.write(String.join(",", data) + "\n");
                } catch (IOException e) {
                    System.out.println("Error al escribir: " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.out.println("Error al actualizar archivo: " + e.getMessage());
        }
        new File(MATERIAL_FILE).delete();
        new File("temp_materiales.txt").renameTo(new File(MATERIAL_FILE));
    }

    private static boolean writeToFile(String fileName, String content, String successMessage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content);
            writer.newLine();
            System.out.println(successMessage);
            return true;
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
            return false;
        }
    }

    private static void removeLoanEntry(String idMaterial, int idNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOANS_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter("temp_prestamos.txt"))) {

            reader.lines().forEach(line -> {
                String[] data = line.split(",");
                if (!(Integer.parseInt(data[0]) == idNumber && data[1].equalsIgnoreCase(idMaterial))) {
                    try {
                        writer.write(line + "\n");
                    } catch (IOException e) {
                        System.out.println("Error al escribir: " + e.getMessage());
                    }
                }
            });
        } catch (IOException e) {
            System.out.println("Error al eliminar préstamo: " + e.getMessage());
        }
        new File(LOANS_FILE).delete();
        new File("temp_prestamos.txt").renameTo(new File(LOANS_FILE));
    }
}
