package com.example.androidprojekti;
/**
 * Tavoite-olio
 */
public class Tavoite {
    private String tavoite;

    /**
     * Tavoite oliolla on vain tekstinä tieto tavoitteesta
     * @param tavoite
     */
    public Tavoite(String tavoite) {
        this.tavoite = tavoite;
    }

    public String getTavoite() {
        return tavoite;
    }

    public void setTavoite(String tavoite) {
        this.tavoite = tavoite;
    }

    @Override
    public String toString() {
        return tavoite;
    }
}
