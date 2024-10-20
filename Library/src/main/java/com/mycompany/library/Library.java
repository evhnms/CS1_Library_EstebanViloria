package com.mycompany.library;

import java.util.Scanner;
import java.util.HashSet;
import java.util.Date;

public class Library {
    private static HashSet<Material> materials = new HashSet<>();
    private static HashSet<Person> people = new HashSet<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Person person = new Student(123, "Juan", "Pérez", "Estudiante");
        materials.add(new Material("MT12345", "Mago de Hoz", new Date(), 1));
        
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
                        System.out.println("Ingrese el ID del material (debe contener [2] letras y [5] números):");
                        String idMaterial = scanner.nextLine().toUpperCase();
                        System.out.println("Ingrese el título del material:");
                        String title = scanner.nextLine();
                        Date dateOfRegistration = new Date();
                        System.out.println("Ingrese la cantidad a registrar:");
                        int quantityRegistered = scanner.nextInt();
                        if (Material.createMaterial(idMaterial, title, dateOfRegistration, quantityRegistered)) {
                            materials.add(new Material(idMaterial, title, dateOfRegistration, quantityRegistered));
                        }
                        break;
                    case 2:
                        System.out.println("Ingrese la cédula del usuario:");
                        int idNumber = scanner.nextInt();
                        System.out.println("Nombre:");
                        scanner.nextLine();
                        String name = scanner.nextLine();
                        System.out.println("Apellido:");
                        String lastNames = scanner.nextLine();
                        System.out.println("Rol: ");
                        String rol = scanner.nextLine();
                        switch(rol.toUpperCase()){
                            case "ESTUDIANTE":
                                if (Person.createPerson(idNumber, name, lastNames, rol)) {
                                    people.add(new Student(idNumber, name, lastNames, rol));
                        }
                            case "DOCENTE":
                                if (Person.createPerson(idNumber, name, lastNames, rol)) {
                                    people.add(new Teacher(idNumber, name, lastNames, rol));
                        }
                            case "ADMINISTRATIVE":
                                if (Person.createPerson(idNumber, name, lastNames, rol)) {
                                    people.add(new Administrative(idNumber, name, lastNames, rol));
                                }}
                        break;
                    case 3:
                        System.out.println("Digite el número de cédula del usuario a eliminar");
                        int idNumberDelete = scanner.nextInt();
                        Person.removePerson(people,idNumberDelete);
                        break;
                    case 4:
                        System.out.println("Digite el código del material a prestar:");
                        String borrowMaterialCode = scanner.nextLine();
                        System.out.println("Escriba su número de cédula:");
                        int idNumberBorrow = scanner.nextInt();
                        Material.borrow(borrowMaterialCode, idNumberBorrow, materials);
                        new History(borrowMaterialCode, "Préstamo", idNumberBorrow);
                        break;
                    case 5:
                        System.out.println("Digite el código del material a renovar:");
                        String renewMaterialCode = scanner.nextLine();
                        System.out.println("Escriba su número de cédula:");
                        int idNumberRenew = scanner.nextInt();
                        Material.renew(renewMaterialCode, idNumberRenew, materials);
                        new History(renewMaterialCode, "Renovación", idNumberRenew);
                        break;
                    case 6:
                        System.out.println("Digite el código del material a devolver:");
                        String returnMaterialCode = scanner.nextLine();
                        System.out.println("Escriba su número de cédula:");
                        int idNumberReturn = scanner.nextInt();
                        Material.renew(returnMaterialCode, idNumberReturn, materials);
                        new History(returnMaterialCode,"Devolución",idNumberReturn);
                        break;
                    case 7:
                        System.out.println("Ingrese el código del material a aumentar registros: ");
                        String idNumberAddExistences = scanner.nextLine();
                        System.out.println("Ingrese los registros a añadir: ");
                        int addQuantityRegistered = scanner.nextInt();
                        Material.addMaterial(idNumberAddExistences, addQuantityRegistered, materials);
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
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
                scanner.nextLine();
            }
        }
        scanner.close();
    }
}
