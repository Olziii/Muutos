package com.example.androidprojekti;

import android.os.Build;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Luokka, jota voidaan hyödynää aikaan liittyvissä toiminnoissa
 * @author Oliver Hamberg
 * @author Joona Nylander
 * @author Niklas Kukkonen
 * @since  11.12.2020
 */
public class Aika {
    /**
     * Asettaa muodosteluksi Suomen kielen
     * @return Päivämäärä
     */
    public static String getAika(){
        Locale.setDefault(new Locale("fi","FI"));
        Date aikaNyt = Calendar.getInstance().getTime();
        return DateFormat.getDateInstance().format(aikaNyt);
    }

    /**
     * Hakee tämän hetkisen päivän
     * @return Monennesko päivä tänään on
     */
    public static int getVuodenPaiva() {
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }
}
