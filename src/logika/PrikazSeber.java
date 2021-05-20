package logika;


import java.io.Serializable;
import java.util.ArrayList;

public class PrikazSeber implements IPrikaz, Serializable {
    private static final String NAZEV = "seber";
    private ArrayList<Vec> veci;
    private Batoh batoh;
    private HerniPlan herniPlan;


    public PrikazSeber(HerniPlan herniPlan, Batoh batoh) {
        this.batoh = batoh;
        this.herniPlan = herniPlan;
    }

    @Override
    public String provedPrikaz(String[] parametry) {
        veci = herniPlan.getAktualniProstor().getVeciProstoru();
        if (parametry.length == 0) {
            return Barvy.ANSI_BLUE + "Co mám sebrat? Musíš zadat jméno věci" + Barvy.ANSI_RESET;
        }

        for (Vec vec : veci) {
            if (vec.getNazev().equals(parametry[0]) || vec.getNazev().equals(parametry[0] + (parametry.length > 1 ? " " + parametry[1] : ""))) { //jestli se název věci v prostoru rovná tomu, co chceme
                if (vec.isPrenositelna()) {
                    if ((batoh.getAktualniPocetVeci() < Batoh.KAPACITA) || (batoh.getAktualniPocetVeci() == Batoh.KAPACITA && ((batoh.obsahujeVec(ZJ.ZLATO) && parametry[0].equals(ZJ.MEC) && herniPlan.getAktualniProstor().jeVecVProstoru(ZJ.MEC)) || (batoh.obsahujeVec(ZJ.MEC) && parametry[0].equals(ZJ.ZLATO) && herniPlan.getAktualniProstor().jeVecVProstoru(ZJ.ZLATO))))) {
                        if (batoh.obsahujeVec(ZJ.ZLATO) && parametry[0].equals(ZJ.MEC) && herniPlan.getAktualniProstor().jeVecVProstoru(ZJ.MEC)) { //když se snažíme směnit zlato za meč, a všechny podmínky jsou splněny (máme zlato v batohu, a meč je v prostoru)
                            veci.add(batoh.vratVec(ZJ.ZLATO));
                            batoh.pridejDoBatohu(vec); //a přidej ho do batohu
                            batoh.odeberZBatohu(ZJ.ZLATO);     //z batohu odebereme zlato
                            veci.remove(vec); //z věcí z prostoru odeber meč
                            return Barvy.ANSI_BLUE + "Zlato směněno za meč!" + Barvy.ANSI_RESET;
                        } else if (!batoh.obsahujeVec(ZJ.ZLATO) && parametry[0].equals(ZJ.MEC) && herniPlan.getAktualniProstor().jeVecVProstoru(ZJ.MEC)) { //jestli se snažíme směnit zlato za meč, ale nemáme zlato
                            return Barvy.ANSI_BLUE + "K získání meče potřebuješ zlato" + Barvy.ANSI_RESET;
                        } else if (batoh.obsahujeVec(ZJ.MEC) && parametry[0].equals(ZJ.ZLATO) && herniPlan.getAktualniProstor().jeVecVProstoru(ZJ.ZLATO)) { //když se naopak snažíme změnit meč za zlato, a všechny podmínky jsou splněny
                            veci.add(batoh.vratVec(ZJ.MEC)); //přidej do prostoru meč
                            batoh.odeberZBatohu(ZJ.MEC);
                            veci.remove(vec); //odeber z prostoru zlato
                            batoh.pridejDoBatohu(vec); //přidej do batohu zlato
                            return Barvy.ANSI_BLUE + "Meč směněn za zlato!" + Barvy.ANSI_RESET;
                        } else {
                            batoh.pridejDoBatohu(vec);
                            veci.remove(vec);
                            return Barvy.ANSI_BLUE + "Věc " + vec.getNazev() + Barvy.ANSI_BLUE + " sebrána!" + Barvy.ANSI_RESET;
                        }
                    } else {
                        return Barvy.ANSI_BLUE + "Batoh je plný, je třeba něco vyhodit." + Barvy.ANSI_RESET;
                    }
                } else {
                    return Barvy.ANSI_BLUE + "Věc " + vec.getNazev() + " není přenositelná" + Barvy.ANSI_RESET;
                }
            }
        }
        return Barvy.ANSI_BLUE + "Chyba v zadání věci" + Barvy.ANSI_RESET;
    }


    @Override
    public String getNazev() {
        return NAZEV;
    }
}
