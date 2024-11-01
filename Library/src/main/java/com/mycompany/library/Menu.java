package com.mycompany.library;

import java.util.Scanner;
import java.util.Date;

public abstract class Menu {
    private static Scanner scanner = new Scanner(System.in);

    public abstract void displayMenu();

    protected void displayMainMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Registrar material");
            System.out.println("2. Registrar persona");
            System.out.println("3. Eliminar persona");
            System.out.println("4. Registrar préstamo");
            System.out.println("5. Registrar renovación");
            System.out.println("6. Registrar devolución");
            System.out.println("7. Incrementar cantidad registrada de un material");
            System.out.println("8. Consultar historial de la biblioteca");
            System.out.println("9. Salir");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        registerMaterial();
                        break;
                    case 2:
                        registerPerson();
                        break;
                    case 3:
                        deletePerson();
                        break;
                    case 4:
                        registerBorrow();
                        break;
                    case 5:
                        registerRenewal();
                        break;
                    case 6:
                        registerReturn();
                        break;
                    case 7:
                        incrementMaterialQuantity();
                        break;
                    case 8:
                        History.showHistory();
                        break;
                    case 9:
                        exit = true;
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private void registerMaterial() {
        System.out.println("Ingrese el ID del material (debe contener [2] letras y [5] números):");
        String idMaterial = scanner.nextLine().toUpperCase();
        System.out.println("Ingrese el título del material:");
        String title = scanner.nextLine();
        Date dateOfRegistration = new Date();
        System.out.println("Ingrese la cantidad a registrar:");
        int quantityRegistered = scanner.nextInt();
        Material.createMaterial(idMaterial, title, dateOfRegistration, quantityRegistered);
    }

    private void registerPerson() {
        System.out.println("Ingrese la cédula del usuario (número entero):");
        int idNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Nombre:");
        String name = scanner.nextLine();
        System.out.println("Apellido:");
        String lastName = scanner.nextLine();
        System.out.println("Rol (Estudiante, Docente, Administrativo): ");
        String rol = scanner.nextLine().toUpperCase();

        switch (rol) {
            case "ESTUDIANTE":
                Student.createPerson(idNumber, name, lastName, rol);
                break;
            case "DOCENTE":
                Teacher.createPerson(idNumber, name, lastName, rol);
                break;
            case "ADMINISTRATIVO":
                Administrative.createPerson(idNumber, name, lastName, rol);
                break;
            default:
                System.out.println("Rol no válido. Por favor, ingrese Estudiante, Docente o Administrativo.");
                break;
        }
    }

    private void deletePerson() {
        System.out.println("Digite el número de cédula del usuario a eliminar");
        int idNumberDelete = scanner.nextInt();
        Person.removePerson(idNumberDelete);
    }

    private void registerBorrow() {
        System.out.println("Digite el código del material a prestar:");
        String borrowMaterialCode = scanner.nextLine();
        System.out.println("Escriba su número de cédula:");
        int idNumberBorrow = scanner.nextInt();
        Person borrower = Person.getPersonById(idNumberBorrow);
        if (!Material.exists(borrowMaterialCode)) {
            System.out.println("El material con código " + borrowMaterialCode + " no existe.");
            return;
        }
        if (borrower != null) {
            if (borrower instanceof Student) {
                Student student = (Student) borrower;
                boolean success = student.borrowMaterial(borrowMaterialCode);
                if (success) {
                    new History(borrowMaterialCode, "Préstamo", idNumberBorrow);
                }
            } else if (borrower instanceof Teacher) {
                Teacher teacher = (Teacher) borrower;
                boolean success = teacher.borrowMaterial(borrowMaterialCode);
                if (success) {
                    new History(borrowMaterialCode, "Préstamo", idNumberBorrow);
                }
            } else if (borrower instanceof Administrative) {
                Administrative administrative = (Administrative) borrower;
                boolean success = administrative.borrowMaterial(borrowMaterialCode);
                if (success) {
                    new History(borrowMaterialCode, "Préstamo", idNumberBorrow);
                }
            } else {
                System.out.println("El rol de la persona no permite realizar préstamos.");
            }
        } else {
            System.out.println("La persona no está registrada.");
        }
    }

    private void registerRenewal() {
        System.out.println("Digite el código del material a renovar:");
        String renewMaterialCode = scanner.nextLine();
        System.out.println("Escriba su número de cédula:");
        int idNumberRenew = scanner.nextInt();
        Person renewer = Person.getPersonById(idNumberRenew);
        if (renewMaterialCode != null && Material.exists(renewMaterialCode) && renewer != null) {
            if (renewer instanceof Student) {
                Student student = (Student) renewer;
                boolean success = student.renew(renewMaterialCode);
                if (success) {
                    new History(renewMaterialCode, "Renovación", idNumberRenew);
                }
            } else if (renewer instanceof Teacher) {
                Teacher teacher = (Teacher) renewer;
                boolean success = teacher.renew(renewMaterialCode);
                if (success) {
                    new History(renewMaterialCode, "Renovación", idNumberRenew);
                }
            } else if (renewer instanceof Administrative) {
                Administrative administrative = (Administrative) renewer;
                boolean success = administrative.renew(renewMaterialCode);
                if (success) {
                    new History(renewMaterialCode, "Renovación", idNumberRenew);
                }
            } else {
                System.out.println("El rol de la persona no permite realizar renovaciones.");
            }
        } else {
            System.out.println("El material no existe o la persona no está registrada.");
        }
    }

    private void registerReturn() {
        System.out.println("Digite el código del material a devolver:");
        String returnMaterialCode = scanner.nextLine();
        System.out.println("Escriba su número de cédula:");
        int idNumberReturn = scanner.nextInt();
        Person returner = Person.getPersonById(idNumberReturn);
        if (returnMaterialCode != null && Material.exists(returnMaterialCode) && returner != null) {
            if (returner instanceof Student) {
                Student student = (Student) returner;
                boolean success = student.returnMaterial(returnMaterialCode);
                if (success) {
                    new History(returnMaterialCode, "Devolución", idNumberReturn);
                }
            } else if (returner instanceof Teacher) {
                Teacher teacher = (Teacher) returner;
                boolean success = teacher.returnMaterial(returnMaterialCode);
                if (success) {
                    new History(returnMaterialCode, "Devolución", idNumberReturn);
                }
            } else if (returner instanceof Administrative) {
                Administrative administrative = (Administrative) returner;
                boolean success = administrative.returnMaterial(returnMaterialCode);
                if (success) {
                    new History(returnMaterialCode, "Devolución", idNumberReturn);
                }
            } else {
                System.out.println("El rol de la persona no permite realizar devoluciones.");
            }
        } else {
            System.out.println("El material no existe o la persona no está registrada.");
        }
    }

    private void incrementMaterialQuantity() {
        System.out.println("Ingrese el código del material a aumentar registros: ");
        String idNumberAddExistences = scanner.nextLine();
        System.out.println("Ingrese los registros a añadir: ");
        int addQuantityRegistered = scanner.nextInt();
        Material.addMaterial(idNumberAddExistences, addQuantityRegistered);
    }
}
