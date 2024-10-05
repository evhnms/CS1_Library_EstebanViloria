package com.mycompany.library;

import java.util.ArrayList;
import java.util.Date;

public class History {
    private String materialId;
    private String movementType;
    private int personId;
    private Date movementDate;

    private static ArrayList<History> history = new ArrayList<>();

    public History(String materialId, String movementType, int personId) {
        this.materialId = materialId;
        this.movementType = movementType;
        this.personId = personId;
        this.movementDate = new Date();
        history.add(this);
    }

    public String getMaterialId() {
        return materialId;
    }

    public String getMovementType() {
        return movementType;
    }

    public int getPersonId() {
        return personId;
    }

    public Date getMovementDate() {
        return movementDate;
    }

    public static void showHistory() {
        for (History movement : history) {
            System.out.println("Material ID: " + movement.getMaterialId() +
                    ", Tipo de Movimiento: " + movement.getMovementType() +
                    ", Cédula: " + movement.getPersonId() +
                    ", Fecha: " + movement.getMovementDate());
        }
    }
}
