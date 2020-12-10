package com.example.androidprojekti;

/**
 * UrheiluSuoritus-olio
 */
public class UrheiluSuoritus {
    private final String nimi;
    private final int pituus;
    private final int rasitustaso;
    private final int paino;
    private final double kalorikulutus;

    /**
     * @param nimi Urheilulaji
     * @param pituus Pituus minuuteissa
     * @param rasitustaso Rasitustaso 1-10
     */
    public UrheiluSuoritus(String nimi, int pituus, int rasitustaso) {
        this.nimi = nimi;
        this.pituus = pituus;
        this.rasitustaso = rasitustaso;

        //Rasitustaso & paino haetaan profiilista ja kalorikulutus lasketaan niiden avulla.
        this.paino = ProfileHolder.getInstance().getProfile().getPaino();
        this.kalorikulutus = (int)this.pituus * (this.rasitustaso * 3.5 * this.paino)/200;
    }

    public double getKalorikulutus() {
        return kalorikulutus;
    }

    @Override
    public String toString() {
        return "Urheilulaji: " + nimi + "\nSuorituksen pituus minuutteina: " + pituus + "\nRasitustaso: " + rasitustaso + "\nKalorikulutus: "  + kalorikulutus + "kcal\n";
    }
}
