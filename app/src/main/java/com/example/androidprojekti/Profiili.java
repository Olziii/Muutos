package com.example.androidprojekti;

/**
 * Profiili-olio
 * @author Oliver Hamberg
 * @author Joona Nylander
 * @author Niklas Kukkonen
 * @since  11.12.2020
 */
public class Profiili {
    private String nimi;
    private int ika;
    private int paino;
    private String sukupuoli;
    private int rasitustaso;

    public Profiili(){
        this.nimi = "NULL";
        this.ika = 0;
        this.paino = 0;
        this.sukupuoli = "NULL";
        this.rasitustaso = 0;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public Integer getIka() {
        return ika;
    }

    public void setIka(int ika) {
        this.ika = ika;
    }

    public Integer getPaino() {
        return paino;
    }

    public void setPaino(int paino) {
        this.paino = paino;
    }

    public String getSukupuoli() {
        return sukupuoli;
    }

    public void setSukupuoli(String sukupuoli) {
        this.sukupuoli = sukupuoli;
    }

    public Integer getRasitustaso() {
        return rasitustaso;
    }
    public void setRasitustaso(int rasitustaso) {
        this.rasitustaso = rasitustaso;
    }
}
