package com.mycompany.library;

import java.io.*;

public abstract class Person {
    protected int idNumber;
    protected String name;
    protected String lastName;
    protected String role;
    protected int currentLoans;

    public static final String PERSON_FILE = "personas.txt";
    public static final String LOANS_FILE = "prestamos.txt";

    public Person(int idNumber, String name, String lastName, String role, int currentLoans) {
        this.idNumber = idNumber;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.currentLoans = currentLoans;
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

    public String getRole() {
        return role;
    }

    public int getCurrentLoans() {
        return currentLoans;
    }

    public void setCurrentLoans(int currentLoans) {
        this.currentLoans = currentLoans;
        updatePersonInFile();
    }

    public abstract int getMaxLoans();

    public static boolean createPerson(int idNumber, String name, String lastName, String role) {
        if (name.length() >= 30 || lastName.length() >= 30) {
            System.out.println("El nombre o apellido no debe superar los 30 caracteres.");
            return false;
        }

        if (isExistingPerson(idNumber)) {
            System.out.println("Ya existe una persona con esa cédula.");
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PERSON_FILE, true))) {
            writer.write(String.join(",", String.valueOf(idNumber), name, lastName, role, "0") + "\n");
            System.out.println("Usuario registrado correctamente.");
            return true;
        } catch (IOException e) {
            System.out.println("Error al guardar la persona: " + e.getMessage());
            return false;
        }
    }

    private static boolean isExistingPerson(int idNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PERSON_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (Integer.parseInt(line.split(",")[0]) == idNumber) return true;
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return false;
    }

    public static void removePerson(int idNumber) {
        if (hasActiveLoans(idNumber)) {
            System.out.println("No se puede eliminar a la persona con ID " + idNumber + " porque tiene préstamos activos.");
            return;
        }

        File tempFile = new File("temp_personas.txt");
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(PERSON_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (Integer.parseInt(data[0]) == idNumber) {
                    found = true;
                    System.out.println("Se ha eliminado a: " + data[1] + " " + data[2]);
                } else {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al eliminar la persona: " + e.getMessage());
        }

        if (found && new File(PERSON_FILE).delete()) {
            tempFile.renameTo(new File(PERSON_FILE));
        } else {
            System.out.println("No existe una persona con la cédula ingresada o no se pudo eliminar el archivo.");
        }
    }

    private static boolean hasActiveLoans(int idNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOANS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (Integer.parseInt(line.split(",")[0]) == idNumber) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al verificar préstamos activos: " + e.getMessage());
        }
        return false;
    }

    public static Person getPersonById(int idNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PERSON_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (Integer.parseInt(data[0]) == idNumber) {
                    return switch (data[3].toLowerCase()) {
                        case "estudiante" -> new Student(idNumber, data[1], data[2], data[3], Integer.parseInt(data[4]));
                        case "docente" -> new Teacher(idNumber, data[1], data[2], data[3], Integer.parseInt(data[4]));
                        case "administrativo" -> new Administrative(idNumber, data[1], data[2], data[3], Integer.parseInt(data[4]));
                        default -> null;
                    };
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return null;
    }

    private void updatePersonInFile() {
        File tempFile = new File("temp_personas.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(PERSON_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (Integer.parseInt(data[0]) == this.idNumber) {
                    writer.write(String.join(",", String.valueOf(this.idNumber), this.name, this.lastName, this.role, String.valueOf(this.currentLoans)) + "\n");
                } else {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar la persona: " + e.getMessage());
        }

        if (tempFile.renameTo(new File(PERSON_FILE))) {
            System.out.println("Registro actualizado.");
        } else {
            System.out.println("No se pudo actualizar el registro.");
        }
    }
}
