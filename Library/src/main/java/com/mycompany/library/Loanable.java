package com.mycompany.library;

public interface Loanable {
    boolean borrowMaterial(String idMaterial);
    boolean returnMaterial(String idMaterial);
    boolean renew(String idMaterial);
}
