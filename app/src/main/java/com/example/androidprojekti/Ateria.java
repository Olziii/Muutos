package com.example.androidprojekti;

/**
 * Ateria olio
 * @author Oliver Hamberg
 * @author Joona Nylander
 * @author Niklas Kukkonen
 * @since  12.11.2020
 */
public class Ateria {
    private final String ateria;
    private final int ateriankalorit;
    private final String juoma;
    private final int juomankalorit;
    private final int kalorityhteensa;

    /**
     * @param ateria  Ruoan nimi
     * @param ateriankalorit  Ruoan sisältämät kalorit
     * @param juoma  Juoman nimi
     * @param juomankalorit  Juoman sisältämät kalorit
     */

    //Ateria olio
    public Ateria(String ateria, int ateriankalorit, String juoma, int juomankalorit) {
        this.ateria = ateria;
        this.ateriankalorit = ateriankalorit;
        this.juoma = juoma;
        this.juomankalorit = juomankalorit;
        this.kalorityhteensa = this.ateriankalorit + this.juomankalorit;
    }

    public String getAteria() {
        return ateria;
    }

    public int getAteriankalorit() {
        return ateriankalorit;
    }

    public String getJuoma() {
        return juoma;
    }

    public int getJuomankalorit() {
        return juomankalorit;
    }

    public int getKalorityhteensa() {
        return kalorityhteensa;
    }

    @Override
    public String toString() {
        return "Ruokalaji: " + ateria +  "\nRuoankalorit: " + ateriankalorit + "\nJuoma: " + juoma + "\nJuomankalorit: " + juomankalorit + "\nKalorit yhteensä: " + kalorityhteensa;
    }
}
