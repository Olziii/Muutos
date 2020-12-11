package com.example.androidprojekti;
/**
 * Tavoite-olio
 * @author Oliver Hamberg
 * @author Joona Nylander
 * @author Niklas Kukkonen
 * @since  11.12.2020
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
