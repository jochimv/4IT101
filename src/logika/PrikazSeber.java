package logika;


import java.util.ArrayList;
import java.util.List;

public class PrikazSeber implements IPrikaz {
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
                    if ((batoh.getAktualniPocetVeci() < Batoh.KAPACITA) || (batoh.getAktualniPocetVeci() == Batoh.KAPACITA && ((batoh.obsahujeVec("zlato") && parametry[0].equals("meč") && herniPlan.getAktualniProstor().jeVecVProstoru("meč")) || (batoh.obsahujeVec("meč") && parametry[0].equals("zlato") && herniPlan.getAktualniProstor().jeVecVProstoru("zlato"))))) {
                        if (batoh.obsahujeVec("zlato") && parametry[0].equals("meč") && herniPlan.getAktualniProstor().jeVecVProstoru("meč")) { //když se snažíme směnit zlato za meč, a všechny podmínky jsou splněny (máme zlato v batohu, a meč je v prostoru)
                            veci.add(batoh.vratVec("zlato"));  //do věcí v prostoru vlož zlato (které víme, že máme)
                            batoh.pridejDoBatohu(vec); //a přidej ho do batohu
                            batoh.odeberZBatohu("zlato");     //z batohu odebereme zlato
                            veci.remove(vec); //z věcí z prostoru odeber meč
                            return Barvy.ANSI_BLUE + "Zlato směněno za meč!" + Barvy.ANSI_RESET;
                        } else if (!batoh.obsahujeVec("zlato") && parametry[0].equals("meč") && herniPlan.getAktualniProstor().jeVecVProstoru("meč")){ //jestli se snažíme směnit zlato za meč, ale nemáme zlato
                         return Barvy.ANSI_BLUE + "K získání meče potřebuješ zlato" + Barvy.ANSI_RESET;
                        }

                        else if (batoh.obsahujeVec("meč") && parametry[0].equals("zlato") && herniPlan.getAktualniProstor().jeVecVProstoru("zlato")) { //když se naopak snažíme změnit meč za zlato, a všechny podmínky jsou splněny
                            batoh.pridejDoBatohu(vec);
                            veci.add(batoh.vratVec("meč"));
                            batoh.odeberZBatohu("meč");
                            veci.remove(vec);
                            return Barvy.ANSI_BLUE + "Meč směněn za zlato!" + Barvy.ANSI_RESET;
                        } else {
                            batoh.pridejDoBatohu(vec);
                            veci.remove(vec);
                            return Barvy.ANSI_BLUE +"věc " + vec.getNazev() + " sebrána!" + Barvy.ANSI_RESET;
                        }
                    } else {
                        return Barvy.ANSI_BLUE + "Batoh je plný, je třeba něco vyhodit." + Barvy.ANSI_RESET;
                    }
                } else {
                    return Barvy.ANSI_BLUE + "věc " + vec.getNazev() + " není přenositelná" + Barvy.ANSI_RESET;
                }
            }
        }
        return Barvy.ANSI_BLUE +"chyba v zadání věci" + Barvy.ANSI_RESET;
    }


    @Override
    public String getNazev() {
        return NAZEV;
    }
}
