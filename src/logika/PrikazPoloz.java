package logika;

import java.io.Serializable;
import java.util.ArrayList;

public class PrikazPoloz implements IPrikaz, Serializable {
    private static final String NAZEV = "polož";
    private ArrayList<Vec> veci;
    private Batoh batoh;
    private HerniPlan herniPlan;


    public PrikazPoloz(HerniPlan herniPlan, Batoh batoh) {
        this.batoh = batoh;
        this.herniPlan = herniPlan;
    }

    @Override
    public String provedPrikaz(String[] parametry) {
        veci = herniPlan.getAktualniProstor().getVeciProstoru();
        if (parametry.length == 0) {
            return Barvy.ANSI_BLUE + "Co vyhodit? Musíš zadat věc" + Barvy.ANSI_RESET;
        } else if (batoh.getAktualniPocetVeci() == 0) {
            return Barvy.ANSI_BLUE + "Batoh je prázdný, nelze nic vyhodit" + Barvy.ANSI_RESET;
        }
        for (Vec vec : batoh.getVeci()) {
            if (vec.getNazev().equals(parametry[0]) || vec.getNazev().equals(parametry[0] + (parametry.length > 1 ? " " + parametry[1] : ""))) {
                if (vec.isPrenositelna()) {
                    batoh.odeberZBatohu(vec);
                    veci.add(vec);
                    return Barvy.ANSI_BLUE + "Věc " + vec.getNazev() + Barvy.ANSI_BLUE + " odebrána z batohu\n" + Barvy.ANSI_RESET + herniPlan.getAktualniProstor().dlouhyPopis() ;
                } else {
                    return Barvy.ANSI_BLUE + vec.getNazev() + " není přenostitelná\n" + Barvy.ANSI_RESET + herniPlan.getAktualniProstor().dlouhyPopis();
                }
            }
        }
        return Barvy.ANSI_BLUE + parametry[0] + Barvy.ANSI_BLUE + " nenalezeno v batohu\n" + Barvy.ANSI_RESET + herniPlan.getAktualniProstor().dlouhyPopis() ;
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
